<template>
  <div class="home-container">
    <el-card class="home-card">
      <div class="home-content">
        <el-row>
          <el-col :span="24">
            <h1>欢迎使用轧钢厂管理系统！</h1>
            <p class="department-info">您所在的部门是：仓储部！</p>
            <p class="greeting">祝您有个美好的一天！</p>
          </el-col>
        </el-row>

        <el-divider></el-divider>

        <!-- 数据概览 -->
        <el-row :gutter="20" class="section">
          <el-col :span="8">
            <el-card shadow="hover" class="overview-card">
              <h3>库存总量</h3>
              <p class="overview-number">186</p>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" class="overview-card">
              <h3>本月入库</h3>
              <p class="overview-number">23</p>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" class="overview-card">
              <h3>本月出库</h3>
              <p class="overview-number">79</p>
            </el-card>
          </el-col>
        </el-row>

        <el-divider></el-divider>

        <!-- 仓储统计图表 -->
        <el-row :gutter="20" class="section">
          <el-col :span="12">
            <el-card shadow="hover" class="chart-card">
              <h2>入库趋势</h2>
              <div ref="inboundTrendChartContainerRef" class="chart-container"></div>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card shadow="hover" class="chart-card">
              <h2>出库趋势</h2>
              <div ref="outboundTrendChartContainerRef" class="chart-container"></div>
            </el-card>
          </el-col>
        </el-row>

        <el-divider></el-divider>

        <!-- 通知与公告 -->
        <el-row :gutter="20" class="section">
          <el-col :span="24">
            <el-card shadow="hover" class="notice-card">
              <h2>通知与公告</h2>
              <div v-for="(notice, index) in notices" :key="index" class="notice-item">
                <i :class="notice.icon"></i>
                <p>{{ notice.date }} - {{ notice.content }}</p>
              </div>
            </el-card>
          </el-col>
        </el-row>

        <el-divider></el-divider>

        <!-- 最新活动 -->
        <el-row :gutter="20" class="section">
          <el-col :span="24">
            <el-card shadow="hover" class="activity-card">
              <h2>最新活动</h2>
              <el-carousel :interval="5000" type="card" height="300px">
                <el-carousel-item v-for="(activity, index) in activities" :key="index">
                  <div class="activity-item">
                    <h3>{{ activity.title }}</h3>
                    <p>{{ activity.description }}</p>
                  </div>
                </el-carousel-item>
              </el-carousel>
            </el-card>
          </el-col>
        </el-row>

        <el-divider></el-divider>

        <!-- 待办事项和考勤 -->
        <el-row :gutter="20" class="section">
          <el-col :span="12">
            <el-card shadow="hover" class="todo-card">
              <h2>待办事项</h2>
              <el-table :data="todos" border stripe style="width: 100%" :cell-style="{ textAlign: 'center' }">>
                <el-table-column prop="task" label="任务" width="180"></el-table-column>
                <el-table-column prop="deadline" label="截止日期" width="180"></el-table-column>
                <el-table-column prop="status" label="状态" width="180"></el-table-column>
              </el-table>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card shadow="hover" class="attendance-card">
              <h2>员工考勤</h2>
              <el-table :data="attendances" border stripe style="width: 100%" :cell-style="{ textAlign: 'center' }">
                <el-table-column prop="name" label="姓名" width="180"></el-table-column>
                <el-table-column prop="date" label="日期" width="180"></el-table-column>
                <el-table-column prop="status" label="状态" width="180"></el-table-column>
              </el-table>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';

// Mock data for overview
const totalInventory = ref(5000);
const monthlyInbound = ref(1200);
const monthlyOutbound = ref(900);

// Mock data for charts
const inboundTrendChartRef = ref(null);
const outboundTrendChartRef = ref(null);

const mockInboundTrendData = [
  { date: '2024-06-01', amount: 50 },
  { date: '2024-06-02', amount: 70 },
  { date: '2024-06-03', amount: 65 },
  { date: '2024-06-04', amount: 80 },
  { date: '2024-06-05', amount: 90 },
];

const mockOutboundTrendData = [
  { date: '2024-06-01', amount: 40 },
  { date: '2024-06-02', amount: 60 },
  { date: '2024-06-03', amount: 55 },
  { date: '2024-06-04', amount: 70 },
  { date: '2024-06-05', amount: 75 },
];

onMounted(() => {
  // Initialize inbound trend chart
  inboundTrendChartRef.value = mockInboundTrendData;

  const inboundTrendChart = echarts.init(inboundTrendChartContainerRef.value);
  const inboundXAxisData = inboundTrendChartRef.value.map(entry => entry.date);
  const inboundSeriesData = inboundTrendChartRef.value.map(entry => entry.amount);

  const inboundOption = {
    color: ['#67C23A'],
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    xAxis: {
      type: 'category',
      data: inboundXAxisData
    },
    yAxis: {
      type: 'value',
      minInterval: 1
    },
    series: [{
      data: inboundSeriesData,
      type: 'line'
    }]
  };
  inboundTrendChart.setOption(inboundOption);

  window.addEventListener('resize', () => {
    inboundTrendChart.resize();
  });

  // Initialize outbound trend chart
  outboundTrendChartRef.value = mockOutboundTrendData;

  const outboundTrendChart = echarts.init(outboundTrendChartContainerRef.value);
  const outboundXAxisData = outboundTrendChartRef.value.map(entry => entry.date);
  const outboundSeriesData = outboundTrendChartRef.value.map(entry => entry.amount);

  const outboundOption = {
    color: ['#E6A23C'],
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    xAxis: {
      type: 'category',
      data: outboundXAxisData
    },
    yAxis: {
      type: 'value',
      minInterval: 1
    },
    series: [{
      data: outboundSeriesData,
      type: 'line'
    }]
  };
  outboundTrendChart.setOption(outboundOption);

  window.addEventListener('resize', () => {
    outboundTrendChart.resize();
  });
});

const inboundTrendChartContainerRef = ref(null);
const outboundTrendChartContainerRef = ref(null);

const notices = ref([
  { date: '2023-01-04', content: '下周一将举行仓库管理培训，请仓储部员工准时参加。' },
  { date: '2023-01-03', content: '本周五将进行库存盘点，请相关员工做好准备。' },
  { date: '2023-01-01', content: '新年快乐！' },
]);

const activities = ref([
  { title: '仓库管理研讨会', description: '欢迎参加仓库管理研讨会，共同探讨提升仓库管理效率的方法。' },
  { title: '库存盘点演练', description: '本周将举行库存盘点演练，请大家积极参与。' },
  { title: '安全培训', description: '下周将有专家为我们进行安全培训，请大家准时参加。' },
]);

const todos = ref([
  { task: '库存盘点', deadline: '2023-01-10', status: '进行中' },
  { task: '仓库清理', deadline: '2023-01-12', status: '未开始' },
  { task: '新货品入库', deadline: '2023-01-15', status: '已完成' },
]);

const attendances = ref([
  { name: '张三', date: '2023-01-04', status: '正常' },
  { name: '李四', date: '2023-01-04', status: '迟到' },
  { name: '王五', date: '2023-01-04', status: '早退' },
]);
</script>

<style lang="scss" scoped src="@/styles/home.css"></style>
