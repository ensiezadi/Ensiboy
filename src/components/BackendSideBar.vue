<template>
  <el-aside
    :width="asideWidth"
    class="my-sidebar"
    :class="{ 'is-hidden': hidden, 'is-collapsed': collapsed }"
  >
    <el-menu
      active-text-color="#ffd04b"
      background-color="#545c64"
      text-color="#fff"
      :default-active="router.currentRoute.value.path"
      :collapse="collapsed"
      :collapse-transition="false"
      class="el-menu-vertical-demo"
      @select="selectMenu"
    >
      <div class="brand-container" @dblclick.stop="toggleCollapse">
        <el-image
          :src="logoSrc"
          alt="logo"
          class="brand-logo"
          fit="cover"
          style="width: 50px; height: 50px"
        >
          <template #error>
            <div class="image-slot">AI</div>
          </template>
        </el-image>
        <div v-show="!collapsed" class="brand-info">
          <h3 class="brand-name">AI助手</h3>
          <p class="brand-desc">智能办公好帮手</p>
        </div>
      </div>

      <el-menu-item
        v-for="item in menuList"
        :key="item.path"
        :index="'/backend/' + item.path"
      >
        <el-icon>
          <component :is="item.meta.icon" />
        </el-icon>
        <span>{{ item.meta.title }}</span>
      </el-menu-item>
    </el-menu>
  </el-aside>
</template>

<script lang="ts" setup>
import { computed } from "vue";
import { useRouter } from "vue-router";

const props = defineProps<{
  hidden?: boolean;
  collapsed?: boolean;
}>();
const emit = defineEmits(["toggle-collapse"]);

const collapsed = computed(() => !!props.collapsed);

const logoSrc =
  "https://cdn.jsdelivr.net/gh/ensiezadi/obsidian-images/images/%E4%BB%93%E5%BA%93.png";

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
  return collapsed.value ? "72px" : "240px";
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
  background-color: #545c64;
  overflow: hidden;
  transition: width 0.2s ease;
  border-right: 0;

  :deep(.el-menu) {
    height: 100%;
    border-right: 0;
  }

  .brand-container {
    display: flex;
    align-items: center;
    padding: 20px;
    background-color: #2d2f33;
    margin-bottom: 20px;
    cursor: pointer;

    .brand-logo {
      border-radius: 50%;
      margin-right: 15px;
    }

    .brand-info {
      .brand-name {
        font-size: 18px;
        color: #fff;
        margin: 0;
      }

      .brand-desc {
        font-size: 12px;
        color: #bbb;
        margin: 0;
      }
    }
  }
}

.my-sidebar.is-collapsed {
  .brand-container {
    justify-content: center;
    padding: 16px 0;

    .brand-logo {
      margin-right: 0;
    }
  }
}

.my-sidebar.is-hidden {
  border: 0;
}
</style>
