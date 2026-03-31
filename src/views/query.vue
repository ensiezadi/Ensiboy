<template>
  <div class="query-page">
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
          class="query-table"
          :data="tableData"
          border
          v-loading="tableLoading"
          empty-text="暂无咨询记录"
          :header-cell-style="tableHeaderStyle"
        >
          <el-table-column label="会话ID" min-width="180">
            <template #default="scope">
              <div class="session-cell">
                <el-avatar :size="34" class="session-avatar">
                  {{ getUserInitial(scope.row.displayUserName) }}
                </el-avatar>
                <div class="session-meta">
                  <p class="session-user">{{ scope.row.displayUserName }}</p>
                  <p class="session-id">{{ scope.row.displaySessionId }}</p>
                </div>
              </div>
            </template>
          </el-table-column>

          <el-table-column
            label="情绪标签"
            min-width="300"
            show-overflow-tooltip
          >
            <template #default="scope">
              <div
                class="tag-list"
                v-if="scope.row.displayEmotionTagList.length"
              >
                <el-tag
                  v-for="tag in scope.row.displayEmotionTagList"
                  :key="tag"
                  class="tag-item"
                  size="small"
                  effect="plain"
                  type="info"
                >
                  {{ tag }}
                </el-tag>
              </div>
              <span class="muted-text" v-else>-</span>
            </template>
          </el-table-column>

          <el-table-column
            prop="displayMessageSummary"
            label="消息摘要"
            min-width="220"
            show-overflow-tooltip
          />

          <el-table-column
            prop="displayMessageCount"
            label="消息数"
            width="90"
            align="center"
          />
          <el-table-column
            prop="displayTime"
            label="时间"
            min-width="170"
            align="center"
          />
          <el-table-column label="操作" width="90" align="center" fixed="right">
            <template #default="scope">
              <el-button
                link
                type="primary"
                @click="handleOpenDetail(scope.row)"
              >
                详情
              </el-button>
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
      width="860px"
      top="8vh"
      destroy-on-close
      class="query-detail-dialog"
    >
      <template #header>
        <div class="dialog-header">
          <span>咨询会话详情</span>
          <el-button
            v-if="!detailEditMode"
            link
            type="primary"
            @click="detailEditMode = true"
          >
            编辑
          </el-button>
        </div>
      </template>

      <div class="detail-header" v-if="!detailEditMode">
        <div class="session-info-grid">
          <div class="info-card">
            <div class="info-title">会话信息</div>
            <div class="info-content">
              <div class="info-item">
                <span class="info-label">标题</span>
                <span class="info-value">{{
                  activeDetail.sessionTitle || "-"
                }}</span>
              </div>
              <div class="info-item">
                <span class="info-label">用户</span>
                <span class="info-value">{{
                  activeDetail.userName || "-"
                }}</span>
              </div>
              <div class="info-item">
                <span class="info-label">开始时间</span>
                <span class="info-value">{{
                  activeDetail.startTime || "-"
                }}</span>
              </div>
              <div class="info-item">
                <span class="info-label">会话时长</span>
                <span class="info-value"
                  >{{ activeDetail.durationMinutes || 0 }} 分钟</span
                >
              </div>
              <div class="info-item">
                <span class="info-label">消息数</span>
                <span class="info-value"
                  >{{ activeDetail.messageCount }} 条</span
                >
              </div>
            </div>
          </div>
          <div class="info-card" v-if="activeDetail.emotionTagList.length">
            <div class="info-title">情绪标签</div>
            <div class="info-content tag-content">
              <div class="tag-list">
                <el-tag
                  v-for="tag in activeDetail.emotionTagList"
                  :key="tag"
                  class="tag-item"
                  size="small"
                  effect="plain"
                  type="info"
                >
                  {{ tag }}
                </el-tag>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="detail-edit-form" v-else>
        <el-form label-width="100px" :model="editFormData">
          <el-form-item label="会话标题">
            <el-input
              v-model="editFormData.sessionTitle"
              placeholder="请输入会话标题"
            />
          </el-form-item>
          <el-form-item label="情绪标签">
            <el-input
              v-model="editFormData.emotionTags"
              type="textarea"
              :rows="3"
              placeholder="请输入情绪标签，以逗号或中文逗号分隔"
            />
          </el-form-item>
        </el-form>
      </div>

      <div class="divider" v-if="!detailEditMode" />

      <div class="chat-panel" v-if="!detailEditMode" v-loading="detailLoading">
        <p class="chat-title">对话记录</p>
        <div class="chat-stat" v-if="activeDetail.messages.length">
          共 {{ activeDetail.messages.length }} 条消息
        </div>
        <div v-if="activeDetail.messages.length" class="record-list">
          <div
            v-for="(msg, idx) in activeDetail.messages"
            :key="`${msg.time}-${idx}`"
            class="record-item"
          >
            <div class="record-meta">
              <span
                class="record-role"
                :class="
                  msg.isUser ? 'record-role-user' : 'record-role-assistant'
                "
              >
                {{ msg.role }}
              </span>
              <span class="record-time">{{ msg.time }}</span>
            </div>
            <p class="record-content">{{ msg.content }}</p>
            <div v-if="msg.emotionTags.length" class="record-tags">
              <el-tag
                v-for="tag in msg.emotionTags"
                :key="tag"
                class="emotion-tag"
                size="small"
                effect="plain"
                type="warning"
              >
                {{ tag }}
              </el-tag>
            </div>
          </div>
        </div>
        <el-empty v-else description="暂无对话内容" :image-size="72" />
      </div>

      <template #footer>
        <div class="dialog-footer">
          <div v-if="detailEditMode" class="edit-buttons">
            <el-button @click="detailEditMode = false">取消</el-button>
            <el-button
              type="primary"
              @click="handleSaveDetail"
              :loading="detailSaving"
            >
              保存
            </el-button>
          </div>
          <div v-else>
            <el-button @click="detailDialogVisible = false">关闭</el-button>
          </div>
        </div>
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
import {
  getQueryList,
  getQueryDetail,
  updateQuerySession,
  getSessionMessages,
} from "@/api/admin";

const route = useRoute();
const props = defineProps({
  title: { type: String, default: "" },
});

const pageTitle = computed(
  () => props.title || route.meta?.title || "咨询记录",
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
const detailLoading = ref(false);
const tableData = ref([]);
const currentSearchParams = ref({});
const detailDialogVisible = ref(false);
const detailEditMode = ref(false);
const detailSaving = ref(false);
const activeDetail = ref({
  id: "",
  userName: "",
  startTime: "",
  messageCount: 0,
  durationMinutes: 0,
  emotionTagList: [],
  messages: [],
  sessionTitle: "",
});
const editFormData = ref({
  sessionTitle: "",
  emotionTags: "",
});

const searchFormItems = ref([
  {
    label: "用户",
    prop: "userName",
    value: "",
    placeholder: "请输入用户名",
    component: "el-input",
  },
  {
    label: "标签",
    prop: "emotionTag",
    value: "",
    placeholder: "请输入情绪标签",
    component: "el-input",
  },
  {
    label: "会话ID",
    prop: "sessionId",
    value: "",
    placeholder: "请输入会话ID",
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

const parseTagList = (value) => {
  if (Array.isArray(value)) {
    return value.map((item) => String(item).trim()).filter(Boolean);
  }

  return String(value || "")
    .split(/[，,、|/]/)
    .map((item) => item.trim())
    .filter(Boolean);
};

const extractArrayData = (payload) => {
  const direct =
    payload?.data?.records ||
    payload?.data?.messages ||
    payload?.data?.messageList ||
    payload?.data?.list ||
    payload?.records ||
    payload?.messages ||
    payload?.messageList ||
    payload?.list ||
    payload?.data ||
    payload ||
    [];

  if (Array.isArray(direct)) {
    return direct;
  }

  if (direct && typeof direct === "object") {
    const key = Object.keys(direct).find(
      (k) => Array.isArray(direct[k]) && direct[k].length >= 0,
    );
    if (key) {
      return direct[key];
    }
  }

  return [];
};

const normalizeMessage = (item) => {
  const roleRaw = String(
    pickFirst(item, [
      "role",
      "roleType",
      "senderRole",
      "messageRole",
      "type",
      "from",
      "source",
      "sender",
      "senderType",
      "userRole",
      "speaker",
      "origin",
    ]),
  ).toLowerCase();

  const isUser = [
    "user",
    "human",
    "client",
    "visitor",
    "question",
    "ask",
    "customer",
    "patient",
    "me",
    "问",
    "0",
    "1",
  ].includes(roleRaw);

  const isAssistant = [
    "assistant",
    "ai",
    "bot",
    "system",
    "reply",
    "answer",
    "2",
  ].includes(roleRaw);

  const role = isUser ? "用户" : isAssistant ? "AI助手" : "消息";
  const content =
    pickFirst(item, [
      "content",
      "messageContent",
      "questionContent",
      "answerContent",
      "text",
      "message",
      "msg",
      "answer",
      "question",
      "reply",
      "body",
      "payload.content",
    ]) || "-";
  const time =
    pickFirst(item, [
      "time",
      "messageTime",
      "createdTime",
      "createTime",
      "createdAt",
      "timestamp",
      "sendTime",
      "replyTime",
      "updateTime",
    ]) || "-";
  const emotionTagsRaw = pickFirst(item, [
    "emotionTags",
    "emotionLabels",
    "emotions",
    "mood",
    "emotionLabel",
    "tags",
    "tagList",
  ]);
  const emotionTags = parseTagList(emotionTagsRaw);

  return {
    role,
    content,
    time,
    isUser: isUser || !isAssistant,
    emotionTags,
  };
};

const mapConsultationRow = (item) => {
  const emotionTagList = parseTagList(
    pickFirst(item, [
      "emotionTags",
      "tags",
      "tagList",
      "emotionLabel",
      "emotions",
      "mood",
    ]),
  );

  const messageCount = Number(
    pickFirst(item, [
      "messageCount",
      "msgCount",
      "totalMessages",
      "count",
      "messageCnt",
    ]) || 0,
  );

  // 消息摘要：优先取 lastMessageContent，其次取首条消息内容
  const messageSummary =
    pickFirst(item, [
      "lastMessageContent",
      "lastMessage",
      "recentMessage",
      "summary",
      "content",
    ]) || "-";
  const displayMessageSummary =
    String(messageSummary).substring(0, 50) +
    (String(messageSummary).length > 50 ? "..." : "");

  return {
    ...item,
    displaySessionId:
      pickFirst(item, [
        "id",
        "sessionId",
        "conversationId",
        "chatId",
        "session_id",
      ]) || "-",
    displayUserName:
      pickFirst(item, [
        "userNickname",
        "userName",
        "username",
        "nickName",
        "name",
        "user.id",
        "userId",
        "user_id",
      ]) || "匿名用户",
    displayEmotionTagList: emotionTagList,
    displayMessageCount: Number.isNaN(messageCount) ? 0 : messageCount,
    displayTime:
      pickFirst(item, [
        "startedAt",
        "startTime",
        "createTime",
        "createdAt",
        "time",
        "created_at",
      ]) || "-",
    displayMessageSummary,
  };
};

const fetchList = async () => {
  tableLoading.value = true;
  try {
    const form = currentSearchParams.value || {};
    const params = {
      userName: form.userName || "",
      emotionTag: form.emotionTag || "",
      sessionId: form.sessionId || "",
      currentPage: pagination.current,
      size: pagination.pageSize,
    };

    const res = await getQueryList(params);
    const payload = res?.data || {};

    // 多层级提取行数据
    const dataBlock = payload?.data || {};
    let rows =
      dataBlock.records ||
      dataBlock.list ||
      payload.list ||
      payload.rows ||
      payload.data?.records ||
      payload.data?.list ||
      payload.records ||
      [];

    // 如果 rows 是对象而非数组，尝试提取数组
    if (!Array.isArray(rows) && rows) {
      const possibleArrayField = Object.keys(rows).find(
        (key) => Array.isArray(rows[key]) && rows[key].length > 0,
      );
      if (possibleArrayField) {
        rows = rows[possibleArrayField];
      }
    }

    tableData.value = (Array.isArray(rows) ? rows : []).map(mapConsultationRow);
    pagination.total =
      dataBlock.total || payload.total || tableData.value.length || 0;
  } catch (error) {
    tableData.value = [];
    pagination.total = 0;
    ElMessage.error(error?.message || "获取咨询记录失败");
  } finally {
    tableLoading.value = false;
  }
};

const mapDetail = (detail, row) => {
  const source = { ...row, ...(detail || {}) };

  // 多层级提取消息数据
  const rawMessages = extractArrayData({
    records: source.records,
    messages: source.messages,
    messageList: source.messageList,
    dialogues: source.dialogues,
    history: source.history,
    chats: source.chats,
    sessions: source.sessions,
    conversation: source.conversation,
    conversations: source.conversations,
    data: source.data,
  });

  const messages = Array.isArray(rawMessages)
    ? rawMessages.map(normalizeMessage)
    : [];

  const emotionTagList = parseTagList(
    pickFirst(source, ["emotionTags", "tags", "tagList", "emotionLabel"]),
  );

  return {
    id: pickFirst(source, ["id", "sessionId"]) || "",
    sessionTitle: pickFirst(source, ["sessionTitle", "title", "name"]) || "-",
    userName:
      pickFirst(source, [
        "userNickname",
        "userName",
        "username",
        "nickName",
        "name",
        "user.id",
        "userId",
      ]) || "匿名用户",
    startTime:
      pickFirst(source, [
        "startedAt",
        "startTime",
        "createTime",
        "createdAt",
        "time",
      ]) || "-",
    messageCount:
      messages.length ||
      Number(source.messageCount || source.displayMessageCount || 0),
    durationMinutes: Number(source.durationMinutes || 0),
    emotionTagList,
    messages,
  };
};

const handleOpenDetail = async (row) => {
  detailDialogVisible.value = true;
  detailEditMode.value = false;
  detailLoading.value = true;
  try {
    const detailId = pickFirst(row, [
      "id",
      "sessionId",
      "conversationId",
      "chatId",
      "displaySessionId",
    ]);

    let detailData = {};
    if (detailId && detailId !== "-") {
      try {
        const res = await getQueryDetail(detailId);
        const payload = res?.data || {};
        // 处理多层嵌套：res.data.data 或 res.data 直接是数据
        detailData = payload.data || payload;
      } catch (fetchError) {
        console.warn("获取详情失败:", fetchError?.message);
        // 降级处理：使用列表行数据
      }

      // 获取会话消息
      try {
        const msgRes = await getSessionMessages(detailId);
        const msgPayload = msgRes?.data || {};

        // 提取消息数据 - 支持多种数据结构
        const messages = extractArrayData(msgPayload);

        if (Array.isArray(messages) && messages.length > 0) {
          detailData.messages = messages;
        } else if (detailData.lastMessageContent) {
          detailData.messages = [
            {
              role: "AI助手",
              content: detailData.lastMessageContent,
              time: detailData.lastMessageTime || detailData.startedAt || "-",
            },
          ];
        }
      } catch (msgError) {
        console.warn("获取消息失败:", msgError?.message);
      }
    }

    activeDetail.value = mapDetail(detailData, row);
    // 初始化编辑表单
    editFormData.value = {
      sessionTitle: activeDetail.value.sessionTitle,
      emotionTags: activeDetail.value.emotionTagList.join(", "),
    };
  } catch (error) {
    activeDetail.value = mapDetail({}, row);
    ElMessage.warning(error?.message || "会话详情获取失败，已显示列表信息");
  } finally {
    detailLoading.value = false;
  }
};

const handleSaveDetail = async () => {
  const sessionId = activeDetail.value.id;
  if (!sessionId) {
    ElMessage.error("会话ID不存在，无法保存");
    return;
  }

  detailSaving.value = true;
  try {
    const emotionTagsList = editFormData.value.emotionTags
      .split(/[，,、]/)
      .map((tag) => tag.trim())
      .filter(Boolean);

    const updateData = {
      sessionTitle: editFormData.value.sessionTitle,
      emotionTags: emotionTagsList,
    };

    await updateQuerySession(sessionId, updateData);
    ElMessage.success("会话信息已保存");

    // 更新本地显示
    activeDetail.value.sessionTitle = editFormData.value.sessionTitle;
    activeDetail.value.emotionTagList = emotionTagsList;

    // 退出编辑模式
    detailEditMode.value = false;

    // 刷新列表
    await fetchList();
  } catch (error) {
    ElMessage.error(error?.message || "保存失败");
  } finally {
    detailSaving.value = false;
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

const getUserInitial = (name) => {
  const safeName = String(name || "").trim();
  if (!safeName) return "U";
  return safeName.slice(0, 1).toUpperCase();
};

onMounted(() => {
  fetchList();
});
</script>

<style scoped>
.query-page {
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

.query-table {
  border-radius: 6px;
  overflow: hidden;
}

:deep(.query-table .el-table__row td) {
  padding: 12px 0;
}

:deep(.query-table .el-table__row:hover > td) {
  background-color: #f8fbff;
}

.session-cell {
  display: flex;
  align-items: center;
  gap: 10px;
}

.session-avatar {
  background: #d9dee7;
  color: #7a8391;
  font-weight: 600;
}

.session-meta {
  min-width: 0;
}

.session-user,
.session-id {
  margin: 0;
  line-height: 1.4;
}

.session-user {
  color: #2f3a4d;
  font-weight: 600;
}

.session-id {
  color: #7b8798;
  font-size: 12px;
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

.pagination-wrap {
  margin-top: 16px;
  display: flex;
  justify-content: flex-end;
}

.detail-header {
  padding: 14px;
  border-radius: 8px;
  border: 1px solid #e8edf4;
  background: #f8fbff;
  margin-bottom: 16px;
}

.detail-row {
  display: flex;
  align-items: center;
  gap: 8px;
  line-height: 1.8;
  color: #2f3a4d;
}

.detail-label {
  color: #64748b;
  min-width: 64px;
}

.chat-panel {
  border: 1px solid #e8edf4;
  border-radius: 8px;
  background: #fff;
  padding: 12px;
  min-height: 160px;
}

.chat-title {
  margin: 0 0 10px;
  font-weight: 600;
  color: #1f2a44;
}

.chat-stat {
  margin: 0 0 12px;
  color: #64748b;
  font-size: 12px;
}

.record-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
  max-height: 420px;
  overflow-y: auto;
}

.record-item {
  border: 1px solid #edf1f7;
  border-radius: 8px;
  padding: 10px 12px;
  background: #f9fbfe;
}

.record-meta {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 8px;
  margin-bottom: 8px;
}

.record-role {
  display: inline-flex;
  align-items: center;
  height: 22px;
  padding: 0 8px;
  border-radius: 999px;
  font-size: 12px;
  font-weight: 600;
}

.record-role-user {
  background: #e8f1ff;
  color: #2a62c9;
}

.record-role-assistant {
  background: #eaf8ee;
  color: #2f8f4a;
}

.record-time {
  color: #94a3b8;
  font-size: 12px;
}

.record-content {
  margin: 0 0 8px;
  color: #2f3a4d;
  line-height: 1.65;
  word-break: break-word;
  white-space: pre-wrap;
}

.record-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
  margin-top: 6px;
}

.emotion-tag {
  margin: 0;
}

.emotion-tag {
  margin: 0;
}

.dialog-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
}

.detail-edit-form {
  padding: 14px;
  border-radius: 8px;
  border: 1px solid #e8edf4;
  background: #f8fbff;
  margin-bottom: 16px;
}

:deep(.detail-edit-form .el-form-item) {
  margin-bottom: 16px;
}

:deep(.detail-edit-form .el-form-item__label) {
  color: #64748b;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
}

.edit-buttons {
  display: flex;
  gap: 8px;
}

.session-info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 16px;
  margin-bottom: 16px;
}

.info-card {
  border: 1px solid #e8edf4;
  border-radius: 8px;
  background: #f8fbff;
  padding: 16px;
}

.info-title {
  font-weight: 600;
  color: #1f2a44;
  margin-bottom: 12px;
  font-size: 14px;
  padding-bottom: 8px;
  border-bottom: 2px solid #d9e4f6;
}

.info-content {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.tag-content {
  gap: 6px;
}

.info-item {
  display: flex;
  align-items: center;
  gap: 12px;
  line-height: 1.6;
}

.info-label {
  color: #64748b;
  font-weight: 500;
  min-width: 68px;
  font-size: 13px;
}

.info-value {
  color: #2f3a4d;
  word-break: break-word;
  flex: 1;
}

.divider {
  height: 1px;
  background: linear-gradient(to right, transparent, #e8edf4, transparent);
  margin: 16px 0;
}

@media (max-width: 768px) {
  .query-page {
    border-radius: 4px;
  }

  .content {
    padding: 12px;
  }

  :deep(.query-detail-dialog) {
    width: calc(100vw - 24px) !important;
    margin: 0 auto;
  }
}

@media (max-width: 512px) {
  .content {
    padding: 8px;
  }

  .session-cell {
    gap: 8px;
  }
}
</style>
