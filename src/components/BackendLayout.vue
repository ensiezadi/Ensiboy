<template>
  <div class="backend-layout">
    <el-container class="layout-container">
      <el-header class="layout-header">
        <BackendMainBar
          :sidebar-hidden="sidebarStore.isHidden"
          :sidebar-collapsed="sidebarStore.isCollapsed"
          @cycle-sidebar="cycleSidebar"
        />
      </el-header>
      <el-container class="layout-body">
        <BackendSideBar
          :hidden="sidebarStore.isHidden"
          :collapsed="sidebarStore.isCollapsed"
          @toggle-collapse="toggleSidebarCollapse"
        />
        <el-main class="layout-main">
          <router-view class="content-container"></router-view>
        </el-main>
      </el-container>
      <el-footer class="layout-footer">
        <BackendEndBar />
      </el-footer>
    </el-container>
  </div>
</template>

<script setup>
import BackendMainBar from "./BackendMainBar.vue";
import BackendSideBar from "./BackendSideBar.vue";
import BackendEndBar from "./BackendEndBar.vue";
import { useSidebarStore } from "@/stores/sidebar";

const sidebarStore = useSidebarStore();

const cycleSidebar = () => {
  sidebarStore.cycleMode();
};

const toggleSidebarCollapse = () => {
  sidebarStore.toggleCollapsedMode();
};
</script>

<style lang="scss" scoped>
.backend-layout {
  height: 100vh;
  width: 100%;
  background-color: #eee;
  overflow: hidden;

  .layout-container {
    height: 100%;
  }

  .layout-header {
    height: 60px;
    padding: 0;
    flex-shrink: 0;
  }

  .layout-body {
    flex: 1;
    min-height: 0;
    overflow: hidden;
  }

  .layout-main {
    min-height: 0;
    overflow: auto;
    padding: 16px;
    background-color: #f5f7fa;
  }

  .layout-footer {
    height: auto;
    padding: 8px 16px;
    flex-shrink: 0;
    border-top: 1px solid #e4e7ed;
    background-color: #fff;
  }

  .content-container {
    height: 100%;
  }
}
</style>
