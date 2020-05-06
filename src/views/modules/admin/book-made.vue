<template>
  <div class="home-vue">
    <el-form :inline="true" :model="dataForm">
      <el-form-item>
        <el-select v-model="dataForm.userId" clearable filterable placeholder="选择客户">
          <el-option
            v-for="item in selectCustomerList"
            :key="item.userId"
            :label="item.username"
            :value="item.userId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button type="primary" @click="addHandle()" :disabled="dataListSelections.length <= 0">批量生成账单</el-button>
      </el-form-item>
    </el-form>
    <div class="tab">
      <el-table
        class="tab1"
        :data="dataList"
        border
        v-loading="dataListLoading"
        @selection-change="selectionChangeHandle">
        <el-table-column
          type="selection"
          header-align="center"
          align="center"
          width="50">
        </el-table-column>
        <el-table-column
          prop="name"
          header-align="center"
          align="center"
          label="leads姓名">
        </el-table-column>
        <el-table-column
          prop="amount"
          header-align="center"
          align="center"
          label="金额">
        </el-table-column>
        <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="150"
          label="操作">
          <template slot-scope="scope">
            <el-button  type="text" size="small" @click="addHandle(scope.row)">生成账单</el-button>
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
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" :selectCustomerList="selectCustomerList"
                   @refreshDataList="getDataList"></add-or-update>
    <add-or-updates v-if="addOrUpdateVisible1" ref="addOrUpdate1"
                    @refreshDataList="getDataList"></add-or-updates>
  </div>
</template>

<script>

  import AddOrUpdate from './book-add-or-update'
  import AddOrUpdates from './book-detail'

  export default {
    data() {
      return {
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        selectCustomerList: [],
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
        addOrUpdateVisible1: false,
        statusList: [{
          label: '关闭',
          value: 0
        }, {
          label: '已结算',
          value: 1
        }, {
          label: '待结算',
          value: 2
        }
        ],
        dataForm: {
          name: '',
          userId: null,
          date: '',
          status: ''
        }
      }
    },

    components: {
      AddOrUpdate,
      AddOrUpdates
    },

    activated() {
      this.getCustomerList()
      // this.getDataList()
    },

    methods: {
      // 新增 / 修改
      addOrUpdateHandle() {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 详情
      addOrUpdateHandle1(id) {
        this.addOrUpdateVisible1 = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate1.init(id)
        })
      },
      // 获取数据列表
      getCustomerList() {
        this.$http({
          url: this.$http.adornUrl('/sys/user/selectCustomerList'),
          method: 'post',
          params: this.$http.adornParams({})
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.selectCustomerList = data.list
            this.dataForm.userId = data.list[0].userId
            this.getDataList()
          }
        })
      },
      // 获取一表格数据列表
      getDataList() {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/common/leads/selectleadsrList'),
          method: 'post',
          params: this.$http.adornParams({
            page: this.pageIndex,
            limit: this.pageSize,
            customerId: this.dataForm.userId
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
      // 账单生成
      addHandle(row) {
        var rows = row ? [row] : this.dataListSelections.map(item => {
          return item
        })
        this.$confirm(`确定对此账本进行[${rows.id ? '生成账单' : '批量生成账单'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/common/billininfo/save'),
            method: 'post',
            data: this.$http.adornData(rows, false)
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

<style scoped>
  .customer {
    color: #00b7ee;
    text-decoration: underline;
    cursor: pointer;
  }
</style>
