<template>
  <div class="emotion-diary-page">
    <PageHead :title="pageTitle" />

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
          class="emotion-table"
          :data="tableData"
          border
          v-loading="tableLoading"
          empty-text="暂无情绪日记"
          :header-cell-style="tableHeaderStyle"
        >
          <el-table-column
            prop="displayId"
            label="ID"
            width="60"
            align="center"
          />
          <el-table-column
            prop="displaySessionId"
            label="会话ID"
            min-width="120"
          />
          <el-table-column
            prop="displayRecordDate"
            label="记录日期"
            min-width="140"
            align="center"
          />

          <el-table-column label="情绪评分" min-width="110" align="center">
            <template #default="scope">
              <div class="rating-stars">
                <el-rate
                  v-model="scope.row.moodScore"
                  :max="5"
                  disabled
                  allow-half
                />
              </div>
            </template>
          </el-table-column>

          <el-table-column label="生活指标" min-width="100" align="center">
            <template #default="scope">
              <span class="life-indicator">
                {{ scope.row.displayLifeIndicator }}
              </span>
            </template>
          </el-table-column>

          <el-table-column
            prop="displayDominantEmotion"
            label="主要情绪"
            min-width="100"
          />

          <el-table-column
            prop="displayEmotionDescription"
            label="自记内容"
            min-width="200"
            show-overflow-tooltip
          />

          <el-table-column
            label="操作"
            width="100"
            align="center"
            fixed="right"
          >
            <template #default="scope">
              <div class="action-group">
                <el-button
                  link
                  type="primary"
                  size="small"
                  @click="handleViewDetail(scope.row)"
                >
                  详情
                </el-button>
                <el-divider direction="vertical" />
                <el-button
                  link
                  type="danger"
                  size="small"
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

    <el-dialog
      v-model="detailDialogVisible"
      title="情绪日记详情"
      width="720px"
      top="10vh"
      destroy-on-close
      class="emotion-detail-dialog"
    >
      <div class="detail-content" v-if="activeDetail">
        <div class="detail-section">
          <div class="section-title">基本信息</div>
          <div class="detail-items">
            <div class="detail-item">
              <span class="item-label">记录日期：</span>
              <span class="item-value">{{
                activeDetail.recordDate || "-"
              }}</span>
            </div>
            <div class="detail-item">
              <span class="item-label">会话ID：</span>
              <span class="item-value">{{
                activeDetail.sessionId || "-"
              }}</span>
            </div>
            <div class="detail-item">
              <span class="item-label">用户ID：</span>
              <span class="item-value">{{ activeDetail.userId || "-" }}</span>
            </div>
          </div>
        </div>

        <div class="detail-section">
          <div class="section-title">情绪指标</div>
          <div class="detail-items">
            <div class="detail-item">
              <span class="item-label">情绪评分：</span>
              <div class="item-value rating-display">
                <el-rate
                  v-model="activeDetail.moodScore"
                  :max="5"
                  disabled
                  allow-half
                />
                <span class="score-text">{{ activeDetail.moodScore }}/5</span>
              </div>
            </div>
            <div class="detail-item">
              <span class="item-label">主要情绪：</span>
              <span class="item-value">{{
                activeDetail.dominantEmotion || "-"
              }}</span>
            </div>
            <div class="detail-item">
              <span class="item-label">其他情绪：</span>
              <div class="item-value emotion-tags">
                <el-tag
                  v-for="emotion in activeDetail.otherEmotions"
                  :key="emotion"
                  size="small"
                  class="emotion-tag"
                  type="info"
                  effect="plain"
                >
                  {{ emotion }}
                </el-tag>
                <span v-if="!activeDetail.otherEmotions.length" class="muted"
                  >无</span
                >
              </div>
            </div>
          </div>
        </div>

        <div class="detail-section">
          <div class="section-title">生活指标</div>
          <div class="detail-items">
            <div class="detail-item">
              <span class="item-label">工作：</span>
              <span class="item-value"
                >{{ activeDetail.workScore || "-" }}/5</span
              >
            </div>
            <div class="detail-item">
              <span class="item-label">健康：</span>
              <span class="item-value"
                >{{ activeDetail.healthScore || "-" }}/5</span
              >
            </div>
            <div class="detail-item">
              <span class="item-label">睡眠：</span>
              <span class="item-value"
                >{{ activeDetail.sleepScore || "-" }}/5</span
              >
            </div>
            <div class="detail-item">
              <span class="item-label">人际关系：</span>
              <span class="item-value"
                >{{ activeDetail.relationshipScore || "-" }}/5</span
              >
            </div>
          </div>
        </div>

        <div class="detail-section">
          <div class="section-title">自记内容</div>
          <div class="emotion-description">
            {{ activeDetail.emotionDescription || "暂无内容" }}
          </div>
        </div>
      </div>

      <template #footer>
        <el-button @click="detailDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from "vue";
import { useRoute } from "vue-router";
import { ElMessage, ElMessageBox } from "element-plus";
import PageHead from "@/components/PageHead.vue";
import TableSearch from "@/components/TableSearch.vue";
import { getEmotionDiary, deleteEmotionDiary } from "@/api/admin";

const route = useRoute();
const props = defineProps({
  title: { type: String, default: "" },
});

const pageTitle = computed(
  () => props.title || route.meta?.title || "情绪日志",
);

const tableHeaderStyle = {
  background: "#f7f9fc",
  color: "#5a6270",
  fontWeight: 600,
};

const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
});

const tableLoading = ref(false);
const tableData = ref([]);
const currentSearchParams = ref({});
const detailDialogVisible = ref(false);
const activeDetail = ref(null);

const searchFormItems = ref([
  {
    label: "用户ID",
    prop: "userId",
    value: "",
    placeholder: "请输入用户ID",
    component: "el-input",
  },
  {
    label: "最低评分",
    prop: "minMoodScore",
    value: "",
    placeholder: "0-5分",
    component: "el-input",
  },
  {
    label: "最高评分",
    prop: "maxMoodScore",
    value: "",
    placeholder: "0-5分",
    component: "el-input",
  },
  {
    label: "主要情绪",
    prop: "dominantEmotion",
    value: "",
    placeholder: "请输入主要情绪",
    component: "el-input",
  },
]);

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

const parseEmotions = (value) => {
  if (Array.isArray(value)) {
    return value.map((item) => String(item).trim()).filter(Boolean);
  }
  return String(value || "")
    .split(/[，,、]/)
    .map((item) => item.trim())
    .filter(Boolean);
};

const extractArrayData = (payload) => {
  const direct =
    payload?.data?.records ||
    payload?.data?.list ||
    payload?.records ||
    payload?.list ||
    payload?.rows ||
    payload?.data ||
    payload ||
    [];

  if (Array.isArray(direct)) {
    return direct;
  }

  if (direct && typeof direct === "object") {
    const key = Object.keys(direct).find((k) => Array.isArray(direct[k]));
    if (key) {
      return direct[key];
    }
  }

  return [];
};

const mapDiaryRow = (item) => {
  const workScore = Number(pickFirst(item, ["workScore", "work"]) || 0);
  const healthScore = Number(pickFirst(item, ["healthScore", "health"]) || 0);
  const sleepScore = Number(pickFirst(item, ["sleepScore", "sleep"]) || 0);
  const relationshipScore = Number(
    pickFirst(item, ["relationshipScore", "relationship"]) || 0,
  );

  const lifeIndicator =
    (workScore ? `工作:${workScore}` : "") +
    (healthScore ? (workScore ? "/" : "") + `健康:${healthScore}` : "") +
    (sleepScore
      ? (workScore || healthScore ? "/" : "") + `睡眠:${sleepScore}`
      : "") +
    (relationshipScore
      ? (workScore || healthScore || sleepScore ? "/" : "") +
        `人际:${relationshipScore}`
      : "");

  return {
    ...item,
    displayId:
      pickFirst(item, ["id", "diaryId", "emotionDiaryId", "recordId"]) || "-",
    displaySessionId:
      pickFirst(item, ["sessionId", "conversationId", "chatSessionId"]) || "-",
    displayRecordDate:
      pickFirst(item, ["recordDate", "createDate", "createdAt"]) || "-",
    moodScore: Number(pickFirst(item, ["moodScore", "score"]) || 0),
    displayDominantEmotion:
      pickFirst(item, ["dominantEmotion", "emotion"]) || "-",
    displayEmotionDescription:
      pickFirst(item, ["emotionDescription", "description", "content"]) || "-",
    displayLifeIndicator: lifeIndicator || "-",
    workScore,
    healthScore,
    sleepScore,
    relationshipScore,
  };
};

const fetchList = async () => {
  tableLoading.value = true;
  try {
    const form = currentSearchParams.value || {};
    const params = {
      current: pagination.current,
      size: pagination.pageSize,
    };

    if (String(form.userId || "").trim()) {
      params.userId = String(form.userId).trim();
    }
    if (String(form.dominantEmotion || "").trim()) {
      params.dominantEmotion = String(form.dominantEmotion).trim();
    }
    if (form.minMoodScore !== "" && form.minMoodScore !== undefined) {
      const minScore = Number(form.minMoodScore);
      if (!Number.isNaN(minScore)) {
        params.minMoodScore = minScore;
      }
    }
    if (form.maxMoodScore !== "" && form.maxMoodScore !== undefined) {
      const maxScore = Number(form.maxMoodScore);
      if (!Number.isNaN(maxScore)) {
        params.maxMoodScore = maxScore;
      }
    }

    const res = await getEmotionDiary(params);
    const payload = res?.data || {};
    const dataBlock = payload?.data || {};

    const rows = extractArrayData(payload);

    tableData.value = (Array.isArray(rows) ? rows : []).map(mapDiaryRow);
    pagination.total =
      dataBlock.total || payload.total || tableData.value.length || 0;
  } catch (error) {
    tableData.value = [];
    pagination.total = 0;
    ElMessage.error(error?.message || "获取情绪日记失败");
  } finally {
    tableLoading.value = false;
  }
};

const handleViewDetail = (row) => {
  activeDetail.value = {
    id: row.displayId,
    recordDate: row.displayRecordDate,
    sessionId: row.displaySessionId,
    userId: row.userId || "-",
    moodScore: row.moodScore,
    dominantEmotion: row.displayDominantEmotion,
    otherEmotions: parseEmotions(
      pickFirst(row, ["otherEmotions", "emotions", "tags"]),
    ),
    workScore: row.workScore,
    healthScore: row.healthScore,
    sleepScore: row.sleepScore,
    relationshipScore: row.relationshipScore,
    emotionDescription: row.displayEmotionDescription,
  };
  detailDialogVisible.value = true;
};

const handleDelete = async (row) => {
  const diaryId = pickFirst(row, [
    "displayId",
    "id",
    "diaryId",
    "emotionDiaryId",
    "recordId",
  ]);
  if (!diaryId || diaryId === "-") {
    ElMessage.error("无法删除此日记");
    return;
  }

  try {
    await ElMessageBox.confirm(
      `确认删除该情绪日记吗？删除后无法恢复。`,
      "删除确认",
      {
        confirmButtonText: "删除",
        cancelButtonText: "取消",
        type: "warning",
      },
    );

    await deleteEmotionDiary(diaryId);
    ElMessage.success("删除成功");
    await fetchList();
  } catch (error) {
    if (error === "cancel" || error === "close") {
      return;
    }
    ElMessage.error(error?.message || "删除失败");
  }
};

const handleSearch = async (formData) => {
  currentSearchParams.value = formData;
  pagination.current = 1;
  await fetchList();
};

const handleReset = () => {
  currentSearchParams.value = {};
  pagination.current = 1;
  fetchList();
};

const handlePageChange = (page) => {
  pagination.current = page;
  fetchList();
};

const handleSizeChange = (size) => {
  pagination.pageSize = size;
  pagination.current = 1;
  fetchList();
};

onMounted(() => {
  fetchList();
});
</script>

<style scoped>
.emotion-diary-page {
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

:deep(.table-search-form) {
  margin-bottom: 0;
  background: transparent;
  padding: 0;
}

.results {
  margin-top: 20px;
}

.results-card {
  padding: 6px;
  border: 1px solid #eceff3;
  border-radius: 8px;
}

.emotion-table {
  border-radius: 6px;
  overflow: hidden;
}

:deep(.emotion-table .el-table__row td) {
  padding: 12px 0;
}

:deep(.emotion-table .el-table__row:hover > td) {
  background-color: #f8fbff;
}

.rating-stars {
  display: flex;
  justify-content: center;
  align-items: center;
}

.life-indicator {
  color: #2f3a4d;
  font-size: 13px;
  font-weight: 500;
}

.action-group {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
}

:deep(.emotion-table .el-divider--vertical) {
  background-color: #d9dee7;
  margin: 0 4px;
}

.pagination-wrap {
  margin-top: 16px;
  display: flex;
  justify-content: flex-end;
}

.detail-content {
  padding: 16px 0;
}

.detail-section {
  margin-bottom: 20px;
}

.section-title {
  font-weight: 600;
  color: #1f2a44;
  margin-bottom: 12px;
  padding-bottom: 8px;
  border-bottom: 2px solid #d9e4f6;
  font-size: 14px;
}

.detail-items {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 12px;
}

.detail-item {
  display: flex;
  align-items: flex-start;
  gap: 12px;
  line-height: 1.6;
}

.item-label {
  color: #64748b;
  font-weight: 500;
  min-width: 80px;
  font-size: 13px;
}

.item-value {
  color: #2f3a4d;
  word-break: break-word;
  flex: 1;
}

.rating-display {
  display: flex;
  align-items: center;
  gap: 8px;
}

.score-text {
  color: #2f3a4d;
  font-weight: 600;
  font-size: 13px;
}

.emotion-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
  align-items: center;
}

.emotion-tag {
  margin: 0;
}

.muted {
  color: #94a3b8;
  font-size: 13px;
}

.emotion-description {
  background: #f8fbff;
  border: 1px solid #e8edf4;
  border-radius: 6px;
  padding: 12px;
  line-height: 1.8;
  color: #2f3a4d;
  max-height: 320px;
  overflow-y: auto;
  white-space: pre-wrap;
  word-wrap: break-word;
}

@media (max-width: 768px) {
  .emotion-diary-page {
    border-radius: 4px;
  }

  .content {
    padding: 12px;
  }

  :deep(.emotion-detail-dialog) {
    width: calc(100vw - 24px) !important;
    margin: 0 auto;
  }

  .detail-items {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 512px) {
  .content {
    padding: 8px;
  }
}
</style>
