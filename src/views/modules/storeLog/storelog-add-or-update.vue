<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="店铺" prop="typeId">
        <el-select v-model="dataForm.storeId" clearable filterable placeholder="店铺">
          <el-option
            v-for="item in StoreList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
    <el-form-item label="公告信息" prop="msg">
      <el-input v-model="dataForm.msg" placeholder="公告信息"></el-input>
    </el-form-item>
    <el-form-item label="新品推荐" prop="newGood">
      <el-input v-model="dataForm.newGood" placeholder="新品推荐"></el-input>
    </el-form-item>
    <el-form-item label="活动信息" prop="activityMsg">
      <el-input v-model="dataForm.activityMsg" placeholder="活动信息"></el-input>
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
        StoreList: [],
        dataForm: {
          id: 0,
          storeId: '',
          msg: '',
          newGood: '',
          activityMsg: ''
        },
        dataRule: {
          storeId: [
            { required: true, message: '店铺id不能为空', trigger: 'blur' }
          ],
          msg: [
            { required: true, message: '公告信息不能为空', trigger: 'blur' }
          ],
          newGood: [
            { required: true, message: '新品推荐不能为空', trigger: 'blur' }
          ],
          activityMsg: [
            { required: true, message: '活动信息不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      getStoreList() {
        this.$http({
          url: this.$http.adornUrl('/generator/store/selectAll'),
          method: 'post',
          params: this.$http.adornParams({})
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.StoreList = data.list
          }
        })
      },
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/generator/storelog/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.storeId = data.storeLog.storeId
                this.dataForm.msg = data.storeLog.msg
                this.dataForm.newGood = data.storeLog.newGood
                this.dataForm.activityMsg = data.storeLog.activityMsg
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
              url: this.$http.adornUrl(`/generator/storelog/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'storeId': this.dataForm.storeId,
                'msg': this.dataForm.msg,
                'newGood': this.dataForm.newGood,
                'activityMsg': this.dataForm.activityMsg
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
