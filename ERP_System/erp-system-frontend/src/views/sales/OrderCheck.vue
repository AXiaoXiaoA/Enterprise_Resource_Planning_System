<template>
  <div class="container">
    <div class="search-box">
      <el-input v-if="employee.position === '部长'" placeholder="请输入员工姓名" v-model="searchCriteria.salesEmployeeName" />
      <el-input placeholder="请输入订单号" v-model="searchCriteria.id"/>
      <el-input placeholder="请输入商品名称" v-model="searchCriteria.productName"/>
      <el-input placeholder="请输入交易公司" v-model="searchCriteria.companyName"/>
      <el-input placeholder="请输入目的地" v-model="searchCriteria.destination"/>
      <el-select placeholder="请选择订单状态" v-model="searchCriteria.status">
        <el-option label="待审批" value="审批"></el-option>
        <el-option label="被打回" value="打回"></el-option>
        <el-option label="已完成" value="出库"></el-option>
        <el-option label="已取消" value="取消"></el-option>
      </el-select>
      <div style="display: flex; align-items: center; justify-content: center;">
        <el-date-picker
            v-model="searchCriteria.startDate"
            placeholder="开始日期"
            type="date"
            style="margin: 0 10px;"
        />
        <span style="margin: 0 5px; font-size: 16px; font-weight: bold;">-</span>
        <el-date-picker
            v-model="searchCriteria.endDate"
            placeholder="结束日期"
            type="date"
            style="margin: 0 10px;"
        />
      </div>
      <div class="search-buttons">
        <el-button type="primary" round @click="search">查询</el-button>
        <el-button type="warning" round @click="reset">重置</el-button>
      </div>
    </div>
    <div class="table-container">
      <div class="table-wrapper">
        <el-table v-if="tableData.items.length > 0" :data="tableData.items" style="width: 100%" class="custom-table">
          <el-table-column v-for="column in columns" :key="column.prop" :prop="column.prop" :label="column.label" :align="column.align" :sortable="true"/>
          <el-table-column label="订单状态" align="center">
            <template #default="scope">
              <span :class="statusClass(scope.row.status)">{{ scope.row.status }}</span>
            </template>
          </el-table-column>
          <el-table-column label="详情" align="center">
            <template #default="scope">
              <el-button size="small" type="primary" @click="searchOrderDetail(scope.row)">查看详情</el-button>
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

    <el-dialog v-model="dialogVisible" title="订单详情" class="custom-dialog" width="80%" height="90%">
      <el-card class="result-card">
        <el-table :data="salesOrder.items" border class="custom-table">
          <el-table-column label="订单信息" align="center">
            <template #default="{row}">
              <el-table :data="[row]" border>
                <el-table-column label="订单号">
                  <template #default="{row}">
                    {{ row.id }}
                  </template>
                </el-table-column>
                <el-table-column label="数量">
                  <template #default="{row}">
                    <span v-if="row.quantity !== '无'">{{ row.quantity }}</span><span v-else>无</span>
                  </template>
                </el-table-column>
                <el-table-column label="申请日期">
                  <template #default="{row}">
                    <span v-if="row.applyDate !== '无'">{{ row.applyDate }}</span><span v-else>无</span>
                  </template>
                </el-table-column>
                <el-table-column label="目的地">
                  <template #default="{row}">
                    <span v-if="row.destination !== '无'">{{ row.destination }}</span><span v-else>无</span>
                  </template>
                </el-table-column>
                <el-table-column label="订单状态">
                  <template #default="{row}">
                    <span v-if="row.status !== '无'">{{ row.status }}</span><span v-else>无</span>
                  </template>
                </el-table-column>
              </el-table>
            </template>
          </el-table-column>
        </el-table>

        <el-table :data="salesOrder.items" border class="custom-table">
          <el-table-column label="产品信息" align="center">
          <template #default="{row}">
            <el-table :data="[row.product]" border>
              <el-table-column label="产品ID">
                <template #default="{row}">
                  <span v-if="row.id !== '无'">{{ row.id }}</span><span v-else>无</span>
                </template>
              </el-table-column>
              <el-table-column label="产品名称">
                <template #default="{row}">
                  <span v-if="row.name !== '无'">{{ row.name }}</span><span v-else>无</span>
                </template>
              </el-table-column>
              <el-table-column label="产品价格">
                <template #default="{row}">
                  <span v-if="row.price !== '无'">{{ row.price }}</span><span v-else>无</span>
                </template>
              </el-table-column>
            </el-table>
          </template>
          </el-table-column>
        </el-table>

        <el-table :data="salesOrder.items" border class="custom-table">
          <el-table-column label="公司信息" align="center">
            <template #default="{row}">
              <el-table :data="[row.company]" border>
                <el-table-column label="公司名称">
                  <template #default="{row}">
                    <span v-if="row.name !== '无'">{{ row.name }}</span><span v-else>无</span>
                  </template>
                </el-table-column>
                <el-table-column label="公司电话">
                  <template #default="{row}">
                    <span v-if="row.tel !== '无'">{{ row.tel }}</span><span v-else>无</span>
                  </template>
                </el-table-column>
              </el-table>
            </template>
          </el-table-column>
        </el-table>

        <el-table :data="salesOrder.items" border class="custom-table">
          <el-table-column label="销售部员信息" align="center">
            <template #default="{row}">
              <el-table :data="[row.salesEmployee]" border>
                <el-table-column label="销售部员工号">
                  <template #default="{row}">
                    <span v-if="row.id !== '无'">{{ row.id }}</span><span v-else>无</span>
                  </template>
                </el-table-column>
                <el-table-column label="销售部员姓名">
                  <template #default="{row}">
                    <span v-if="row.person.name !== '无'">{{ row.person.name }}</span><span v-else>无</span>
                  </template>
                </el-table-column>
                <el-table-column label="销售部员职位">
                  <template #default="{row}">
                    <span v-if="row.position !== '无'">{{ row.position }}</span><span v-else>无</span>
                  </template>
                </el-table-column>
                <el-table-column label="销售部员邮箱">
                  <template #default="{row}">
                    <span v-if="row.person.email !== '无'">{{ row.person.email }}</span><span v-else>无</span>
                  </template>
                </el-table-column>
                <el-table-column label="销售部员电话">
                  <template #default="{row}">
                    <span v-if="row.person.tel !== '无'">{{ row.person.tel }}</span><span v-else>无</span>
                  </template>
                </el-table-column>
              </el-table>
            </template>
          </el-table-column>
        </el-table>

        <el-table :data="salesOrder.items" border class="custom-table">
          <el-table-column label="仓储部员信息" align="center">
            <template #default="{row}">
              <el-table v-if="row.repoEmployee" :data="[row.repoEmployee]" border>
                <el-table-column label="仓储部员工号">
                  <template #default="{row}">
                    <span v-if="row.id !== '无'">{{ row.id }}</span><span v-else>无</span>
                  </template>
                </el-table-column>
                <el-table-column label="仓储部员姓名">
                  <template #default="{row}">
                    <span v-if="row.person.name !== '无'">{{ row.person.name }}</span><span v-else>无</span>
                  </template>
                </el-table-column>
                <el-table-column label="仓储部员职位">
                  <template #default="{row}">
                    <span v-if="row.position !== '无'">{{ row.position }}</span><span v-else>无</span>
                  </template>
                </el-table-column>
                <el-table-column label="仓储部员邮箱">
                  <template #default="{row}">
                    <span v-if="row.person.email !== '无'">{{ row.person.email }}</span><span v-else>无</span>
                  </template>
                </el-table-column>
                <el-table-column label="仓储部员电话">
                  <template #default="{row}">
                    <span v-if="row.person.tel !== '无'">{{ row.person.tel }}</span><span v-else>无</span>
                  </template>
                </el-table-column>
              </el-table>
            </template>
          </el-table-column>
        </el-table>
        <div class="decision-box">
          <el-button type="primary" @click="searchContract" class="decision-button">查看合同</el-button>
        </div>
      </el-card>

      <template #footer>
        <div class="footer-buttons">
          <el-button v-if="employee.position === '部长' && salesOrder.items[0].status === '等待部长审批'"
                     type="success"
                     @click="checkOrder('等待仓储部审批')"
                     class="decision-button">通过</el-button>
          <el-button v-if="employee.position === '部长' && salesOrder.items[0].status === '等待部长审批'"
                     type="danger"
                     @click="checkOrder('部长打回')"
                     class="decision-button">打回</el-button>

          <el-button v-if="employee.position === '部长' && salesOrder.items[0].status === '仓储部打回'"
                     type="danger"
                     @click="checkOrder('等待仓储部审批')"
                     class="decision-button">重新提交</el-button>
          <el-button v-if="employee.position === '部长' && salesOrder.items[0].status === '仓储部打回'"
                     type="danger"
                     @click="checkOrder('部长打回')"
                     class="decision-button">打回</el-button>

          <el-button v-if="employee.position === '部员' && salesOrder.items[0].status === '部长打回'"
                     type="success"
                     @click="checkOrder('等待部长审批')"
                     class="decision-button">重新提交</el-button>

          <el-button v-if="salesOrder.items[0].status === '已交货'"
                     type="success"
                     @click="checkOrder('已完成')"
                     class="decision-button">完成订单</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { ElMessage, ElButton, ElInput, ElDialog, ElSelect, ElOption, ElTable, ElTableColumn, ElPagination } from 'element-plus';
import axios from 'axios';

// 初始化
const columns = [
  { prop: 'id', label: '订单号', align: 'center' },
  { prop: 'productName', label: '产品名称', align: 'center' },
  { prop: 'quantity', label: '产品数量', align: 'center' },
  { prop: 'companyName', label: '交易公司', align: 'center' },
  { prop: 'destination', label: '目的地', align: 'center' },
  { prop: 'date', label: '订单日期', align: 'center' },
  { prop: 'salesEmployeeName', label: '销售部部员', align: 'center' },
  { prop: 'repoEmployeeName', label: '仓储部部员', align: 'center' },
];
const statusClass = (status) => {
  if (/等待/.test(status) || /运输/.test(status) || /生产/.test(status)) {
    return 'status-pending';
  } else if (/打回/.test(status) || /不合格/.test(status)) {
    return 'status-rejected';
  } else if (/完成/.test(status) || /交货/.test(status)) {
    return 'status-completed';
  } else if (/取消/.test(status)) {
    return 'status-canceled';
  }
};
const employee = JSON.parse(localStorage.getItem('user') || '{}');
const searchCriteria = reactive({
  id: '',
  salesEmployeeId: '',
  salesEmployeeName: '',
  repoEmployeeId: '',
  repoEmployeeName: '',
  productName: '',
  companyName: '',
  destination: '',
  startDate: '',
  endDate: '',
  status: ''
});

// 显示订单
const tableData = reactive({
  items: []
});
const loadData = (page = 1) => {
  if (employee.position === '部员') {
    searchCriteria.salesEmployeeId = employee.id;
  }
  axios.post('/api/sales/searchOrder', searchCriteria)
      .then(response => {
        const res = response.data;
        tableData.items = res.data.map(item => ({
          id: item.id,
          productName: item.product.name,
          quantity: item.quantity,
          companyName: item.company.name,
          destination: item.destination,
          date: item.applyDate,
          status: item.status,
          salesEmployeeName: item.salesEmployee.person.name,
          repoEmployeeName: item.repoEmployee ? item.repoEmployee.person.name : '暂无'
        }));
      })
      .catch(error => {
        ElMessage.error("加载数据失败");
        console.error('Error loading data:', error);
      });
};

// 查找订单
const search = () => {
  loadData();
};

// 查看订单详情
const salesOrder = reactive({
  items: []
});
const searchOrderDetail = (row) => {
  selectedId.value = row.id;
  axios.post('/api/sales/searchOrderDetail', { id: selectedId.value })
      .then(response => {
        console.log('Response received:', response.data);
        const res = response.data;
        salesOrder.items = res.data.map(item => ({
          id: item.id,
          quantity: item.quantity || '无',
          applyDate: item.applyDate || '无',
          destination: item.destination || '无',
          status: item.status || '无',
          product: {
            id: item.product ? item.product.id : '无',
            name: item.product ? item.product.name : '无',
            price: item.product ? item.product.price : '无'
          },
          company: {
            id: item.company ? item.company.id : '无',
            name: item.company ? item.company.name : '无',
            tel: item.company ? item.company.tel : '无'
          },
          salesEmployee: {
            id: item.salesEmployee ? item.salesEmployee.id : '无',
            position: item.salesEmployee.position ? item.salesEmployee.position : '无',
            person: {
              name: item.salesEmployee && item.salesEmployee.person ? item.salesEmployee.person.name : '无',
              email: item.salesEmployee && item.salesEmployee.person ? item.salesEmployee.person.email : '无',
              tel: item.salesEmployee && item.salesEmployee.person ? item.salesEmployee.person.tel : '无'
            },
          },
          repoEmployee: item.repoEmployee ? {
            id: item.repoEmployee.id ? item.repoEmployee.id : '无',
            position: item.repoEmployee.position ? item.repoEmployee.position : '无',
            person: {
              name: item.repoEmployee && item.repoEmployee.person ? item.repoEmployee.person.name : '无',
              email: item.repoEmployee && item.repoEmployee.person ? item.repoEmployee.person.email : '无',
              tel: item.repoEmployee && item.repoEmployee.person ? item.repoEmployee.person.tel : '无'
            },
          } : null
        }));
        dialogVisible.value = true;
      })
      .catch(error => {
        console.error('Error fetching order detail:', error);
        ElMessage.error("获取详情失败");
      });
};

// 查看合同
const dialogVisible = ref(false);
const selectedId = ref(0);
const searchContract = () => {
  axios.get('/api/sales/searchContract', { params: { id: selectedId.value } })
      .then(response => {
        const blob = new Blob([response.data], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);
        window.open(url, '_blank');
      })
      .catch(error => {
        ElMessage.error("获取合同失败");
      });
};

// 审核申请
const checkOrder = (status)=>{
  const requestData = {
    id: selectedId.value,
    status: status
  };
  axios.post('/api/sales/checkOrder', requestData)
      .then(res=>{
        if(res.data.code==='200'){
          ElMessage.success('操作成功');
          loadData();
        }else{
          ElMessage.error('操作失败');
        }
      })
}

// 重置
const reset = () => {
  searchCriteria.salesEmployeeName = '';
  searchCriteria.id = '';
  searchCriteria.productName = '';
  searchCriteria.companyName = '';
  searchCriteria.destination = '';
  searchCriteria.status = '';
  searchCriteria.startDate = '';
  searchCriteria.endDate = '';
  loadData();
};

// 导出表格
const exportTable = () => {
  const items = tableData.items;
  const headers = columns.map(column => column.label).join(',') + '\n';
  const rows = items.map(item => columns.map(column => item[column.prop]).join(',')).join('\n');
  const csvContent = headers + rows;

  const BOM = '\uFEFF';
  const blob = new Blob([BOM + csvContent], { type: 'text/csv;charset=utf-8;' });
  const link = document.createElement('a');
  const url = URL.createObjectURL(blob);
  link.setAttribute('href', url);
  link.setAttribute('download', 'table_data.csv');
  link.style.visibility = 'hidden';
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
};

// 分页
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
<style>
.status-pending {
  color: orange;
}
.status-rejected {
  color: red;
}
.status-completed {
  color: green;
}
.status-canceled {
  color: grey;
}
.custom-table {
  background-color: #d1cfcf;
  border: 1px solid #685f5f;
  border-radius: 4px;
  color: #333;
}
</style>
