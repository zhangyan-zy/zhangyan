<template>
  <div class="mod-user">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-select v-model="coustomerId" filterable placeholder="请选择客户">
        <el-option
          v-for="item in coustomerList"
          :key="item.userId"
          :label="item.username"
          :value="item.userId">
        </el-option>
      </el-select>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        prop="username"
        header-align="center"
        align="center"
        label="坐席名称">
      </el-table-column>
      <el-table-column
        prop="power"
        header-align="center"
        align="center"
        label="权限">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.rolePower === 1" size="small">组长</el-tag>
          <el-tag v-else size="small">组员</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" size="small" type="danger">禁用</el-tag>
          <el-tag v-else size="small">正常</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        width="180"
        label="创建时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small"
                     @click="addOrUpdateHandle(scope.row.userId)">修改
          </el-button>
          <el-button v-if="scope.row.rolePower !== 1" type="text" size="small"
                     @click="setHandle(scope.row.userId)">设置为组长
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
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate"
                   @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './account-add-or-update'

  export default {
    data () {
      return {
        coustomerList: [],
        coustomerId: '',
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
      this.getCoustomerList()
    },
    methods: {
      // 获取数据列表
      getCoustomerList () {
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
      // 获取数据列表
      getDataList () {
        if (this.coustomerId) {
          this.dataListLoading = true
          this.$http({
            url: this.$http.adornUrl('/common/account/list'),
            method: 'get',
            params: this.$http.adornParams({
              'page': this.pageIndex,
              'limit': this.pageSize,
              'roleId': 5,
              'parentId': this.coustomerId
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
        } else {
          this.$message.error('请选择客户')
        }
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
        if (this.coustomerId) {
          this.addOrUpdateVisible = true
          this.$nextTick(() => {
            this.$refs.addOrUpdate.init(this.coustomerId, id)
          })
        } else {
          this.$message.error('请选择客户')
        }
      },
      // 设置组长
      setHandle (id) {
        this.$http({
          url: this.$http.adornUrl('/common/account/setWorkerAdmin'),
          method: 'post',
          params: this.$http.adornParams({
            'userId': id
          })
        }).then(({data}) => {
          this.$message({
            message: '操作成功',
            type: 'success',
            duration: 1500,
            onClose: () => {
              this.visible = false
              this.getDataList()
            }
          })
        })
      }
    }
  }
</script>
