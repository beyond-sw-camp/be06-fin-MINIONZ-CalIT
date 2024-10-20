<script setup>
import * as echarts from 'echarts';
import { onMounted, ref, watch } from 'vue';
import { useRoute } from "vue-router";
import { useSprintStore } from "@/stores/scrum/useSprintStore";
import { getBurndownData, calculateBurndownData } from "@/utils/burndownChartUtils";

const route = useRoute();
const workspaceId = route.params.workspaceId;

const sprintStore = useSprintStore();
const sprintOptions = ref([]);
const selectedSprintId = ref(null);
const chartRef = ref(null);
let chartInstance = null;

const fetchBurndownData = async () => {
  if (!selectedSprintId.value) return;

  let idealData = [];
  let actualData = [];

  const data = await getBurndownData(workspaceId, selectedSprintId.value);

  if (data.result && data.result.length > 0) {
    const result = calculateBurndownData(data.result[0].DONE, data.sprint.startDate, data.sprint.endDate);
    idealData = result.idealData;
    actualData = result.actualData;
  }

  if (chartInstance) {
    chartInstance.setOption({
      xAxis: {
        data: Array.from({ length: idealData.length }, (_, i) => `Day ${i + 1}`)
      },
      series: [
        {
          name: 'Ideal',
          data: idealData
        },
        {
          name: 'Actual',
          data: actualData
        }
      ]
    });
  }
};

onMounted(async () => {
  await sprintStore.getSprintList(workspaceId);
  sprintOptions.value = sprintStore.sprints;

  if (sprintOptions.value.length > 0) {
    selectedSprintId.value = sprintOptions.value[0].sprintId;
    await fetchBurndownData();
  }

  chartInstance = echarts.init(chartRef.value);
  chartInstance.setOption({
    title: {
      text: 'Sprint Burndown Chart',
      left: 'center',
      textStyle: {
        color: '#2c3e50',
        fontSize: 20,
        fontWeight: 'bold',
      },
    },
    tooltip: {
      trigger: 'axis',
      backgroundColor: 'rgba(0, 0, 0, 0.7)',
      borderColor: '#2c3e50',
      borderWidth: 1,
      textStyle: {
        color: '#fff',
      },
      padding: 10,
      formatter: '{b}: {c}',
    },
    legend: {
      data: ['Ideal', 'Actual'],
      top: '5%',
      textStyle: {
        color: '#2c3e50',
      },
    },
    grid: {
      left: '3%',
      right: '3%',
      bottom: '3%',
      containLabel: true,
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: [],
      axisLine: {
        lineStyle: {
          color: '#2c3e50',
        },
      },
      axisLabel: {
        color: '#34495e',
        fontSize: 12,
      },
    },
    yAxis: {
      type: 'value',
      axisLine: {
        lineStyle: {
          color: '#2c3e50',
        },
      },
      axisLabel: {
        color: '#34495e',
        fontSize: 12,
      },
      splitLine: {
        lineStyle: {
          color: '#ecf0f1',
        },
      },
    },
    series: [
      {
        name: 'Ideal',
        type: 'line',
        smooth: true,
        data: [],
        lineStyle: {
          color: 'rgba(8,181,234)',
          width: 3,
        },
        itemStyle: {
          color: 'rgba(8,181,234)',
        },
        areaStyle: {
          color: 'rgba(8,181,234,0.2)',
        },
        symbol: 'circle',
        symbolSize: 8,
        emphasis: {
          focus: 'series',
        },
      },
      {
        name: 'Actual',
        type: 'line',
        smooth: true,
        data: [],
        lineStyle: {
          color: 'rgba(236, 72, 153)',
          width: 3,
        },
        itemStyle: {
          color: 'rgba(236, 72, 153)',
        },
        areaStyle: {
          color: 'rgba(236, 72, 153, 0.2)',
        },
        symbol: 'circle',
        symbolSize: 8,
        emphasis: {
          focus: 'series',
        },
      },
    ],
  });
});

watch(selectedSprintId, fetchBurndownData);
</script>

<template>
  <div class="burndown-chart">
    <div>
      <select v-model="selectedSprintId" @change="fetchBurndownData" class="input-field">
        <option v-for="sprint in sprintOptions" :key="sprint.sprintId" :value="sprint.sprintId">
          {{ sprint.title }}
        </option>
      </select>
    </div>
    <div>
      <h3>Sprint Burndown Chart</h3>
      <div ref="chartRef" style="width: 100%; height: 400px;"></div>
    </div>
  </div>
</template>

<style scoped>
h3 {
  font-size: 24px;
  font-weight: 600;
  margin-bottom: 20px;
  color: #28303f;
  text-align: center;
}

.burndown-chart {
  margin: 20px auto;
  padding: 10px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.1);
  max-width: 800px;
}
</style>