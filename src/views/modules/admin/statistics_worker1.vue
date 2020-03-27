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
          v-model="date"
          type="date"
          placeholder="选择日期">
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
        show-summary
        v-loading="dataListLoading">
        <el-table-column
          width="60px"
          prop="idx"
          align="center"
          :index="indexMethod"
          label="排名">

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
          prop="addTime"
          header-align="center"
          align="center"
          label="新增数量">
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
        date: new Date(),
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
          idx: 0
        },
        num: 12
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
          url: this.$http.adornUrl('/common/AddAgentsLeadersList'),
          method: 'post',
          params: this.$http.adornParams({
            page: this.pageIndex,
            limit: this.pageSize,
            parentId: this.coustomerId,
            date: this.date
          })
        }).then(({data}) => {
          console.log('data', data)
          if (data && data.code === 0) {
            this.dataList = data.data.list
            this.totalPage = data.data.totalCount
            this.dataList.forEach((el, i) => {
              this.dataList[i].idx = i + 1
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
      indexMethod(index) {
        if (index > 0 && this.dataList[index - 1].addTime === this.dataList[index].addTime) {
          this.dataList[index].index = this.dataList[index - 1].index + 1
        } else {
          this.dataList[index].index = -1
        }
        index = index - this.dataList[index].index
        return index
      },
      //
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
