<template>
  <el-dialog
    :title="!dataForm.orderNo ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="商品" prop="goodId">
        <el-select v-model="dataForm.goodId" clearable filterable placeholder="商品">
          <el-option
            v-for="item in goodsList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="店铺" prop="storeId">
        <el-select v-model="dataForm.storeId" clearable filterable placeholder="店铺">
          <el-option
            v-for="item in storeList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="用户" prop="userId">
        <el-select v-model="dataForm.userId" clearable filterable placeholder="用户">
          <el-option
            v-for="item in userList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
    <el-form-item label="订单金额" prop="score">
      <el-input v-model="dataForm.score" placeholder="订单金额"></el-input>
    </el-form-item>
      <el-form-item label="订单状态" size="mini" prop="state">
        <el-radio-group v-model="dataForm.state">
          <el-radio :label="0">制作中</el-radio>
          <el-radio :label="1">成功</el-radio>
          <el-radio :label="2">失败</el-radio>
          <el-radio :label="3">未支付</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="评价" prop="msg">
        <el-input v-model="dataForm.msg" placeholder="评价"></el-input>
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
        goodsList: [],
        storeList: [],
        userList: [],
        visible: false,
        dataForm: {
          orderNo: 0,
          goodId: '',
          storeId: '',
          score: '',
          state: '',
          userId: '',
          msg: ''
        },
        dataRule: {
          goodId: [
            { required: true, message: '商品编号不能为空', trigger: 'blur' }
          ],
          storeId: [
            { required: true, message: '店铺编号不能为空', trigger: 'blur' }
          ],
          userId: [
            { required: true, message: '用户不能为空', trigger: 'blur' }
          ],
          score: [
            { required: true, message: '订单金额不能为空', trigger: 'blur' }
          ],
          state: [
            { required: true, message: '订单状态（0制作中 1成功 2失败 3.未支付）不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      getGoodsList() {
        this.$http({
          url: this.$http.adornUrl('/generator/goods/selectAll'),
          method: 'post',
          params: this.$http.adornParams({})
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.goodsList = data.goodsList
          }
        })
      },
      getStoreList() {
        this.$http({
          url: this.$http.adornUrl('/generator/store/selectAll'),
          method: 'post',
          params: this.$http.adornParams({})
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.storeList = data.list
          }
        })
      },
      getUserList() {
        this.$http({
          url: this.$http.adornUrl('/generator/appuser/selectAll'),
          method: 'post',
          params: this.$http.adornParams({})
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.userList = data.appUserList
          }
        })
      },
      init (id) {
        this.dataForm.orderNo = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.orderNo) {
            this.$http({
              url: this.$http.adornUrl(`/generator/orders/info/${this.dataForm.orderNo}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.goodId = data.orders.goodId
                this.dataForm.storeId = data.orders.storeId
                this.dataForm.score = data.orders.score
                this.dataForm.state = data.orders.state
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/generator/orders/${!this.dataForm.orderNo ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'orderNo': this.dataForm.orderNo || undefined,
                'goodId': this.dataForm.goodId,
                'storeId': this.dataForm.storeId,
                'score': this.dataForm.score,
                'userId': this.dataForm.userId,
                'state': this.dataForm.state,
                'createTime': this.dataForm.createTime,
                'updateTime': this.dataForm.updateTime,
                'msg': this.dataForm.msg
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
