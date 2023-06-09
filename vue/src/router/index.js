import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from "../views/layout/IndexLayout.vue"
import Login from "../views/login/IndexLogin.vue"
import HomeIndex from "@/views/home/HomeIndex"
import UserInfo from "../views/user/UserInformation"
import BankAccount from "../views/user/BankAccount"
import ManifestCreate from "../views/manifest/manifestCreate"
import ManifestMange from "../views/manifest/manifestManage"
import BillCreate from "../views/bill/BillCreate"
import BillMange from "../views/bill/BillManage"
import Transport from "../views/transport/transportManifest"

Vue.use(VueRouter)

const routes = [
  {
    path:"/home",
    component:Layout,
    //配置二级路由
    children:[
      {
        path:"/home",
        name:"home",
        component:HomeIndex
      },
      {
        path:"/user/user-info",
        name:"userInfo",
        component: UserInfo
      },
      {
        path:"/user/bank-account",
        name:"bankAccount",
        component:BankAccount
      },
      {
        path:"/manifest/create",
        name:"createOneMani",
        component:ManifestCreate

      },
      {
        path:"/manifest/manage",
        name:"manageMani",
        component:ManifestMange
      },
      {
        path:"/bill/create",
        name:"createOneBill",
        component:BillCreate
      },
      {
        path:"/bill/manage",
        name:"manageBill",
        component:BillMange
      },
      {
        path:"/transport/transport-plan",
        name:"transportPlan",
        component:Transport
      }
    ]
  },
  {
    path:"/",
    name:"login",
    component: Login
  }
]

const router = new VueRouter({
  mode:'history',
  base:process.env.BASE_URL,
  routes
})

export default router
