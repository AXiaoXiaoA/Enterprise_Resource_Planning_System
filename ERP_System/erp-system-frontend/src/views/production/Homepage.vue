<template>
  <div>
    <div class="header">
      <div class="header-left">
        <img src="@/assets/img/logo.png" alt="Logo" class="logo">
        <div class="title">轧钢厂管理系统</div>
      </div>
      <div class="header-right">
        <img :src="employee.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" alt="User Avatar" class="avatar">
        <span class="user-info">生产部: {{ employee.person.name }}</span>
      </div>
    </div>

    <div class="main-content">
      <div class="sidebar">
        <el-menu
            router
            style="border: none"
            :default-active="$route.path"
            :default-openeds="['/production/userhome', 'order', 'manage']"
        >
          <el-menu-item index="/production/Home">
            <el-icon><HomeFilled /></el-icon>
            <span>系统首页</span>
          </el-menu-item>

          <el-menu-item index="/production/OrderCheck">
            <el-icon><Document /></el-icon>
            <span>处理订单</span>
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
const $route = useRoute()
const employee = JSON.parse(localStorage.getItem('user') || '{}')
const logout = () => {
  localStorage.removeItem('user')
}
</script>

<style lang="scss" scoped src="@/styles/homepage.css"></style>
