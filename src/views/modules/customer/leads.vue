<template>
  <div class="mod-user">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.userName" placeholder="客户名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
      <el-input v-model="dataForm.mobile" placeholder="客户手机" clearable></el-input>
    </el-form-item>
      <el-form-item label="金额范围">
        <el-input-number :controls="false" :min="0" v-model="dataForm.ammount1" placeholder="金额"
                         clearable></el-input-number>
      </el-form-item>
      <el-form-item>
        ~
      </el-form-item>
      <el-form-item>
        <el-input-number :controls="false" :min="0" v-model="dataForm.ammount2" placeholder="金额"
                         clearable></el-input-number>
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
        <el-select v-model="dataForm.staff" clearable filterable placeholder="请选择员工">
          <el-option
            v-for="item in staffList"
            :key="item.userId"
            :label="item.username"
            :value="item.userId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.status" clearable placeholder="客户状态">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
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
        prop="name"
        header-align="center"
        align="center"
        label="客户名称">
      </el-table-column>
      <el-table-column
        prop="name1"
        header-align="center"
        align="center"
        label="客户姓名">
      </el-table-column>
      <el-table-column
        prop="phone"
        header-align="center"
        align="center"
        label="客户电话">
      </el-table-column>
      <el-table-column
        prop="webchat"
        header-align="center"
        align="center"
        label="客户微信">
      </el-table-column>
      <el-table-column
        prop="amount"
        header-align="center"
        align="center"
        label="金额">
      </el-table-column>
      <el-table-column
        prop="need"
        header-align="center"
        align="center"
        label="是否意向">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.need === 0" size="small" type="danger">无意向</el-tag>
          <el-tag v-if="scope.row.need === 1" size="small">有意向</el-tag>
          <el-tag v-if="scope.row.need === 2" size="small">一般</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="状态">
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
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗,  -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate"
                   @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './leads-add-or-update'

  export default {
    data () {
      return {
        staffList: [],
        options: [{
          value: 0,
          label: '已关闭'
        }, {
          value: 1,
          label: '待分配'
        }, {
          value: 2,
          label: '待处理'
        }, {
          value: 3,
          label: '已加微'
        }, {
          value: 4,
          label: '已响应'
        }, {
          value: 5,
          label: '已成单'
        }, {
          value: 6,
          label: '未成单'
        }],
        dataForm: {
          userName: '',
          mobile: '',
          ammount1: '',
          ammount2: '',
          status: '',
          staff: '',
          date: []
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
    components: {
      AddOrUpdate
    },
    activated () {
      this.getDataList()
      this.getStaffList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        if (this.dataForm.ammount1 > this.dataForm.ammount2) {
          this.$message.error('请输入正确的金额范围')
        } else {
          this.dataListLoading = true
          this.$http({
            url: this.$http.adornUrl('/common/leads/listByCustomer'),
            method: 'get',
            params: this.$http.adornParams({
              'page': this.pageIndex,
              'limit': this.pageSize,
              'amount1': this.dataForm.ammount1,
              'amount2': this.dataForm.ammount2,
              'status': this.dataForm.status,
              'staff': this.dataForm.staff,
              'mobile': this.dataForm.mobile,
              'date1': this.dataForm.date ? this.dataForm.date[0] : '',
              'date2': this.dataForm.date ? this.dataForm.date[1] : '',
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
        }
      },
      getStaffList () {
        this.$http({
          url: this.$http.adornUrl('/common/account/staff'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.staffList = data.user
          }
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
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      }
    }
  }
</script>
