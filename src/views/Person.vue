<template>
  <el-card style="width: 500px;padding: 20px;">
    <!-- Form -->

    <el-form :model="form" label-width="80px" size="small">
      <el-form-item label="用戶名稱" >
        <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="用戶密碼" >
        <el-input v-model="form.password" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="用戶暱稱" >
        <el-input v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="用戶電子信箱" >
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="用戶電話" >
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="用戶地址" >
        <el-input v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <div slot="footer" class="dialog-footer">
<!--          <el-button @click="dialogFormVisible = false">取 消</el-button>-->
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-form-item>
    </el-form>


  </el-card>
</template>

<script>
export default {
  name: "Person.vue",
  data(){
    return{
      form:{},
      // labelWidth:"80px",
      // formLabelWidth:'500px',
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  created() {
    this.request.get("/user/username/" + this.user.username)
        .then(res=>{
          if(res.code === '200'){
            this.form = res.data
          }
        })
  },
  methods:{
    save() {
      this.request.post("/user", this.form)
          .then(res => {
            if (res.data) {
              console.log(res)
              this.$message.success("新增成功")
            } else {
              this.$message.error("新增失敗")
            }

          })



    },
  }
}
</script>

<style scoped>

</style>