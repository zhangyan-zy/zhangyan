<template>
  <div class="home-vue">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-select v-model="dataForm.station" clearable filterable placeholder="请选择站点">
          <el-option
            v-for="item in stationList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
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
          prop="billDate"
          header-align="center"
          align="center"
          label="账单时间">
        </el-table-column>
        <el-table-column
          prop="stationName"
          header-align="center"
          align="center"
          label="站点名称">
        </el-table-column>
        <el-table-column
          prop="num"
          header-align="center"
          align="center"
          label="人数">
        </el-table-column>
        <el-table-column
          prop="cost1"
          header-align="center"
          align="center"
          label="人力成本">
        </el-table-column>
        <el-table-column
          prop="cost2"
          header-align="center"
          align="center"
          label="花费">
        </el-table-column>
        <el-table-column
          prop="cost3"
          header-align="center"
          align="center"
          label="运营成本">
        </el-table-column>
        <el-table-column
          prop="allCost"
          header-align="center"
          align="center"
          label="总成本">
        </el-table-column>
        <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="150"
          label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="small"
                       @click="addOrUpdateHandle(scope.row.id)">修改
            </el-button>
            <el-button type="text" size="small"
                       @click="deleteHandle(scope.row.id)">删除
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
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" :stationList="stationList"
                   @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './cost-add-or-update'

  export default {
    data() {
      return {
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        key: '',
        stationList: [],
        dataListSelections: [],
        dataListLoading: false,
        addOrUpdateVisible: false,
        dataForm: {
          status: '',
          station: '',
          date: []
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
      this.getStationList()
    },

    methods: {
      // 新增 / 修改
      addOrUpdateHandle(id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 获取数据列表
      getStationList() {
        this.$http({
          url: this.$http.adornUrl('/common/station/AllList'),
          method: 'post',
          params: this.$http.adornParams({})
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.stationList = data.list
          }
        })
      },
      // 获取一表格数据列表
      getDataList() {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/sys/billout/list'),
          method: 'post',
          params: this.$http.adornParams({
            page: this.pageIndex,
            limit: this.pageSize,
            station: this.dataForm.station
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
      },
      // 删除
      deleteHandle(id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对此成本进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/sys/billout/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      }
    }
  }
</script>

<style></style>
