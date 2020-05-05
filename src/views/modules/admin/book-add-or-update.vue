<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm"
             @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="用户" prop="userId">
        <el-select v-model="dataForm.userId" @change="selectUser" clearable filterable
                   placeholder="选择用户">
          <el-option
            v-for="(item,index) in selectCustomerList"
            :key="index"
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
        <el-select v-model="dataForm.leadsId" @change="selectStatus" clearable filterable placeholder="选择用户">
          <el-option
            v-for="item in selectLeadList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="金额" prop="amount">
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
        selectLeadList: [],
        dataForm: {
          id: 0,
          userId: '',
          leadsId: '',
          amount: 0,
          status1: ''
        },
        dataRule: {
          userId: [
            {required: true, message: '员工名称不能为空', trigger: 'blur'}
          ],
          leadsId: [
            {required: true, message: 'leads不能为空', trigger: 'blur'}
          ],
          amount: [
            {required: true, message: '金额不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    // watch: {
    //   'dataForm.userId': function (newValue, oldValue) {
    //     this.dataForm.leadsId = ''
    //   }
    // },
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
            console.log(data)
            if (data && data.code === 0) {
              this.dataForm.userId = data.billInInfo.userId
              this.dataForm.leadsId = data.billInInfo.leadsId
              this.dataForm.amount = data.billInInfo.amount
            }
          })
        }
      },
      selectUser(val) {
        this.dataForm.leadsId = ''
        this.selectCustomerList.forEach((el, i) => {
          if (val === el.userId) {
            this.selectLeadList = el.leadsEntities
          }
        })
      },
      selectStatus(val) {
        console.log('val', val)
        this.selectLeadList.forEach((el, i) => {
          if (val === el.id) {
            this.dataForm.status1 = el.status1
          }
        })
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
                customerId: this.dataForm.userId,
                amount: this.dataForm.amount,
                status1: this.dataForm.status1
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
