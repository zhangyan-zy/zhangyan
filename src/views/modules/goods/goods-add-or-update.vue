<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="商品类型" prop="typeId">
        <el-select v-model="dataForm.typeId" clearable filterable placeholder="商品类型">
          <el-option
            v-for="item in typeIdList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
    <el-form-item label="名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="名称"></el-input>
    </el-form-item>
    <el-form-item label="价格" prop="price">
<!--      <el-input v-model="dataForm.price" placeholder="价格"></el-input>-->
      <el-input-number v-model="dataForm.price" :precision="2" :step="0.01"></el-input-number>
    </el-form-item>
      <el-form-item label="数量" prop="count">
        <!--      <el-input v-model="dataForm.price" placeholder="价格"></el-input>-->
        <el-input-number v-model="dataForm.count" :precision="0" :step="1"></el-input-number>
      </el-form-item>
      <el-form-item label="重量" prop="weight">
<!--      <el-input v-model="dataForm.weight" placeholder="重量"></el-input>-->
      <el-input-number v-model="dataForm.weight" :precision="0" :step="1"></el-input-number>
      </el-form-item>
    <el-form-item label="描述" prop="description">
      <el-input v-model="dataForm.description" placeholder="描述"></el-input>
    </el-form-item >
      <el-form-item label="图片" prop="url" >
        <el-upload
          drag
          :action="url"
          :before-upload="beforeUploadHandle"
          :on-success="successHandle"
          multiple
          style="text-align: center;">
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
        </el-upload>
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
        typeIdList: [],
        url: '',
        dataForm: {
          id: 0,
          name: '',
          price: '',
          weight: '',
          description: '',
          typeId: '',
          count: '',
          url: ''
        },
        dataRule: {
          typeId: [
            { required: true, message: '商品种类不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '名称不能为空', trigger: 'blur' }
          ],
          price: [
            { required: true, message: '价格不能为空', trigger: 'blur' }
          ],
          weight: [
            { required: true, message: '重量不能为空', trigger: 'blur' }
          ],
          description: [
            { required: true, message: '描述不能为空', trigger: 'blur' }
          ],
          count: [
            { required: true, message: '数量不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/generator/goods/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.goods.name
                this.dataForm.price = data.goods.price
                this.dataForm.weight = data.goods.weight
                this.dataForm.description = data.goods.description
                this.dataForm.count = data.goods.count
                this.dataForm.typeId = data.goods.typeId
                this.dataForm.url = this.url
              }
            })
          }
        })
        this.url = this.$http.adornUrl(`/sys/oss/upload?token=${this.$cookie.get('token')}`)
        this.visible = true
      },
      getTypeList() {
        this.$http({
          url: this.$http.adornUrl('/generator/type/selectAll'),
          method: 'post',
          params: this.$http.adornParams({})
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.typeIdList = data.list
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/generator/goods/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'price': this.dataForm.price,
                'weight': this.dataForm.weight,
                'description': this.dataForm.description,
                'typeId': this.dataForm.typeId,
                'count': this.dataForm.count,
                'imageUrl': this.dataForm.url
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
      // 上传之前
      beforeUploadHandle (file) {
        if (file.type !== 'image/jpg' && file.type !== 'image/jpeg' && file.type !== 'image/png' && file.type !== 'image/gif') {
          this.$message.error('只支持jpg、png、gif格式的图片！')
          return false
        }
        this.num++
      },
      // 上传成功
      successHandle (response, file, fileList) {
        this.fileList = fileList
        this.successNum++
        if (response && response.code === 0) {
          this.dataForm.url = response.url
          if (this.num === this.successNum) {
            this.$confirm('操作成功, 是否继续操作?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).catch(() => {
              this.visible = false
            })
          }
        } else {
          this.$message.error(response.msg)
        }
      },
      // 弹窗关闭时
      closeHandle () {
        this.fileList = []
        this.$emit('refreshDataList')
      }
    }
  }
</script>
