<template>
  <div>
    <div class="header">
      <div class="header-left">
        <img src="@/assets/img/logo.png" alt="Logo" class="logo">
        <div class="title">轧钢厂管理系统</div>
      </div>
      <div class="header-right">
        <img :src="employee.avatar || defaultAvatar" alt="User Avatar" class="avatar">
        <span class="user-info">采购部: {{ employee.person.name }}</span>
      </div>
    </div>

    <div class="main-content">
      <div class="sidebar">
        <el-menu
            router
            :default-active="$route.path"
            :default-openeds="['/purchasing/userhome', 'order', 'manage']"
        >
          <el-menu-item index="/purchasing/Home">
            <el-icon><HomeFilled /></el-icon>
            <span>系统首页</span>
          </el-menu-item>

          <el-menu-item index="/purchasing/OrderCheck">
            <el-icon><Document /></el-icon>
            <span>查看采购单</span>
          </el-menu-item>
          <el-menu-item index="/purchasing/OrderSubmit">
            <el-icon><Document /></el-icon>
            <span>提交采购单</span>
          </el-menu-item>

          <el-menu-item index="/person" v-if="employee.position === 'sales'">
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

<script setup>
import { useRoute } from 'vue-router';
import {User} from "@element-plus/icons-vue";
import router from "@/router/index.js";

const defaultAvatar = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png';
const employee = JSON.parse(localStorage.getItem('user') || '{}');
const $route = useRoute();
const logout = () => {
  localStorage.removeItem('employee');
}
</script>

<style lang="scss" scoped src="@/styles/homepage.css"></style>