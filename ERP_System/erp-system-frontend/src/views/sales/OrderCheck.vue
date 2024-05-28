<template>
  <div class="container">
    <div class="search-box">
      <el-input placeholder="请输入订单号" v-model="searchCriteria.id"/>
      <el-input placeholder="请输入商品名称" v-model="searchCriteria.name"/>
      <el-input placeholder="请输入交易公司" v-model="searchCriteria.company"/>
      <el-input placeholder="请输入订单日期" v-model="searchCriteria.date"/>
      <el-input placeholder="请输入订单状态" v-model="searchCriteria.status"/>
       <div class="search-buttons">
        <el-button type="primary" round @click="search">查询</el-button>
        <el-button type="warning" round @click="reset">重置</el-button>
      </div>
    </div>

    <div class="table-container">
      <div class="table-wrapper">
        <el-table v-if="tableData.items.length > 0" :data="tableData.items" style="width: 100%" class="custom-table">
          <el-table-column v-for="column in columns" :key="column.prop" :prop="column.prop" :label="column.label" :align="column.align"/>
          <el-table-column label="合同" align="center">
            <template #default="scope">
              <el-button size="small" type="danger" @click="searchContract(scope.row)">查看合同</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div v-else class="no-data">No data available</div>
      </div>

      <div class="pagination-box">
        <el-pagination background layout="prev, pager, next" :total="totalItems" :page-size="pageSize" @current-change="handlePageChange"/>
      </div>
    </div>

    <el-dialog v-model="dialogVisible" title="合同内容" class="custom-dialog">
      <el-input type="textarea" class="textarea-box" v-model="text" rows="10" autosize :readonly="true"/>
      <template #footer>
        <el-button @click="dialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { ElMessage, ElButton, ElInput, ElDialog, ElSelect, ElOption, ElTable, ElTableColumn, ElPagination } from 'element-plus';
import axios from 'axios';

// 显示数据
const tableData = reactive({
  items: []
});
const columns = [
  { prop: 'id', label: '订单号', align: 'center' },
  { prop: 'name', label: '商品名称', align: 'center' },
  { prop: 'company', label: '交易公司', align: 'center' },
  { prop: 'date', label: '订单日期', align: 'center' },
  { prop: 'status', label: '订单状态', align: 'center' }
];
const statusMap = {
  resign: '已离职',
  join: '在职'
};
const loadData = (page = 1) => {
  axios.get('/api/sales/searchAllSalesOrder', { params: { page } })
      .then(response => {
        const res = response.data;
        tableData.items = res.data.map(item => ({
        }));
      })
      .catch(error => {
        ElMessage.error("加载数据失败");
        console.error('Error loading data:', error);
      });
};

// 查找员工
const searchCriteria = reactive({
  id: '',
  name: '',
  company: '',
  date: '',
  status: ''
});
const search = () => {
  axios.get('/api/sales/searchSalesOrder', { params: searchCriteria })
      .then(response => {
        const res = response.data;
        tableData.items = res.data.map(item => ({
          id: item.id,
          status: statusMap[item.status] || item.status,
        }));
      })
      .catch(error => {
        ElMessage.error("搜索失败");
        console.error('Error searching:', error);
      });
};

// 查看合同
const text = ref('');
const dialogVisible = ref(false);
const searchContract = (row) => {
  let selectedId = ref(0);
  selectedId.value = row.id;
  axios.get('/api/sales/searchContract', { params: { id: selectedId.value } })
      .then(response => {
        text.value = response.data.data;
        dialogVisible.value = true;
      })
      .catch(error => {
        ElMessage.error("获取合同失败");
        console.error('Error fetching resume:', error);
      });
};

const reset = () => {
  searchCriteria.id = '';
  searchCriteria.name = '';
  searchCriteria.company = '';
  searchCriteria.date = '';
  searchCriteria.status = 'join';
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
