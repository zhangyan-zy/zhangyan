<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm"
             @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="站点名称" prop="stationName">
        <el-select v-model="dataForm.station" clearable filterable placeholder="请选择站点">
          <el-option
            v-for="item in stationList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="人力" prop="num">
        <el-input-number v-model="dataForm.num" :min="0" placeholder="人力"></el-input-number>
        <span>个</span>
      </el-form-item>
      <el-form-item label="人力成本" prop="cost1">
        <el-input-number v-model="dataForm.cost1" :min="0" :precision="2" :step="1"
                         placeholder="人力成本">
        </el-input-number>
        <span>￥</span>
      </el-form-item>
      <el-form-item label="话费" prop="cost2">
        <el-input-number v-model="dataForm.cost2" :min="0" :precision="2" :step="1" placeholder="话费"></el-input-number>
        <span>￥</span>
      </el-form-item>
      <el-form-item label="运营成本" prop="cost3">
        <el-input-number v-model="dataForm.cost3" :min="0" :precision="2" :step="1"
                         placeholder="运营成本"></el-input-number>
        <span>￥</span>
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
      stationList: {
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
          num: 0,
          cost1: 0,
          cost2: 0,
          cost3: 0
        },
        dataRule: {
          station: [
            {required: true, message: '站点名称不能为空', trigger: 'blur'}
          ],
          num: [
            {required: true, message: '人力不能为空', trigger: 'blur'}
          ],
          cost1: [
            {required: true, message: '人力成本不能为空', trigger: 'blur'}
          ],
          cost2: [
            {required: true, message: '话费不能为空', trigger: 'blur'}
          ],
          cost3: [
            {required: true, message: '运营成本不能为空', trigger: 'blur'}
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
            url: this.$http.adornUrl(`/sys/billout/info/${this.dataForm.id}`),
            method: 'post',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.station = data.billOut.station
              this.dataForm.num = data.billOut.num
              this.dataForm.cost1 = data.billOut.cost1
              this.dataForm.cost2 = data.billOut.cost2
              this.dataForm.cost3 = data.billOut.cost3
            }
          })
        }
      },
      // 表单提交
      dataFormSubmit() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/sys/billout/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'station': this.dataForm.station,
                'num': this.dataForm.num,
                'cost1': this.dataForm.cost1,
                'cost2': this.dataForm.cost2,
                'cost3': this.dataForm.cost3
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
