<template>
  <div>
    <div class="header">
      <div class="header-left">
        <img src="@/assets/img/logo.png" alt="Logo" class="logo">
        <div class="title">轧钢厂管理系统</div>
      </div>
      <div class="header-right">
        <img :src="employee.avatar || defaultAvatar" alt="User Avatar" class="avatar">
        <span class="welcome-text">欢迎您: {{ employee.id }}</span>
      </div>
    </div>

    <div class="main-content">
      <div class="sidebar">
        <el-menu
            router
            :default-active="$route.path"
            :default-openeds="['/personnel/userhome', 'order', 'manage']"
        >
          <el-menu-item index="/personnel/Home">
            <el-icon><HomeFilled /></el-icon>
            <span>系统首页</span>
          </el-menu-item>

          <el-sub-menu index="order">
            <template #title>
              <el-icon><Memo /></el-icon>
              <span>智能聘用模型</span>
            </template>
            <el-menu-item index="/personnel/ModelParam">
              <el-icon><Document /></el-icon>
              <span>模型参数</span>
            </el-menu-item>
            <el-menu-item index="/personnel/ModelTrain">
              <el-icon><Document /></el-icon>
              <span>模型训练</span>
            </el-menu-item>
          </el-sub-menu>

          <el-menu-item index="/personnel/ApplyReview">
            <el-icon><Document /></el-icon>
            <span>聘用审核</span>
          </el-menu-item>

          <el-menu-item index="/personnel/EmployeeManage">
            <el-icon><Document /></el-icon>
            <span>员工管理</span>
          </el-menu-item>

          <el-menu-item index="/" @click="logout">
            <el-icon><SwitchButton /></el-icon>
            <span>退出系统</span>
          </el-menu-item>
        </el-menu>
      </div>

      <div class="content">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
const defaultAvatar = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
const employee = JSON.parse(localStorage.getItem('user') || '{}')
const $route = useRoute()
const logout = () => {
  localStorage.removeItem('employee')
}
</script>

<style lang="scss" scoped src="@/styles/homepage.css"></style>
