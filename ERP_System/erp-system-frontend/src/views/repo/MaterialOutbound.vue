<template>
  <div class="container">
    <div class="search-box">
      <el-input placeholder="请输入原料编号" v-model="searchCriteria.id"/>
      <el-input placeholder="请输入原料名称" v-model="searchCriteria.name"/>
      <div class="search-buttons">
        <el-button type="primary" round @click="search">查询</el-button>
        <el-button type="warning" round @click="reset">重置</el-button>
      </div>
    </div>

    <div class="table-container">
      <div class="table-wrapper">
        <el-table v-if="tableData.items.length > 0" :data="tableData.items" style="width: 100%" class="custom-table">
          <el-table-column v-for="column in columns" :key="column.prop" :prop="column.prop" :label="column.label" :align="column.align"/>
          <el-table-column label="操作" align="center">
            <template #default="scope">
              <el-button size="small" type="primary" @click="outbound(scope.row)">出库</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div v-else class="no-data">暂无数据</div>
      </div>
      <div class="pagination-box">
        <el-pagination background layout="prev, pager, next" :total="totalItems" :page-size="pageSize" @current-change="handlePageChange"/>
      </div>
    </div>

    <el-dialog v-model="dialogVisible" title="出库" class="custom-dialog" :align-center>
      <div class="dialog-body">
        <el-input v-model="outboundQuantity" type="number" placeholder="请输入出库数量"/>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="success" @click="confirmOutbound">确认</el-button>
        <el-button type="danger" @click="dialogVisible = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import {ElDialog, ElMessage} from 'element-plus';
import axios from 'axios';

const tableData = reactive({
  items: []
});
const columns = [
  { prop: 'id', label: '原料编号', align: 'center' },
  { prop: 'name', label: '原料名称', align: 'center' },
  { prop: 'price', label: '原料价格（元/吨）', align: 'center' },
  { prop: 'inventory', label: '原料库存（吨）', align: 'center' },
];
const employee = JSON.parse(localStorage.getItem('user') || '{}');
const searchCriteria = reactive({
  id: '',
  name: '',
});

const loadData = (page = 1) => {
  if (employee.position === '部员') {
    searchCriteria.repoEmployeeId = employee.id;
  }
  axios.post('/api/repo/searchMaterial', searchCriteria)
      .then(response => {
        const res = response.data;
        tableData.items = res.data.map(item => ({
          id: item.id,
          name: item.name,
          price: item.price,
          inventory: item.inventory
        }));
      })
      .catch(error => {
        ElMessage.error("加载数据失败");
        console.error('Error loading data:', error);
      });
};

const search = () => {
  loadData();
};

const dialogVisible = ref(false);
const selectedId = ref(0);
const outboundQuantity = ref(0);

const outbound = (row) => {
  selectedId.value = row.id;
  dialogVisible.value = true;
};

const confirmOutbound = () => {
  const requestData = {
    id: selectedId.value,
    quantity: outboundQuantity.value
  };
  axios.post('/api/repo/materialOutbound', requestData)
      .then(response => {
        ElMessage.success("出库成功");
        dialogVisible.value = false;
        loadData();
      })
      .catch(error => {
        ElMessage.error("出库失败");
        console.error('Error during outbound:', error);
      });
};

const reset = () => {
  searchCriteria.id = '';
  searchCriteria.name = '';
  loadData();
};

const totalItems = ref(1000);
const pageSize = ref(10);
const handlePageChange = (page) => {
  loadData(page);
};

onMounted(() => {
  loadData();
});
</script>

<style lang="scss" src="@/styles/table.css"></style>
