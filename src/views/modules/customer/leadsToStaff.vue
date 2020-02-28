<template>
  <el-dialog
    title="分配"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm"
             @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="员工名称" prop="staffId">
        <el-select v-model="dataForm.staffId" filterable placeholder="请选择员工">
          <el-option
            v-for="item in staffList"
            :key="item.userId"
            :label="item.username"
            :value="item.userId">
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        staffList: [],
        dataForm: {
          staffId: ''
        },
        leadsId: 0,
        dataRule: {
          staffId: [
            {required: true, message: '员工不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.leadsId = id
        this.visible = true
        this.getStaffList()
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
        })
        if (!this.leadsId) {
          this.visible = false
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
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/common/leads/toStaff`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.leadsId,
                'disposeUser': this.dataForm.staffId
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
