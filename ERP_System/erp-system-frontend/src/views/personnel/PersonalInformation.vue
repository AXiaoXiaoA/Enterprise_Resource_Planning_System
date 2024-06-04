<template>
  <div class="container">
    <el-card class="box-card">
      <h1>个人信息</h1>
      <el-form :model="person" ref="personForm" label-width="120px" class="demo-ruleForm">
        <el-form-item label="ID">
          <el-input v-model="person.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名" :rules="[{ required: true, message: '请输入姓名', trigger: 'blur' }]">
          <el-input v-model="person.name"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="person.gender" placeholder="选择性别">
            <el-option label="男" value="male"></el-option>
            <el-option label="女" value="female"></el-option>
            <el-option label="其他" value="other"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="国籍">
          <el-input v-model="person.nationality"></el-input>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker v-model="person.birthday" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="出生地">
          <el-input v-model="person.birthplace"></el-input>
        </el-form-item>
        <el-form-item label="政治面貌">
          <el-input v-model="person.politicalStatus"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="person.tel" type="tel"></el-input>
        </el-form-item>
        <el-form-item label="电子邮箱" :rules="[{ required: true, message: '请输入电子邮箱', trigger: 'blur' }, { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }]">
          <el-input v-model="person.email" type="email"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="savePerson">保存</el-button>
          <el-button @click="resetForm('personForm')">重置</el-button>
          <el-button type="danger" @click="deletePerson">删除</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { ElMessage, ElButton, ElInput, ElSelect, ElOption, ElDatePicker, ElCard, ElForm, ElFormItem } from 'element-plus';
import axios from 'axios';

// 表单数据
const person = reactive({
  id: '',
  name: '',
  gender: '',
  nationality: '',
  birthday: '',
  birthplace: '',
  politicalStatus: '',
  tel: '',
  email: ''
});

// 获取个人信息
const employee = JSON.parse(localStorage.getItem('user') || '{}')
const employeeDate = reactive({
  items: []
});
const fetchPerson = () => {
  axios.post('/api/getPersonalInfo', employee.id)
      .then(response => {
        const res = response.data;
        employeeDate.items = res.data.map(item => ({
          id: item.id ? item.id : '无',
          department: item.department ? item.department : '无',
          position: item.position ? item.position : '无',
          startDate: item.startDate ? item.startDate : '无',
          person: {
            name: item.person.name ? item.person.name : '无',
            gender: item.person.gender ? item.person.gender : '无',
            nationality: item.person.nationality ? item.person.nationality : '无',
            birthday: item.person.birthday ? item.person.birthday : '无',
            birthplace: item.person.birthplace ? item.person.birthplace : '无',
            politicalStatus: item.person.politicalStatus ? item.person.politicalStatus : '无',
            email: item.person.email ? item.person.email : '无',
            tel: item.person.tel ? item.person.tel : '无',
          },
        }));
      })
      .catch(error => {
        ElMessage.error('加载数据失败');
        console.error('Error:', error);
      });
};
//
// // 保存个人信息
// const savePerson = () => {
//   axios.post('/api/savePersonalInfo', person)
//       .then(response => {
//         ElMessage.success('保存成功');
//         console.log('Success:', response.data);
//       })
//       .catch(error => {
//         ElMessage.error('保存失败');
//         console.error('Error:', error);
//       });
// };

// // 重置表单
// const resetForm = (formName) => {
//   const form = document.querySelector(`[name=${formName}]`);
//   if (form) {
//     form.reset();
//   }
//   Object.keys(person).forEach(key => person[key] = '');
//   fetchPerson(); // 重新加载数据
// };

// // 删除个人信息
// const deletePerson = () => {
//   if (confirm('确认删除该记录吗？')) {
//     axios.delete(`/api/person/delete/${person.id}`)
//         .then(response => {
//           ElMessage.success('删除成功');
//           resetForm('personForm');
//           console.log('Success:', response.data);
//         })
//         .catch(error => {
//           ElMessage.error('删除失败');
//           console.error('Error:', error);
//         });
//   }
// };

// 组件挂载时获取数据
onMounted(() => {
  fetchPerson();
});
</script>

<style scoped>
.container {
  padding: 20px;
}

.box-card {
  margin: 20px;
}
</style>
