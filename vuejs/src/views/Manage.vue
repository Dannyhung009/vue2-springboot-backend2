<template>
  <div class="home">
    <el-container style=" border: 1px solid #eee">
      <!--      側邊欄-->
      <el-aside :width="sideWidth+'px'"
                style="background-color: rgb(238, 241, 246); box-shadow: 2px 0 6px rgb(0 21 41/35%)">
        <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"/>
      </el-aside>

      <el-container>
        <!--        頂部-->
        <el-header style="border: 1px solid #ccc;">
        <Header :collapseBtnClass="collapseBtnClass" :isCollapse="isCollapse" />
        </el-header>
        <!--  主要區域-->
        <el-main>
<!--          表示當前頁面的子路由會在<router-view />裡面展示 -->
        <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from '@/components/HelloWorld.vue'
import request from "@/utils/request";
import Aside from "@/components/Aside"
import Header from "@/components/Header"
import User from "@/views/User";

export default {
  name: 'Manage',
  components: {Aside,Header,User},
  data() {
    // const item = {
    //   date: '2016-05-02',
    //   name: '王小虎',
    //   address: '上海市普陀区金沙江路 1518 弄'
    // };

    return {
      // msg: "hello 青哥哥",
      // tableData: Array(20).fill(item),

      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,





    }
  },
  created() {
    // this.load()

  },
  methods: {





  }

}
</script>
