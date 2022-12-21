<template>
  <div style=" font-size: 20px; line-height: 60px;display: flex;">

    <div style="flex:1;font-size: 18px">
      <span :class="collapseBtnClass" style="cursor:pointer;font-size: 20px;" @click="collapse"></span>

      <!--    麵包屑-->
      <el-breadcrumb separator="/" style="display: inline-block;margin-left: 15px;">
        <el-breadcrumb-item :to="{ path: '/' }" >首頁</el-breadcrumb-item>
        <el-breadcrumb-item  >{{ currentPathName }}</el-breadcrumb-item>


      </el-breadcrumb>
    </div>

    <el-dropdown style="width:100px; cursor: pointer">

      <span><i class="el-icon-setting"></i> 王小虎 </span><i class="el-icon-arrow-down"
                                                           style="margin-left: 5px"></i>
      <el-dropdown-menu slot="dropdown" style="width:150px;text-align: center;">
        <el-dropdown-item style="font-size: 14px; padding:5px 0">個人資訊</el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding:5px 0">
          <router-link to="/login" style="text-decoration: none">退出</router-link>
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
    isCollapse:Boolean,
    pathName: String,
  },
  created() {
  // console.log(this.$route)
    },
  computed:{
    currentPathName(){
      return this.$store.state.currentPathName;  //需要監聽的數據
    }
  },
  watch:{ //監聽路由變化
    '$route':function(){
      this.currentPathName = localStorage.getItem("currentPathName")
    }
  },
  data(){
    return{
      // paths:[],
      // currentPathName:""
    }
  },
  methods:{
    collapse() {  //點擊收縮按鈕觸發
      this.isCollapse = !this.isCollapse
      if (this.isCollapse == false) {
        this.sideWidth = 64
        this.collapseBtnClass = "el-icon-s-unfold"
        this.logoTextShow = false
      } else {
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },
  }
}
</script>

<style scoped>

</style>