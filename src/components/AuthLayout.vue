<template>
  <div class="auth">
    <div class="leftLayout">
      <div class="leftContent">
        <div class="logo-area">
          <div class="logo">💭</div>
          <h1 class="brand">心理健康</h1>
        </div>

        <div class="intro">
          <p class="slogan">专注心理健康，提升生活质量</p>
          <p class="description">专业的心理健康管理和咨询服务平台</p>
        </div>

        <div class="features">
          <div class="feature-item">
            <span class="feature-icon">📊</span>
            <div>
              <div class="feature-title">数据追踪</div>
              <div class="feature-desc">实时记录心理状态</div>
            </div>
          </div>

          <div class="feature-item">
            <span class="feature-icon">🎯</span>
            <div>
              <div class="feature-title">个性建议</div>
              <div class="feature-desc">AI驱动的改善方案</div>
            </div>
          </div>

          <div class="feature-item">
            <span class="feature-icon">🔒</span>
            <div>
              <div class="feature-title">隐私保护</div>
              <div class="feature-desc">数据100%安全加密</div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="rightLayout">
      <router-view></router-view>
    </div>
  </div>
</template>

<script setup>
const authBgUrl = new URL("@/assets/images/p1.jpg", import.meta.url).href;
</script>

<style scoped>
.auth {
  min-height: 100vh;
  display: grid;
  grid-template-columns: 1fr 1fr;
  background: #f8fafc;
}

/* ========== 左侧布局 ========== */
.leftLayout {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 60px 40px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  position: relative;
  overflow: hidden;
}

.leftLayout::before {
  content: "";
  position: absolute;
  top: -50%;
  right: -50%;
  width: 400px;
  height: 400px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 50%;
  animation: float 6s ease-in-out infinite;
}

.leftLayout::after {
  content: "";
  position: absolute;
  bottom: -30%;
  left: -10%;
  width: 300px;
  height: 300px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 50%;
  animation: float 8s ease-in-out infinite reverse;
}

@keyframes float {
  0%,
  100% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(20px);
  }
}

.leftContent {
  position: relative;
  z-index: 1;
  max-width: 480px;
}

.logo-area {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 32px;
}

.logo {
  font-size: 48px;
  animation: scale 2s ease-in-out infinite;
}

@keyframes scale {
  0%,
  100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.05);
  }
}

.brand {
  font-size: 32px;
  font-weight: 700;
  color: #fff;
  margin: 0;
}

.intro {
  margin-bottom: 40px;
}

.slogan {
  font-size: 24px;
  font-weight: 600;
  color: #fff;
  margin: 0 0 12px 0;
  line-height: 1.4;
}

.description {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.8);
  margin: 0;
  line-height: 1.6;
}

/* ========== 功能特性 ========== */
.features {
  margin-bottom: 40px;
}

.feature-item {
  display: flex;
  align-items: flex-start;
  gap: 16px;
  margin-bottom: 24px;
  padding: 0;
  background: transparent;
  border-radius: 0;
}

.feature-item:hover {
  background: transparent;
  transform: translateX(4px);
}

.feature-icon {
  font-size: 32px;
  min-width: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.feature-title {
  font-size: 16px;
  font-weight: 600;
  color: #fff;
  margin-bottom: 4px;
}

.feature-desc {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.7);
}

/* ========== 图片区域 ========== */
.image-area {
  margin-top: 16px;
}

.hero-image {
  width: 100%;
  max-width: 320px;
  height: auto;
  display: block;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
}

/* ========== 右侧布局 ========== */
.rightLayout {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px 24px;
  background: #f8fafc;
}

/* ========== 响应式设计 ========== */
@media (max-width: 1024px) {
  .auth {
    grid-template-columns: 1fr;
  }

  .leftLayout {
    padding: 48px 32px;
    min-height: 320px;
  }

  .leftContent {
    max-width: 100%;
  }

  .brand {
    font-size: 28px;
  }

  .slogan {
    font-size: 20px;
  }

  .features {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 12px;
    margin-bottom: 24px;
  }

  .feature-item {
    flex-direction: column;
    gap: 8px;
  }

  .rightLayout {
    padding: 24px;
  }
}

@media (max-width: 768px) {
  .leftLayout {
    padding: 32px 20px;
  }

  .logo-area {
    margin-bottom: 24px;
  }

  .logo {
    font-size: 40px;
  }

  .brand {
    font-size: 24px;
  }

  .slogan {
    font-size: 18px;
  }

  .features {
    grid-template-columns: 1fr;
  }

  .image-area {
    margin-top: 20px;
  }

  .hero-image {
    max-width: 280px;
  }
}

@media (max-width: 480px) {
  .auth {
    grid-template-columns: 1fr;
  }

  .leftLayout {
    padding: 24px 16px;
    min-height: 280px;
  }

  .logo-area {
    gap: 8px;
    margin-bottom: 16px;
  }

  .logo {
    font-size: 32px;
  }

  .brand {
    font-size: 20px;
  }

  .slogan {
    font-size: 16px;
  }

  .description {
    font-size: 13px;
  }

  .feature-text h3 {
    font-size: 13px;
  }

  .feature-text p {
    font-size: 11px;
  }

  .rightLayout {
    padding: 16px;
  }
}
</style>
