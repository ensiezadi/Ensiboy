<template>
  <div class="knowledge-page">
    <PageHead :title="pageTitle">
      <template #buttons>
        <el-button type="primary" @click="handleCreate">添加知识</el-button>
      </template>
    </PageHead>

    <div class="content">
      <div class="search-panel">
        <TableSearch
          :form-item="searchFormItems"
          @search="handleSearch"
          @reset="handleReset"
        />
      </div>

      <div class="results results-card">
        <el-table
          class="knowledge-table"
          :data="sortedTableData"
          border
          v-loading="tableLoading"
          empty-text="暂无数据"
          :header-cell-style="tableHeaderStyle"
        >
          <el-table-column
            prop="displayTitle"
            min-width="220"
            show-overflow-tooltip
          >
            <template #header>
              <button
                class="title-sort-btn"
                type="button"
                @click="toggleTitleSort"
              >
                <span>文章标题</span>
                <span class="sort-indicator">{{ titleSortText }}</span>
              </button>
            </template>
          </el-table-column>
          <el-table-column
            prop="displayCategory"
            label="分类"
            min-width="140"
          />
          <el-table-column
            prop="displaySummary"
            label="摘要"
            min-width="220"
            show-overflow-tooltip
          />
          <el-table-column
            prop="displayTags"
            label="标签"
            min-width="180"
            show-overflow-tooltip
          >
            <template #default="scope">
              <div class="tag-list" v-if="scope.row.displayTagList.length">
                <el-tag
                  v-for="tag in scope.row.displayTagList"
                  :key="tag"
                  class="tag-item"
                  size="small"
                  effect="plain"
                >
                  {{ tag }}
                </el-tag>
              </div>
              <span class="muted-text" v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="displayAuthor"
            label="发布者"
            min-width="120"
          />
          <el-table-column prop="displayViews" label="阅读量" width="110" />
          <el-table-column prop="displayCreatedAt" min-width="180">
            <template #header>
              <button
                class="title-sort-btn"
                type="button"
                @click="toggleTimeSort"
              >
                <span>发布时间</span>
                <span class="sort-indicator">{{ timeSortText }}</span>
              </button>
            </template>
          </el-table-column>
          <el-table-column label="操作" min-width="180" fixed="right">
            <template #default="scope">
              <div class="action-group">
                <el-button
                  class="action-btn action-edit"
                  link
                  type="primary"
                  @click="handleEdit(scope.row)"
                >
                  编辑
                </el-button>
                <el-button
                  class="action-btn action-publish"
                  v-if="scope.row.canPublish"
                  link
                  type="success"
                  @click="handlePublish(scope.row)"
                >
                  发布
                </el-button>
                <el-button
                  class="action-btn action-offline"
                  v-else
                  link
                  type="warning"
                  @click="handleOffline(scope.row)"
                >
                  下线
                </el-button>
                <el-button
                  class="action-btn action-delete"
                  link
                  type="danger"
                  @click="handleDelete(scope.row)"
                >
                  删除
                </el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>

        <div class="pagination-wrap">
          <el-pagination
            background
            layout="total, sizes, prev, pager, next, jumper"
            :total="pagination.total"
            :page-size="pagination.pageSize"
            :current-page="pagination.current"
            :page-sizes="[10, 20, 50, 100]"
            @current-change="handlePageChange"
            @size-change="handleSizeChange"
          />
        </div>
      </div>
    </div>

    <ArticalDialog
      v-model="dialogVisible"
      :title="dialogTitle"
      :article="currentArticle"
      :category-options="dialogCategoryOptions"
      @success="handleArticleSaved"
    />
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from "vue";
import { useRoute } from "vue-router";
import { ElMessage, ElMessageBox } from "element-plus";
import TableSearch from "@/components/TableSearch.vue";
import PageHead from "@/components/PageHead.vue";
import ArticalDialog from "@/components/ArticalDialog.vue";
import { getCategory } from "@/api/admin";
import { getArticle } from "@/api/admin";
import { getArticleDetail } from "@/api/admin";
import { updateArticleStatus, deleteArticle } from "@/api/admin";

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
});

const tableData = ref([]);
const titleSortOrder = ref("");
const timeSortOrder = ref("desc");
const lastSortField = ref("time");
const tableLoading = ref(false);
const dialogVisible = ref(false);
const dialogTitle = ref("文章详情");
const currentArticle = ref(null);
const tableHeaderStyle = {
  background: "#f7f9fc",
  color: "#5a6270",
  fontWeight: 600,
};

const pickFirst = (obj, keys = []) => {
  for (const key of keys) {
    const value = key
      .split(".")
      .reduce((acc, part) => (acc == null ? undefined : acc[part]), obj);
    if (value !== undefined && value !== null && value !== "") {
      return value;
    }
  }
  return "";
};

const getStatusMeta = (rawStatus) => {
  const status = String(rawStatus ?? "").toLowerCase();
  if (
    [
      "enabled",
      "enable",
      "1",
      "true",
      "open",
      "normal",
      "published",
      "online",
    ].includes(status)
  ) {
    return { text: "启用", tag: "success" };
  }
  if (
    ["disabled", "disable", "0", "false", "close", "forbidden"].includes(status)
  ) {
    return { text: "禁用", tag: "info" };
  }
  return { text: rawStatus || "未知", tag: "warning" };
};

const handleSearch = async (formData) => {
  currentSearchParams.value = formData;
  pagination.current = 1;
  await fetchArticleList();
};

const route = useRoute();
const props = defineProps({
  title: { type: String, default: "" },
});
const pageTitle = computed(
  () => props.title || route.meta?.title || "默认标题",
);

const titleSortText = computed(() => {
  if (titleSortOrder.value === "asc") {
    return "升序";
  }
  if (titleSortOrder.value === "desc") {
    return "降序";
  }
  return "默认";
});

const timeSortText = computed(() => {
  if (timeSortOrder.value === "asc") {
    return "最早";
  }
  if (timeSortOrder.value === "desc") {
    return "最新";
  }
  return "默认";
});

const parseDateTime = (value) => {
  if (!value) return 0;
  const timestamp = Date.parse(String(value).replace(/-/g, "/"));
  return Number.isNaN(timestamp) ? 0 : timestamp;
};

const sortedTableData = computed(() => {
  const list = [...tableData.value];
  if (!timeSortOrder.value && !titleSortOrder.value) {
    return list;
  }

  return list.sort((a, b) => {
    const compareTitle = () => {
      if (!titleSortOrder.value) return 0;
      const titleDirection = titleSortOrder.value === "asc" ? 1 : -1;
      return (
        String(a.displayTitle || "").localeCompare(
          String(b.displayTitle || ""),
          "zh-CN",
          {
            numeric: true,
            sensitivity: "base",
          },
        ) * titleDirection
      );
    };

    const compareTime = () => {
      if (!timeSortOrder.value) return 0;
      const timeDirection = timeSortOrder.value === "asc" ? 1 : -1;
      const timeA = parseDateTime(a.displayCreatedAt);
      const timeB = parseDateTime(b.displayCreatedAt);
      return (timeA - timeB) * timeDirection;
    };

    // 谁最后被点击，谁优先排序，另一个作为次级排序
    if (lastSortField.value === "title") {
      const titleDiff = compareTitle();
      if (titleDiff !== 0) return titleDiff;
      const timeDiff = compareTime();
      if (timeDiff !== 0) return timeDiff;
      return 0;
    }

    const timeDiff = compareTime();
    if (timeDiff !== 0) return timeDiff;
    const titleDiff = compareTitle();
    if (titleDiff !== 0) return titleDiff;
    return 0;
  });
});

const toggleTitleSort = () => {
  lastSortField.value = "title";
  if (titleSortOrder.value === "") {
    titleSortOrder.value = "asc";
    return;
  }

  if (titleSortOrder.value === "asc") {
    titleSortOrder.value = "desc";
    return;
  }

  titleSortOrder.value = "";
};

const toggleTimeSort = () => {
  lastSortField.value = "time";
  if (timeSortOrder.value === "") {
    timeSortOrder.value = "desc";
    return;
  }

  if (timeSortOrder.value === "desc") {
    timeSortOrder.value = "asc";
    return;
  }

  timeSortOrder.value = "";
};

const fetchArticleList = async () => {
  const form = currentSearchParams.value || {};
  const params = {
    title: form.name || "",
    categoryId: form.categoryId || "",
    authorName: form.authorName || "",
    currentPage: pagination.current,
    size: pagination.pageSize,
  };

  tableLoading.value = true;
  try {
    const res = await getArticle(params);
    const payload = res?.data || {};
    const dataBlock = payload?.data || {};
    const rows = dataBlock.records || dataBlock.list || payload.list || [];

    tableData.value = (Array.isArray(rows) ? rows : []).map((item) => {
      const rawStatus = pickFirst(item, ["status", "articleStatus", "state"]);
      const statusMeta = getStatusMeta(rawStatus);
      const isPublished = statusMeta.tag === "success";
      const displayTitle =
        pickFirst(item, [
          "title",
          "articleTitle",
          "name",
          "articleName",
          "knowledgeTitle",
        ]) || "-";
      const displayCategory =
        pickFirst(item, [
          "categoryName",
          "category.name",
          "category.title",
          "categoryTitle",
          "classifyName",
          "categoryId",
        ]) || "-";
      const displayAuthor =
        pickFirst(item, [
          "authorName",
          "author",
          "createBy",
          "creatorName",
          "userName",
        ]) || "-";
      const displayCreatedAt =
        pickFirst(item, [
          "createdAt",
          "createTime",
          "publishTime",
          "gmtCreate",
          "createdTime",
          "createDate",
        ]) || "-";
      const displaySummary =
        pickFirst(item, ["summary", "description", "remark", "content"]) || "-";
      const displayTagsRaw = pickFirst(item, ["tags", "tagList", "labels"]);
      const displayTagList = Array.isArray(displayTagsRaw)
        ? displayTagsRaw.map((tag) => String(tag).trim()).filter(Boolean)
        : String(displayTagsRaw || "")
            .split(/[，,]/)
            .map((tag) => tag.trim())
            .filter(Boolean);
      const displayTags = displayTagList.join("、") || "-";
      const displayViews =
        pickFirst(item, [
          "views",
          "viewCount",
          "readCount",
          "readingNum",
          "browseCount",
        ]) || 0;

      return {
        ...item,
        displayTitle,
        displayCategory,
        displaySummary,
        displayTags,
        displayTagList,
        displayAuthor,
        displayViews,
        displayCreatedAt,
        statusText: statusMeta.text,
        statusTag: statusMeta.tag,
        canPublish: !isPublished,
      };
    });

    pagination.total =
      dataBlock.total || payload.total || tableData.value.length || 0;
  } catch (error) {
    tableData.value = [];
    pagination.total = 0;
    ElMessage.error(error?.message || "获取知识列表失败");
  } finally {
    tableLoading.value = false;
  }
};

const currentSearchParams = ref({});
const categoryLoading = ref(false);
const categoryList = ref([]);
const categoryOptions = ref([]);
const dialogCategoryOptions = computed(() =>
  categoryOptions.value.map((item) => ({
    label: item?.attrs?.label,
    value: item?.attrs?.value,
  })),
);
const searchFormItems = ref([
  {
    label: "知识标题",
    prop: "name",
    value: "",
    placeholder: "请输入知识标题",
    component: "el-input",
  },
  {
    label: "分类",
    prop: "categoryId",
    value: "",
    placeholder: "请选择分类",
    component: "el-select",
    props: [],
  },
  {
    label: "发布者",
    prop: "authorName",
    value: "",
    placeholder: "请输入发布者",
    component: "el-input",
  },
]);

const flattenCategory = (nodes, collector = []) => {
  if (!Array.isArray(nodes)) {
    return collector;
  }

  nodes.forEach((node) => {
    const value = pickFirst(node, ["id", "categoryId", "value"]);
    const label =
      pickFirst(node, ["categoryName", "name", "title", "label"]) ||
      String(value || "");

    if (value !== "" && label) {
      collector.push({
        component: "el-option",
        attrs: {
          label,
          value,
        },
      });
    }

    const children = node?.children || node?.childList || node?.nodes;
    if (Array.isArray(children) && children.length) {
      flattenCategory(children, collector);
    }
  });

  return collector;
};

const syncCategoryOptions = () => {
  const categoryField = searchFormItems.value.find(
    (item) => item.prop === "categoryId",
  );
  if (categoryField) {
    categoryField.props = [...categoryOptions.value];
  }
};

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

    categoryOptions.value = flattenCategory(categoryList.value);
    syncCategoryOptions();
  } catch (error) {
    ElMessage.error(error?.message || "获取分类失败");
  } finally {
    categoryLoading.value = false;
  }

  await fetchArticleList();
});

const handleReset = () => {
  currentSearchParams.value = {};
  pagination.current = 1;
  fetchArticleList();
};

const handlePageChange = (page) => {
  pagination.current = page;
  fetchArticleList();
};

const handleSizeChange = (size) => {
  pagination.pageSize = size;
  pagination.current = 1;
  fetchArticleList();
};

const handleEdit = (row) => {
  openEditDialog(row);
};

const normalizeArticle = (article = {}, row = {}) => {
  const source = { ...row, ...article };
  return {
    ...source,
    id: pickFirst(source, ["id", "articleId", "knowledgeId"]) || "",
    title:
      pickFirst(source, ["title", "articleTitle", "name", "displayTitle"]) ||
      "",
    content:
      pickFirst(source, [
        "content",
        "articleContent",
        "description",
        "remark",
      ]) || "",
    coverImage:
      pickFirst(source, ["coverImage", "cover", "filePath", "coverUrl"]) || "",
    categoryId:
      pickFirst(source, ["categoryId", "category.id", "category.value"]) || "",
    summary: pickFirst(source, ["summary", "description", "remark"]) || "",
    tags: pickFirst(source, ["tags", "tagList", "labels"]) || [],
  };
};

const openEditDialog = async (row) => {
  const articleId = pickFirst(row, ["id", "articleId", "knowledgeId"]);
  let detail = {};

  if (articleId !== "") {
    try {
      const res = await getArticleDetail(articleId);
      const payload = res?.data || {};
      detail = payload.data || payload;
    } catch (error) {
      ElMessage.warning(
        error?.message || "文章详情获取失败，已使用列表数据回填",
      );
    }
  }

  currentArticle.value = normalizeArticle(detail, row);
  dialogTitle.value = "编辑知识";
  dialogVisible.value = true;
};

const handleCreate = () => {
  currentArticle.value = {
    id: "",
    title: "",
    content: "",
    coverImage: "",
    categoryId: "",
    summary: "",
    tags: [],
  };
  dialogTitle.value = "添加知识";
  dialogVisible.value = true;
};

const handlePublish = (row) => {
  changeArticleStatus(row, "1", "发布");
};

const handleOffline = (row) => {
  changeArticleStatus(row, "0", "下线");
};

const getArticleId = (row) =>
  pickFirst(row, ["id", "articleId", "knowledgeId"]);

const changeArticleStatus = async (row, status, actionText) => {
  const articleId = getArticleId(row);
  if (!articleId) {
    ElMessage.error("未找到文章ID，无法操作");
    return;
  }

  try {
    await ElMessageBox.confirm(
      `确认要${actionText}《${row.displayTitle || "该文章"}》吗？`,
      `${actionText}确认`,
      {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      },
    );

    await updateArticleStatus(articleId, status);
    ElMessage.success(`${actionText}成功`);
    await fetchArticleList();
  } catch (error) {
    if (error === "cancel" || error === "close") {
      return;
    }
    ElMessage.error(error?.message || `${actionText}失败`);
  }
};

const handleDelete = async (row) => {
  const articleId = getArticleId(row);
  if (!articleId) {
    ElMessage.error("未找到文章ID，无法删除");
    return;
  }

  try {
    await ElMessageBox.confirm(
      `删除后不可恢复，确认删除《${row.displayTitle || "该文章"}》吗？`,
      "删除确认",
      {
        confirmButtonText: "删除",
        cancelButtonText: "取消",
        type: "warning",
      },
    );

    await deleteArticle(articleId);
    ElMessage.success("删除成功");
    await fetchArticleList();
  } catch (error) {
    if (error === "cancel" || error === "close") {
      return;
    }
    ElMessage.error(error?.message || "删除失败");
  }
};

const handleArticleSaved = async () => {
  await fetchArticleList();
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

.search-panel {
  padding: 16px 16px 0;
  background: #f7f8fa;
  border: 1px solid #eceff3;
  border-radius: 8px;
}

:deep(.table-search-form .button-col) {
  justify-content: flex-end;
}

.results {
  margin-top: 20px;
}

.results-card {
  padding: 6px;
  border: 1px solid #eceff3;
  border-radius: 8px;
}

.knowledge-table {
  border-radius: 6px;
  overflow: hidden;
}

.title-sort-btn {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  border: none;
  background: transparent;
  color: #3f4f68;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  padding: 0;
}

.title-sort-btn:hover {
  color: #2f88ff;
}

.sort-indicator {
  min-width: 30px;
  font-size: 12px;
  font-weight: 500;
  color: #7b8798;
  text-align: center;
}

.tag-list {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
  max-width: 100%;
}

.tag-item {
  margin: 0;
}

.muted-text {
  color: #94a3b8;
}

.action-group {
  display: inline-flex;
  align-items: center;
  justify-content: flex-start;
  gap: 4px;
}

.action-btn {
  font-weight: 500;
  min-width: 36px;
  justify-content: center;
}

.action-group :deep(.el-button + .el-button) {
  margin-left: 0;
}

.action-edit {
  color: #2f88ff;
}

.action-publish {
  color: #33a852;
}

.action-offline {
  color: #d79b24;
}

.action-delete {
  color: #f56c6c;
}

:deep(.table-search-form) {
  margin-bottom: 0;
  background: transparent;
  padding: 0;
}

:deep(.knowledge-table .el-table__row td) {
  padding: 12px 0;
}

:deep(.knowledge-table .el-table__row:hover > td) {
  background-color: #f8fbff;
}

.pagination-wrap {
  margin-top: 16px;
  display: flex;
  justify-content: flex-end;
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
