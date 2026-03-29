<template>
  <div class="auth-container">
    <div class="header">
      <p class="welcome">创建新账号</p>
      <h2 class="title">用户注册</h2>
      <p class="subtitle">完成注册后即可登录并使用全部功能</p>
    </div>

    <el-form
      ref="registerFormRef"
      :model="form"
      :rules="rules"
      class="auth-form"
      label-position="top"
      @submit.prevent
    >
      <el-form-item label="用户名" prop="username">
        <el-input
          v-model="form.username"
          placeholder="请输入用户名"
          clearable
        />
      </el-form-item>

      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" placeholder="请输入邮箱" clearable />
      </el-form-item>

      <el-form-item label="密码" prop="password">
        <el-input
          v-model="form.password"
          type="password"
          placeholder="请输入密码（至少6位）"
          show-password
        />
      </el-form-item>

      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input
          v-model="form.confirmPassword"
          type="password"
          placeholder="请再次输入密码"
          show-password
          @keyup.enter="handleRegister"
        />
      </el-form-item>

      <el-form-item prop="agreement">
        <el-checkbox v-model="form.agreement"
          >我已阅读并同意服务条款</el-checkbox
        >
      </el-form-item>

      <el-form-item class="actions">
        <el-button type="primary" class="main-btn" @click="handleRegister"
          >注册</el-button
        >
        <el-button class="ghost-btn" @click="goHome">返回首页</el-button>
      </el-form-item>
    </el-form>

    <div class="footer-line">
      <p>已有账号？<router-link to="/auth/login">立即登录</router-link></p>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";

const router = useRouter();
const registerFormRef = ref();

const form = reactive({
  username: "",
  email: "",
  password: "",
  confirmPassword: "",
  agreement: false,
});

const validateConfirmPassword = (_, value, callback) => {
  if (!value) {
    callback(new Error("请再次输入密码"));
    return;
  }
  if (value !== form.password) {
    callback(new Error("两次输入密码不一致"));
    return;
  }
  callback();
};

const validateAgreement = (_, value, callback) => {
  if (!value) {
    callback(new Error("请先勾选服务条款"));
    return;
  }
  callback();
};

const rules = {
  username: [
    { required: true, message: "请输入用户名", trigger: "blur" },
    { min: 2, max: 20, message: "用户名长度为2-20位", trigger: "blur" },
  ],
  email: [
    { required: true, message: "请输入邮箱", trigger: "blur" },
    { type: "email", message: "邮箱格式不正确", trigger: ["blur", "change"] },
  ],
  password: [
    { required: true, message: "请输入密码", trigger: "blur" },
    { min: 6, message: "密码至少6位", trigger: "blur" },
  ],
  confirmPassword: [{ validator: validateConfirmPassword, trigger: "blur" }],
  agreement: [{ validator: validateAgreement, trigger: "change" }],
};

const handleRegister = async () => {
  if (!registerFormRef.value) {
    return;
  }

  try {
    await registerFormRef.value.validate();
    ElMessage.success("注册成功，请登录");
    router.push("/auth/login");
  } catch {
    ElMessage.warning("请检查注册信息");
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

:deep(.el-checkbox__label) {
  color: #4b5b77;
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
