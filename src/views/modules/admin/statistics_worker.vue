<template>
  <div class="home-vue">
    <el-form
      :inline="true"
      :model="dataForm"
      @keyup.enter.native="getDataList()">
      <el-select
        v-model="coustomerId"
        clearable
        filterable
        placeholder="请选择客户">
        <el-option
          v-for="item in coustomerList"
          :key="item.userId"
          :label="item.username"
          :value="item.userId">
        </el-option>
      </el-select>
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
          prop="idx"
          label="排名"
          align="center"
          width="65px"
        >
        </el-table-column>
        <el-table-column
          prop="username"
          header-align="center"
          align="center"
          label="客户名称">
        </el-table-column>
        <el-table-column
          prop="leadsname"
          header-align="center"
          align="center"
          label="坐席名称">
        </el-table-column>
        <el-table-column
          prop="sumLeads"
          header-align="center"
          align="center"
          label="leads数量">
        </el-table-column>
        <el-table-column
          prop="responseRate"
          header-align="center"
          align="center"
          label="响应数量">
        </el-table-column>
        <el-table-column
          prop="sumAvg"
          header-align="center"
          align="center"
          label="响应率">
        </el-table-column>
        <el-table-column
          prop="addTime"
          header-align="center"
          align="center"
          label="新增">
        </el-table-column>
        <el-table-column
          prop="addTimeResponce"
          header-align="center"
          align="center"
          label="新增响应数量">
        </el-table-column>
        <el-table-column
          prop="added"
          header-align="center"
          align="center"
          label="新增响应率">
        </el-table-column>
        <el-table-column
          prop="addVagDaliy"
          header-align="center"
          align="center"
          label="平均每日新增">
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
          idx: 0,
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
    computed: {
      added(e) {

      }
    },
    methods: {
      // 获取一表格数据列表
      getDataList() {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/common/admin/allWorker'),
          method: 'post',
          params: this.$http.adornParams({
            page: this.pageIndex,
            limit: this.pageSize,
            parentId: this.coustomerId,
            date1: this.dataForm.date ? this.dataForm.date[0] : '',
            date2: this.dataForm.date ? this.dataForm.date[1] : ''
          })
        }).then(({data}) => {
          console.log('data', data)
          if (data && data.code === 0) {
            this.dataList = data.list.list
            this.totalPage = data.list.totalCount
            let added = 0
            this.dataList.forEach((el, i) => {
              this.dataList[i].idx = i + 1
              el.sumAvg = ((parseFloat(el.sumAvg)) * 100).toFixed(2) + '%'
              added = ((parseFloat(el.addTimeResponce / el.addTime)) * 100).toFixed(2) + "%"
              el.added = added
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
          url: this.$http.adornUrl('/common/account/coustomer'),
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
