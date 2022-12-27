import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css';

//使用axios做AJAX
import request from "@/utils/request";
Vue.prototype.request=request

Vue.config.productionTip = false
Vue.use(ElementUI,{size: "small"});
// import axios from 'axios';
// Vue.prototype.$axios=axios;


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
