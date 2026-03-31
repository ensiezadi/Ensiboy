<template>
  <el-dialog
    v-model="visible"
    :title="dialogTitle"
    width="50%"
    @close="handleClose"
  >
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="90px"
      class="article-form"
    >
      <el-form-item label="文章标题" prop="title">
        <el-input v-model="formData.title" placeholder="请输入文章标题" />
      </el-form-item>

      <el-form-item label="文章内容" prop="content">
        <RichTextEditor
          v-model="formData.content"
          placeholder="请输入文章内容"
          :max-char-count="5000"
          min-height="240px"
        />
      </el-form-item>

      <el-form-item label="封面图">
        <div class="cover-upload-wrap">
          <div class="cover-action-row">
            <el-upload
              class="cover-uploader"
              :http-request="uploadCoverRequest"
              name="file"
              :show-file-list="false"
              :before-upload="beforeCoverUpload"
              :on-success="handleCoverUploadSuccess"
              :on-error="handleCoverUploadError"
            >
              <el-button type="primary" plain>上传封面</el-button>
            </el-upload>
            <el-button
              v-if="formData.coverImage"
              type="danger"
              plain
              @click="handleRemoveCover"
            >
              移除图片
            </el-button>
          </div>
          <div
            v-if="coverPreviewUrl && coverPreviewVisible"
            class="cover-preview-wrap"
          >
            <el-image
              :key="coverPreviewUrl"
              :src="coverPreviewUrl"
              fit="contain"
              class="cover-preview"
              :preview-src-list="[coverPreviewUrl]"
              preview-teleported
              @error="handleCoverPreviewError"
            >
              <template #placeholder>
                <div class="cover-loading">封面加载中...</div>
              </template>
            </el-image>
          </div>
        </div>
      </el-form-item>

      <el-form-item label="分类" prop="categoryId">
        <el-select
          v-model="formData.categoryId"
          placeholder="请选择分类"
          style="width: 100%"
          clearable
          filterable
        >
          <el-option
            v-for="item in categoryOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="摘要" prop="summary">
        <el-input
          v-model="formData.summary"
          type="textarea"
          :rows="3"
          placeholder="请输入摘要"
        />
      </el-form-item>

      <el-form-item label="标签" prop="tags">
        <el-select
          v-model="formData.tags"
          multiple
          filterable
          allow-create
          default-first-option
          clearable
          style="width: 100%"
          placeholder="请选择或输入标签后回车创建"
        >
          <el-option
            v-for="tag in tagOptions"
            :key="tag"
            :label="tag"
            :value="tag"
          />
        </el-select>
      </el-form-item>
    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="previewVisible = true">内容预览</el-button>
        <el-button type="primary" :loading="submitLoading" @click="handleSubmit"
          >保存</el-button
        >
        <el-button @click="handleClose">关闭</el-button>
      </div>
    </template>
  </el-dialog>

  <el-dialog
    v-model="previewVisible"
    title="内容预览"
    width="70%"
    append-to-body
    destroy-on-close
  >
    <div class="content-preview" v-html="previewHtml"></div>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="previewVisible = false"
          >我知道了</el-button
        >
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, reactive, ref, watch } from "vue";
import { ElMessage } from "element-plus";
import { uploadFile } from "@/api/admin";
import { fileBaseUrl } from "@/config";
import RichTextEditor from "@/components/RichTextEditor.vue";
import { addArticle } from "@/api/admin";
import { updateArticle } from "@/api/admin";

const formData = reactive({
  title: "",
  content: "",
  coverImage: "",
  categoryId: 0,
  summary: "",
  tags: [],
  id: "",
});

const tagData = [
  "情绪管理",
  "人际关系",
  "职场发展",
  "心理健康",
  "自我提升",
  "家庭关系",
  "学习方法",
  "时间管理",
  "压力管理",
  "沟通技巧",
  "情感关系",
  "个人成长",
  "心理学知识",
  "生活习惯",
  "社交技巧",
  "职业规划",
];
const tagOptions = ref([...tagData]);

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
  title: {
    type: String,
    default: "文章详情",
  },
  article: {
    type: Object,
    default: null,
  },
  categoryOptions: {
    type: Array,
    default: () => [],
  },
});

const emit = defineEmits(["update:modelValue", "success"]);
const coverPreviewVisible = ref(true);
const previewVisible = ref(false);
const submitLoading = ref(false);
const formRef = ref(null);

const stripHtml = (html = "") =>
  String(html)
    .replace(/<[^>]+>/g, "")
    .replace(/&nbsp;/gi, " ")
    .replace(/\s+/g, " ")
    .trim();

const validateContent = (_rule, value, callback) => {
  const plainText = stripHtml(value);
  if (!plainText) {
    callback(new Error("请输入文章内容"));
    return;
  }
  callback();
};

const formRules = reactive({
  title: [
    { required: true, message: "请输入文章标题", trigger: "blur" },
    { min: 2, max: 100, message: "标题长度需在 2-100 个字符", trigger: "blur" },
  ],
  content: [{ validator: validateContent, trigger: "change" }],
  categoryId: [{ required: true, message: "请选择分类", trigger: "change" }],
  summary: [{ required: true, message: "请输入摘要", trigger: "blur" }],
  tags: [
    {
      type: "array",
      required: true,
      message: "请至少选择一个标签",
      trigger: "change",
    },
  ],
});

const visible = computed({
  get: () => props.modelValue,
  set: (val) => emit("update:modelValue", val),
});

const dialogTitle = computed(() => props.title || "文章详情");

const normalizeFileUrl = (value) => {
  if (typeof value !== "string") {
    return "";
  }
  const url = value.trim();
  if (!url) {
    return "";
  }
  if (
    url.startsWith("http://") ||
    url.startsWith("https://") ||
    url.startsWith("data:")
  ) {
    return url;
  }
  return `${fileBaseUrl}${url.startsWith("/") ? "" : "/"}${url}`;
};

const coverPreviewUrl = computed(() => normalizeFileUrl(formData.coverImage));

const previewHtml = computed(() => {
  const rawHtml = formData.content || "";
  // 预览层做最基础清理，避免脚本执行
  const sanitized = rawHtml.replace(/<script[\s\S]*?>[\s\S]*?<\/script>/gi, "");
  return sanitized.trim()
    ? sanitized
    : '<p style="color:#9ca3af;">暂无内容</p>';
});

watch(
  coverPreviewUrl,
  () => {
    coverPreviewVisible.value = true;
  },
  { immediate: true },
);

const normalizeTags = (rawTags) => {
  if (Array.isArray(rawTags)) {
    return rawTags.map((item) => String(item).trim()).filter(Boolean);
  }
  if (typeof rawTags === "string") {
    return rawTags
      .split(/[,，]/)
      .map((item) => item.trim())
      .filter(Boolean);
  }
  return [];
};

watch(
  () => props.article,
  (article) => {
    formData.title = article?.title || "";
    formData.content = article?.content || "";
    formData.coverImage = normalizeFileUrl(
      article?.coverImage || article?.filePath || article?.cover || "",
    );
    formData.categoryId = article?.categoryId ?? "";
    formData.summary = article?.summary || "";
    formData.tags = normalizeTags(article?.tags);
    formData.id = article?.id ? String(article.id) : "";

    formData.tags.forEach((tag) => {
      if (!tagOptions.value.includes(tag)) {
        tagOptions.value.push(tag);
      }
    });
  },
  { immediate: true },
);

watch(
  () => formData.tags,
  (tags) => {
    tags.forEach((tag) => {
      if (!tagOptions.value.includes(tag)) {
        tagOptions.value.push(tag);
      }
    });
  },
  { deep: true },
);

const handleClose = () => {
  visible.value = false;
  previewVisible.value = false;
  formRef.value?.clearValidate();
};

const buildSubmitPayload = () => {
  // 后端通常保存相对路径，这里优先去掉 fileBaseUrl 前缀
  const normalizedCoverImage = String(formData.coverImage || "").replace(
    new RegExp(`^${fileBaseUrl.replace(/[.*+?^${}()|[\]\\]/g, "\\$&")}`),
    "",
  );

  return {
    title: (formData.title || "").trim(),
    content: formData.content || "",
    coverImage: normalizedCoverImage || formData.coverImage || "",
    categoryId: Number(formData.categoryId || 0),
    summary: (formData.summary || "").trim(),
    tags: Array.isArray(formData.tags)
      ? formData.tags.join(",")
      : String(formData.tags || ""),
    id: formData.id || "",
  };
};

const handleSubmit = async () => {
  if (!formRef.value || submitLoading.value) {
    return;
  }

  try {
    await formRef.value.validate();
  } catch (_error) {
    ElMessage.warning("请先完成表单校验");
    return;
  }

  submitLoading.value = true;
  try {
    const payload = buildSubmitPayload();
    const isEdit = Boolean(String(payload.id || "").trim());
    if (isEdit) {
      await updateArticle(payload);
    } else {
      await addArticle(payload);
    }
    ElMessage.success(isEdit ? "更新成功" : "新增成功");
    emit("success");
    handleClose();
  } catch (error) {
    ElMessage.error(error?.message || "保存失败");
  } finally {
    submitLoading.value = false;
  }
};

const extractUploadUrl = (response) => {
  const filePath =
    response?.data?.filePath ||
    response?.data?.data?.filePath ||
    response?.filePath ||
    "";
  return normalizeFileUrl(filePath);
};

const uploadCoverRequest = async (options) => {
  try {
    const res = await uploadFile(options.file, {
      businessType: "ARTICLE",
      businessId: formData.id || "0",
      businessField: "cover",
    });
    options.onSuccess?.(res?.data ?? res);
  } catch (error) {
    options.onError?.(error);
  }
};

const beforeCoverUpload = (file) => {
  const isImage = file.type?.startsWith("image/");
  const isLt5M = file.size / 1024 / 1024 < 5;

  if (!isImage) {
    ElMessage.error("请上传图片文件");
    return false;
  }
  if (!isLt5M) {
    ElMessage.error("图片大小不能超过 5MB");
    return false;
  }
  return true;
};

const handleCoverUploadSuccess = (response) => {
  const url = extractUploadUrl(response);
  if (!url) {
    console.warn("上传成功但未返回 filePath:", response);
    return;
  }
  formData.coverImage = url;
};

const handleCoverUploadError = (error) => {
  const msg =
    error?.response?.data?.message ||
    error?.response?.data?.msg ||
    error?.message ||
    "封面上传失败";
  ElMessage.error(msg);
  console.error("上传失败详情:", {
    status: error?.response?.status,
    url: error?.config?.url,
    data: error?.response?.data,
  });
};

const handleRemoveCover = () => {
  formData.coverImage = "";
  ElMessage.success("已移除封面图");
};

const handleCoverPreviewError = () => {
  coverPreviewVisible.value = false;
};
</script>

<style scoped>
.article-form {
  max-height: 60vh;
  overflow: auto;
  padding-right: 8px;
}

.cover-upload-wrap {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.cover-action-row {
  display: flex;
  align-items: center;
  gap: 8px;
}

.cover-uploader {
  align-self: flex-start;
}

.cover-preview-wrap {
  position: relative;
  width: 200px;
  height: 120px;
}

.cover-loading {
  position: absolute;
  inset: 0;
  z-index: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 6px;
  border: 1px solid #e5e7eb;
  background: #f8fafc;
  color: #6b7280;
  font-size: 12px;
}

.cover-preview {
  width: 200px;
  height: 120px;
  object-fit: contain;
  border-radius: 6px;
  border: 1px solid #e5e7eb;
  background: #f8fafc;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
}

.content-preview {
  min-height: 220px;
  max-height: 60vh;
  overflow: auto;
  padding: 12px;
  border: 1px solid #e5e7eb;
  border-radius: 6px;
  background: #fff;
  color: #1f2937;
  line-height: 1.75;
  word-break: break-word;
}

.content-preview :deep(h1),
.content-preview :deep(h2),
.content-preview :deep(h3) {
  margin: 14px 0 10px;
  font-weight: 600;
}

.content-preview :deep(p) {
  margin: 0 0 10px;
}

.content-preview :deep(ul),
.content-preview :deep(ol) {
  margin: 8px 0 10px;
  padding-left: 24px;
}

.content-preview :deep(ul) {
  list-style: disc;
}

.content-preview :deep(ol) {
  list-style: decimal;
}

.content-preview :deep(li) {
  list-style: inherit;
  margin: 4px 0;
}

.content-preview :deep(blockquote) {
  margin: 10px 0;
  padding: 8px 12px;
  border-left: 3px solid #93c5fd;
  background: #f8fafc;
  color: #475569;
}

.content-preview :deep(pre) {
  overflow: auto;
  padding: 10px;
  border-radius: 6px;
  background: #0f172a;
  color: #e2e8f0;
}
</style>
