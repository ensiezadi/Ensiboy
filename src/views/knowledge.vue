<template>
  <div class="knowledge-page">
    <PageHead :title="props.title">
      <template #buttons>
        <el-button type="primary">添加知识</el-button>
      </template>
    </PageHead>

    <div class="content">
      <TableSearch @search="handleSearch" @reset="handleReset" />

      <div class="results">
        <p class="result-title">分类树加载结果</p>
        <p v-if="categoryLoading" class="muted">分类加载中...</p>
        <p v-else-if="!categoryList.length" class="muted">暂无分类数据</p>
        <pre v-else class="json-box">{{
          JSON.stringify(categoryList, null, 2)
        }}</pre>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { ElMessage } from "element-plus";
import TableSearch from "@/components/TableSearch.vue";
import PageHead from "@/components/PageHead.vue";
import { getCategory } from "@/api/admin";

const props = defineProps({
  title: { type: String, default: "默认标题" },
});

const currentSearchParams = ref({});
const categoryLoading = ref(false);
const categoryList = ref([]);

onMounted(async () => {
  categoryLoading.value = true;
  try {
    const res = await getCategory();
    const payload = res?.data || {};
    const rawList =
      payload.data ||
      payload.rows ||
      payload.list ||
      payload.categoryTree ||
      [];

    categoryList.value = Array.isArray(rawList)
      ? rawList
      : rawList
        ? [rawList]
        : [];
  } catch (error) {
    console.error("获取分类树失败:", error);
    ElMessage.error(error?.message || "获取分类树失败");
  } finally {
    categoryLoading.value = false;
  }
});

const handleSearch = (formData) => {
  currentSearchParams.value = formData;
};

const handleReset = () => {
  currentSearchParams.value = {};
};
</script>

<style scoped>
.knowledge-page {
  background: #fff;
  border-radius: 8px;
}

.content {
  padding: 20px;
}

.results {
  margin-top: 20px;
}

.result-title {
  margin-bottom: 12px;
  font-weight: 600;
  color: #1f2a44;
}

.muted {
  margin: 0;
  color: #64748b;
}

.json-box {
  margin: 0;
  padding: 12px;
  border-radius: 8px;
  border: 1px solid #e2e8f0;
  background: #f8fafc;
  max-height: 360px;
  overflow: auto;
  font-size: 12px;
  line-height: 1.5;
}

@media (max-width: 768px) {
  .knowledge-page {
    border-radius: 4px;
  }

  .content {
    padding: 12px;
  }
}

@media (max-width: 512px) {
  .content {
    padding: 8px;
  }
}
</style>
