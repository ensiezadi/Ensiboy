<template>
  <el-form class="table-search-form" :model="formModel" label-width="100px" ref="formRef">
    <el-row :gutter="16">
      <template v-for="item in formItem" :key="item.label">
        <el-col
          :xs="getColSpan.xs"
          :sm="getColSpan.sm"
          :md="getColSpan.md"
          :lg="getColSpan.lg"
          :xl="getColSpan.xl"
          class="form-col"
        >
          <el-form-item :label="item.label" :prop="item.prop" class="form-item">
            <component
              :is="isComp(item.component) ? item.component : 'el-input'"
              v-model="item.value"
              :placeholder="item.placeholder"
            >
              <template v-if="item.props">
                <component
                  v-for="(prop, key) in item.props"
                  :key="key"
                  :is="prop.component || 'el-input'"
                  v-bind="prop.attrs || {}"
                />
              </template>
            </component>
          </el-form-item>
        </el-col>
      </template>
      <!-- 按钮列 -->
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="button-col">
        <div class="button-group">
          <el-button @click="handleSearch" type="primary">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </div>
      </el-col>
    </el-row>
  </el-form>
</template>

<script setup>
import { reactive, computed, ref } from "vue";

const props = defineProps({
  formItem: {
    type: Array,
    default: () => [],
  },
});

const emit = defineEmits(["search", "reset"]);
const formRef = ref(null);
const formModel = reactive({});

// 保存初始值用于重置
const initialValues = {};

// 初始化表单项时保存初始值
const initializeForm = () => {
  props.formItem.forEach((item) => {
    initialValues[item.prop] = item.value || "";
  });
};

// 根据表单项数量动态计算列宽
const getColSpan = computed(() => {
  const count = props.formItem.length;

  // 定义不同屏幕下的最大列数
  const colsPerRow = {
    xs: 1, // 超小屏幕：1列
    sm: 2, // 小屏幕：2列
    md: 2, // 中等屏幕：2列
    lg: Math.min(3, count), // 大屏幕：3列或项目数（取小值）
    xl: Math.min(3, count), // 超大屏幕：3列或项目数（取小值）
  };

  // 计算每列占的宽度（24 是总宽度）
  return {
    xs: 24,
    sm: Math.ceil(24 / colsPerRow.sm),
    md: Math.ceil(24 / colsPerRow.md),
    lg: Math.ceil(24 / colsPerRow.lg),
    xl: Math.ceil(24 / colsPerRow.xl),
  };
});

const isComp = (comp) => {
  // 字符串组件名（如 "el-input"）返回 false，使用 :is 直接解析
  if (typeof comp === "string") return true;
  // 对象组件才需要检查是否有 render 函数
  return typeof comp === "object" && comp !== null && "render" in comp;
};

// 搜索处理
const handleSearch = () => {
  const formData = {};
  props.formItem.forEach((item) => {
    formData[item.prop] = item.value;
  });
  emit("search", formData);
};

// 重置表单
const handleReset = () => {
  props.formItem.forEach((item) => {
    item.value = initialValues[item.prop] || "";
  });
  if (formRef.value) {
    formRef.value.clearValidate();
  }
  emit("reset");
};

// 初始化
initializeForm();
</script>

<style scoped>
.table-search-form {
  background: #f9f9f9;
  padding: 20px;
  border-radius: 4px;
  margin-bottom: 20px;
}

.form-col {
  margin-bottom: 0;
}

.form-item {
  margin-bottom: 0;
}

.button-col {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.button-group {
  display: flex;
  gap: 8px;
}

/* 响应式调整标签宽度 */
@media (max-width: 768px) {
  :deep(.el-form-item__label) {
    width: 80px !important;
  }

  :deep(.el-form--label-left .el-form-item__content) {
    margin-left: 80px;
  }
}

@media (max-width: 512px) {
  :deep(.el-form-item) {
    display: block;
  }

  :deep(.el-form-item__label) {
    width: 100% !important;
    padding: 0 0 8px 0;
    margin-bottom: 8px;
    text-align: left;
  }

  :deep(.el-form--label-left .el-form-item__content) {
    margin-left: 0 !important;
  }
}</style>
