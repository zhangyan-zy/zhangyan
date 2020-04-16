<template>
  <el-dialog
    :title="'跟进'"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="90%">
    <div class="table">
      <el-table
        :data="dataList"
        border
        v-loading="dataListLoading"
        style="width: 100%;">
        <el-table-column
          type="index"
          header-align="center"
          align="center"
          label="排序"
          width="60">
        </el-table-column>
        <el-table-column
          :show-overflow-tooltip="true"
          prop="gmtCreat"
          header-align="center"
          align="center"
          label="创建时间">
        </el-table-column>
        <el-table-column
          prop="gmtModified"
          header-align="center"
          align="center"
          label="修改时间">
        </el-table-column>
        <el-table-column
          :show-overflow-tooltip="true"
          prop="content"
          header-align="center"
          align="center"
          label="跟进内容">
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
  </el-dialog>
</template>

<script>
  export default {
    data() {
      return {
        pid: 0,
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: true,
        addOrUpdateVisible1: false,
        visible: false,
        dataRule: {
          gmtCreate: [
            {required: true, message: '创建时间不能为空', trigger: 'blur'}
          ],
          gmtModified: [
            {required: true, message: '修改时间不能为空', trigger: 'blur'}
          ],
          insta: [
            {required: true, message: '统计时间不能为空', trigger: 'blur'}
          ],
          channelRegistered: [
            {required: true, message: '注册量不能为空', trigger: 'blur'}
          ],
          channelView: [
            {required: true, message: '访问量不能为空', trigger: 'blur'}
          ],
          channelApply: [
            {required: true, message: '申请量不能为空', trigger: 'blur'}
          ],
          channelActivity: [
            {required: true, message: '活跃量不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init(id) {
        this.visible = true
        this.pid = id
        this.$nextTick(() => {
          this.$http({
            url: this.$http.adornUrl(`/common/leads/traceList`),
            method: 'post',
            params: this.$http.adornParams({
              'page': this.pageIndex,
              'limit': this.pageSize,
              'id': id
            })
          }).then(({data}) => {
            console.log('修改', data)
            this.dataListLoading = false
            if (data && data.code === 0) {
              this.dataList = data.page.list
              this.totalPage = data.page.total
            } else {
              this.dataList = []
              this.totalPage = 0
            }
          })
        })
      },
      // 每页数
      sizeChangeHandle(val) {
        this.pageSize = val
        this.pageIndex = 1
        this.init(this.pid)
      },
      // 当前页
      currentChangeHandle(val) {
        this.pageIndex = val
        this.init(this.pid)
      },
      // 多选
      selectionChangeHandle(val) {
        this.dataListSelections = val
      }
    }
  }
</script>

<style>
  .table {
    /*border: 1px solid red;*/
    width: 95%;
    margin: 0 auto;
  }
</style>
