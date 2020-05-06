<template>
  <el-dialog
    :close-on-click-modal="false"
    :visible.sync="visible">
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
          prop="leadsName"
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
        <!--<el-table-column-->
          <!--fixed="right"-->
          <!--header-align="center"-->
          <!--align="center"-->
          <!--width="150"-->
          <!--label="操作">-->
          <!--<template slot-scope="scope">-->
            <!--<el-button type="text" size="small"-->
                       <!--@click="addOrUpdateHandle(scope.row.id)">修改-->
            <!--</el-button>-->
            <!--<el-button type="text" size="small"-->
                       <!--@click="deleteHandle(scope.row.id)">删除-->
            <!--</el-button>-->
          <!--</template>-->
        <!--</el-table-column>-->
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
  </el-dialog>
</template>

<script>
  export default {
    data() {
      return {
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        selectCustomerList: [],
        visible: false,
        dataListLoading: false,
        roleList: [],
        dataForm: {
          id: 0,
          userId: ''
        },
        dataRule: {
          userId: [
            {required: true, message: '员工名称不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init(id) {
        this.billId = id
        this.visible = true
        this.$http({
          url: this.$http.adornUrl(`/common/billininfo/selectByBillId`),
          method: 'post',
          params: this.$http.adornParams({
            billId: id
          })
        }).then(({data}) => {
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
        this.init(this.billId)
      },
      // 当前页
      currentChangeHandle(val) {
        this.pageIndex = val
        this.init(this.billId)
      },
      // 每页数
      sizeChangeHandle1(val) {
        this.pageSize = val
        this.pageIndex = 1
        this.init(this.billId)
      },
      // 当前页
      currentChangeHandle1(val) {
        this.pageIndex = val
        this.init(this.billId)
      }
    }
  }
</script>
