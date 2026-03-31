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
      <el-menu-item
        v-for="item in navItems"
        :key="item.index"
        :index="item.index"
      >
        {{ item.label }}
      </el-menu-item>
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
import { ElMessage } from "element-plus";
import { useRoute, useRouter } from "vue-router";

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
const route = useRoute();
const router = useRouter();

const navItems = [
  { index: "/backend/dashboard", label: "数据分析" },
  { index: "/backend/history", label: "情绪日记" },
  { index: "/backend/knowledge", label: "知识库" },
  { index: "/backend/query", label: "咨询记录" },
];

const controlText = computed(() => {
  if (props.sidebarHidden) {
    return "展开";
  }
  if (props.sidebarCollapsed) {
    return "收起";
  }
  return "折叠图标";
});

const activeIndex = computed(() => {
  const currentPath = route.path;
  const activeItem = navItems.find((item) =>
    currentPath.startsWith(item.index),
  );
  return activeItem?.index || "/backend/dashboard";
});
const username = ref("管理员");
const avatarUrl = "/p1.jpg";

const logoSrc = "/logo.png";

const handleSelect = (index) => {
  router.push(index);
};

const handleCommand = (command) => {
  if (command === "logout") {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    ElMessage.success("已退出登录");
    router.push("/auth/login");
    return;
  }

  if (command === "profile") {
    ElMessage.info("个人中心功能开发中");
    return;
  }

  ElMessage.info("设置功能开发中");
};
</script>

<style lang="scss" scoped>
.backend-main-bar {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  background: linear-gradient(120deg, #0f1b34 0%, #1a2f5d 55%, #1b3e73 100%);
  border-bottom: 1px solid rgba(255, 255, 255, 0.14);
  box-sizing: border-box;
  color: #eaf0ff;
}

.brand-container {
  display: flex;
  align-items: center;
  width: 360px;
  min-width: 360px;
}

.sidebar-control-btn {
  margin-left: 12px;
  background: rgba(255, 255, 255, 0.12) !important;
  border-color: rgba(255, 255, 255, 0.24) !important;
  color: #eaf0ff !important;
  font-weight: 500;
  backdrop-filter: blur(6px);

  &:hover {
    background: rgba(255, 255, 255, 0.2) !important;
    border-color: rgba(255, 255, 255, 0.45) !important;
    color: #ffffff !important;
  }

  &:active {
    background: rgba(255, 255, 255, 0.16) !important;
  }
}

.brand-logo {
  width: 36px;
  height: 36px;
  border-radius: 10px;
  margin-right: 10px;
  box-shadow: 0 6px 18px rgba(7, 15, 35, 0.35);
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
  background: linear-gradient(135deg, #46d6ff 0%, #5d7dff 100%);
}

.brand-name {
  margin: 0;
  font-size: 16px;
  line-height: 1.2;
  color: #f3f6ff;
  letter-spacing: 0.3px;
}

.brand-desc {
  margin: 2px 0 0;
  font-size: 12px;
  color: rgba(233, 240, 255, 0.72);
}

.top-nav {
  flex: 1;
  border-bottom: none;
  min-width: 360px;
  margin: 0 12px;
  background: transparent;
}

:deep(.top-nav.el-menu--horizontal > .el-menu-item) {
  height: 60px;
  line-height: 60px;
  color: rgba(233, 240, 255, 0.78);
  border-bottom: 2px solid transparent;
  transition: all 0.2s ease;
}

:deep(.top-nav.el-menu--horizontal > .el-menu-item:hover) {
  color: #ffffff;
  background-color: rgba(255, 255, 255, 0.1);
}

:deep(.top-nav.el-menu--horizontal > .el-menu-item.is-active) {
  color: #ffffff;
  border-bottom-color: #67d7ff;
  background-color: rgba(255, 255, 255, 0.08);
}

:deep(.top-nav.el-menu--horizontal) {
  border-bottom: none;
}

:deep(.top-nav.el-menu) {
  background: transparent;
}

.user-info {
  min-width: 140px;
  display: flex;
  justify-content: flex-end;
}

.el-dropdown-link {
  display: inline-flex;
  align-items: center;
  padding: 6px 10px;
  border-radius: 999px;
  border: 1px solid rgba(255, 255, 255, 0.2);
  background: rgba(255, 255, 255, 0.08);
  cursor: pointer;
  color: #f5f8ff;

  &:hover {
    background: rgba(255, 255, 255, 0.15);
  }
}

.avatar {
  margin-right: 8px;
}

@media (max-width: 1200px) {
  .brand-desc {
    display: none;
  }

  .brand-container {
    min-width: 300px;
    width: 300px;
  }
}
</style>
