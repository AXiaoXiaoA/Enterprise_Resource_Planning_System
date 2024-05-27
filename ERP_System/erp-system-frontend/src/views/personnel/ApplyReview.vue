<template>
  <div class="container">
    <div class="search-box">
      <el-input placeholder="请输入入职申请号" v-model="searchCriteria.id"/>
      <el-input placeholder="请输入申请人姓名" v-model="searchCriteria.name"/>
      <el-select placeholder="请选择申请部门" v-model="searchCriteria.department">
        <el-option label="销售部" value="sales"></el-option>
        <el-option label="仓储部" value="repo"></el-option>
        <el-option label="人事部" value="personnel"></el-option>
        <el-option label="生产部" value="production"></el-option>
        <el-option label="采购部" value="purchasing"></el-option>
      </el-select>
      <el-select placeholder="请选择申请职务" v-model="searchCriteria.position">
        <el-option label="总经理" value="manager"></el-option>
        <el-option label="普通职员" value="worker"></el-option>
      </el-select>
      <div class="age-range-input">
        <el-input v-model="searchCriteria.ageStart" type="number" placeholder="年龄起" style="width: 120px;"/>
        <span class="separator">-</span>
        <el-input v-model="searchCriteria.ageEnd" type="number" placeholder="年龄止" style="width: 120px;"/>
      </div>
      <el-select placeholder="请选择性别" v-model="searchCriteria.gender">
        <el-option label="男" value="male"></el-option>
        <el-option label="女" value="female"></el-option>
      </el-select>
      <el-switch v-model="searchCriteria.status" active-text="显示已拒绝" active-value="reject" inactive-text="隐藏已拒绝" inactive-value="apply"/>
      <div class="search-buttons">
        <el-button type="primary" round @click="search">查询</el-button>
        <el-button type="warning" round @click="reset">重置</el-button>
      </div>
    </div>

    <div class="table-container">
      <div class="table-wrapper">
        <el-table v-if="tableData.items.length > 0" :data="tableData.items" style="width: 100%" class="custom-table">
          <el-table-column v-for="column in columns" :key="column.prop" :prop="column.prop" :label="column.label" :align="column.align"/>
          <el-table-column label="简历" align="center">
            <template #default="scope">
              <el-button size="small" type="danger" @click="searchResume(scope.row)">查看简历</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div v-else class="no-data">No data available</div>
      </div>
      <div class="pagination-box">
        <el-pagination background layout="prev, pager, next" :total="totalItems" :page-size="pageSize" @current-change="handlePageChange"/>
      </div>
    </div>

    <el-dialog v-model="dialogVisible" title="简历内容" class="custom-dialog">
      <el-input type="textarea" class="textarea-box" v-model="text" rows="10" autosize :readonly="true"/>
      <div class="progress-animation">
        <el-progress v-if="loading" :percentage="100" status="success" class="progress-bar"></el-progress>
      </div>
      <div class="result-box" v-if="analysis_result">
        <el-row justify="center">
          <el-col :span="24" :lg="12">
            <el-card class="result-card" shadow="hover">
              <div slot="header" class="result-header">
                <h3 class="result-title">{{ getFavor() }}</h3>
              </div>
              <div class="result-content">
                <p class="result-subtitle">{{ getResult() }}</p>
              </div>
              <div class="decision-box">
                <el-button type="success" @click="acceptApply" class="decision-button">同意录用</el-button>
                <el-button type="danger" @click="rejectApply" class="decision-button">拒绝录用</el-button>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
      <template #footer>
        <div class="footer-buttons">
          <el-button @click="analysis">分析</el-button>
          <el-button @click="dialogVisible = false">关闭</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { ElMessage, ElButton, ElInput, ElDialog, ElSelect, ElOption, ElTable, ElTableColumn, ElPagination, ElLoading } from 'element-plus';
import axios from 'axios';

// 显示数据
const tableData = reactive({
  items: []
});
const columns = [
  { prop: 'id', label: '入职申请号', align: 'center' },
  { prop: 'name', label: '申请人姓名', align: 'center' },
  { prop: 'gender', label: '性别', align: 'center' },
  { prop: 'age', label: '年龄', align: 'center' },
  { prop: 'department', label: '申请部门', align: 'center' },
  { prop: 'position', label: '申请职务', align: 'center' },
  { prop: 'status', label: '求职状态', align: 'center' },
  { prop: 'applyDate', label: '投递时间', align: 'center' },
];
const departmentMap = {
  sales: '销售部',
  repo: '仓储部',
  personnel: '人事部',
  production: '生产部',
  purchasing: '采购部'
};
const positionMap = {
  manager: '总经理',
  worker: '职员'
};
const genderMap = {
  male: '男',
  female: '女'
};
const statusMap = {
  reject: '已拒绝',
  apply: '应聘中'
};
const loadData = (page = 1) => {
  axios.get('/api/personnel/searchAllApply', { params: { page } })
      .then(response => {
        const res = response.data;
        tableData.items = res.data.map(item => ({
          id: item.id,
          name: item.personalInformation.name,
          gender: genderMap[item.personalInformation.gender] || item.personalInformation.gender,
          age: new Date().getFullYear() - new Date(item.personalInformation.birthday).getFullYear(),
          department: departmentMap[item.department] || item.department,
          position: positionMap[item.position] || item.position,
          status: statusMap[item.status] || item.status,
          applyDate: item.startDate,
        }));
      })
      .catch(error => {
        ElMessage.error("加载数据失败");
        console.error('Error loading data:', error);
      });
};

// 查找应聘者
const searchCriteria = reactive({
  id: '',
  name: '',
  department: '',
  position: '',
  gender: '',
  ageStart: '',
  ageEnd: '',
  status: 'apply'
});
const search = () => {
  axios.get('/api/personnel/searchApply', { params: searchCriteria })
      .then(response => {
        const res = response.data;
        tableData.items = res.data.map(item => ({
          id: item.id,
          name: item.personalInformation.name,
          gender: genderMap[item.personalInformation.gender] || item.personalInformation.gender,
          age: new Date().getFullYear() - new Date(item.personalInformation.birthday).getFullYear(),
          department: departmentMap[item.department] || item.department,
          position: positionMap[item.position] || item.position,
          status: statusMap[item.status] || item.status,
          applyDate: item.startDate,
        }));
      })
      .catch(error => {
        ElMessage.error("搜索失败");
        console.error('Error searching:', error);
      });
};

// 查看简历
const text = ref('');
const dialogVisible = ref(false);
const selectedId = ref(0);
const searchResume = (row) => {
  selectedId.value = row.id;
  axios.get('/api/personnel/searchResume', { params: { id: selectedId.value } })
      .then(response => {
        text.value = response.data.data;
        dialogVisible.value = true;
        analysis_result.value = '';
        analysis_favor.value = '';
        analysis_iconType.value = '';
      })
      .catch(error => {
        ElMessage.error("获取简历失败");
        console.error('Error fetching resume:', error);
      });
};

// 分析简历
const analysis_result = ref('');
const analysis_favor = ref('');
const analysis_iconType = ref('');
const loading = ref(false);
const analysis = () => {
  loading.value = true;
  axios.post('/api/personnel/analysis', { content: text.value })
      .then(res => {
        setTimeout(() => {
          loading.value = false;
          analysis_result.value = res.data.data;
          const lastIndex = analysis_result.value.lastIndexOf(":");
          analysis_favor.value = lastIndex !== -1 ? analysis_result.value.substring(lastIndex + 1).trim() : "";
          analysis_iconType.value = analysis_favor.value === "positive" ? 'success' : 'error';
        }, 2000);
      })
      .catch(error => {
        loading.value = false;
        ElMessage.error("分析失败");
        console.error('Error loading data:', error);
        analysis_result.value = '';
        analysis_favor.value = '';
        analysis_iconType.value = '';
      });
};
const getResult = () => {
  if (analysis_favor.value === 'positive') {
    return '经大模型分析，这位应聘者能力出众，我建议您聘用这位人才';
  } else {
    return '经大模型分析，这位应聘者不足以胜任该岗位，我不建议您聘用';
  }
};
const getFavor = () => {
  if (analysis_favor.value === 'positive') {
    return '这位应聘者非常优秀！';
  } else {
    return '这位应聘者实力欠佳';
  }
};

// 聘用员工
const acceptApply = ()=>{
  axios.post('/api/personnel/acceptApply',null,{ params: { id: selectedId.value } })
      .then(res=>{
        if(res.data.code==='200'){
          ElMessage.success('聘用成功');
          loadData();
        }else{
          ElMessage.error('聘用失败');
        }
  })
}
const rejectApply = ()=>{
  axios.post('/api/personnel/rejectApply',null,{ params: { id: selectedId.value } })
      .then(res=>{
        if(res.data.code==='200'){
          ElMessage.success('拒绝成功');
          loadData();
        }else{
          ElMessage.error('拒绝失败');
        }
  })
}

const reset = () => {
  searchCriteria.id = '';
  searchCriteria.name = '';
  searchCriteria.department = '';
  searchCriteria.position = '';
  searchCriteria.gender = '';
  searchCriteria.ageStart = '';
  searchCriteria.ageEnd = '';
  searchCriteria.status = 'apply';
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
<style scoped>
.progress-animation {
  width: 100%;
  height: 8px;
  background-color: #f3f4f6;
  border-radius: 5px;
  position: relative;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.progress-bar {
  width: 0;
  height: 100%;
  background-color: #3498db;
  position: absolute;
  top: 0;
  left: 0;
  animation: progress-animation 2s ease forwards;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  background-image: linear-gradient(to right, #3498db, #2980b9);
}

@keyframes progress-animation {
  0% {
    width: 0;
  }
  100% {
    width: 100%;
  }
}

.result-box {
  margin-top: 20px;
}

.result-card {
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.result-header {
  background-color: #3498db;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  padding: 10px;
  text-align: center;
}

.result-title {
  color: #ffffff;
  margin: 0;
}

.result-content {
  padding: 20px;
}

.result-subtitle {
  font-size: 16px;
  color: #2c3e50;
  margin: 0;
}

.decision-box {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.decision-button {
  margin: 0 10px;
  border-radius: 20px;
  padding: 10px 20px;
  font-size: 16px;
}
</style>