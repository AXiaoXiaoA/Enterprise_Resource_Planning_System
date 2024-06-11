<template>
    <div>
        <div class="title_box">
            <div class="content">
                <el-text class="mx-1" type="primary" size="large">PROMPTBERT v2.0
            </el-text>
             </div>
        </div>
        <div class="search_box">
            <el-upload
    v-model:file-list="fileList"
    class="upload-demo"
    action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
    :on-change="handleChange"
  >
    <el-button type="primary">上传训练文件</el-button>
    <template #tip>
      <div class="el-upload__tip">
        txt文件不可超过500kb
      </div>
    </template>
  </el-upload>

        </div>
        <div class = "train_box">
            <div class="training_status">
                <span style="font-weight:bold; color: green;">训练中</span>
            </div>
            <!-- 简单的CSS进度条 -->
            <div class="progress_bar">
                <div class="progress" style="width: 5%;"></div> <!-- 示例进度5% -->
            </div>
            <div class="progress_text">2/40</div>
            </div>
        <div class="operation_box">
            <el-button type="primary" round style="margin-left:10px;" @click="analysis">开始训练</el-button>
            <el-button type="danger" round @click="reset">终止训练</el-button>
        </div>

        <!-- 只有在 result 有值的情况下显示结果 -->
        <div class="result_box" v-if="result">
            <el-col :sm="15" :lg="6">
                <el-result
                    :icon="iconType"
                    :title="getfavor()"
                    :sub-title="getresult()"
                >
                </el-result>
            </el-col>
        </div>
    </div>
</template>

<style>
.search_box {
    display: flex;
    margin-bottom: 10px;
    background-color: white;
    align-items: center;
    border-radius: 5px;
    padding: 10px;
    width: 100%;
}

.title_box {
    display: flex;
    flex-direction: column;
    margin-bottom: 10px;
    background-color: white;
    align-items: center;
    border-radius: 5px;
    padding: 10px;
    width: 100%;
}

.train_box {
    display: flex;
    flex-direction: column;
    margin-bottom: 10px;
    background-color: white;
    align-items: center;
    border-radius: 5px;
    padding: 10px;
    width: 100%;
}

.progress_bar {
    width: 100%;
    background-color: #ddd;
    border-radius: 5px;
}

.progress {
    height: 20px;
    background-color: green;
    border-radius: 5px;
}

.progress_text {
    margin-top: 5px;
    font-size: 16px;
    color: #666;
}

.textarea_box .el-textarea__inner {
    width: 100%;
    min-height: 50px;
    line-height: normal;
    padding: 10px;
    resize: none;
}

.result_box {
    display: flex;
    justify-content: center;
    font-weight:bold;
    align-items: center;
}

.title_box .content .el-text {
    font-family: '宋体', sans-serif;
    font-weight: bold;
}

.title_box .writer .el-text {
    font-family: '宋体', sans-serif;
}

.progress_container {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
}

.progress_text {
    margin-top: 10px;
    font-size: 16px;
    color: #666;
}
</style>




<script setup>
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import axios from 'axios';

const fileList = ref([
  {
    name: 'food.jpeg',
    url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
  },
  {
    name: 'food2.jpeg',
    url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
  },
])

const handleChange = (uploadFile, uploadFiles) => {
  fileList.value = fileList.value.slice(-3)
}



const text = ref('');
const result = ref('');
const favor = ref('');
const iconType = ref('');

const analysis = () => {
    axios.post('/api/personnel/analysis', {content: text.value})
        .then(res => {
            result.value = res.data.data; // 假设这是正确的数据路径
            const lastIndex = result.value.lastIndexOf(":");
            favor.value = lastIndex !== -1 ? result.value.substring(lastIndex + 1).trim() : "";
            iconType.value = favor.value === "positive" ? 'success' : 'error';
            console.log(favor.value);
            console.log(iconType.value);
        })
        .catch(error => {
            ElMessage.error("分析失败");
            console.error('Error loading data:', error);
            result.value = '';
            favor.value = '';
            iconType.value = '';
        });
};

const reset = () => {
    text.value = '';
    result.value = '';
    favor.value = '';
    iconType.value = '';
};

const getresult = () =>{
    if(favor.value==='positive'){
        return '经舆论预测，您提交的文本是一个正面的文本';
    }else{
        return '经舆论预测，您提交的文本是一个负面的文本'
    }
}

const getfavor = () =>{
    if(favor.value==='positive'){
        return '正面舆论';
    }else{
        return '负面舆论'
    }
}
</script>

