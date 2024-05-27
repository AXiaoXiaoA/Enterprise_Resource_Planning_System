import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueJsx(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    proxy: {
      // 定义代理规则，"/api" 是代理请求的匹配前缀
      '/api': {
        // 目标服务器地址，即后端服务地址
        target: 'http://localhost:8081/',
        changeOrigin: true, // 是否改变请求源
        rewrite: (path) => path.replace(/^\/api/, '') // 可选，重写请求路径
      }
    }
  }
})
