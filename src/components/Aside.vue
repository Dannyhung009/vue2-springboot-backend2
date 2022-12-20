<template>
  <el-menu :default-openeds="['1', '3']" style="min-height: 100vh; overflow-x: hidden"
           background-color="rgb(48,65,86)"
           text-color="#fff"
           active-text-color="#ffd04d"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
           @select="handleSelect"
  >
    <!--          LOGO區域-->
    <div style="height:60px;line-height: 60px;text-align: center">
      <img src="../assets/logo.png" width="20px" style="position: relative; top: 5px;margin-right: 5px">
      <b style="color:orange" v-show="logoTextShow">後台管理系統</b>
    </div>
    <el-menu-item index="/">
      <template slot="title">
        <i class="el-icon-house"></i>
        <span slot="title">主頁</span>
      </template>

    </el-menu-item>

    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span slot="title">系統管理</span>
      </template>
      <el-menu-item index="/user">
        <template slot="title">
          <i class="el-icon-s-custom"></i>
          <span slot="title">用戶管理</span>
        </template>
      </el-menu-item>



    </el-submenu>

  </el-menu>
</template>

<script>
export default {
  name: "Aside.vue",
  props: {
    logoTextShow: Boolean,
    isCollapse: Boolean
  },
  methods:{
    handleSelect(index){
      console.log(index)
      console.log(this.$router.options.routes)
    }
  }
}
</script>

<style scoped>

</style>