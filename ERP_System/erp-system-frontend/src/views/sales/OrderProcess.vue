<template>
  <div class="container">
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
        <div class="footer-buttons">
          <el-button @click="acceptOrder">通过</el-button>
          <el-button @click="rejectOrder">打回</el-button>
          <el-button @click="dialogVisible = false">关闭</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {reactive, onMounted, ref} from 'vue';
import {ElButton, ElDialog, ElInput, ElMessage, ElPagination, ElTable, ElTableColumn} from 'element-plus';
import axios from 'axios';

// 显示数据
const tableData = reactive({
  items: [],
});
const columns = [
  {prop: 'id', label: '订单号', align: 'center'},
  {prop: 'finishDate', label: '完成时间', align: 'center'},
  {prop: 'status', label: '完成状态', align: 'center'},
  {prop: 'type', label: '货物类型', align: 'center'},
  {prop: 'amount', label: '货物数量', align: 'center'},
  {prop: 'money', label: '金额', align: 'center'},
];
const loadData = () => {
  axios.get('/api/sales/checkPreparedOrder')
      .then(res => {
        console.log(res);
        res = res.data;
        tableData.items = res.data;
      })
      .catch(error => {
        ElMessage.error("加载数据失败");
        console.error('Error loading data:', error);
      });
};

// 查找订单
const searchCriteria = reactive({
  id: '',
  name: '',
  department: '',
  position: '',
  ageStart: '',
  ageEnd: '',
  gender: '',
  status: 'apply'
});
const search = () => {
  axios.get('/api/sales/searchOrder', { params: searchCriteria })
      .then(response => {
        const res = response.data;
        tableData.items = res.data.map(item => ({
          ...item,
          gender: genderMap[item.gender] || item.gender,
          dept: departmentMap[item.dept] || item.dept,
          position: positionMap[item.position] || item.position,
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
const selectedId = ref(0);
const searchContract = (row) => {
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

// 审批合同
const acceptOrder = ()=>{
  axios.post('/api/sales/acceptOrder',{id:selectedId.value}).then(res=>{
    if(res.data.code==='200'){
      ElMessage.success('通过成功');
      loadData();
    }else{
      ElMessage.error('通过失败');
    }
  })
}
const rejectOrder = ()=>{
  axios.post('/api/sales/rejectOrder',{id:selectedId.value}).then(res=>{
    if(res.data.code==='200'){
      ElMessage.success('打回成功');
      loadData();
    }else{
      ElMessage.error('打回失败');
    }
  })
}

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
