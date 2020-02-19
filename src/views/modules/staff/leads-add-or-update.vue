<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm"
             @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="状态" prop="amount">
        <el-select v-model="dataForm.status" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态备注" prop="statusRemark">
        <el-input v-model="dataForm.statusRemark" placeholder="备注"></el-input>
      </el-form-item>
      <el-form-item v-if="dataForm.status===4" label="金额" prop="amount">
        <el-input-number controls-position="right" :precision="2" :step="1"
                         v-model="dataForm.amount"
                         placeholder="金额"></el-input-number>
      </el-form-item>
      <el-form-item label="是否意向" prop="need">
        <el-switch
          v-model="isNeed"
          active-color="#13ce66"
          inactive-color="#ff4949">
        </el-switch>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
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
        options: [{
          value: 0,
          label: '关闭'
        }, {
          value: 2,
          label: '待处理'
        }, {
          value: 3,
          label: '已响应'
        }, {
          value: 4,
          label: '已加微'
        }, {
          value: 5,
          label: '已成单'
        }, {
          value: 6,
          label: '未成单'
        }],
        visible: false,
        roleList: [],
        isNeed: false,
        dataForm: {
          id: 0,
          amount: '',
          status: '',
          statusRemark: '',
          remark: ''
        },
        dataRule: {}
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
        })
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/common/leads/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.amount = data.leads.amount
              this.dataForm.status = data.leads.status
              this.dataForm.remark = data.leads.remark
              this.isNeed = (data.leads.need === 1)
            }
          })
        }
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            if (this.dataForm.status === 5 || this.dataForm.status === 0) {
              if (this.dataForm.statusRemark) {
                this.$message.error('请填写状态备注')
              }
            }
            this.$http({
              url: this.$http.adornUrl(`/common/leads/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'amount': this.dataForm.amount,
                'status': this.dataForm.status,
                'need': this.isNeed ? 1 : 0,
                'statusRemark': this.dataForm.statusRemark,
                'remark': this.dataForm.remark
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
