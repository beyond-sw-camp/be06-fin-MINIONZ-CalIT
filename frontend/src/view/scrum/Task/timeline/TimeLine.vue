<template>
  <div class="scheduler">
    <div class="timeline-header">
      <div class="timeline-date" v-for="date in dates" :key="date">{{ date }}</div>
    </div>
    <div class="timeline">
      <vue-draggable-resizable :handles="['ml', 'mr']" :w="width" :h="height" :x="x" :y="y" @resizing="onResize"
                               @resize-stop="onResizeStop" @dragging="onDrag" @drag-stop="onDragStop" class="draggable-item">
        <p>{{ resizing ? 'You are resizing me' : 'Task Item' }}<br>
          X: {{ x }} / Y: {{ y }} - Width: {{ width }} / Height: {{ height }}</p>
      </vue-draggable-resizable>
    </div>
  </div>
</template>

<script>
import VueDraggableResizable from 'vue-draggable-resizable';
import './vue-draggable-resizable.css'

import { defineComponent } from 'vue'

export default defineComponent({
  components: {
    VueDraggableResizable,
  },
  data() {
    return {
      resizing: false,
      x: 50,
      y: 50,
      width: 300,
      height: 100,
      dates: this.generateDates(),
    }
  },
  methods: {
    onResize(...$event) {
      this.resizing = true;
      this.x = $event[0];
      this.y = $event[1];
      this.width = $event[2];
      this.height = $event[3];
    },
    onResizeStop() {

      this.resizing = false;
    },
    onDrag: function (...$event) {
      this.dragging = true
      this.x = $event[0]
      this.y = $event[1]
    },
    onDragStop: function (...$event) {
      this.y = Math.round($event[1] / 100) * 100;
      this.dragging = false
    },
    generateDates() {
      // Create a list of dates for the top header
      const today = new Date();
      let dates = [];
      for (let i = 0; i < 14; i++) {
        let nextDate = new Date(today);
        nextDate.setDate(today.getDate() + i);
        dates.push(nextDate.toISOString().slice(0, 10)); // Format as YYYY-MM-DD
      }
      return dates;
    }
  }
})
</script>

<style scoped>
.scheduler {
  position: relative;
  height: 300px;
  width: 100%;
  background-color: #f0f0f0;
}

.timeline-header {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
}

.timeline-date {
  flex: 1;
  text-align: center;
  font-weight: bold;
  border-left: 1px solid #ccc;
}

.timeline {
  position: relative;
  height: 100%;
  border: 1px solid #ccc;
  background: white;
  display: grid;
  grid-template-columns: repeat(14, 1fr);
  /* 14 days */
}

.timeline::before {
  content: '';
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background-image: repeating-linear-gradient(to right, transparent, transparent 99%, #ccc 1%);
  background-size: 1fr;
}

.draggable-item {
  position: absolute;
  height: 40px;
  border: 1px solid #007bff;
  background-color: #007bff;
  color: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1;
}

.vdr {
  touch-action: none;
  position: absolute;
  box-sizing: border-box;
  border: 1px dashed black;
}
.handle {
  box-sizing: border-box;
  position: absolute;
  width: 10px;
  height: 10px;
  background: #eee;
  border: 1px solid #333;
}
.handle-tl {
  top: -10px;
  left: -10px;
  cursor: nw-resize;
}
.handle-tm {
  top: -10px;
  left: 50%;
  margin-left: -5px;
  cursor: n-resize;
}
.handle-tr {
  top: -10px;
  right: -10px;
  cursor: ne-resize;
}
.handle-ml {
  top: 50%;
  margin-top: -5px;
  left: -10px;
  cursor: w-resize;
}
.handle-mr {
  top: 50%;
  margin-top: -5px;
  right: -10px;
  cursor: e-resize;
}
.handle-bl {
  bottom: -10px;
  left: -10px;
  cursor: sw-resize;
}
.handle-bm {
  bottom: -10px;
  left: 50%;
  margin-left: -5px;
  cursor: s-resize;
}
.handle-br {
  bottom: -10px;
  right: -10px;
  cursor: se-resize;
}
@media only screen and (max-width: 768px) {
  [class*="handle-"]:before {
    content: "";
    left: -10px;
    right: -10px;
    bottom: -10px;
    top: -10px;
    position: absolute;
  }
}

</style>
