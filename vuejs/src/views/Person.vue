<template>
  <el-card style="width: 500px;padding: 20px;">
    <!-- Form -->

    <el-form :model="form" label-width="80px" size="small">
      <el-upload
          style="text-align: center;padding-bottom: 10px; "
          class="avatar-uploader"
          action="http://localhost:9090/file/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
      >
        <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <el-form-item label="用戶名稱">
        <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="用戶密碼">
        <el-input v-model="form.password" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="用戶暱稱">
        <el-input v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="用戶電子信箱">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="用戶電話">
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="用戶地址">
        <el-input v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">確 定</el-button>
      </el-form-item>
    </el-form>


  </el-card>
</template>

<script>
export default {
  name: "Person.vue",
  data() {
    return {
      form: {},
      // labelWidth:"80px",
      // formLabelWidth:'500px',
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  created() {
    this.getUser2()
  },
  methods: {
    getUser() {
      this.request.get("/user/username/" + this.user.username)
          .then(res => {
            if (res.code === '200') {
              this.form = res.data
            }
          })
    },
    async getUser2() {
      let res = await this.request.get("/user/username/" + this.user.username)
      // console.log(res)
      this.form = res.data
      // return res.data

    },
    save() {
      this.request.post("/user", this.form)
          .then(res => {
            if (res.code === '200') {
              console.log(res)
              this.$message.success("新增成功")


              //更新瀏覽器存儲的用戶信息
              this.getUser2()
                  .then(res=>{
                    res.token = JSON.parse(localStorage.getItem("user")).token
                    localStorage.setItem("user", JSON.stringify(res))
                  })

            } else {
              this.$message.error("新增失敗")
            }

          })


    },
    handleAvatarSuccess(res) {
      this.form.avatarUrl = res
    }
  }
}
</script>

<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>