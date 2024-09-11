<template>
  <div class="container">
    <div class="search-box">
      <el-input placeholder="请输入员工工号" v-model="searchCriteria.id"/>
      <el-input placeholder="请输入员工姓名" v-model="searchCriteria.name"/>
      <el-select placeholder="请选择员工部门" v-model="searchCriteria.department">
        <el-option label="销售部" value="sales"></el-option>
        <el-option label="仓储部" value="repo"></el-option>
        <el-option label="人事部" value="personnel"></el-option>
        <el-option label="生产部" value="production"></el-option>
        <el-option label="采购部" value="purchasing"></el-option>
      </el-select>
      <el-select placeholder="请选择员工职务" v-model="searchCriteria.position">
        <el-option label="部长" value="部长"></el-option>
        <el-option label="部员" value="部员"></el-option>
      </el-select>
      <div class="age-range-input">
        <el-input v-model="searchCriteria.ageStart" type="number" placeholder="年龄起" style="width: 120px;"/>
        <span class="separator">-</span>
        <el-input v-model="searchCriteria.ageEnd" type="number" placeholder="年龄止" style="width: 120px;"/>
      </div>
      <el-select placeholder="请选择员工性别" v-model="searchCriteria.gender">
        <el-option label="男" value="男"></el-option>
        <el-option label="女" value="女"></el-option>
      </el-select>
      <el-switch v-model="searchCriteria.status" active-text="显示已离职" inactive-text="隐藏已离职" active-value="离职" inactive-value="在职"/>
      <div class="search-buttons">
        <el-button type="primary" round @click="search">查询</el-button>
        <el-button type="warning" round @click="reset">重置</el-button>
      </div>
    </div>

    <div class="table-container">
      <div class="table-wrapper">
        <el-table ref="tableRef" v-if="tableData.items.length > 0" :data="tableData.items" style="width: 100%" class="custom-table">
          <el-table-column v-for="column in columns" :key="column.prop" :prop="column.prop" :label="column.label" :align="column.align" :sortable="true"/>
          <el-table-column label="简历" align="center">
            <template #default="scope">
              <el-button size="small" type="danger" @click="searchResume(scope.row)">查看简历</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div v-else class="no-data">暂无数据</div>
      </div>

      <div class="pagination-box">
        <el-pagination background layout="prev, pager, next" :total="totalItems" :page-size="pageSize" @current-change="handlePageChange"/>
        <el-button type="primary" @click="exportTable">导出表格</el-button>
      </div>
    </div>

    <el-dialog v-model="dialogVisible" title="简历内容" class="custom-dialog">
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
  { prop: 'id', label: '工号', align: 'center' },
  { prop: 'name', label: '姓名', align: 'center' },
  { prop: 'gender', label: '性别', align: 'center' },
  { prop: 'age', label: '年龄', align: 'center' },
  { prop: 'department', label: '部门', align: 'center' },
  { prop: 'position', label: '职务', align: 'center' },
  { prop: 'status', label: '状态', align: 'center' }
];
const departmentMap = {
  sales: '销售部',
  repo: '仓储部',
  personnel: '人事部',
  production: '生产部',
  purchasing: '采购部'
};
const genderMap = {
  male: '男',
  female: '女'
};
const loadData = (page = 1) => {
  axios.post('/api/personnel/searchEmployee', searchCriteria)
      .then(response => {
        const res = response.data;
        tableData.items = res.data.map(item => ({
          id: item.id,
          name: item.person.name,
          gender: genderMap[item.person.gender] || item.person.gender,
          age: new Date().getFullYear() - new Date(item.person.birthday).getFullYear(),
          department: departmentMap[item.department] || item.department,
          position: item.position,
          status: item.status,
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
  department: '',
  position: '',
  gender: '',
  ageStart: '',
  ageEnd: '',
  status: '在职'
});
const search = () => {
  loadData();
};

// 查看简历
const text = ref('');
const dialogVisible = ref(false);
const searchResume = (row) => {
  let selectedId = ref(0);
  selectedId.value = row.id;
  axios.get('/api/personnel/searchResume', { params: { id: selectedId.value } })
      .then(response => {
        text.value = response.data.data;
        dialogVisible.value = true;
      })
      .catch(error => {
        ElMessage.error("获取简历失败");
        console.error('Error fetching resume:', error);
      });
};

// 重置
const reset = () => {
  searchCriteria.id = '';
  searchCriteria.name = '';
  searchCriteria.department = '';
  searchCriteria.position = '';
  searchCriteria.gender = '';
  searchCriteria.ageStart = '';
  searchCriteria.ageEnd = '';
  searchCriteria.status = '在职';
  loadData();
};
const totalItems = ref(1000);
const pageSize = ref(10);
const handlePageChange = (page) => {
  loadData(page);
};

// 导出表格
const tableRef = ref(null);
const exportTable = () => {
  const tableInstance = tableRef.value; // 获取表格实例
  if (tableInstance) {
    tableInstance.exportCsv({
      filename: 'table_data.csv',
      original: false
    });
  } else {
    ElMessage.error('无法导出表格数据：找不到表格实例');
  }
};

onMounted(() => {
  loadData();
});
</script>

<style lang="scss" src="@/styles/table.css"></style>
