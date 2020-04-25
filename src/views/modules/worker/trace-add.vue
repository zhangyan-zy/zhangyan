<template>
  <el-dialog
    :title="!dataForm.id ? '添加跟进' : '跟进详情' "
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <!--<el-form-item label="leadsId" prop="leadsId">-->
        <!--<el-input v-model="dataForm.leadsId" readonly placeholder="leads"></el-input>-->
      <!--</el-form-item>-->
      <el-form-item label="跟进内容" prop="content">
        <el-input v-model="dataForm.content" placeholder="跟进内容"></el-input>
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
        url: '',
        visible: false,
        ThemeList: [],
        dataForm: {
          id: 0,
          gmtCreate: '',
          gmtModified: '',
          leadsId: '',
          content: ''
        },
        dataRule: {
          leadsId: [
            { required: true, message: 'leads不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '内容不能为空', trigger: 'blur' }
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
              this.dataForm.leadsId = data.leads.id
            }
          })
        }
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              // url: this.$http.adornUrl(`/common/leads/${!this.dataForm.id ? 'addTrace' : 'update'}`),
              url: this.$http.adornUrl(`/common/leads/addTrace`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'gmtCreate': this.dataForm.gmtCreate,
                'gmtModified': this.dataForm.gmtModified,
                'leadsId': this.dataForm.leadsId,
                'content': this.dataForm.content
                // 'content': this.dataForm.content,
                // 'img': this.dataForm.img
                //  'channelActivity': this.dataForm.channelActivity
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
