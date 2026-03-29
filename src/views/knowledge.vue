<template>
  <div class="knowledge-page">
    <PageHead :title="props.title">
      <template #buttons>
        <el-button type="primary">添加知识</el-button>
      </template>
    </PageHead>

    <div class="content">
      <p>这是知识视图的内容</p>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  title: { type: String, default: "默认标题" },
});
import PageHead from "@/components/PageHead.vue";
</script>

<style scoped>
.knowledge-page {
  background: #fff;
  border-radius: 8px;
}

.content {
  padding: 20px;
}
</style>
