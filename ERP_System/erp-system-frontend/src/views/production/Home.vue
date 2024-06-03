<template>
  <div class="home-container">
    <el-card class="home-card">
      <div class="home-content">
        <el-row>
          <el-col :span="24">
            <h1>欢迎使用轧钢厂管理系统！</h1>
            <p class="department-info">您所在的部门是：生产部！</p>
            <p class="greeting">祝您有个美好的一天！</p>
          </el-col>
        </el-row>

        <el-divider></el-divider>

        <!-- 数据概览 -->
        <el-row :gutter="20" class="section">
          <el-col :span="8">
            <el-card shadow="hover" class="overview-card">
              <h3>设备总数</h3>
              <p class="overview-number">{{ totalEquipment }}</p>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" class="overview-card">
              <h3>当前运行设备</h3>
              <p class="overview-number">{{ runningEquipment }}</p>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" class="overview-card">
              <h3>今日产量</h3>
              <p class="overview-number">{{ todayOutput }} 吨</p>
            </el-card>
          </el-col>
        </el-row>

        <el-divider></el-divider>

        <!-- 员工统计图表 -->
        <el-row :gutter="20" class="section">
          <el-col :span="12">
            <el-card shadow="hover" class="chart-card">
              <h2>设备使用情况</h2>
              <div ref="equipmentUsageChartContainerRef" class="chart-container"></div>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card shadow="hover" class="chart-card">
              <h2>生产效率</h2>
              <div ref="productionEfficiencyChartContainerRef" class="chart-container"></div>
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
              <el-table :data="todos" border stripe style="width: 100%" :cell-style="{ textAlign: 'center' }">
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
import { computed, onMounted, ref } from 'vue';
import axios from 'axios';
import * as echarts from 'echarts';

const totalEquipment = ref(0);
const runningEquipment = ref(0);
const todayOutput = ref(0);

onMounted(async () => {
  try {
    const equipmentResponse = await axios.get('/api/production/equipmentStatistics');
    totalEquipment.value = equipmentResponse.data.total;
    runningEquipment.value = equipmentResponse.data.running;

    const outputResponse = await axios.get('/api/production/todayOutput');
    todayOutput.value = outputResponse.data.output;
  } catch (error) {
    console.error(error);
  }
});

const equipmentUsageChartRef = ref(null);
const equipmentUsageChartContainerRef = ref(null);
onMounted(async () => {
  try {
    const response = await axios.get('/api/production/equipmentUsage');
    equipmentUsageChartRef.value = response.data;

    const equipmentUsageChart = echarts.init(equipmentUsageChartContainerRef.value);
    const xAxisData = equipmentUsageChartRef.value.map(entry => entry.name);
    const seriesData = equipmentUsageChartRef.value.map(entry => entry.usage);

    const option = {
      color: ['#3398DB'],
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        }
      },
      xAxis: {
        type: 'category',
        data: xAxisData
      },
      yAxis: {
        type: 'value',
        minInterval: 1
      },
      series: [{
        data: seriesData,
        type: 'bar'
      }]
    };
    equipmentUsageChart.setOption(option);

    window.addEventListener('resize', () => {
      equipmentUsageChart.resize();
    });
  } catch (error) {
    console.error(error);
  }
});

const productionEfficiencyChartRef = ref(null);
const productionEfficiencyChartContainerRef = ref(null);
onMounted(async () => {
  try {
    const response = await axios.get('/api/production/efficiency');
    productionEfficiencyChartRef.value = response.data;

    const productionEfficiencyChart = echarts.init(productionEfficiencyChartContainerRef.value);
    const xAxisData = productionEfficiencyChartRef.value.map(entry => entry.date);
    const seriesData = productionEfficiencyChartRef.value.map(entry => entry.efficiency);

    const option = {
      color: ['#67C23A'],
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        }
      },
      xAxis: {
        type: 'category',
        data: xAxisData
      },
      yAxis: {
        type: 'value',
        minInterval: 1
      },
      series: [{
        data: seriesData,
        type: 'line'
      }]
    };
    productionEfficiencyChart.setOption(option);

    window.addEventListener('resize', () => {
      productionEfficiencyChart.resize();
    });
  } catch (error) {
    console.error(error);
  }
});

const notices = ref([
  { date: '2023-01-04', content: '下周一将进行设备维护，请生产部员工注意安全。' },
  { date: '2023-01-03', content: '本周五将举行生产部安全培训，请大家准时参加。' },
  { date: '2023-01-01', content: '新年快乐！' },
]);

const activities = ref([
  { title: '设备升级', description: '生产部将于本月底进行设备升级，请相关人员提前做好准备。' },
  { title: '安全演练', description: '本周三将进行安全演练，请大家积极参与并遵守相关规定。' },
  { title: '技术交流会', description: '下周将举行技术交流会，邀请各位同事分享生产中的技术经验。' },
]);

const todos = ref([
  { task: '生产计划编制', deadline: '2023-01-10', status: '进行中' },
  { task: '设备维护', deadline: '2023-01-12', status: '未开始' },
  { task: '安全检查', deadline: '2023-01-15', status: '已完成' },
]);

const attendances = ref([
  { name: '张三', date: '2023-01-04', status: '正常' },
  { name: '李四', date: '2023-01-04', status: '迟到' },
  { name: '王五', date: '2023-01-04', status: '请假' },
]);
</script>

<style lang="scss" scoped src="@/styles/home.css"></style>
