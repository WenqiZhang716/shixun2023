import Vue from 'vue'
import App from './App.vue'

import 'element-ui/lib/theme-chalk/index.css';
import router from './router'
import store from './store'
import './plugins/element.js'
import 'normalize.css'
import '@/assets/css/base.css'
import axios from 'axios'
Vue.config.productionTip = false;
Vue.prototype.$axios=axios;
axios.defaults.baseURL='/api';
axios.defaults.headers.post['Content-Type']='application/json';
//进行token
axios.interceptors.request.use(function (config) {
  // 放入token信息
  let token = localStorage.getItem('token'); // jwt-密钥，加密的内容-id-过期时间
  if(token){
    config.headers.Authorization = "Bearer "+token;
  }
  // 在发送请求之前做些什么
  return config;
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error);
});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
