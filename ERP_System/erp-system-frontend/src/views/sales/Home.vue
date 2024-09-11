<template>
  <div class="home-container">
    <el-card class="home-card">
      <div class="home-content">
        <el-row>
          <el-col :span="24">
            <h1>欢迎使用轧钢厂管理系统！</h1>
            <p class="department-info">您所在的部门是：销售部！</p>
            <p class="greeting">祝您有个美好的一天！</p>
          </el-col>
        </el-row>

        <el-divider></el-divider>

        <!-- 数据概览 -->
        <el-row :gutter="20" class="section">
          <el-col :span="8">
            <el-card shadow="hover" class="overview-card">
              <h3>总销售额</h3>
              <p class="overview-number">233</p>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" class="overview-card">
              <h3>本月销售额</h3>
              <p class="overview-number">79</p>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" class="overview-card">
              <h3>今日订单</h3>
              <p class="overview-number">1</p>
            </el-card>
          </el-col>
        </el-row>

        <el-divider></el-divider>

        <!-- 销售统计图表 -->
        <el-row :gutter="20" class="section">
          <el-col :span="12">
            <el-card shadow="hover" class="chart-card">
              <h2>销售额趋势</h2>
              <div ref="salesTrendChartContainerRef" class="chart-container"></div>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card shadow="hover" class="chart-card">
              <h2>订单分布</h2>
              <div ref="ordersDistributionChartContainerRef" class="chart-container"></div>
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
import { computed, onMounted, ref } from 'vue';
import axios from 'axios';
import * as echarts from 'echarts';

const totalSales = ref(0);
const monthlySales = ref(0);
const todayOrders = ref(0);

onMounted(async () => {
  try {
    const salesResponse = await axios.get('/api/sales/totalSales');
    totalSales.value = salesResponse.data.total;

    const monthlySalesResponse = await axios.get('/api/sales/monthlySales');
    monthlySales.value = monthlySalesResponse.data.monthly;

    const todayOrdersResponse = await axios.get('/api/sales/todayOrders');
    todayOrders.value = todayOrdersResponse.data.orders;
  } catch (error) {
    console.error(error);
  }
});

const salesTrendChartRef = ref(null);
const salesTrendChartContainerRef = ref(null);
onMounted(async () => {
  try {
    const response = await axios.get('/api/sales/salesTrend');
    salesTrendChartRef.value = response.data;

    const salesTrendChart = echarts.init(salesTrendChartContainerRef.value);
    const xAxisData = salesTrendChartRef.value.map(entry => entry.date);
    const seriesData = salesTrendChartRef.value.map(entry => entry.sales);

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
    salesTrendChart.setOption(option);

    window.addEventListener('resize', () => {
      salesTrendChart.resize();
    });
  } catch (error) {
    console.error(error);
  }
});

const ordersDistributionChartRef = ref(null);
const ordersDistributionChartContainerRef = ref(null);
onMounted(async () => {
  try {
    const response = await axios.get('/api/sales/ordersDistribution');
    ordersDistributionChartRef.value = response.data;

    const ordersDistributionChart = echarts.init(ordersDistributionChartContainerRef.value);
    const seriesData = ordersDistributionChartRef.value.map(entry => ({
      value: entry.count,
      name: entry.region
    }));

    const option = {
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        orient: 'vertical',
        left: 'left',
      },
      series: [
        {
          name: '订单分布',
          type: 'pie',
          radius: '50%',
          data: seriesData,
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };
    ordersDistributionChart.setOption(option);

    window.addEventListener('resize', () => {
      ordersDistributionChart.resize();
    });
  } catch (error) {
    console.error(error);
  }
});

const notices = ref([
  { date: '2023-01-04', content: '下周一将举行销售策略会议，请销售部员工准时参加。' },
  { date: '2023-01-03', content: '本周五将进行销售技能培训，请大家积极参加。' },
  { date: '2023-01-01', content: '新年快乐！' },
]);
const activities = ref([
  { title: '年度销售总结会', description: '欢迎参加年度销售总结会，共同回顾过去一年的销售情况。' },
  { title: '客户联谊会', description: '本周将举行客户联谊会，欢迎大家积极参加。' },
  { title: '销售技能培训', description: '下周将有专家为我们进行销售技能培训，请大家积极参与。' },
]);
const todos = ref([
  { task: '客户拜访', deadline: '2023-01-10', status: '进行中' },
  { task: '销售报告', deadline: '2023-01-12', status: '未开始' },
  { task: '市场调研', deadline: '2023-01-15', status: '已完成' },
]);
const attendances = ref([
  { name: '张三', date: '2023-01-04', status: '正常' },
  { name: '李四', date: '2023-01-04', status: '迟到' },
  { name: '王五', date: '2023-01-04', status: '早退' },
]);

onMounted(() => {
  // 销售额趋势图
  const salesTrendChart = echarts.init(salesTrendChartContainerRef.value);
  const salesTrendOption = {
    xAxis: { type: 'category', data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'] },
    yAxis: { type: 'value' },
    series: [{ data: [120, 130, 140, 135, 144, 123, 150], type: 'line' }]
  };
  salesTrendChart.setOption(salesTrendOption);

  // 订单分布图
  const ordersDistributionChart = echarts.init(ordersDistributionChartContainerRef.value);
  const ordersDistributionOption = {
    tooltip: { trigger: 'item' },
    legend: { top: '5%', left: 'center' },
    series: [
      {
        name: '订单来源',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: '40',
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: [
          { value: 335, name: '' },
          { value: 310, name: '镀锌钢板' },
          { value: 234, name: '不锈钢管' },
          { value: 135, name: '热轧钢卷' },
          { value: 1548, name: '冷轧钢卷' }
        ]
      }
    ]
  };
  ordersDistributionChart.setOption(ordersDistributionOption);
});
</script>

<style lang="scss" scoped src="@/styles/home.css"></style>
