<script setup>
import { useRoute } from 'vue-router';
const defaultAvatar = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png';
const user = JSON.parse(localStorage.getItem('user') || '{}');
const $route = useRoute();
const logout = () => {
  localStorage.removeItem('user');
}
</script>

<template>
  <div>
    <div class="header">
      <div class="header-left">
        <img src="@/assets/img/logo.png" alt="Logo" class="logo">
        <div class="title">轧钢厂管理系统</div>
      </div>
      <div class="header-right">
        <img :src="user.avatar || defaultAvatar" alt="User Avatar" class="avatar">
        <span class="user-info">销售部: {{ user.name }}</span>
      </div>
    </div>

    <div class="main-content">
      <div class="sidebar">
        <el-menu
            router
            :default-active="$route.path"
            :default-openeds="['/sales/userhome', 'order', 'manage']"
        >
          <el-menu-item index="/sales/Home">
            <el-icon><HomeFilled /></el-icon>
            <span>系统首页</span>
          </el-menu-item>

          <el-menu-item index="/sales/OrderCheck">
            <el-icon><Document /></el-icon>
            <span>查看订单</span>
          </el-menu-item>
          <el-menu-item index="/sales/OrderSubmit">
            <el-icon><Document /></el-icon>
            <span>提交订单</span>
          </el-menu-item>
          <el-menu-item index="/sales/OrderProcess">
            <el-icon><Document /></el-icon>
            <span>处理订单</span>
          </el-menu-item>

          <el-menu-item index="/person" v-if="user.role === 'STUDENT'">
            <el-icon><User /></el-icon>
            <span>用户信息</span>
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

<style lang="scss" scoped src="@/styles/homepage.css"></style>