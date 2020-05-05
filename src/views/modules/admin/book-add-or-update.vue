<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm"
             @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="用户" prop="userId">
        <el-select v-model="dataForm.name" clearable filterable placeholder="选择用户">
          <el-option
            v-for="item in selectCustomerList"
            :key="item.userId"
            :label="item.username"
            :value="item.userId">
          </el-option>
        </el-select>
      </el-form-item>
      <!--<el-form-item label="leads" prop="leadsId">-->
        <!--<el-select v-model="dataForm.leadsId" clearable filterable placeholder="选择leads">-->
          <!--<el-option-->
            <!--v-for="item in selectLeadsList"-->
            <!--:key="item.leadsId"-->
            <!--:label="item.leadsname"-->
            <!--:value="item.leadsId">-->
          <!--</el-option>-->
        <!--</el-select>-->
      <!--</el-form-item>-->
      <el-form-item label="leadsId" prop="leadsId">
        <el-input v-model="dataForm.leadsId" placeholder="leadsId"></el-input>
      </el-form-item>
      <el-form-item label="amount" prop="amount">
        <el-input-number v-model="dataForm.amount" :min="0" placeholder="金额"></el-input-number>
        <span>元</span>
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
    props: {
      selectCustomerList: {
        type: Array,
        default: () => []
      }
    },
    data() {
      return {
        visible: false,
        roleList: [],
        dataForm: {
          id: 0,
          userId: 0,
          leadsId: '',
          amount: 0
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
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
        })
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/common/billininfo/info/${this.dataForm.id}`),
            method: 'post',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.name = this.billInInfo.leadsId
              this.dataForm.leadsId = data.billInInfo.leadsId
              this.dataForm.amount = data.billInInfo.amount
            }
          })
        }
      },
      // 表单提交
      dataFormSubmit() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/common/billininfo/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                id: this.dataForm.id,
                leadsId: this.dataForm.leadsId,
                customerId: this.dataForm.name,
                amount: this.dataForm.amount
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
