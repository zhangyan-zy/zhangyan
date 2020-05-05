<template>
  <div class="home-vue">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="查询" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
    </el-form>

    <div class="tab">
      <el-table
        class="tab1"
        :data="dataList"
        border
        v-loading="dataListLoading">
        <el-table-column
          prop="gmtCreate"
          header-align="center"
          align="center"
          label="创建时间">
        </el-table-column>
        <el-table-column
          prop="name"
          header-align="center"
          align="center"
          label="站点名称">
        </el-table-column>
        <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="150"
          label="操作">
          <template slot-scope="scope">
            <el-button v-if="scope.row.status !== 0" type="text" size="small"
                       @click="addOrUpdateHandle(scope.row.id)">修改
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 下栏列表1 -->
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
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate"
                   @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './stations-add-or-update'

  export default {
    data() {
      return {
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        key: '',
        dataListLoading: false,
        addOrUpdateVisible: false,
        dataForm: {
          status: '',
          date: [],
          key: ''
        }
      }
    },
    mounted() {
    },

    components: {
      AddOrUpdate
    },

    activated() {
      this.getDataList()
    },

    methods: {
      // 新增 / 修改
      addOrUpdateHandle(id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 获取一表格数据列表
      getDataList() {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/common/station/list'),
          method: 'post',
          params: this.$http.adornParams({
            page: this.pageIndex,
            limit: this.pageSize,
            key: this.dataForm.key
          })
        }).then(({data}) => {
          console.log('data', data)
          if (data && data.code === 0) {
            this.totalPage = data.page.totalCount
            this.dataList = data.page.list
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
