<template>
  <div class="page">
    <p class="page-title">{{ title }}</p>
    <div class="action-btn">
      <slot name="buttons"></slot>
    </div>
  </div>
</template>

<script setup>
defineProps({
  title: {
    type: String,
    default: "页面标签",
  },
});
</script>

<style lang="scss" scoped>
.page {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  border-bottom: 1px solid #eee;

  .page-title {
    font-size: 18px;
    font-weight: bold;
    margin: 0;
  }
}
</style>
