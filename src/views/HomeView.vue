<template>
  <div class="home">
    <el-container style=" border: 1px solid #eee">
      <!--      側邊欄-->
      <el-aside :width="sideWidth+'px'" style="background-color: rgb(238, 241, 246); box-shadow: 2px 0 6px rgb(0 21 41/35%)">
        <el-menu :default-openeds="['1', '3']" style="min-height: 100vh; overflow-x: hidden"
                 background-color="rgb(48,65,86)"
                 text-color="#fff"
                 active-text-color="#ffd04d"
                 :collapse-transition="false"
                 :collapse="isCollapse"
        >
<!--          LOGO區域-->
          <div style="height:60px;line-height: 60px;text-align: center">
            <img src="../assets/logo.png" width="20px" style="position: relative; top: 5px;margin-right: 5px">
            <b style="color:orange" v-show="logoTextShow">後台管理系統</b>
          </div>
          <el-submenu index="1" width="100%">
            <template slot="title">
              <i class="el-icon-message"></i>
              <span slot="title">导航一</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="1-1">选项1</el-menu-item>
              <el-menu-item index="1-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="1-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="1-4">
              <template slot="title">选项4</template>
              <el-menu-item index="1-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span slot="title">导航二</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="2-1">选项1</el-menu-item>
              <el-menu-item index="2-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="2-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="2-4">
              <template slot="title">选项4</template>
              <el-menu-item index="2-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span slot="title">导航三</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="3-1">选项1</el-menu-item>
              <el-menu-item index="3-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="3-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="3-4">
              <template slot="title">选项4</template>
              <el-menu-item index="3-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <!--        頂部-->
        <el-header style=" font-size: 12px;border: 1px solid #ccc; line-height: 60px;display: flex">
          <div style="flex:1;font-size: 18px">
            <span :class="collapseBtnClass" style="cursor:pointer" @click="collapse"></span>
          </div>

          <el-dropdown style="width:100px; cursor: pointer">

            <span><i class="el-icon-setting" ></i>王小虎</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>個人資訊</el-dropdown-item>
              <el-dropdown-item>退出</el-dropdown-item>

            </el-dropdown-menu>
          </el-dropdown>

        </el-header>

        <!--  主要區域-->
        <el-main>
<!--          搜尋欄-->
          <div style="margin:10px 0">
            <el-input placeholder="請輸入名稱" clearable style="width: 200px" suffix-icon="el-icon-search" class="ml-5" v-model="username"></el-input>
            <el-input placeholder="請輸入email" clearable style="width: 200px" suffix-icon="el-icon-search" class="ml-5" v-model="email"></el-input>
            <el-input placeholder="請輸入地址" clearable style="width: 200px" suffix-icon="el-icon-search" class="ml-5" v-model="address"></el-input>
            <el-button class="ml-5" type="primary" icon="el-icon-search" @click="load">搜尋</el-button>
            <el-button class="ml-5" type="warning" icon="el-icon-search" @click="reset">重置</el-button>

          </div>
          <div style="margin:10px 0">
            <el-button type="primary">新增<i class="el-icon-circle-plus-outline"></i></el-button>
            <el-button type="primary">修改<i class="el-icon-circle-plus-outline"></i></el-button>
            <el-button type="primary">刪除<i class="el-icon-circle-plus-outline"></i></el-button>
            <el-button type="primary">批量刪除<i class="el-icon-circle-plus-outline"></i></el-button>
            <el-button type="primary">導入<i class="el-icon-circle-plus-outline"></i></el-button>
            <el-button type="primary">導出<i class="el-icon-circle-plus-outline"></i></el-button>
          </div>
          <el-table :data="tableData" border stripe>
            <el-table-column prop="id" label="ID" ></el-table-column>
            <el-table-column prop="username" label="帳號名稱" ></el-table-column>
            <el-table-column prop="nickname" label="暱稱" ></el-table-column>
            <el-table-column prop="email" label="電子信箱"></el-table-column>
            <el-table-column prop="phone" label="電話"></el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
            <el-table-column label="操作" width="200">
              <template solt-scope="scope" >
                <el-button type="success">編輯<i class="el-icon-edit"></i></el-button>
                <el-button type="danger">刪除<i class="el-icon-edit"></i></el-button>

              </template>
            </el-table-column>
          </el-table>
<!--          分頁區域-->
          <div style="padding: 10px 0">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[5, 10, 15, 20]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'HomeView',
  components: {},
  data() {
    // const item = {
    //   date: '2016-05-02',
    //   name: '王小虎',
    //   address: '上海市普陀区金沙江路 1518 弄'
    // };

    return {
      msg: "hello 青哥哥",
      // tableData: Array(20).fill(item),
      tableData: [],
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse:false,
      sideWidth:200,
      logoTextShow:true,
      total:10,
      pageSize:5,
      pageNum:1,
      username: "",
      email:"",
      address:"",

    }
  },
  created() {
    this.load()

  },
  methods:{
    load(){
      //改用axios，使用參數加入
      this.request.get("http://localhost:9090/api/user/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          username:this.username,
          email:this.email,
          address:this.address,
        }
      })
          .then(res=>{
            console.log(res)
            this.tableData = res.records
            this.total = res.total
          })

      // //改用axios
      // this.request.get("http://localhost:9090/api/user/page?pageNum="+ this.pageNum +"&pageSize="+ this.pageSize + "&username=" + this.username)
      //     .then(res=>{
      //       console.log(res)
      //       this.tableData = res.records
      //       this.total = res.total
      //     })
      //請求分頁查詢數據
      // fetch("http://localhost:9090/api/user/page?pageNum="+ this.pageNum +"&pageSize="+ this.pageSize + "&username=" + this.username)
      //     .then((res)=>{return res.json()})
      //     .then((res)=>{
      //       console.log(res)
      //       this.tableData = res.data
      //       this.total = res.total
      //     })
    },
    collapse(){  //點擊收縮按鈕觸發
      this.isCollapse = !this.isCollapse
      if(this.isCollapse == false){
        this.sideWidth=64
        this.collapseBtnClass="el-icon-s-unfold"
        this.logoTextShow=false
      }else{
        this.sideWidth=200
        this.collapseBtnClass='el-icon-s-fold'
        this.logoTextShow=true
      }
    },
    handleSizeChange(pageSize){
      console.log(`每页 ${pageSize} 条`);
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      console.log(`当前页: ${pageNum}`);
      this.pageNum=pageNum
      this.load()
    },
    reset(){
      this.username=""
      this.email=""
      this.address=""
      this.load()
    }
  }

}
</script>
