<template>
  <div class="home-vue">
    <el-form
      :inline="true"
      :model="dataForm"
      @keyup.enter.native="getDataList()"
    >
      <el-select
        v-model="coustomerId"
        clearable
        filterable
        placeholder="请选择客户"
      >
        <el-option
          v-for="item in coustomerList"
          :key="item.userId"
          :label="item.username"
          :value="item.userId"
        >
        </el-option>
      </el-select>

      <el-form-item>
        <el-date-picker
          v-model="dataForm.date"
          type="daterange"
          value-format="yyyy-MM-dd"
          range-separator="——"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        >
        </el-date-picker>
      </el-form-item>

      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
      </el-form-item>
    </el-form>

    <div class="tab">
      <el-table
        class="tab1"
        :data="dataList"
        border
        v-loading="dataListLoading"
      >
        <el-table-column
          prop="username"
          header-align="center"
          align="center"
          label="客户名称"
        ></el-table-column>
        <el-table-column
          prop="sumLeads"
          header-align="center"
          align="center"
          label="leads数量"
        ></el-table-column>
        <el-table-column
          prop="status1"
          header-align="center"
          align="center"
          label="待分配"
        ></el-table-column>
        <el-table-column
          prop="status2"
          header-align="center"
          align="center"
          label="待处理"
        ></el-table-column>
        <el-table-column
          prop="status3"
          header-align="center"
          align="center"
          label="已加微"
        ></el-table-column>
        <el-table-column
          prop="status4"
          header-align="center"
          align="center"
          label="已响应"
        ></el-table-column>
        <el-table-column
          prop="status5"
          header-align="center"
          align="center"
          label="已成单"
        ></el-table-column>
        <el-table-column
          prop="status6"
          header-align="center"
          align="center"
          label="未成单"
        ></el-table-column>
        <el-table-column
          prop="status0"
          header-align="center"
          align="center"
          label="已关闭"
        ></el-table-column>
      </el-table>
    </div>

    <!-- 下栏列表1 -->

    <el-col>
      <div>
        <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize1"
          :total="totalPage1"
          layout="total, sizes, prev, pager, next, jumper"
        ></el-pagination>
      </div>
    </el-col>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      workerList: [],
      workerId: "",
      coustomerList: [],
      coustomerId: "",
      dataForm: {
        status: "",
        date: []
      }
    };
  },
  mounted() {},

  components: {},

  activated() {
    this.getDataList();
    this.getCoustomerList();
  },

  methods: {
    // 获取一表格数据列表
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/common/coustomerLeadersList"),
        method: "post",
        params: this.$http.adornParams({
          page: this.pageIndex,
          limit: this.pageSize,
          parentId: this.coustomerId,
          date1: this.dataForm.date ? this.dataForm.date[0] : "",
          date2: this.dataForm.date ? this.dataForm.date[1] : ""
        })
      }).then(({ data }) => {
        console.log("data", data);
        if (data && data.code === 0) {
          this.dataList = data.user;
        } else {
          this.dataList = [];
          this.totalPage = 0;
        }
        this.dataListLoading = false;
      });
    },
    //
    getCoustomerList() {
      this.$http({
        url: this.$http.adornUrl("/common/account/coustomer"),
        method: "get",
        params: this.$http.adornParams({})
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.coustomerList = data.user;
        }
      });
    },

    // 每页数
    sizeChangeHandle1(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    // 当前页
    currentChangeHandle1(val) {
      this.pageIndex = val;
      this.getDataList();
    }
  }
};
</script>

<style></style>
