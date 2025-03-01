<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="店铺名称" prop="name">
      <el-input v-model="dataForm.name" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="店铺编号" prop="no">
      <el-input v-model="dataForm.no" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="地区" prop="area">
      <el-input v-model="dataForm.area" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="地址" prop="address">
      <el-input v-model="dataForm.address" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="坐标" prop="coordinate">
      <el-input v-model="dataForm.coordinate" placeholder="坐标"></el-input>
    </el-form-item>
      <el-form-item label="联系方式" prop="coordinate">
        <el-input v-model="dataForm.phone" placeholder="联系方式"></el-input>
      </el-form-item>
      <el-form-item label="营业时间" prop="coordinate">
        <el-input v-model="dataForm.opentime" placeholder="营业时间"></el-input>
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
        dataForm: {
          id: 0,
          name: '',
          no: '',
          area: '',
          address: '',
          coordinate: '',
          opentime: '',
          phone: ''
        },
        dataRule: {
          name: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          no: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          area: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          address: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          coordinate: [
            { required: true, message: '坐标不能为空', trigger: 'blur' }
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
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/generator/store/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.store.name
                this.dataForm.no = data.store.no
                this.dataForm.area = data.store.area
                this.dataForm.address = data.store.address
                this.dataForm.coordinate = data.store.coordinate
                this.dataForm.createtime = data.store.createtime
                this.dataForm.openTime = data.store.openTime
                this.dataForm.phone = data.store.phone
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
              url: this.$http.adornUrl(`/generator/store/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'no': this.dataForm.no,
                'area': this.dataForm.area,
                'address': this.dataForm.address,
                'coordinate': this.dataForm.coordinate,
                'opentime': this.dataForm.opentime,
                'phone': this.dataForm.phone

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
