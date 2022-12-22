import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store"


Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        // name: 'Manage',
        component: () => import('../views/Manage.vue'),
        redirect: "/home",
        children: [
            {path: 'home', name: '首頁', component: () => import('../views/Home.vue')},
            {path: 'user', name: '使用者管理', component: () => import('../views/User.vue')},

            {path: 'person', name: '使用者個人資料', component: () => import('../views/Person.vue') },
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/Register.vue')
    },

    // {
    //   // path: '/about',
    //   // name: 'about',
    //   // // route level code-splitting
    //   // // this generates a separate chunk (about.[hash].js) for this route
    //   // // which is lazy-loaded when the route is visited.
    //   // component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
    // },
    // {
    //   path: '/login',
    //   name: 'Login',
    //   component: () => import('../views/Login.vue')
    // },

]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

router.beforeEach((to, from, next) => {  //設置當前的路由名稱，為了在Header組件中去使用
    // console.log("to",to)
    // console.log("from",from)
    localStorage.setItem("currentPathName", to.name)
    store.commit("setPath")  //觸發store的數據更新
    next()
//   if(to.path == '/login' || to.path == '/register'){
//
//     next();
//
//   }else{
//
//     alert('您还没有登录，请先登录');
//
//     next('/login');
//
//   }
//
})

export default router
