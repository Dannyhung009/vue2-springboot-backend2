<template>
  <div class="wrapper">
    <div style="margin:100px auto; background-color: #fff;width: 350px;height:80em;padding: 20px;border-radius: 10px">
      <div style="margin: 5px 0; text-align: center;font-size: 24px;"><b>註 冊</b></div>
      <el-form :model="user" ref="userForm" :rules="rules">
        <el-form-item label="用戶名稱" prop="username">
          <el-input size="medium" placeholder="請輸入用戶名稱 "style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item label="用戶密碼" prop="password">
          <el-input prop="password" placeholder="請輸入密碼" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password
                    v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item label="確認用戶密碼" prop="confirmPassword">
          <el-input prop="confirmPassword" placeholder="請再次輸入密碼" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password
                    v-model="user.confirmPassword"></el-input>
        </el-form-item>


        <div style="margin:10px 0;text-align: right">
          <el-button type="primary" autocomplete="off" @click="register">註 冊</el-button>
          <el-button type="warning" autocomplete="off" @click="$router.push('/login')">去登入頁面</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login.vue",
  data() {
    return {
      user: {},
      rules: {
        username: [
          {required: true, message: '請輸入用戶名', trigger: 'blur'},
          // {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '請輸入密碼', trigger: 'blur'},
          // {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, message: '請輸入確認密碼', trigger: 'blur'},
          // {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
      }

    }
  },
  methods: {
    register() {

      this.$refs["userForm"].validate((valid) => {
        if (valid) {  //表單驗證合法
          if(this.user.password !== this.user.confirmPassword){
            this.$message.error("請確認輸入密碼相同")
            return false
          }

          // this.$message.success("驗證成功")
          // alert('submit!');
          this.request.post("/user/register", this.user)
              .then(res => {
                if (res.code == 200) {
                  console.log(res)
                  // localStorage.setItem("user",JSON.stringify(res.data))//存儲使用者資料到瀏覽器
                  // this.$router.push("/")
                  this.$message.success("註冊成功")
                  this.$router.push("/login")
                } else {
                  this.$message.error(res.msg)
                }


                // if (!res) {
                //   this.$message.error("登入失敗，用戶名或密碼錯誤")
                // } else {
                //   console.log(res)
                //   this.$router.push("/")
                // }

              })
          // } else {
          //   this.$message.error("驗證失敗，用戶名或密碼錯誤")
          //   console.log('error submit!!');
          //   return false;
          // }
          //   }
          // )
          //   ;
          // if(this.user.username === "" ){
          //   this.$message.error("請輸入用戶名")
          // }else if(this.user.password === ""){
          //   this.$message.error("請輸入密碼")
        }
      })
    }
  }
}
</script>

<style scoped>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #FC466B, #3F5EFB);
  overflow: hidden;
}
</style>