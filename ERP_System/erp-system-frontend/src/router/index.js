import { createRouter, createWebHistory } from 'vue-router'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/', name: 'Login', component: () => import('../views/employee/UserLogin.vue')
    },
    {
      path:'/register', name:'Register', component:() => import('../views/employee/UserRegister.vue')
    },
    //人事部门界面
    {
      path: '/Personnel/Homepage', name: 'personnel_homepage', component: () => import('../views/personnel/Homepage.vue'), redirect:'/personnel/Home',
      children:[
        {
          path:'/Personnel/Home', name:'personnel_home', component:()=>import('@/views/personnel/Home.vue'),
        },
        {
          path:'/Personnel/ModelTrain', name:'personnel_modelTrain', component:()=>import('@/views/personnel/ModelTrain.vue'),
        },
        {
          path:'/Personnel/ApplyReview', name:'personnel_applyReview', component:()=>import('@/views/personnel/ApplyReview.vue'),
        },
        {
          path:'/Personnel/EmployeeManage', name:'personnel_employeeManage', component:()=>import('@/views/personnel/EmployeeManage.vue'),
        }
      ]
    },
    //销售部门界面
    {
      path: '/sales/Homepage',
      name: 'Sales_homepage',
      component: () => import('../views/sales/Homepage.vue'),
      redirect: '/sales/Home',
      children:[
        {
          path:'/sales/Home',name:'sales_home',component:()=>import('@/views/sales/Home.vue')
        },
        {
          path:'/sales/OrderCheck',name:'sales_orderCheck',component:()=>import('@/views/sales/OrderCheck.vue'),
        },
        {
          path:'/sales/OrderSubmit',name:'sales_orderSubmit',component:()=>import('@/views/sales/OrderSubmit.vue')
        },
        {
          path:'/sales/OrderProcess',name:'sales_orderProcess',component:()=>import('@/views/sales/OrderProcess.vue')
        }
      ]
    },
  ]
})

export default router
