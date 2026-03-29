<template>
  <div class="auth-container">
    <div class="header">
      <p class="welcome">欢迎回来</p>
      <h2 class="title">登录账号</h2>
      <p class="subtitle">登录后即可进入心理健康管理平台</p>
    </div>

    <el-form :model="form" class="auth-form" @submit.prevent>
      <el-form-item label="用户名" :label-width="80">
        <el-input
          v-model="form.username"
          placeholder="请输入用户名"
          clearable
        />
      </el-form-item>
      <el-form-item label="密码" :label-width="80">
        <el-input
          v-model="form.password"
          type="password"
          placeholder="请输入密码"
          show-password
          @keyup.enter="handleLogin"
        />
      </el-form-item>
      <el-form-item class="actions">
        <el-button type="primary" class="main-btn" @click="handleLogin"
          >登录</el-button
        >
        <el-button class="ghost-btn" @click="goHome">返回首页</el-button>
      </el-form-item>
    </el-form>

    <div class="footer-line">
      <p>还没有账号？<router-link to="/auth/register">立即注册</router-link></p>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { login } from "@/api/admin";

const router = useRouter();

const form = reactive({
  username: "",
  password: "",
});

const handleLogin = async () => {
  if (!form.username || !form.password) {
    ElMessage.warning("请输入用户名和密码");
    return;
  }

  try {
    const res = await login({
      username: form.username,
      password: form.password,
    });

    const payload = res?.data || {};
    const token = payload.token || payload.data?.token;
    const userInfo =
      payload.userInfore ||
      payload.userInfo ||
      payload.data?.userInfore ||
      payload.data?.userInfo ||
      {};
    const userType = String(userInfo.userType || "");

    if (!token) {
      ElMessage.error(
        payload.message || "登录成功但未获取到凭证，请联系管理员",
      );
      return;
    }

    localStorage.setItem("token", token);
    localStorage.setItem(
      "user",
      JSON.stringify({ username: form.username, ...userInfo }),
    );
    ElMessage.success(payload.message || "登录成功");

    if (userType === "2") {
      router.push("/backend/dashboard");
    } else {
      router.push("/frontend");
    }
  } catch (err) {
    console.error("Login error:", err);
    ElMessage.error(err.message || "登录失败，请检查用户名和密码或稍后重试");
  }
};

const goHome = () => {
  router.push("/");
};
</script>

<style scoped>
.auth-container {
  width: min(460px, 100%);
  padding: 28px;
  border-radius: 16px;
  background: #fff;
  border: 1px solid #e6ebf2;
  box-shadow: 0 14px 35px rgba(24, 35, 76, 0.1);
}

.header {
  text-align: left;
}

.welcome {
  font-size: 13px;
  color: #4c75d9;
  margin-bottom: 6px;
}

.title {
  font-size: 28px;
  margin-bottom: 8px;
  color: #1f2a44;
}

.subtitle {
  color: #63708a;
  font-size: 14px;
  margin-bottom: 4px;
}

.auth-form {
  margin-top: 18px;
}

:deep(.el-form-item__label) {
  font-weight: 600;
  color: #2f3d58;
}

:deep(.el-input__wrapper) {
  border-radius: 10px;
}

.actions {
  margin-top: 8px;
  margin-bottom: 8px;
}

.main-btn,
.ghost-btn {
  width: 100%;
  border-radius: 10px;
}

.main-btn {
  margin-bottom: 10px;
}

.ghost-btn {
  margin-left: 0;
}

.footer-line {
  margin-top: 10px;
  text-align: center;
  font-size: 14px;
  color: #64748b;
}

.footer-line a {
  color: #2457d6;
  font-weight: 600;
}

@media (max-width: 480px) {
  .auth-container {
    padding: 22px 16px;
    border-radius: 12px;
  }

  .title {
    font-size: 24px;
  }
}
</style>
