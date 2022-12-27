<template>
  <div>

    <!--          搜尋欄-->
    <div style="margin:10px 0">
      <el-input placeholder="請輸入名稱" clearable style="width: 200px" suffix-icon="el-icon-search" class="ml-5"
                v-model="username"></el-input>
      <el-input placeholder="請輸入email" clearable style="width: 200px" suffix-icon="el-icon-search" class="ml-5"
                v-model="email"></el-input>
      <el-input placeholder="請輸入地址" clearable style="width: 200px" suffix-icon="el-icon-search" class="ml-5"
                v-model="address"></el-input>
      <el-button class="ml-5" type="primary" icon="el-icon-search" @click="load">搜尋</el-button>
      <el-button class="ml-5" type="warning" icon="el-icon-search" @click="reset">重置</el-button>

    </div>
    <!--          表格區域-->
    <div style="margin:10px 0">
      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
      <!--            <el-button type="primary" @click="handleEdit">修改<i class="el-icon-circle-plus-outline"></i></el-button>-->
      <!--            <el-button type="primary" @click="handleDelete">刪除<i class="el-icon-circle-plus-outline"></i></el-button>-->
      <el-popconfirm
          style="margin: 0px 10px"
          confirm-button-text='好的'
          cancel-button-text='不用了'
          icon="el-icon-info"
          icon-color="red"
          title="確定批量刪除嗎？"
          @confirm="handleBatchDelete"
      >

        <el-button slot="reference" type="primary" @click="">批量刪除<i class="el-icon-circle-plus-outline"></i>
        </el-button>
      </el-popconfirm>

      <el-upload action="http://localhost:9090/user/import" style="display: inline-block"
                 :show-file-list="false"
                 accept="xlsx"
                 :on-success="handleExcelImportSuccess">

        <el-button type="primary" @click="handleImport">導入<i class="el-icon-circle-plus-outline"></i></el-button>
        <span slot="tip" class="el-upload__tip">只能上传xlsx文件</span>
      </el-upload>

      <el-button type="primary" @click="handleExport">導出<i class="el-icon-circle-plus-outline"></i></el-button>
    </div>
    <!--    表格區域-->
    <el-table :data="tableData" border stripe @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <!--            <el-table-column prop="id" label="ID"></el-table-column>-->
      <el-table-column prop="username" label="帳號名稱"></el-table-column>
      <el-table-column prop="nickname" label="暱稱"></el-table-column>
      <el-table-column prop="email" label="電子信箱"></el-table-column>
      <el-table-column prop="phone" label="電話"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">編輯<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='好的'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              title="確定刪除嗎？"
              @confirm="handleDelete(scope.row.id)"
          >

            <el-button slot="reference" type="danger" @click="">刪除<i class="el-icon-edit"></i></el-button>
          </el-popconfirm>


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
    <!--          對話框-->
    <!-- Form -->
    <el-dialog title="用戶訊息" :visible.sync="dialogFormVisible" width="50%">
      <el-form :model="form">
        <el-form-item label="用戶名稱" :label-width="formLabelWidth">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用戶密碼" :label-width="formLabelWidth">
          <el-input v-model="form.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用戶暱稱" :label-width="formLabelWidth">
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用戶電子信箱" :label-width="formLabelWidth">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用戶電話" :label-width="formLabelWidth">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用戶地址" :label-width="formLabelWidth">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveUser">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "User",
  props: {},
  data() {
    return {
      tableData: [],
      total: 0,
      pageSize: 5,
      pageNum: 1,
      username: "",
      email: "",
      address: "",
      dialogFormVisible: false,
      // form: {
      //   username: "",
      //   password: "",
      //   nickname: "",
      //   email: "",
      //   phone: "",
      //   address: ""
      // },
      form: {},
      formLabelWidth: "120px",
      multipleSelection: [],
    }


  },
  created() {
    this.load()

  },
  methods: {
    load() {
      //改用axios，使用參數加入
      this.request.get("/user/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          email: this.email,
          address: this.address,
        }
      })
          .then(res => {
            if (res.data) {
              // console.log(res)
              this.tableData = res.data.records
              this.total = res.data.total
            }

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
    handleSizeChange(pageSize) {
      console.log(`每页 ${pageSize} 条`);
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(`当前页: ${pageNum}`);
      this.pageNum = pageNum
      this.load()
    },
    reset() {
      this.username = ""
      this.email = ""
      this.address = ""
      this.load()
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}

    },
    handleEdit(row) {
      this.form = row
      this.dialogFormVisible = true
      // this.saveUser()
    },
    handleDelete(id) {
      this.request.delete("/user/" + id)
          .then(res => {
            console.log(res)
            if (res.data) {
              this.$message.success("刪除成功")
            } else {
              this.$message.error("刪除失敗")
            }
          })
      this.load()
    },

    handleImport() {

    },
    handleExport() {
      window.open("http://localhost:9090/user/export")


    },
    saveUser() {
      this.request.post("/user", this.form)
          .then(res => {
            console.log(res)
            if (res.data) {
              this.$message.success("新增成功")
              this.dialogFormVisible = false
              this.load()
            } else {
              this.$message.error("新增失敗")
              this.dialogFormVisible = false
              this.load()
            }

          })


    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(val)


    },
    handleBatchDelete() {
      console.log(this.multipleSelection)
      let ids = this.multipleSelection.map(v => v.id)//[{},{},{}]=>[1,2,3]
      console.log(ids)
      this.request.post("/user/delBatch", ids)
          .then(res => {
            console.log(res)
            if (res.data) {
              this.$message.success("批量刪除成功")
              this.load()
            } else {
              this.$message.error("批量刪除失敗")
              this.load()
            }
          })

    },
    handleExcelImportSuccess() {
      this.$message.success("Excel導入成功")
      this.load()
    }
  }


}
</script>

<style scoped>

</style>