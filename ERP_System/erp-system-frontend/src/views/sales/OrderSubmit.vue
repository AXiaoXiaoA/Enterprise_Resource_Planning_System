<template>
  <div class="container">
    <div class="table-container">
      <h2>销售订单</h2>
      <el-form :model="form" ref="formRef" :rules="rules" label-width="120px" class="demo-ruleForm">
        <el-form-item label="产品" prop="productName">
          <el-autocomplete v-model="form.productName" placeholder="请输入产品名称" :fetch-suggestions="querySearch" @select="handleSelect">
            <template #suffix>
              <el-button type="primary" icon="el-icon-search"></el-button>
            </template>
          </el-autocomplete>
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input v-model="form.quantity" class="el-input" placeholder="请输入数量" type="number"></el-input>
        </el-form-item>
        <el-form-item label="公司" prop="companyName">
          <el-input v-model="form.companyName" class="el-input" placeholder="请输入公司名称"></el-input>
        </el-form-item>
        <el-form-item label="订单描述" prop="description">
          <el-input type="textarea" v-model="form.description" placeholder="请输入订单描述"></el-input>
        </el-form-item>
        <el-form-item label="合同 (PDF)" prop="contract">
          <el-upload
              class="upload-demo"
              action="#"
              :file-list="form.contract"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-upload="beforeUpload"
              :auto-upload="false">
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
const form = reactive({
  productName: '',
  quantity: '',
  companyName: '',
  description: '',
  contract: [],
  contractData: '',
});
const rules = {
  productName: [{ required: true, message: '请选择产品', trigger: 'blur' }],
  quantity: [{ required: true, message: '请输入产品数量', trigger: 'blur' }],
  companyName: [{ required: true, message: '请输入公司名称', trigger: 'blur' }],
  description: [{ required: true, message: '请输入订单描述', trigger: 'blur' }],
  contract: [{ required: true, message: '请上传合同', trigger: 'blur' }],
};
const products = ref([
  { ID: 1, NAME: '镀锌钢板' },
  { ID: 2, NAME: '不锈钢管' },
  { ID: 3, NAME: '热轧钢卷' },
  { ID: 4, NAME: '冷轧钢卷' },
  { ID: 5, NAME: '螺纹钢' },
  { ID: 6, NAME: '角钢' },
  { ID: 7, NAME: 'H型钢' },
  { ID: 8, NAME: '槽钢' },
  { ID: 9, NAME: '钢板' },
  { ID: 10, NAME: '钢筋' },
  { ID: 11, NAME: '钢丝' },
  { ID: 12, NAME: '钢丝绳' },
  { ID: 13, NAME: '钢丝网片' },
  { ID: 14, NAME: '钢丝网卷' },
]);
const querySearch = (queryString, cb) => {
  console.log('查询字符串:', queryString);
  const results = products.value
      .filter(product => product.NAME.toLowerCase().includes(queryString.toLowerCase()))
      .map(product => ({ value: product.NAME }));
  console.log('过滤结果:', results);
  cb(results);
};
const handleSelect = (item) => {
  form.productName = item.value;
};

// 上传合同文件
const handlePreview = (file) => {
  console.log('预览文件:', file);
};
const handleRemove = (file, fileList) => {
  console.log('移除文件:', file, fileList);
  form.contract = fileList;
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
  const reader = new FileReader();
  reader.onload = (e) => {
    form.contractData = e.target.result;
  };
  reader.readAsDataURL(file);
  return isPDF && isLt10M;
};

// 提交和重置表单
const formRef = ref(null);
const submitOrder = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      axios.post('/api/sales/submitOrder', form)
          .then(response => {
            const res = response.data;
            if (res.success) {
              ElMessage.success("订单提交成功");
              resetOrder();
            } else {
              ElMessage.error("订单提交失败: " + res.message);
            }
          })
          .catch(error => {
            ElMessage.error("订单提交失败");
            console.error('Error submitting order:', error);
          });
    } else {
      ElMessage.error('表单验证失败!');
      return false;
    }
  });
};
const resetOrder = () => {
  formRef.value.resetFields();
  form.contract = [];
  form.contractData = '';
};
</script>

<style lang="scss" src="@/styles/table.css"></style>
