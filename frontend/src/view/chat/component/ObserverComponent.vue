<template>
  <div ref="trigger" style="height: 1px"></div>
</template>

<script>
export default {
  props: {
    callback: {
      type: Function,
      required: true,
    },
    isLoading: {
      type: Boolean,
      default: false,
    },
  },
  mounted() {
    const observer = new IntersectionObserver((entries) => {
      if (entries[0].isIntersecting && !this.isLoading) {
        this.callback();
      }
    });

    observer.observe(this.$refs.trigger);
  },
};
</script>
