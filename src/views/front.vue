<template>
  <div class="front-page">
    <!-- ========== 导航栏 ========== -->
    <nav class="navbar">
      <div class="nav-container">
        <div class="logo-group">
          <span class="logo-emoji">💭</span>
          <span class="logo-text">心理健康</span>
        </div>
        <div class="nav-actions">
          <router-link to="/auth/login" class="nav-btn login-btn"
            >登录</router-link
          >
          <router-link to="/auth/register" class="nav-btn register-btn"
            >注册</router-link
          >
        </div>
      </div>
    </nav>

    <!-- ========== Hero 区域 ========== -->
    <section class="hero">
      <div class="hero-content">
        <h1 class="hero-title">您的心理健康管理专家</h1>
        <p class="hero-subtitle">专业、安全、便捷的心理健康管理平台</p>
        <div class="hero-buttons">
          <router-link to="/auth/register" class="btn btn-primary"
            >立即开始</router-link
          >
          <router-link to="/auth/login" class="btn btn-secondary"
            >登录账号</router-link
          >
        </div>
      </div>
      <div class="hero-visual">
        <div class="visual-circle circle-1"></div>
        <div class="visual-circle circle-2"></div>
        <div class="visual-circle circle-3"></div>
      </div>
    </section>

    <!-- ========== 功能特性区域 ========== -->
    <section class="features">
      <div class="section-header">
        <h2>为什么选择我们</h2>
        <p>我们提供全面的心理健康管理服务</p>
      </div>

      <div class="features-grid">
        <div class="feature-card">
          <div class="feature-icon">📊</div>
          <h3>数据追踪</h3>
          <p>系统地记录和分析您的心理状态变化，让您更好地了解自己</p>
        </div>

        <div class="feature-card">
          <div class="feature-icon">🎯</div>
          <h3>个性建议</h3>
          <p>基于AI的智能分析，为您提供个性化的改善建议</p>
        </div>

        <div class="feature-card">
          <div class="feature-icon">🔒</div>
          <h3>隐私保护</h3>
          <p>军级加密技术，您的数据100%安全</p>
        </div>

        <div class="feature-card">
          <div class="feature-icon">👥</div>
          <h3>社区支持</h3>
          <p>与志同道合的朋友分享经验，互相鼓励</p>
        </div>

        <div class="feature-card">
          <div class="feature-icon">📱</div>
          <h3>随时随地</h3>
          <p>随时随地访问，在您最需要的时刻提供支持</p>
        </div>

        <div class="feature-card">
          <div class="feature-icon">⚡</div>
          <h3>快速见效</h3>
          <p>科学的方法和循序渐进的计划，帮助您快速改善</p>
        </div>
      </div>
    </section>

    <!-- ========== 数据统计区域 ========== -->
    <section class="stats">
      <div class="stat-item">
        <div class="stat-number">10K+</div>
        <div class="stat-label">活跃用户</div>
      </div>
      <div class="stat-item">
        <div class="stat-number">500K+</div>
        <div class="stat-label">心理评估</div>
      </div>
      <div class="stat-item">
        <div class="stat-number">98%</div>
        <div class="stat-label">用户满意度</div>
      </div>
      <div class="stat-item">
        <div class="stat-number">24/7</div>
        <div class="stat-label">客服支持</div>
      </div>
    </section>

    <!-- ========== 行动召唤区域 ========== -->
    <section class="cta">
      <div class="cta-content">
        <h2>准备好开始您的心理健康之旅了吗?</h2>
        <p>今天就加入我们，享受专业的心理健康管理服务</p>
        <router-link to="/auth/register" class="btn btn-large btn-primary"
          >免费注册</router-link
        >
      </div>
    </section>

    <!-- ========== 页脚 ========== -->
    <footer class="footer">
      <div class="footer-content">
        <div class="footer-section">
          <h4>心理健康</h4>
          <p>专业的心理健康管理平台</p>
        </div>
        <div class="footer-section">
          <h4>快速链接</h4>
          <ul>
            <li><router-link to="/">首页</router-link></li>
            <li><router-link to="/auth/login">登录</router-link></li>
            <li><router-link to="/auth/register">注册</router-link></li>
          </ul>
        </div>
        <div class="footer-section">
          <h4>联系我们</h4>
          <ul>
            <li>邮箱: support@health-mind.com</li>
            <li>电话: 400-123-4567</li>
          </ul>
        </div>
      </div>
      <div class="footer-bottom">
        <p>&copy; 2024 心理健康管理平台. 保留所有权利.</p>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";

const router = useRouter();
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.front-page {
  background: #f8fafc;
  color: #1f2a44;
}

/* ========== 导航栏 ========== */
.navbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  z-index: 100;
}

.nav-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo-group {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 20px;
  font-weight: 700;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.logo-group:hover {
  transform: scale(1.05);
}

.logo-emoji {
  font-size: 28px;
}

.logo-text {
  color: #667eea;
}

.nav-actions {
  display: flex;
  gap: 12px;
}

.nav-btn {
  padding: 8px 16px;
  border-radius: 6px;
  text-decoration: none;
  font-size: 14px;
  transition: all 0.3s ease;
}

.login-btn {
  color: #667eea;
  border: 1px solid #667eea;
}

.login-btn:hover {
  background: #f0f4ff;
}

.register-btn {
  color: white;
  background: #667eea;
}

.register-btn:hover {
  background: #764ba2;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}

/* ========== Hero 区域 ========== */
.hero {
  margin-top: 64px;
  padding: 100px 24px;
  min-height: calc(100vh - 64px);
  display: grid;
  grid-template-columns: 1fr 1fr;
  align-items: center;
  gap: 60px;
  background: linear-gradient(
    135deg,
    rgba(102, 126, 234, 0.05) 0%,
    rgba(118, 75, 162, 0.05) 100%
  );
  position: relative;
  overflow: hidden;
}

.hero-content {
  max-width: 500px;
}

.hero-title {
  font-size: 48px;
  font-weight: 700;
  line-height: 1.2;
  margin-bottom: 16px;
  color: #1f2a44;
}

.hero-subtitle {
  font-size: 18px;
  color: #4f5f79;
  margin-bottom: 32px;
  line-height: 1.6;
}

.hero-buttons {
  display: flex;
  gap: 16px;
}

.btn {
  padding: 12px 32px;
  border-radius: 8px;
  text-decoration: none;
  font-size: 16px;
  font-weight: 600;
  transition: all 0.3s ease;
  display: inline-block;
  text-align: center;
  cursor: pointer;
  border: none;
}

.btn-primary {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.btn-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(102, 126, 234, 0.3);
}

.btn-secondary {
  background: white;
  color: #667eea;
  border: 2px solid #667eea;
}

.btn-secondary:hover {
  background: #f0f4ff;
  transform: translateY(-2px);
}

.btn-large {
  padding: 16px 48px;
  font-size: 18px;
}

.hero-visual {
  position: relative;
  height: 400px;
}

.visual-circle {
  position: absolute;
  border-radius: 50%;
  background: linear-gradient(135deg, #667eea, #764ba2);
  opacity: 0.2;
}

.circle-1 {
  width: 200px;
  height: 200px;
  top: 50px;
  right: 100px;
  animation: float 6s ease-in-out infinite;
}

.circle-2 {
  width: 300px;
  height: 300px;
  top: 200px;
  right: 0;
  animation: float 8s ease-in-out infinite reverse;
}

.circle-3 {
  width: 150px;
  height: 150px;
  bottom: 100px;
  right: 150px;
  animation: float 7s ease-in-out infinite;
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

/* ========== 功能特性区域 ========== */
.features {
  padding: 80px 24px;
  max-width: 1200px;
  margin: 0 auto;
}

.section-header {
  text-align: center;
  margin-bottom: 60px;
}

.section-header h2 {
  font-size: 36px;
  font-weight: 700;
  margin-bottom: 12px;
}

.section-header p {
  font-size: 16px;
  color: #4f5f79;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 24px;
}

.feature-card {
  background: white;
  padding: 32px 24px;
  border-radius: 12px;
  border: 1px solid #e6ebf2;
  text-align: center;
  transition: all 0.3s ease;
}

.feature-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 32px rgba(0, 0, 0, 0.1);
  border-color: #667eea;
}

.feature-icon {
  font-size: 48px;
  margin-bottom: 16px;
}

.feature-card h3 {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 12px;
}

.feature-card p {
  font-size: 14px;
  color: #4f5f79;
  line-height: 1.6;
}

/* ========== 数据统计区域 ========== */
.stats {
  padding: 60px 24px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 40px;
  text-align: center;
  max-width: 1200px;
  margin: 0 auto;
  border-radius: 12px;
}

.stat-item {
  color: white;
}

.stat-number {
  font-size: 36px;
  font-weight: 700;
  margin-bottom: 8px;
}

.stat-label {
  font-size: 14px;
  opacity: 0.9;
}

/* ========== 行动召唤区域 ========== */
.cta {
  padding: 80px 24px;
  text-align: center;
  background: white;
}

.cta-content h2 {
  font-size: 32px;
  font-weight: 700;
  margin-bottom: 12px;
}

.cta-content p {
  font-size: 16px;
  color: #4f5f79;
  margin-bottom: 32px;
}

/* ========== 页脚 ========== */
.footer {
  background: #1f2a44;
  color: white;
  padding: 48px 24px 24px;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 40px;
  margin-bottom: 32px;
}

.footer-section h4 {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 12px;
}

.footer-section p,
.footer-section ul {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.7);
  list-style: none;
}

.footer-section ul li {
  margin-bottom: 8px;
}

.footer-section a {
  color: rgba(255, 255, 255, 0.7);
  text-decoration: none;
  transition: color 0.3s ease;
}

.footer-section a:hover {
  color: white;
}

.footer-bottom {
  max-width: 1200px;
  margin: 0 auto;
  padding-top: 24px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  text-align: center;
  font-size: 14px;
  color: rgba(255, 255, 255, 0.5);
}

/* ========== 响应式设计 ========== */
@media (max-width: 768px) {
  .nav-container {
    padding: 0 16px;
  }

  .hero {
    grid-template-columns: 1fr;
    padding: 80px 16px;
    gap: 40px;
  }

  .hero-title {
    font-size: 32px;
  }

  .hero-subtitle {
    font-size: 16px;
  }

  .hero-buttons {
    flex-direction: column;
  }

  .section-header h2 {
    font-size: 28px;
  }

  .features-grid {
    grid-template-columns: 1fr;
  }

  .stats {
    grid-template-columns: 1fr 1fr;
  }

  .cta-content h2 {
    font-size: 24px;
  }

  .footer-content {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 480px) {
  .nav-actions {
    gap: 8px;
  }

  .nav-btn {
    padding: 6px 12px;
    font-size: 12px;
  }

  .hero {
    padding: 60px 12px;
  }

  .hero-title {
    font-size: 24px;
  }

  .hero-subtitle {
    font-size: 14px;
  }

  .btn {
    padding: 10px 24px;
    font-size: 14px;
  }

  .btn-large {
    padding: 12px 32px;
    font-size: 16px;
  }

  .stats {
    grid-template-columns: 1fr;
    padding: 40px 16px;
  }

  .stat-number {
    font-size: 28px;
  }
}
</style>
