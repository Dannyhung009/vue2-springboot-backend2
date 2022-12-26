<template>
  <div>
    <!--          搜尋欄-->
    <div style="margin:10px 0">
      <el-input placeholder="請輸入名稱" clearable style="width: 200px" suffix-icon="el-icon-search" class="ml-5"
                v-model="name"></el-input>
      <el-button class="ml-5" type="primary" icon="el-icon-search" @click="load">搜尋</el-button>
      <el-button class="ml-5" type="warning" icon="el-icon-search" @click="reset">重置</el-button>
    </div>
    <!--          表格區域-->
    <div style="margin:10px 0">
      <el-upload action="http://localhost:9090/file/upload" style="display: inline-block"
                 :show-file-list="false"
                 :on-success="handleUploadFileSuccess">
        <el-button type="primary" @click="handleImport">上傳文件<i class="el-icon-top"></i></el-button>
        <span slot="tip" class="el-upload__tip">只能上传xlsx文件</span>
      </el-upload>

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


    </div>
    <!--    表格區域-->
    <el-table :data="tableData" border stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID"></el-table-column>
      <el-table-column prop="name" label="文件名稱"></el-table-column>
      <el-table-column prop="type" label="文件類型"></el-table-column>
      <el-table-column prop="size" label="文件大小(KB)"></el-table-column>
      <el-table-column label="下載連結">
        <template slot-scope="scope">
          <el-button type="primary" @click="download(scope.row.url)">下載</el-button>

        </template>
      </el-table-column>
      <el-table-column label="是否啟用">
        <template slot-scope="scope">
          <el-switch @change="changeEnable(scope.row)" v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc"></el-switch>

        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">

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
  </div>
</template>

<script>
export default {
  name: "File.vue",
  data() {
    return {
      tableData: [],
      total: 0,
      pageSize: 5,
      pageNum: 1,
      name: "",
      multipleSelection: [],


    }
  },
  created() {
    this.load()

  },
  methods: {
    load() {
      //改用axios，使用參數加入
      this.request.get("/file/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
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
      this.name = ""
      this.load()
    },
    handleAdd() {


    },
    handleEdit(row) {
      this.form = row
      this.dialogFormVisible = true
      // this.saveUser()
    },
    handleDelete(id) {
      this.request.delete("/file/" + id)
          .then(res => {
            console.log(res)
            if (res.code === '200') {
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
      this.request.post("/file", this.form)
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
      this.request.post("/file/delBatch", ids)
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
    },
    handleUploadFileSuccess(res) {
      console.log(res)
      this.load()
    },
    download(url){
      window.open(url)

    },
    changeEnable(row){
      this.request.post("/file/update/", row)
          .then(res=>{
            console.log(res)
            if(res.code === '200'){
              this.$message.success("操作成功")
            }
          })
    }
  }
}
</script>

<style scoped>

</style>