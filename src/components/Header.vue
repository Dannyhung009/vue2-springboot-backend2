<template>
  <div style=" font-size: 20px; line-height: 60px;display: flex;">

    <!--    收縮功能-->
    <div style="flex:1;font-size: 18px">
      <span :class="collapseBtnClass" style="cursor:pointer;font-size: 20px;" @click="collapse"></span>

      <!--    麵包屑-->
      <el-breadcrumb separator="/" style="display: inline-block;margin-left: 15px;">
        <el-breadcrumb-item :to="{ path: '/' }">首頁</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>


      </el-breadcrumb>
    </div>
    <!--下拉選單-->
    <el-dropdown style="width:100px; cursor: pointer">
      <div>
        <img :src="user.avatarUrl" style="width:30px;border-radius: 50%;position: relative;top: 10px;right:5px">
        <span><i class="el-icon-setting"></i> {{ user.nickname }} </span><i class="el-icon-arrow-down"
                                                                            style="margin-left: 5px"></i>
      </div>

      <el-dropdown-menu slot="dropdown" style="width:150px;text-align: center;">
        <el-dropdown-item style="font-size: 14px; padding:5px 0">
          <router-link to="/person"> 個人資訊</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding:5px 0">
          <!--          <router-link to="/login" style="text-decoration: none" @click="logout">退出</router-link>-->
          <span style="text-decoration: none" @click="logout">退出</span>
        </el-dropdown-item>

      </el-dropdown-menu>
    </el-dropdown>


  </div>
</template>

<script>
export default {
  name: "Header.vue",
  props: {
    collapseBtnClass: String,
    // collapse: Boolean,
    isCollapse: Boolean,
    pathName: String,
  },
  created() {
    // console.log(this.$route)
  },
  computed: {
    currentPathName() {
      return this.$store.state.currentPathName;  //需要監聽的數據
    }
  },
  watch: { //監聽路由變化
    '$route': function () {
      this.currentPathName = localStorage.getItem("currentPathName")
    }
  },
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      // paths:[],
      // currentPathName:""
    }
  },
  methods: {
    collapse() {  //點擊收縮按鈕觸發
      // this.isCollapse = !this.isCollapse
      // if (this.isCollapse == false) {
      //   this.sideWidth = 64
      //   this.collapseBtnClass = "el-icon-s-unfold"
      //   this.logoTextShow = false
      // } else {
      //   this.sideWidth = 200
      //   this.collapseBtnClass = 'el-icon-s-fold'
      //   this.logoTextShow = true
      // }
    },
    logout() {
      this.$router.push("/login");
      localStorage.removeItem("user");
      this.$message.success("登出成功");
    }
  }
}
</script>

<style scoped>

</style>