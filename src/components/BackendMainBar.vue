<template>
  <div class="backend-main-bar">
    <div class="brand-container">
      <el-image :src="logoSrc" alt="logo" class="brand-logo" fit="cover">
        <template #error>
          <div class="image-slot">AI</div>
        </template>
      </el-image>
      <div class="brand-info">
        <h3 class="brand-name">AI助手后台</h3>
        <p class="brand-desc">智能办公好帮手</p>
      </div>
      <el-button
        class="sidebar-control-btn"
        size="small"
        @click="emit('cycle-sidebar')"
      >
        <el-icon v-if="props.sidebarCollapsed || props.sidebarHidden"
          ><Expand
        /></el-icon>
        <el-icon v-else><Fold /></el-icon>
        <span style="margin-left: 4px">{{ controlText }}</span>
      </el-button>
    </div>

    <el-menu
      class="top-nav"
      mode="horizontal"
      :default-active="activeIndex"
      @select="handleSelect"
    >
      <el-menu-item index="dashboard">处理器</el-menu-item>
      <el-menu-item index="model">模型</el-menu-item>
      <el-menu-item index="dataset">数据集</el-menu-item>
      <el-menu-item index="train">训练</el-menu-item>
      <el-menu-item index="eval">评测</el-menu-item>
    </el-menu>

    <div class="user-info">
      <el-dropdown @command="handleCommand">
        <span class="el-dropdown-link">
          <el-avatar :src="avatarUrl" size="small" class="avatar" />
          {{ username }}
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="profile">个人中心</el-dropdown-item>
            <el-dropdown-item command="settings">设置</el-dropdown-item>
            <el-dropdown-item command="logout" divided
              >退出登录</el-dropdown-item
            >
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from "vue";

const props = defineProps({
  sidebarHidden: {
    type: Boolean,
    default: false,
  },
  sidebarCollapsed: {
    type: Boolean,
    default: false,
  },
});

const emit = defineEmits(["cycle-sidebar"]);

const controlText = computed(() => {
  if (props.sidebarHidden) {
    return "展开";
  }
  if (props.sidebarCollapsed) {
    return "收起";
  }
  return "折叠图标";
});

const activeIndex = ref("dashboard");
const username = ref("管理员");

const logoSrc =
  "https://cdn.jsdelivr.net/gh/ensiezadi/obsidian-images/images/%E4%BB%93%E5%BA%93.png";
const avatarUrl = "https://i.pravatar.cc/100?img=12";

const handleSelect = (index) => {
  activeIndex.value = index;
};

const handleCommand = (command) => {
  console.log("user command:", command);
};
</script>

<style lang="scss" scoped>
.backend-main-bar {
  height: 100%; /* 继承 Header 的高度 */
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  background-color: #fff;
  border-bottom: 1px solid #e4e7ed;
  box-sizing: border-box;
}

.brand-container {
  display: flex;
  align-items: center;
  width: 360px;
  min-width: 360px;
}

.sidebar-control {
  margin-left: 12px;
  padding: 6px 10px;
  border-radius: 6px;
  border: 1px dashed #cdd0d6;
  color: #606266;
  font-size: 12px;
  line-height: 1;
  white-space: nowrap;
  cursor: pointer;
  user-select: none;
  transition: all 0.2s ease;

  &:hover {
    border-color: #409eff;
    color: #409eff;
    background-color: #ecf5ff;
  }
}

/* 移除旧的 .sidebar-control 样式，替换为以下内容 */
.sidebar-control-btn {
  margin-left: 12px;

  /* 如果想要图片中那种极简的浅灰色效果，可以进行如下微调 */
  background-color: #f4f4f5 !important;
  border-color: #e9e9eb !important;
  color: #909399 !important;
  font-weight: normal;

  &:hover {
    background-color: #ecf5ff !important;
    border-color: #409eff !important;
    color: #409eff !important;
  }

  &:active {
    background-color: #d9ecff !important;
  }
}

.brand-logo {
  width: 36px;
  height: 36px;
  border-radius: 8px;
  margin-right: 10px;
}

.image-slot {
  width: 36px;
  height: 36px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  color: #fff;
  background-color: #409eff;
}

.brand-name {
  margin: 0;
  font-size: 16px;
  line-height: 1.2;
  color: #303133;
}

.brand-desc {
  margin: 2px 0 0;
  font-size: 12px;
  color: #909399;
}

.top-nav {
  flex: 1;
  border-bottom: none;
  min-width: 320px;
  margin: 0 12px;
}

.user-info {
  min-width: 120px;
  display: flex;
  justify-content: flex-end;
}

.el-dropdown-link {
  display: inline-flex;
  align-items: center;
  cursor: pointer;
  color: #303133;
}

.avatar {
  margin-right: 8px;
}
</style>
