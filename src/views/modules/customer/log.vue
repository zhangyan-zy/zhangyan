<template>
  <div class="mod-user">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.userName" placeholder="客户名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        prop="leadsName"
        header-align="center"
        align="center"
        label="客户名称">
      </el-table-column>
      <el-table-column
        prop="statusOld"
        header-align="center"
        align="center"
        label="操作前状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.statusOld === 0" size="small" type="danger">已关闭</el-tag>
          <el-tag v-if="scope.row.statusOld === 1" size="small">待分配</el-tag>
          <el-tag v-if="scope.row.statusOld === 2" size="small">待处理</el-tag>
          <el-tag v-if="scope.row.statusOld === 3" size="small">已加微</el-tag>
          <el-tag v-if="scope.row.statusOld === 4" size="small">已响应</el-tag>
          <el-tag v-if="scope.row.statusOld === 5" size="small">已成单</el-tag>
          <el-tag v-if="scope.row.statusOld === 6" size="small">未成单</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="操作后状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" size="small" type="danger">已关闭</el-tag>
          <el-tag v-if="scope.row.status === 1" size="small">待分配</el-tag>
          <el-tag v-if="scope.row.status === 2" size="small">待处理</el-tag>
          <el-tag v-if="scope.row.status === 3" size="small">已加微</el-tag>
          <el-tag v-if="scope.row.status === 4" size="small">已响应</el-tag>
          <el-tag v-if="scope.row.status === 5" size="small">已成单</el-tag>
          <el-tag v-if="scope.row.status === 6" size="small">未成单</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="remark"
        header-align="center"
        align="center"
        label="备注">
      </el-table-column>
      <el-table-column
        prop="disposeUserName"
        header-align="center"
        align="center"
        label="操作人">
      </el-table-column>
      <el-table-column
        prop="gmtCreat"
        header-align="center"
        align="center"
        label="创建时间">
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        dataForm: {
          userName: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {},
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/common/leads/log'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'name': this.dataForm.userName
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      }
    }
  }
</script>
