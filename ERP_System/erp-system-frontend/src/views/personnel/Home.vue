<template>
  <div class="home-container">
    <el-card class="home-card">
      <div class="home-content">
        <el-row>
          <el-col :span="24">
            <h1>欢迎使用轧钢厂管理系统！</h1>
            <p class="department-info">您所在的部门是：人事部！</p>
            <p class="greeting">祝您有个美好的一天！</p>
          </el-col>
        </el-row>

        <el-divider></el-divider>

        <!-- 数据概览 -->
        <el-row :gutter="20" class="section">
          <el-col :span="8">
            <el-card shadow="hover" class="overview-card">
              <h3>员工总数</h3>
              <p class="overview-number">{{ totalEmployees }}</p>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" class="overview-card">
              <h3>当前应聘</h3>
              <p class="overview-number">{{ totalApply }}</p>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" class="overview-card">
              <h3>招聘指标</h3>
              <p class="overview-number">5</p>
            </el-card>
          </el-col>
        </el-row>

        <el-divider></el-divider>

        <!-- 员工统计图表 -->
        <el-row :gutter="20" class="section">
          <el-col :span="12">
            <el-card shadow="hover" class="chart-card">
              <h2>员工年龄分布</h2>
              <div ref="ageChartContainerRef" class="chart-container"></div>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card shadow="hover" class="chart-card">
              <h2>各部门人数</h2>
              <div ref="departmentChartContainerRef" class="chart-container"></div>
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
import {computed, onMounted, ref} from 'vue';
import axios from 'axios';
import * as echarts from 'echarts';

const employees = ref(null);
onMounted(async () => {
  try {
    const response = await axios.get('/api/personnel/employeeDistribution');
    employees.value = response.data;
  } catch (error) {
    console.error(error);
  }
});
const totalEmployees = computed(() => employees.value ?? 0);

const apply = ref(null);
onMounted(async () => {
  try {
    const response = await axios.get('/api/personnel/todayApplyDistribution');
    apply.value = response.data;
  } catch (error) {
    console.error(error)
  }
});
const totalApply = computed(() => apply.value ?? 0);

const ageChartRef = ref(null);
const ageChartContainerRef = ref(null);
onMounted(async () => {
  try {
    const response = await axios.get('/api/personnel/ageDistribution');
    ageChartRef.value = response.data;

    const ageChart = echarts.init(ageChartContainerRef.value);
    const xAxisData = ageChartRef.value.map(entry => `${entry['AGE_BRACKET']} - ${entry['AGE_BRACKET'] + 4}`);
    const seriesData = ageChartRef.value.map(entry => entry['COUNT']);

    const option = {
      color: ['#3398DB'],
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        },
        backgroundColor: 'rgba(0,0,0,0.7)',
        borderColor: '#333',
        borderWidth: 1,
        textStyle: {
          color: '#fff'
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
    ageChart.setOption(option);

    window.addEventListener('resize', () => {
      ageChart.resize();
    });
  } catch (error) {
    console.error(error);
  }
});

const departmentChartRef = ref(null);
const departmentChartContainerRef = ref(null);
onMounted(async () => {
  try {
    const response = await axios.get('/api/personnel/departmentDistribution');
    departmentChartRef.value = response.data;

    const departmentNameMap = {
      'personnel': '人事部',
      'repo': '仓储部',
      'purchasing': '采购部',
      'sales': '销售部',
      'production': '生产部'
    };

    const departmentChart = echarts.init(departmentChartContainerRef.value);
    const seriesData = departmentChartRef.value.map(entry => ({
      value: entry['COUNT'],
      name: departmentNameMap[entry['DEPARTMENT']]
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
          name: '部门人数',
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
    departmentChart.setOption(option);

    window.addEventListener('resize', () => {
      departmentChart.resize();
    });
    departmentChart.resize();

  } catch (error) {
    console.error(error);
  }
});


const notices = ref([
  { date: '2023-01-04', content: '下周一是法定假日，全公司放假一天。' },
  { date: '2023-01-03', content: '本周五将举行全员会议，请大家准时参加。' },
  { date: '2023-01-01', content: '新年快乐！' },
]);
const activities = ref([
  { title: '新年联欢会', description: '欢迎大家参加新年联欢会，共同庆祝新的一年的到来！' },
  { title: '员工培训', description: '本周五将举行员工培训，主题是提高团队协作能力。' },
  { title: '健康讲座', description: '下周一将有专家为我们做健康讲座，让我们一起关注健康。' },
]);
const todos = ref([
  { task: '完成报告', deadline: '2023-01-10', status: '进行中' },
  { task: '准备会议', deadline: '2023-01-12', status: '未开始' },
  { task: '更新项目', deadline: '2023-01-15', status: '已完成' },
]);
const attendances = ref([
  { name: '张三', date: '2023-01-04', status: '正常' },
  { name: '李四', date: '2023-01-04', status: '迟到' },
  { name: '王五', date: '2023-01-04', status: '早退' },
]);
</script>

<style lang="scss" scoped src="@/styles/home.css"></style>
