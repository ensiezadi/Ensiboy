<template>
  <div class="front-page">
    <!-- ========== 导航栏 ========== -->
    <nav class="navbar">
      <div class="nav-container">
        <router-link to="/" class="logo-group">
          <span class="logo-emoji">💭</span>
          <div class="logo-info">
            <span class="logo-text">心理健康AI助手</span>
            <span class="logo-tagline">专业心理健康管理</span>
          </div>
        </router-link>
        <div class="nav-actions">
          <router-link to="/auth/login" class="nav-btn login-btn"
            >登录</router-link
          >
          <router-link to="/auth/register" class="nav-btn register-btn"
            >立即开始</router-link
          >
        </div>
      </div>
    </nav>

    <!-- ========== Hero 区域 ========== -->
    <section class="hero">
      <div class="hero-content">
        <div class="hero-badge">🌟 AI驱动的心理健康解决方案</div>
        <h1 class="hero-title">
          关爱心理健康，从<span class="highlight">现在</span>开始
        </h1>
        <p class="hero-subtitle">
          与我们的AI心理健康助手一起，实时跟踪情绪、获取专业建议、建立健康习惯。一个更快乐、更健康的你正在等待。
        </p>
        <div class="hero-buttons">
          <router-link to="/auth/register" class="btn btn-primary"
            >👋 免费开始</router-link
          >
          <a href="#features" class="btn btn-secondary">📚 了解更多</a>
        </div>
        <div class="hero-stats">
          <div class="hero-stat">
            <span class="hero-stat-value">10K+</span>
            <span class="hero-stat-label">活跃用户</span>
          </div>
          <div class="hero-stat">
            <span class="hero-stat-value">4.8⭐</span>
            <span class="hero-stat-label">用户评分</span>
          </div>
          <div class="hero-stat">
            <span class="hero-stat-value">500K+</span>
            <span class="hero-stat-label">情绪记录</span>
          </div>
        </div>
      </div>
      <div class="hero-visual">
        <div class="visual-circle circle-1"></div>
        <div class="visual-circle circle-2"></div>
        <div class="visual-circle circle-3"></div>
        <div class="floating-card card-1">
          <div class="card-emoji">😊</div>
          <span>心情安定</span>
        </div>
        <div class="floating-card card-2">
          <div class="card-emoji">💪</div>
          <span>积极向上</span>
        </div>
        <div class="floating-card card-3">
          <div class="card-emoji">🧘</div>
          <span>放松冥想</span>
        </div>
      </div>
    </section>

    <!-- ========== 功能特性区域 ========== -->
    <section class="features" id="features">
      <div class="section-header">
        <h2>核心功能</h2>
        <p>全面的心理健康管理工具，助您找回内心的平静</p>
      </div>

      <div class="features-grid">
        <div class="feature-card">
          <div class="feature-icon">📊</div>
          <h3>情绪追踪</h3>
          <p>每天记录您的情绪状态，通过可视化图表清晰了解心情变化趋势</p>
          <div class="feature-badge">实时同步</div>
        </div>

        <div class="feature-card">
          <div class="feature-icon">🤖</div>
          <h3>AI心理咨询</h3>
          <p>与我们的AI助手进行专业心理咨询对话，获得实时的心理健康建议</p>
          <div class="feature-badge">24/7可用</div>
        </div>

        <div class="feature-card">
          <div class="feature-icon">📔</div>
          <h3>心情日记</h3>
          <p>记录您的日常心理状态，通过日记回顾发现更深层的情绪根源</p>
          <div class="feature-badge">隐私保护</div>
        </div>

        <div class="feature-card">
          <div class="feature-icon">🎯</div>
          <h3>个性化计划</h3>
          <p>根据您的情绪数据和健康状况，制定专属的心理改善计划</p>
          <div class="feature-badge">科学方法</div>
        </div>

        <div class="feature-card">
          <div class="feature-icon">👥</div>
          <h3>社区支持</h3>
          <p>与志同道合的用户分享经历，互相支持和鼓励，建立健康的支持网络</p>
          <div class="feature-badge">温暖社区</div>
        </div>

        <div class="feature-card">
          <div class="feature-icon">🔐</div>
          <h3>数据安全</h3>
          <p>军级加密保护您的所有数据，严格遵守隐私政策，绝不共享个人信息</p>
          <div class="feature-badge">端到端加密</div>
        </div>
      </div>
    </section>

    <!-- ========== 数据统计区域 ========== -->
    <section class="stats-section">
      <div class="stats">
        <div class="stat-item">
          <div class="stat-number">10K+</div>
          <div class="stat-label">活跃用户</div>
          <div class="stat-desc">正在享受更健康的生活</div>
        </div>
        <div class="stat-item">
          <div class="stat-number">500K+</div>
          <div class="stat-label">心理评估</div>
          <div class="stat-desc">帮助用户了解自己</div>
        </div>
        <div class="stat-item">
          <div class="stat-number">98%</div>
          <div class="stat-label">用户满意度</div>
          <div class="stat-desc">持续改善中</div>
        </div>
        <div class="stat-item">
          <div class="stat-number">24/7</div>
          <div class="stat-label">全天支持</div>
          <div class="stat-desc">随时为您服务</div>
        </div>
      </div>
    </section>

    <!-- ========== 行动召唤区域 ========== -->
    <section class="cta">
      <div class="cta-content">
        <div class="cta-header">
          <h2>💝 开始您的心理健康之旅</h2>
          <p>加入数千名正在改善心理健康的用户，体验AI助手的专业支持</p>
        </div>
        <div class="cta-features">
          <div class="cta-feature">
            <span class="cta-check">✓</span>
            <span>免费试用7天高级功能</span>
          </div>
          <div class="cta-feature">
            <span class="cta-check">✓</span>
            <span>无需信用卡即可注册</span>
          </div>
          <div class="cta-feature">
            <span class="cta-check">✓</span>
            <span>随时取消，无隐藏费用</span>
          </div>
        </div>
        <router-link to="/auth/register" class="btn btn-large btn-primary"
          >🚀 立即免费开始</router-link
        >
      </div>
    </section>

    <!-- ========== 页脚 ========== -->
    <footer class="footer">
      <div class="footer-content">
        <div class="footer-section">
          <h4>💭 心理健康AI助手</h4>
          <p>专业的心理健康管理平台，让每个人都能获得关爱和支持</p>
          <div class="social-links">
            <a href="#" title="微信">💬</a>
            <a href="#" title="微博">📱</a>
            <a href="#" title="抖音">🎵</a>
          </div>
        </div>
        <div class="footer-section">
          <h4>产品</h4>
          <ul>
            <li><router-link to="/">首页</router-link></li>
            <li><a href="#features">功能特性</a></li>
            <li><router-link to="/auth/register">开始免费试用</router-link></li>
          </ul>
        </div>
        <div class="footer-section">
          <h4>快速链接</h4>
          <ul>
            <li><router-link to="/auth/login">登录</router-link></li>
            <li><router-link to="/auth/register">注册</router-link></li>
            <li><a href="#">隐私政策</a></li>
          </ul>
        </div>
        <div class="footer-section">
          <h4>联系我们</h4>
          <ul>
            <li>📧 邮箱: support@mindhealth-ai.com</li>
            <li>📞 电话: 400-123-4567</li>
            <li>⏰ 工作时间: 9:00-21:00</li>
          </ul>
        </div>
      </div>
      <div class="footer-bottom">
        <p>
          &copy; 2024 心理健康AI助手. 保留所有权利. | <a href="#">用户协议</a> |
          <a href="#">隐私政策</a>
        </p>
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
  gap: 12px;
  font-size: 20px;
  font-weight: 700;
  cursor: pointer;
  transition: transform 0.3s ease;
  text-decoration: none;
}

.logo-group:hover {
  transform: scale(1.05);
}

.logo-emoji {
  font-size: 32px;
  animation: bounce 2s ease-in-out infinite;
}

.logo-info {
  display: flex;
  flex-direction: column;
}

.logo-text {
  color: #667eea;
  font-size: 16px;
  font-weight: 700;
}

.logo-tagline {
  color: #999;
  font-size: 11px;
  font-weight: 400;
  letter-spacing: 1px;
}

@keyframes bounce {
  0%,
  100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-4px);
  }
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

.hero-badge {
  display: inline-block;
  background: rgba(102, 126, 234, 0.1);
  border: 1px solid #667eea;
  padding: 8px 16px;
  border-radius: 20px;
  font-size: 13px;
  color: #667eea;
  margin-bottom: 16px;
  font-weight: 500;
}

.hero-title {
  font-size: 48px;
  font-weight: 700;
  line-height: 1.2;
  margin-bottom: 16px;
  color: #1f2a44;
}

.hero-title .highlight {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.hero-subtitle {
  font-size: 18px;
  color: #4f5f79;
  margin-bottom: 32px;
  line-height: 1.6;
}

.hero-stats {
  display: flex;
  gap: 32px;
  margin-top: 24px;
}

.hero-stat {
  display: flex;
  flex-direction: column;
}

.hero-stat-value {
  font-size: 24px;
  font-weight: 700;
  color: #667eea;
}

.hero-stat-label {
  font-size: 12px;
  color: #999;
  margin-top: 4px;
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

.floating-card {
  position: absolute;
  background: white;
  padding: 16px 20px;
  border-radius: 12px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 14px;
  font-weight: 600;
  color: #1f2a44;
  animation: float 6s ease-in-out infinite;
}

.card-emoji {
  font-size: 24px;
}

.card-1 {
  top: 60px;
  left: 40px;
  animation-delay: 0s;
}

.card-2 {
  top: 200px;
  right: 80px;
  animation-delay: 1s;
  animation-direction: reverse;
}

.card-3 {
  bottom: 80px;
  left: 120px;
  animation-delay: 2s;
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
  position: relative;
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
  margin-bottom: 16px;
}

.feature-badge {
  display: inline-block;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 11px;
  font-weight: 600;
}

/* ========== 数据统计区域 ========== */
.stats-section {
  padding: 80px 24px;
  background: white;
}

.stats {
  padding: 60px 24px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 40px;
  text-align: center;
  max-width: 1200px;
  margin: 0 auto;
  border-radius: 16px;
  box-shadow: 0 20px 60px rgba(102, 126, 234, 0.2);
}

.stat-item {
  color: white;
}

.stat-number {
  font-size: 40px;
  font-weight: 700;
  margin-bottom: 8px;
  animation: fadeInScale 0.8s ease forwards;
}

.stat-label {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 8px;
  opacity: 0.95;
}

.stat-desc {
  font-size: 13px;
  opacity: 0.8;
}

@keyframes fadeInScale {
  from {
    opacity: 0;
    transform: scale(0.8);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

/* ========== 行动召唤区域 ========== */
.cta {
  padding: 80px 24px;
  text-align: center;
  background: linear-gradient(
    135deg,
    rgba(102, 126, 234, 0.05) 0%,
    rgba(118, 75, 162, 0.05) 100%
  );
}

.cta-content {
  max-width: 600px;
  margin: 0 auto;
}

.cta-header h2 {
  font-size: 36px;
  font-weight: 700;
  margin-bottom: 12px;
  color: #1f2a44;
}

.cta-header p {
  font-size: 16px;
  color: #4f5f79;
  margin-bottom: 32px;
  line-height: 1.6;
}

.cta-features {
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin-bottom: 32px;
}

.cta-feature {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  font-size: 15px;
  color: #1f2a44;
}

.cta-check {
  color: #85ce61;
  font-weight: 700;
  font-size: 18px;
}

/* ========== 页脚 ========== */
.footer {
  background: linear-gradient(135deg, #1f2a44 0%, #2d3f5a 100%);
  color: white;
  padding: 60px 24px 24px;
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
  margin-bottom: 16px;
  color: white;
}

.footer-section p,
.footer-section ul {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.7);
  list-style: none;
  line-height: 1.8;
}

.footer-section p {
  margin-bottom: 16px;
}

.footer-section ul li {
  margin-bottom: 10px;
}

.footer-section a {
  color: rgba(255, 255, 255, 0.7);
  text-decoration: none;
  transition: all 0.3s ease;
}

.footer-section a:hover {
  color: #667eea;
  text-decoration: underline;
}

.social-links {
  display: flex;
  gap: 12px;
  margin-top: 12px;
}

.social-links a {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 32px;
  height: 32px;
  background: rgba(102, 126, 234, 0.2);
  border-radius: 50%;
  font-size: 16px;
  transition: all 0.3s ease;
}

.social-links a:hover {
  background: #667eea;
  transform: translateY(-3px);
}

.footer-bottom {
  max-width: 1200px;
  margin: 0 auto;
  padding-top: 24px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  text-align: center;
  font-size: 13px;
  color: rgba(255, 255, 255, 0.5);
}

.footer-bottom a {
  color: rgba(255, 255, 255, 0.5);
  text-decoration: none;
  transition: color 0.3s ease;
  margin: 0 4px;
}

.footer-bottom a:hover {
  color: #667eea;
}

/* ========== 响应式设计 ========== */
@media (max-width: 768px) {
  .nav-container {
    padding: 0 16px;
  }

  .logo-text {
    font-size: 14px;
  }

  .logo-tagline {
    display: none;
  }

  .hero {
    grid-template-columns: 1fr;
    padding: 60px 16px;
    gap: 40px;
  }

  .hero-badge {
    font-size: 12px;
  }

  .hero-title {
    font-size: 36px;
  }

  .hero-subtitle {
    font-size: 16px;
  }

  .hero-buttons {
    flex-direction: column;
  }

  .hero-stats {
    gap: 24px;
  }

  .floating-card {
    display: none;
  }

  .section-header h2 {
    font-size: 28px;
  }

  .features-grid {
    grid-template-columns: 1fr;
  }

  .stats {
    grid-template-columns: 1fr 1fr;
    gap: 20px;
    padding: 40px 16px;
  }

  .stat-number {
    font-size: 32px;
  }

  .cta-header h2 {
    font-size: 28px;
  }

  .cta-features {
    gap: 10px;
  }

  .footer-content {
    grid-template-columns: 1fr;
    gap: 32px;
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

  .logo-group {
    gap: 8px;
  }

  .logo-emoji {
    font-size: 24px;
  }

  .hero {
    padding: 40px 12px;
    margin-top: 64px;
  }

  .hero-title {
    font-size: 24px;
  }

  .hero-subtitle {
    font-size: 13px;
    margin-bottom: 20px;
  }

  .hero-badge {
    font-size: 11px;
    padding: 6px 12px;
  }

  .hero-stats {
    flex-direction: column;
    gap: 16px;
    margin-top: 16px;
  }

  .hero-stat {
    align-items: flex-start;
  }

  .btn {
    padding: 10px 24px;
    font-size: 14px;
  }

  .btn-large {
    padding: 12px 32px;
    font-size: 15px;
    display: block;
    width: 100%;
  }

  .stats {
    grid-template-columns: 1fr;
    padding: 30px 12px;
  }

  .stat-number {
    font-size: 28px;
  }

  .stat-label {
    font-size: 14px;
  }

  .cta {
    padding: 40px 12px;
  }

  .cta-header h2 {
    font-size: 22px;
  }

  .cta-header p {
    font-size: 14px;
  }

  .cta-feature {
    font-size: 14px;
  }

  .features {
    padding: 40px 12px;
  }

  .feature-card {
    padding: 20px 16px;
  }

  .feature-icon {
    font-size: 36px;
  }

  .section-header h2 {
    font-size: 24px;
  }

  .footer {
    padding: 40px 12px 12px;
  }

  .footer-content {
    gap: 24px;
  }

  .footer-section h4 {
    font-size: 14px;
  }

  .footer-bottom {
    font-size: 12px;
  }
}
</style>
