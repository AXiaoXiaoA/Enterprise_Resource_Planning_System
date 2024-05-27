<template>
  <div>
    <LoginBackground/>
    <div class="container">
      <div class="box">
        <div class="title">入 职 申 请</div>
        <el-form :model="data.form" ref="formRef" :rules="rules">
          <el-form-item label="您的名字是？" prop="name">
            <el-input prefix-icon="User" v-model="data.form.name" placeholder="姓名"/>
          </el-form-item>
          <el-form-item label="您的年龄是？" prop="age">
            <el-input v-model="data.form.age" placeholder="年龄" />
          </el-form-item>
          <el-form-item label="您的性别是？" prop="gender">
            <div class="mb-2 flex items-center text-sm">
              <el-radio-group v-model="data.form.gender" class="ml-4">
                <el-radio class="male-color" label="male" size="large">男</el-radio>
                <el-radio class="female-color" label="female" size="large">女</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>
          <el-form-item label="您希望申请什么部门？" prop="department">
            <el-select v-model="data.form.department" placeholder="部门">
              <el-option label="销售部" value="sales"/>
              <el-option label="仓储部" value="repo"/>
              <el-option label="人事部" value="personnel"/>
              <el-option label="生产部" value="production"/>
              <el-option label="采购部" value="purchase"/>
            </el-select>
          </el-form-item>
          <el-form-item label="您希望申请什么岗位？" prop="position">
            <el-select v-model="data.form.position" placeholder="岗位">
              <el-option label="总经理" value="manager"/>
              <el-option label="普通职员" value="worker"/>
            </el-select>
          </el-form-item>
          <el-upload class="upload-demo" drag :file-list="data.form.fileList" multiple :auto-upload="false" @change="handleFileChange" :before-upload="beforeUpload">
            <el-button slot="trigger" type="small" color="primary">选择文件</el-button>
            <div slot="tip" class="el-upload__tip">文件必须小于 500KB，支持 txt 格式。</div>
          </el-upload>

          <div class="button-container">
            <el-button type="primary" class="button" @click="register">申 请 入 职</el-button>
          </div>
        </el-form>
        <div class="link">已有账号？<a href="/">直接登录</a></div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue"
import {ElMessage} from "element-plus";
import router from "@/router";
import axios from 'axios';
import LoginBackground from '../../components/LoginBG.vue';

const data = reactive({
  form: {
    name: '',
    gender: '',
    age:'',
    position: '',
    department: '',
    resume: null,
    fileList: []
  }
});

const handleFileChange = (file) => {
  if (file.raw) {
    data.form.resume = file.raw;
  }
};

const beforeUpload = (file) => {
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (!isLt2M) {
    ElMessage.error('上传的文件大小不能超过 2MB!');
    return false;
  }
  return true;
};

const rules = reactive({
  name: [
    {required: true, message: '我们希望知道您的名字', trigger: 'blur'},
  ],
  gender: [
    {required: true, trigger: 'change'}
  ],
  age:[
    {required: true,trigger:'change'}
  ],
  position: [
    {required: true, trigger: 'change'}
  ],
  department: [
    {required: true, trigger: 'change'}
  ]
});

const formRef = ref();

const register = () => {
  if (formRef.value.validate()) {
    let EmployeeApply = new FormData();
    EmployeeApply.append('name', data.form.name);
    EmployeeApply.append('gender', data.form.gender);
    EmployeeApply.append('age', data.form.age);
    EmployeeApply.append('position', data.form.position);
    EmployeeApply.append('department', data.form.department);
    if (data.form.resume) {
      EmployeeApply.append('resume', data.form.resume);
    }

    axios.post('/api/register', EmployeeApply, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    }).then(res => {
      if (res.data.code === '200') {
        ElMessage.success('入职申请成功');
        router.push('/');
      } else {
        ElMessage.error(res.data.msg);
      }
    }).catch(error => {
      ElMessage.error('提交失败: ' + error.message);
    });
  }
};
</script>

<style scoped lang="scss" src="@/styles/login.css"></style>
