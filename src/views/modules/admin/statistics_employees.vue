<template>
  <div class="home-vue">
    <el-form
      :inline="true"
      :model="dataForm"
      @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input
          v-model="key"
          placeholder="员工名称"
          clearable>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="dataForm.date"
          type="daterange"
          value-format="yyyy-MM-dd"
          range-separator="——"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
      </el-form-item>
    </el-form>

    <div class="tab">
      <el-table
        class="tab1"
        :data="dataList"
        border
        v-loading="dataListLoading">
        <el-table-column
          type="index"
          label="排名"
          align="center"
          width="65px"
        >
        </el-table-column>
        <el-table-column
          prop="leadsname"
          header-align="center"
          align="center"
          label="员工">
        </el-table-column>
        <el-table-column
          prop="quantum"
          header-align="center"
          align="center"
          label="分配量">
        </el-table-column>
        <el-table-column
          prop="responseRate"
          header-align="center"
          align="center"
          label="加微以响应">
        </el-table-column>
        <el-table-column
          prop="responseAvg"
          header-align="center"
          align="center"
          label="响应率">
        </el-table-column>
        <el-table-column
          prop="sumAmount"
          header-align="center"
          align="center"
          label="签单金额">
        </el-table-column>
        <el-table-column
          prop="signingRate"
          header-align="center"
          align="center"
          label="签单率">
        </el-table-column>
      </el-table>
    </div>

    <!-- 下栏列表1 -->

    <el-col>
      <div>
        <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          :total="totalPage"
          layout="total, sizes, prev, pager, next, jumper"
        ></el-pagination>
      </div>
    </el-col>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        key: '',
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        workerList: [],
        workerId: '',
        coustomerList: [],
        coustomerId: '',
        dataForm: {
          status: '',
          date: [],
          leadsname: ''
        }
      }
    },
    mounted() {
    },

    components: {},

    activated() {
      this.getDataList()
      this.getCoustomerList()

    },
    methods: {
      // 获取一表格数据列表
      getDataList() {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/common/admin/allStaff'),
          method: 'post',
          params: this.$http.adornParams({
            page: this.pageIndex,
            limit: this.pageSize,
            parentId: this.coustomerId,
            key: this.key,
            date1: this.dataForm.date ? this.dataForm.date[0] : '',
            date2: this.dataForm.date ? this.dataForm.date[1] : ''
          })
        }).then(({data}) => {
          console.log('data', data)
          if (data && data.code === 0) {
            this.dataList = data.list.list
            this.totalPage = data.list.totalCount
            let signingRate = 0;
            this.dataList.forEach((el, i) => {
              if (el.signingRate != 0) {
                signingRate = (parseInt(el.signingRate * 10000)) / 100 + '%'
                this.dataList[i].signingRate = signingRate
              }
            })
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      sizeChangeHandle(val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle(val) {
        this.pageIndex = val
        this.getDataList()
      },

      getCoustomerList() {
        this.$http({
          url: this.$http.adornUrl('/common/account/staff'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.coustomerList = data.user
          }
        })
      },

      // 每页数
      sizeChangeHandle1(val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle1(val) {
        this.pageIndex = val
        this.getDataList()
      }
    }
  }
</script>

<style></style>
