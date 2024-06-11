<template>
  <div>
    <LoginBackground/>
    <div class="container">
      <div class="box">
        <div class="title">入 职 申 请</div>
        <el-form :model="data.form" ref="formRef" :rules="rules">
          <el-form-item label="姓名" prop="name">
            <el-input prefix-icon="User" v-model="data.form.name" placeholder="姓名"/>
          </el-form-item>
          <el-form-item label="身份证号" prop="id">
            <el-input v-model="data.form.id" placeholder="身份证号"/>
          </el-form-item>
          <el-form-item label="性别" prop="gender">
            <div class="mb-2 flex items-center text-sm">
              <el-radio-group v-model="data.form.gender" class="ml-4">
                <el-radio class="male-color" label="男" size="large">男</el-radio>
                <el-radio class="female-color" label="女" size="large">女</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>
          <el-form-item label="民族" prop="nationality">
            <el-input v-model="data.form.nationality" placeholder="民族"/>
          </el-form-item>
          <el-form-item label="出生日期" prop="birthday">
            <el-date-picker v-model="data.form.birthday" placeholder="生日" type="date" />
          </el-form-item>
          <el-form-item label="出生地" prop="birthplace">
            <el-input v-model="data.form.birthplace" placeholder="出生地"/>
          </el-form-item>
          <el-form-item label="政治面貌" prop="politicalStatus">
            <el-input v-model="data.form.politicalStatus" placeholder="政治面貌"/>
          </el-form-item>
          <el-form-item label="联系方式" prop="tel">
            <el-input v-model="data.form.tel" placeholder="联系方式"/>
          </el-form-item>
          <el-form-item label="电子邮件" prop="email">
            <el-input v-model="data.form.email" placeholder="电子邮件"/>
          </el-form-item>
          <el-form-item label="申请部门" prop="department">
            <el-select v-model="data.form.department" placeholder="部门">
              <el-option label="销售部" value="sales"/>
              <el-option label="仓储部" value="repo"/>
              <el-option label="人事部" value="personnel"/>
              <el-option label="生产部" value="production"/>
              <el-option label="采购部" value="purchasing"/>
            </el-select>
          </el-form-item>
          <el-form-item label="申请岗位" prop="position">
            <el-select v-model="data.form.position" placeholder="岗位">
              <el-option label="部长" value="部长"/>
              <el-option label="部员" value="部员"/>
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
    id: '',
    gender: '',
    nationality: '',
    birthday: '',
    birthplace: '',
    politicalStatus: '',
    tel:'',
    email: '',
    department: '',
    position: '',
    resume: null,
    fileList: []
  }
});

const rules = reactive({
  name: [
    {required: true, message: '请输入您的姓名', trigger: 'blur'},
  ],
  id: [
    {required: true, message: '请输入您的身份证号码', trigger: 'blur'},
  ],
  gender: [
    {required: true, message: '请选择您的性别', trigger: 'change'}
  ],
  nationality: [
    {required: true, message: '请输入您的民族', trigger: 'blur'}
  ],
  birthday: [
    {required: true, type: 'date', message: '请选择您的生日', trigger: 'change'}
  ],
  birthplace: [
    {required: true, message: '请输入您的出生地', trigger: 'blur'}
  ],
  politicalStatus: [
    {required: true, message: '请输入您的政治面貌', trigger: 'blur'}
  ],
  tel: [
    {required: true, message: '请输入有效的电话号码', trigger: 'blur'}
  ],
  email: [
    {required: true, type: 'email', message: '请输入有效的电子邮件地址', trigger: 'blur'}
  ],
  department: [
    {required: true, message: '请选择应聘部门', trigger: 'change'}
  ],
  position: [
    {required: true, message: '请选择应聘职位', trigger: 'change'}
  ],
  resume: [
    {required: true, message: '请上传您的简历', trigger: 'change'}
  ]
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

const formRef = ref();
const formatDate = (date) => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};
const register = () => {
  if (formRef.value.validate()) {
    let EmployeeApply = new FormData();
    EmployeeApply.append('name', data.form.name);
    EmployeeApply.append('id', data.form.id);
    EmployeeApply.append('gender', data.form.gender);
    EmployeeApply.append('nationality', data.form.nationality);
    const formattedBirthday = formatDate(data.form.birthday);
    EmployeeApply.append('birthday', formattedBirthday);
    EmployeeApply.append('birthplace', data.form.birthplace);
    EmployeeApply.append('politicalStatus', data.form.politicalStatus);
    EmployeeApply.append('tel', data.form.tel);
    EmployeeApply.append('email', data.form.email);
    EmployeeApply.append('department', data.form.department);
    EmployeeApply.append('position', data.form.position);
    EmployeeApply.append('resume', data.form.resume);

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
