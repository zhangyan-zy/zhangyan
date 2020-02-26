<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm"
             @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="客户名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="客户名称"></el-input>
      </el-form-item>
      <el-form-item label="客户姓名" prop="name1">
        <el-input v-model="dataForm.name1" placeholder="客户姓名"></el-input>
      </el-form-item>
      <el-form-item label="客户电话" prop="phone">
        <el-input v-model="dataForm.phone" @change="updateWebChat" placeholder="客户电话"></el-input>
      </el-form-item>
      <el-form-item label="客户微信" prop="webchat">
        <el-input v-model="dataForm.webchat" placeholder="客户微信"></el-input>
      </el-form-item>
      <el-form-item label="是否意向" prop="need">
        <el-switch
          v-model="dataForm.isNeed"
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
        visible: false,
        roleList: [],
        dataForm: {
          id: 0,
          name: '',
          name1: '',
          phone: '',
          isNeed: false,
          webchat: '',
          remark: ''
        },
        dataRule: {
          name: [
            {required: true, message: '客户名称不能为空', trigger: 'blur'}
          ],
          name1: [
            {required: true, message: '客户姓名不能为空', trigger: 'blur'}
          ],
          phone: [
            {required: true, message: '客户电话不能为空', trigger: 'blur'}
          ],
          webchat: [
            {required: true, message: '客户微信不能为空', trigger: 'blur'}
          ]
        }
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
              this.dataForm.name = data.leads.name
              this.dataForm.name1 = data.leads.name1
              this.dataForm.phone = data.leads.phone
              this.dataForm.webchat = data.leads.webchat
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
            this.$http({
              url: this.$http.adornUrl(`/common/leads/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'name1': this.dataForm.name1,
                'phone': this.dataForm.phone,
                'need': this.isNeed ? 1 : 0,
                'webchat': this.dataForm.webchat,
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
      },
      updateWebChat () {
        if (!this.dataForm.webchat) {
          this.dataForm.webchat = this.dataForm.phone
        }
      }
    }
  }
</script>
