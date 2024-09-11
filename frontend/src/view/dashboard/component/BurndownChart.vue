<script setup>
import * as echarts from 'echarts';
import { onMounted, ref } from 'vue';

const chartRef = ref(null);

onMounted(() => {
  const chart = echarts.init(chartRef.value);

  const option = {
    title: {
      text: 'Sprint Burndown Chart',
      left: 'center',
      textStyle: {
        color: '#333',
        fontSize: 18,
      },
    },
    tooltip: {
      trigger: 'axis',
      backgroundColor: 'rgba(255, 255, 255, 0.8)',
      borderColor: '#ccc',
      borderWidth: 1,
      textStyle: {
        color: '#333',
      },
    },
    legend: {
      data: ['Ideal', 'Actual'],
      top: '10%',
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true,
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: ['Day 1', 'Day 2', 'Day 3', 'Day 4', 'Day 5', 'Day 6', 'Day 7'],
      axisLine: {
        lineStyle: {
          color: '#ccc',
        },
      },
      axisLabel: {
        color: '#333',
      },
    },
    yAxis: {
      type: 'value',
      axisLine: {
        lineStyle: {
          color: '#ccc',
        },
      },
      axisLabel: {
        color: '#333',
      },
      splitLine: {
        lineStyle: {
          color: '#eee',
        },
      },
    },
    series: [
      {
        name: 'Ideal',
        type: 'line',
        smooth: true,
        data: [100, 80, 60, 40, 20, 10, 0],
        lineStyle: {
          color: '#67C23A',
          width: 2,
        },
        itemStyle: {
          color: '#67C23A',
        },
        areaStyle: {
          color: 'rgba(103, 194, 58, 0.2)',
        },
      },
      {
        name: 'Actual',
        type: 'line',
        smooth: true,
        data: [100, 90, 70, 50, 30, 20, 10],
        lineStyle: {
          color: '#F56C6C',
          width: 2,
        },
        itemStyle: {
          color: '#F56C6C',
        },
        areaStyle: {
          color: 'rgba(245, 108, 108, 0.2)',
        },
      },
    ],
  };

  chart.setOption(option);
});
</script>

<template>
  <div class="burndown-chart">
    <h3>Burn Down Chart</h3>
    <div ref="chartRef" style="width: 100%; height: 400px;"></div>
  </div>
</template>

<style scoped>
h3 {
  font-size: 20px;
  font-weight: 500;
  margin: 0 0 1rem;
  text-align: center;
}
</style>