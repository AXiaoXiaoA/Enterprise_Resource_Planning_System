<template>
  <div class="container">
    <div class="table-container">
      <h2>采购订单</h2>
      <el-form :model="form" ref="formRef" :rules="rules" label-width="120px" class="demo-ruleForm">
        <el-form-item label="产品" prop="materialName">
          <el-autocomplete v-model="form.materialName" placeholder="请输入产品名称" :fetch-suggestions="querySearch" @select="handleSelect">
            <template #suffix>
              <el-button type="primary" icon="el-icon-search"></el-button>
            </template>
          </el-autocomplete>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" class="el-input" placeholder="请输入价格" type="number"></el-input>
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input v-model="form.quantity" class="el-input" placeholder="请输入数量" type="number"></el-input>
        </el-form-item>
        <el-form-item label="公司名称" prop="companyName">
          <el-input v-model="form.companyName" class="el-input" placeholder="请输入公司名称"></el-input>
        </el-form-item>
        <el-form-item label="公司联系方式" prop="companyTel">
          <el-input v-model="form.companyTel" class="el-input" placeholder="请输入公司联系方式" type="number"></el-input>
        </el-form-item>
        <el-form-item label="出发地" prop="departure">
          <el-input v-model="form.departure" class="el-input" placeholder="请输入出发地"></el-input>
        </el-form-item>
        <el-form-item label="订单描述" prop="description">
          <el-input type="textarea" v-model="form.description" placeholder="请输入订单描述"></el-input>
        </el-form-item>
        <el-form-item label="合同 (PDF)" prop="contract">
          <el-upload
              class="upload-demo"
              action="#"
              :file-list="form.contract"
              :on-remove="handleRemove"
              :before-upload="beforeUpload"
              :auto-upload="false"
              :on-change="handleUploadChange">
            <el-button type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
    </div>
    <div class="button-container">
      <el-button class="el-button" type="primary" @click="submitOrder">提交订单</el-button>
      <el-button class="el-button" type="primary" @click="resetOrder">重置</el-button>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { ElMessage, ElForm, ElButton, ElInput, ElAutocomplete, ElUpload } from 'element-plus';
import axios from "axios";

// 表单数据
const employee = JSON.parse(localStorage.getItem('user') || '{}');
const form = reactive({
  purchasingEmployeeId:employee.id,
  materialName: '',
  price: '',
  quantity: '',
  companyName: '',
  companyTel: '',
  departure: '',
  description: '',
  contract: [],
  contractData: '',
  status: '',
});
const rules = {
  materialName: [{ required: true, message: '请选择产品', trigger: 'blur' }],
  price: [{ required: true, message: '请输入产品价格', trigger: 'blur' }],
  quantity: [{ required: true, message: '请输入产品数量', trigger: 'blur' }],
  companyName: [{ required: true, message: '请输入公司名称', trigger: 'blur' }],
  companyTel: [{ required: true, message: '请输入公司联系方式', trigger: 'blur' }],
  departure: [{ required: true, message: '请输入出发地', trigger: 'blur' }],
  description: [{ required: true, message: '请输入订单描述', trigger: 'blur' }],
};
const materials = ref([
  { ID: 1, NAME: '铁矿石' },
  { ID: 2, NAME: '铬矿石' },
  { ID: 3, NAME: '煤炭' },
  { ID: 4, NAME: '石灰石' },
  { ID: 5, NAME: '锰矿石' },
  { ID: 6, NAME: '石墨电极' },
  { ID: 7, NAME: '钒矿石' },
  { ID: 8, NAME: '硅铁' },
  { ID: 9, NAME: '铝矿石' },
  { ID: 10, NAME: '钛矿石' },
  { ID: 11, NAME: '铜矿石' },
  { ID: 12, NAME: '锌矿石' },
  { ID: 13, NAME: '镍矿石' },
  { ID: 14, NAME: '钴矿石' }
]);
const querySearch = (queryString, cb) => {
  const results = materials.value
      .filter(material => material.NAME.toLowerCase().includes(queryString.toLowerCase()))
      .map(material => ({ value: material.NAME }));
  cb(results);
};
const handleSelect = (item) => {
  form.materialName = item.value;
};

// 上传合同文件
const handleRemove = (file, fileList) => {
  form.contract = fileList;
  if (fileList.length === 0) {
    form.contractData = '';
  }
};
const beforeUpload = (file) => {
  const isPDF = file.type === 'application/pdf';
  const isLt10M = file.size / 1024 / 1024 < 10;
  if (!isPDF) {
    ElMessage.error('上传的文件格式只能是PDF!');
  }
  if (!isLt10M) {
    ElMessage.error('上传的文件大小不能超过10MB!');
  }
  if (isPDF && isLt10M) {
    const reader = new FileReader();
    reader.onload = (e) => {
      form.contractData = e.target.result;
    };
    reader.readAsDataURL(file);
  }
  return false;
};
const handleUploadChange = (file, fileList) => {
  form.contract = fileList;
  if (fileList.length > 0) {
    console.log('Uploaded file response:', fileList[0].response);
    form.contractData = fileList[0].response;
  } else {
    form.contractData = '';
  }
};

// 提交和重置表单
const formRef = ref(null);
const submitOrder = () => {
  formRef.value.validate((valid) => {
    if (employee.position === '部长') {
      form.status = '等待仓储部审批';
    } else {
      form.status = '等待部长审批';
    }
    if (valid) {
      axios.post('/api/purchasing/submitOrder', form)
          .then(response => {
            console.log(response.data);
            const res = response.data;
            ElMessage.success("订单提交成功");
          })
          .catch(error => {
            ElMessage.error("订单提交失败");
          });
    } else {
      ElMessage.error('表单验证失败!');
      return false;
    }
  });
};
const resetOrder = () => {
  formRef.value.resetFields();
  form.purchasingEmployeeId = employee.id;
  form.contract = [];
  form.contractData = '';
};
</script>

<style lang="scss" src="@/styles/table.css"></style>
