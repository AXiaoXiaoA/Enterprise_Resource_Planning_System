<template>
  <div>
    <LoginBackground />
    <div class="container">
      <div class="box">
        <div class="title">登  录</div>
        <el-form :model="data.form" ref="formRef" :rules="rules">
          <el-form-item prop="tel">
            <el-input placeholder="请输入账号" prefix-icon="UserFilled" v-model="data.form.tel" />
          </el-form-item>
          <el-form-item prop="password">
            <el-input show-password placeholder="请输入密码" prefix-icon="Lock" v-model="data.form.password" />
          </el-form-item>
          <el-form-item>
            <div class="button-container">
              <el-button type="primary" class="button" @click="login">登录</el-button>
            </div>
          </el-form-item>
        </el-form>
        <div class="link"><a href="/register">申请入职</a></div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import router from '@/router';
import axios from 'axios';
import LoginBackground from '../../components/LoginBG.vue';

const data = reactive({
  form: {}
});

const rules = reactive({
  tel: [
    { required: true, message: '账号不可以为空', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '密码不可以为空', trigger: 'blur' },
  ],
});

const formRef = ref();

const login = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      axios.post('/api/login', data.form).then(res => {
        res = res.data;
        if (res.code === '200') {
          ElMessage.success('登录成功');
          localStorage.setItem('user', JSON.stringify(res.data));
          let user = localStorage.getItem('user');
          console.log(JSON.parse(user));

          let department = JSON.parse(user).department;
          console.log(department);
          router.push('/' + department + '/homepage');
        } else {
          ElMessage.error(res.msg);
        }
      });
    }
  });
};
</script>

<style scoped lang="scss" src="@/styles/login.css"></style>