<template>
  <div class="mod-user">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.userName" placeholder="客户名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-form-item>
          <el-select v-model="dataForm.need" clearable placeholder="是否意向">
            <el-option v-for="item in need" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button @click="addOrUpdateHandle()">批量分配</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
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
        label="客户名称">
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
        prop="remark"
        header-align="center"
        align="center"
        label="备注">
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
          <el-button type="text" size="small"
                     @click="addOrUpdateHandle(scope.row.id)">分配
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
  import AddOrUpdate from './leadsToStaff'

  export default {
    data () {
      return {
        dataForm: {
          userName: '',
          need: ''
        },
        need:[{
          value:'2',
          label:'一般',
        },{
          value:'1',
          label:'有意向',
        },{
          value:'0',
          label:'无意向',
        }],
        timer: '',
        size: 0,
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
    mounted () {
      this.timer = setInterval(this.tips, 5000)
    },
    beforeDestroy () {
      clearInterval(this.timer)
    },
    activated () {
      this.getDataList()
      this.tips()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/common/leads/waitList'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'name': this.dataForm.userName,
            'need': this.dataForm.need
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
      tips () {
        this.$http({
          url: this.$http.adornUrl('/common/leads/waitCount'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({data}) => {
          if (data && data.code === 0) {
            if (this.size < data.count && this.size !== 0) {
              this.setNotification('您有新的待分配Leads')
            }
            this.size = data.count
          }
        })
      },
      setNotification (content) {
        let notify = new Notification('新消息来啦！', {
          body: content,
          lang: 'zh-CN'
        })
        notify.onshow = function () {
        }
        notify.onclick = function () {
          window.focus()
          notify.close()
        }
        notify.onerror = function () {
          notify.close()
        }
        notify.onclose = function () {
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
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        if (ids.length > 0) {
          this.addOrUpdateVisible = true
          this.$nextTick(() => {
            this.$refs.addOrUpdate.init(ids)
          })
        } else {
          this.$message.error('请选择Leads')
        }
      }
    }
  }
</script>
