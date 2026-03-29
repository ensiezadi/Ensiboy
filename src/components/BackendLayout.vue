<template>
  <div class="backend-layout">
    <el-container class="layout-container">
      <el-header class="layout-header">
        <BackendMainBar
          :sidebar-hidden="sidebarHidden"
          :sidebar-collapsed="sidebarCollapsed"
          @cycle-sidebar="cycleSidebar"
        />
      </el-header>
      <el-container class="layout-body">
        <BackendSideBar
          :hidden="sidebarHidden"
          :collapsed="sidebarCollapsed"
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
import { ref } from "vue";
import BackendMainBar from "./BackendMainBar.vue";
import BackendSideBar from "./BackendSideBar.vue";
import BackendEndBar from "./BackendEndBar.vue";

const sidebarHidden = ref(false);
const sidebarCollapsed = ref(false);

const cycleSidebar = () => {
  if (!sidebarHidden.value && !sidebarCollapsed.value) {
    sidebarCollapsed.value = true;
    return;
  }

  if (!sidebarHidden.value && sidebarCollapsed.value) {
    sidebarHidden.value = true;
    sidebarCollapsed.value = false;
    return;
  }

  sidebarHidden.value = false;
  sidebarCollapsed.value = false;
};

const toggleSidebarCollapse = () => {
  sidebarHidden.value = false;
  sidebarCollapsed.value = !sidebarCollapsed.value;
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
