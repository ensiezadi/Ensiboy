<template>
  <div class="my-sidebar">
    <div class="flex-box">
      <el-button>
        <el-icon><Expand /></el-icon>
      </el-button>
      <p class="page-title">AI助手导航栏</p>
    </div>
    <div class="flex-box">
      <el-dropdown @command="handleCommand">
        <el-button type="primary">
          下拉菜单<i class="el-icon-arrow-down el-icon--right"></i>
        </el-button>
      </el-dropdown>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.flex-box {
  display: flex;
  align-items: center;
  justify-self: center;
}
</style>
