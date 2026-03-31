<template>
  <div class="backend-end-bar">
    <span class="brand">AI 助手管理端</span>
    <span class="divider">|</span>
    <span class="meta">{{ currentYear }} Copyright</span>
    <span class="divider">|</span>
    <span class="meta">Built with Vue 3 + Element Plus</span>
  </div>
</template>

<script setup>
const currentYear = new Date().getFullYear();
</script>

<style scoped>
.backend-end-bar {
  min-height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  font-size: 12px;
  color: #7d8798;
}

.brand {
  font-weight: 600;
  color: #4f5d75;
}

.divider {
  color: #c0c4cc;
}

.meta {
  white-space: nowrap;
}

@media (max-width: 768px) {
  .backend-end-bar {
    flex-wrap: wrap;
    row-gap: 4px;
    column-gap: 8px;
  }

  .divider {
    display: none;
  }
}
</style>
