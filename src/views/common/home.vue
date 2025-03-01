<template>
  <div class="home-big-screen-container">
    <!-- 整体头部区域 -->
    <div class="header">
      <h1 class="title">奶茶小程序后台管理 - 数据大屏</h1>
    </div>
    <!-- 数据总览区域 -->
    <div class="data-summary">
      <div class="stat-item">
        <span class="stat-label">今日订单总量</span>
        <span class="stat-value">{{ totalOrdersToday }}</span>
      </div>
      <div class="stat-item">
        <span class="stat-label">今日销售额</span>
        <span class="stat-value">{{ totalSalesToday }} 元</span>
      </div>
      <div class="stat-item">
        <span class="stat-label">活跃用户数</span>
        <span class="stat-value">{{ activeUsers }}</span>
      </div>
    </div>
    <!-- 订单趋势区域 -->
    <div class="order-trend">
      <h2 class="section-title">近7日订单趋势</h2>
      <div class="echarts-container" ref="orderTrendChart"></div>
    </div>
    <!-- 热门商品区域 -->
    <div class="popular-products">
      <h2 class="section-title">热门商品TOP5</h2>
      <ul>
        <li v-for="(product, index) in topProducts" :key="index">
          <span class="product-name">{{ product.name }}</span>
          <span class="product-sales">销量: {{ product.sales }}</span>
        </li>
      </ul>
    </div>
    <!-- 地区销售分布区域 -->
    <div class="sales-distribution">
      <h2 class="section-title">地区销售分布</h2>
      <div class="echarts-container" ref="salesDistributionChart"></div>
    </div>
  </div>
</template>

<style scoped>
  .home-big-screen-container {
    background-color: white;
    color: #1a1a1a;
    padding: 20px;
    font-family: Arial, sans-serif;
  }
  .header {
    text-align: center;
    margin-bottom: 30px;
  }
  .title {
    font-size: 36px;
    font-weight: bold;
  }
  .data-summary {
    display: flex;
    justify-content: space-around;
    margin-bottom: 40px;
  }
  .stat-item {
    text-align: center;
  }
  .stat-label {
    font-size: 20px;
    display: block;
    margin-bottom: 10px;
  }
  .stat-value {
    font-size: 30px;
    font-weight: bold;
  }
  .section-title {
    font-size: 28px;
    margin-bottom: 20px;
  }
  .echarts-container {
    height: 400px;
    width: 100%;
  }
  .popular-products ul {
    list-style-type: none;
    padding: 0;
  }
  .popular-products li {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #444;
    padding: 15px 0;
    font-size: 20px;
  }
  .product-name {
    flex: 1;
  }
  .product-sales {
    color: #00ff00;
  }
  .sales-distribution {
    margin-top: 40px;
  }
</style>

<script>
  export default {
    data() {
      return {
        totalOrdersToday: 0,
        totalSalesToday: 0,
        activeUsers: 0,
        topProducts: [],
        orderTrendData: [],
        salesDistributionData: []
      };
    },
    mounted() {
      // 模拟获取数据
      this.fetchData();
      this.renderOrderTrendChart();
      this.renderSalesDistributionChart();
    },
    methods: {
      async fetchData() {
        // 这里应该使用真实的API请求获取数据，例如使用axios
        // 模拟数据
        this.totalOrdersToday = 120;
        this.totalSalesToday = 1500;
        this.activeUsers = 80;
        this.topProducts = [
          { name: "珍珠奶茶", sales: 30 },
          { name: "草莓奶昔", sales: 25 },
          { name: "芋泥波波奶茶", sales: 20 },
          { name: "杨枝甘露", sales: 15 },
          { name: "抹茶星冰乐", sales: 10 }
        ];
        this.orderTrendData = [
          { name: "周一", value: 10 },
          { name: "周二", value: 15 },
          { name: "周三", value: 20 },
          { name: "周四", value: 25 },
          { name: "周五", value: 30 },
          { name: "周六", value: 35 },
          { name: "周日", value: 40 }
        ];
        this.salesDistributionData = [
          { name: "北京", value: 30 },
          { name: "上海", value: 25 },
          { name: "广州", value: 20 },
          { name: "深圳", value: 15 },
          { name: "成都", value: 10 }
        ];
      },
      renderOrderTrendChart() {
        const echarts = require('echarts');
        const chart = echarts.init(this.$refs.orderTrendChart);
        const option = {
          xAxis: {
            type: 'category',
            data: this.orderTrendData.map(item => item.name)
          },
          yAxis: {
            type: 'value'
          },
          series: [{
            data: this.orderTrendData.map(item => item.value),
            type: 'line'
          }]
        };
        chart.setOption(option);
      },
      renderSalesDistributionChart() {
        const echarts = require('echarts');
        const chart = echarts.init(this.$refs.salesDistributionChart);
        const option = {
          series: [{
            type: 'pie',
            data: this.salesDistributionData.map(item => ({ name: item.name, value: item.value }))
          }]
        };
        chart.setOption(option);
      }
    }
  }
</script>


