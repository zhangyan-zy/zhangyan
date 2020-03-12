<template>
  <div class="home-vue">
    <div class="tab">
      <el-table
        class="tab1"
        :data="dataList"
        border
        v-loading="dataListLoading"
      >
        <el-table-column
          type="index"
          :index="indexMethod"
          label="排名"
        ></el-table-column>
        <el-table-column
          prop="aname"
          header-align="center"
          align="center"
          label="姓名"
        ></el-table-column>
        <el-table-column
          prop="asumLeads"
          header-align="center"
          align="center"
          label="总leads"
        ></el-table-column>
        <el-table-column
          prop="aaddleads"
          header-align="center"
          align="center"
          label="今日新增leads"
        ></el-table-column>
      </el-table>
    </div>

    <el-col>
      <div>
        <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize1"
          :total="totalPage1"
          layout="total, sizes, prev, pager, next, jumper"
        ></el-pagination>
      </div>
    </el-col>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        asumLeads: 1
      }
    },
    components: {},
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取一表格数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/common/AgentsLeadersList'),
          method: 'post',
          params: this.$http.adornParams({
            page: this.pageIndex,
            limit: this.pageSize
          })
        }).then(({data}) => {
          console.log('data', data)
          if (data && data.code === 0) {
            this.dataList = data.user
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      indexMethod (index) {
        if (
          index > 0 &&
          this.dataList[index - 1].asumLeads === this.dataList[index].asumLeads
        ) {
          this.dataList[index].index = this.dataList[index - 1].index + 1
        } else {
          this.dataList[index].index = -1
        }
        index = index - this.dataList[index].index
        return index
      },
      // 每页数
      sizeChangeHandle1 (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle1 (val) {
        this.pageIndex = val
        this.getDataList()
      }
    }
  }
</script>

<style></style>
