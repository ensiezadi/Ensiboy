<template>
  <el-aside
    :width="asideWidth"
    class="my-sidebar"
    :class="{ 'is-hidden': hidden, 'is-collapsed': collapsed }"
  >
    <div class="sidebar-wrapper">
      <div class="brand-container" @dblclick.stop="toggleCollapse">
        <div class="brand-logo-wrapper">
          <el-image :src="logoSrc" alt="logo" class="brand-logo" fit="cover">
            <template #error>
              <div class="image-slot">AI</div>
            </template>
          </el-image>
          <div v-show="!collapsed" class="logo-online-indicator"></div>
        </div>
        <div v-show="!collapsed" class="brand-info">
          <h3 class="brand-name">心理健康助手</h3>
          <p class="brand-desc">管理平台</p>
        </div>
      </div>

      <el-menu
        active-text-color="#409eff"
        background-color="transparent"
        text-color="#d4d4d8"
        :default-active="router.currentRoute.value.path"
        :collapse="collapsed"
        :collapse-transition="false"
        class="sidebar-menu"
        @select="selectMenu"
      >
        <el-menu-item
          v-for="item in menuList"
          :key="item.path"
          :index="'/backend/' + item.path"
          class="menu-item"
        >
          <el-icon class="menu-icon">
            <component :is="item.meta.icon" />
          </el-icon>
          <span class="menu-text">{{ item.meta.title }}</span>
        </el-menu-item>
      </el-menu>
    </div>

    <div class="sidebar-footer">
      <div
        class="collapse-btn"
        @click="toggleCollapse"
        :title="collapsed ? '展开' : '收起'"
      >
        <el-icon>
          <component :is="collapsed ? 'Expand' : 'Fold'" />
        </el-icon>
      </div>
    </div>
  </el-aside>
</template>

<script lang="ts" setup>
import { computed } from "vue";
import { useRouter } from "vue-router";
import { Expand, Fold } from "@element-plus/icons-vue";

const props = defineProps<{
  hidden?: boolean;
  collapsed?: boolean;
}>();
const emit = defineEmits(["toggle-collapse"]);

const collapsed = computed(() => !!props.collapsed);

const logoSrc = "/logo.png";

const router = useRouter();

const menuList = computed(() => {
  const allRoutes = router.options.routes;
  const backendRoute = allRoutes.find((route) => route.name === "backend");
  return (backendRoute?.children || []).filter(
    (item) => item.meta && item.meta.title,
  );
});

const asideWidth = computed(() => {
  if (props.hidden) {
    return "0px";
  }
  return collapsed.value ? "72px" : "260px";
});

const selectMenu = (index: string) => {
  router.push(index);
};

const toggleCollapse = () => {
  emit("toggle-collapse");
};
</script>

<style lang="scss" scoped>
.my-sidebar {
  height: 100%;
  background: linear-gradient(180deg, #1a1a1a 0%, #2d2d2d 100%);
  overflow: hidden;
  transition: width 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  border-right: 1px solid #3d3d3d;
  display: flex;
  flex-direction: column;

  :deep(.sidebar-menu) {
    background: transparent;
    border: 0;

    .el-menu-item {
      background-color: transparent !important;
      transition: all 0.3s ease;

      &:hover {
        background-color: rgba(255, 255, 255, 0.08) !important;
      }

      &.is-active {
        background: linear-gradient(
          90deg,
          #409eff 0%,
          transparent 100%
        ) !important;
        color: #409eff !important;

        .menu-icon {
          color: #409eff;
        }
      }
    }
  }

  .sidebar-wrapper {
    flex: 1;
    overflow-y: auto;
    overflow-x: hidden;
    padding: 16px 0 0 0;

    &::-webkit-scrollbar {
      width: 6px;
    }

    &::-webkit-scrollbar-track {
      background: transparent;
    }

    &::-webkit-scrollbar-thumb {
      background: rgba(255, 255, 255, 0.2);
      border-radius: 3px;

      &:hover {
        background: rgba(255, 255, 255, 0.3);
      }
    }
  }

  .brand-container {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 12px 16px;
    margin-bottom: 8px;
    cursor: pointer;
    transition: all 0.3s ease;
    position: relative;

    &:hover {
      background-color: rgba(64, 158, 255, 0.1);
    }

    .brand-logo-wrapper {
      position: relative;
      flex-shrink: 0;
      width: 42px;
      height: 42px;
      margin-right: 12px;

      .brand-logo {
        width: 100%;
        height: 100%;
        border-radius: 10px;
        border: 2px solid #409eff;
        box-shadow: 0 2px 8px rgba(64, 158, 255, 0.2);
      }

      .image-slot {
        width: 100%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        background: linear-gradient(135deg, #409eff, #66b1ff);
        border-radius: 10px;
        font-size: 20px;
      }

      .logo-online-indicator {
        position: absolute;
        bottom: 0;
        right: 0;
        width: 12px;
        height: 12px;
        background: #85ce61;
        border-radius: 50%;
        border: 2px solid #1a1a1a;
        animation: pulse 2s infinite;
      }
    }

    .brand-info {
      min-width: 0;

      .brand-name {
        font-size: 14px;
        font-weight: 600;
        color: #fff;
        margin: 0 0 2px 0;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }

      .brand-desc {
        font-size: 11px;
        color: #999;
        margin: 0;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
  }

  :deep(.sidebar-menu) {
    padding: 0;

    .menu-item {
      padding: 0 16px;
      margin: 4px 8px;
      border-radius: 8px;
      height: 40px;
      line-height: 40px;
      display: flex;
      align-items: center;

      .menu-icon {
        margin-right: 12px;
        font-size: 16px;
        transition: all 0.3s ease;
      }

      .menu-text {
        font-size: 13px;
        transition: all 0.3s ease;
      }
    }
  }

  .sidebar-footer {
    padding: 12px 8px;
    border-top: 1px solid #3d3d3d;
    display: flex;
    justify-content: center;

    .collapse-btn {
      width: 36px;
      height: 36px;
      border-radius: 8px;
      background-color: rgba(255, 255, 255, 0.08);
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      color: #d4d4d8;
      transition: all 0.3s ease;

      &:hover {
        background-color: rgba(64, 158, 255, 0.2);
        color: #409eff;
      }
    }
  }
}

.my-sidebar.is-collapsed {
  .brand-container {
    justify-content: center;
    padding: 12px 0;
    margin-bottom: 0;
  }

  :deep(.sidebar-menu) {
    .menu-item {
      padding: 0;
      margin: 4px 0;
      justify-content: center;
    }
  }
}

.my-sidebar.is-hidden {
  border: 0;
}

@keyframes pulse {
  0% {
    box-shadow: 0 0 0 0 rgba(133, 206, 97, 0.7);
  }
  70% {
    box-shadow: 0 0 0 6px rgba(133, 206, 97, 0);
  }
  100% {
    box-shadow: 0 0 0 0 rgba(133, 206, 97, 0);
  }
}

@media (max-width: 768px) {
  .my-sidebar {
    :deep(.sidebar-menu) {
      .menu-item {
        padding: 0 12px;
      }
    }
  }
}
</style>
