<template>
  <div class="dashboard-page">
    <PageHead title="数据分析" />

    <div class="dashboard-content" v-loading="loading">
      <el-row :gutter="16" class="overview-row">
        <el-col
          :xs="24"
          :sm="12"
          :md="12"
          :lg="6"
          v-for="item in cardList"
          :key="item.key"
        >
          <el-card shadow="hover" class="overview-card">
            <div class="card-inner">
              <div class="card-icon" :class="`card-icon-${item.key}`">
                <el-icon :size="22">
                  <component :is="item.icon" />
                </el-icon>
              </div>
              <div class="card-text">
                <p class="card-title">{{ item.title }}</p>
                <p class="card-value">{{ item.value }}</p>
                <p class="card-desc">{{ item.desc }}</p>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <el-row :gutter="16" class="chart-row">
        <el-col :xs="24" :lg="12">
          <el-card class="chart-card">
            <template #header>
              <span>情绪趋势分析</span>
            </template>
            <div ref="emotionChartRef" class="chart-container"></div>
          </el-card>
        </el-col>
        <el-col :xs="24" :lg="12">
          <el-card class="chart-card">
            <template #header>
              <span>咨询会话统计</span>
            </template>
            <div ref="sessionChartRef" class="chart-container"></div>
          </el-card>
        </el-col>
      </el-row>

      <el-row :gutter="16" class="chart-row">
        <el-col :xs="24">
          <el-card class="chart-card large-card">
            <template #header>
              <span>用户活跃度趋势</span>
            </template>
            <div ref="activityChartRef" class="chart-container-large"></div>
          </el-card>
        </el-col>
      </el-row>

      <el-row :gutter="16" class="chart-row">
        <el-col :xs="24">
          <el-card class="chart-card">
            <template #header>
              <span>🗓️ 咨询统计数据表</span>
            </template>
            <el-table
              :data="dailyTrendData"
              border
              stripe
              size="small"
              style="width: 100%"
              max-height="400"
            >
              <el-table-column prop="date" label="日期" width="150" />
              <el-table-column
                prop="sessionCount"
                label="咨询会话数"
                width="120"
                align="center"
              />
              <el-table-column
                prop="userCount"
                label="用户数"
                width="120"
                align="center"
              />
              <el-table-column
                prop="avgDuration"
                label="平均时长(分钟)"
                width="150"
                align="center"
              />
              <el-table-column label="咨询密度" width="120" align="center">
                <template #default="scope">
                  <el-progress
                    :percentage="calculateDensity(scope.row.sessionCount)"
                    :color="
                      getDensityColor(calculateDensity(scope.row.sessionCount))
                    "
                  />
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, onUnmounted, ref, nextTick } from "vue";
import { ElMessage } from "element-plus";
import { User, ChatDotRound, Message, Sunny } from "@element-plus/icons-vue";
import * as echarts from "echarts";
import PageHead from "@/components/PageHead.vue";
import { getDataAnalyticsOverview } from "@/api/admin";

const loading = ref(false);
const overview = ref({});
const emotionChartRef = ref(null);
const sessionChartRef = ref(null);
const activityChartRef = ref(null);

let emotionChart = null;
let sessionChart = null;
let activityChart = null;

const pickFirst = (obj, keys = []) => {
  for (const key of keys) {
    const value = key
      .split(".")
      .reduce((acc, part) => (acc == null ? undefined : acc[part]), obj);
    if (value !== undefined && value !== null && value !== "") {
      return value;
    }
  }
  return "";
};

const pickNumber = (obj, keys = [], fallback = 0) => {
  const value = Number(pickFirst(obj, keys));
  return Number.isFinite(value) ? value : fallback;
};

const pickArray = (obj, keys = []) => {
  const value = pickFirst(obj, keys);
  return Array.isArray(value) ? value : [];
};

const formatRatioPercentage = (value) => {
  const ratio = Number(value || 0);
  if (!Number.isFinite(ratio)) {
    return 0;
  }
  return ratio <= 1 ? Number((ratio * 100).toFixed(2)) : ratio;
};

const consultationAvgDuration = computed(() =>
  pickNumber(overview.value, [
    "consultationStats.avgDurationMinutes",
    "consultationStats.avg_duration_minutes",
    "avgDurationMinutes",
    "avg_duration_minutes",
    "avgDuration",
    "avg_duration",
  ]),
);

const dailyTrendData = computed(() => {
  const dailyTrend = pickArray(overview.value, [
    "dailyTrend",
    "daily_trend",
    "consultationStats.dailyTrend",
    "consultationStats.daily_trend",
    "consultationTrend",
    "consultation_trend",
  ]);
  const avgDuration = consultationAvgDuration.value;

  return dailyTrend.map((item) => ({
    date: pickFirst(item, ["date", "statDate", "stat_date", "day"]) || "-",
    sessionCount: pickNumber(item, [
      "sessionCount",
      "session_count",
      "consultationCount",
      "consultation_count",
      "sessions",
    ]),
    userCount: pickNumber(item, [
      "userCount",
      "user_count",
      "activeUserCount",
      "active_user_count",
      "users",
    ]),
    avgDuration: pickNumber(
      item,
      [
        "avgDuration",
        "avg_duration",
        "avgDurationMinutes",
        "avg_duration_minutes",
      ],
      avgDuration,
    ),
  }));
});

const calculateDensity = (sessionCount) => {
  const allSessionCounts = (dailyTrendData.value || [])
    .map((item) => Number(item.sessionCount || 0))
    .filter((count) => Number.isFinite(count));

  const maxSessions = Math.max(
    1,
    ...(allSessionCounts.length ? allSessionCounts : [1]),
  );

  return Math.min(
    100,
    Math.round((Number(sessionCount || 0) / maxSessions) * 100),
  );
};

const getDensityColor = (percentage) => {
  if (percentage >= 80) return "#f56c6c";
  if (percentage >= 60) return "#ffc03b";
  if (percentage >= 40) return "#85ce61";
  return "#909399";
};

const formatScore = (value) => {
  const score = Number(value || 0);
  if (Number.isNaN(score)) {
    return "0/10";
  }
  return `${score}/10`;
};

const cardList = computed(() => {
  const source = overview.value || {};

  const userCount = pickNumber(source, [
    "systemOverview.totalUsers",
    "systemOverview.total_users",
    "totalUserCount",
    "total_user_count",
    "userCount",
    "user_count",
    "totalUsers",
    "total_users",
  ]);

  const diaryCount = pickNumber(source, [
    "systemOverview.totalDiaries",
    "systemOverview.total_diaries",
    "diaryCount",
    "diary_count",
    "emotionDiaryCount",
    "emotion_diary_count",
    "totalDiaryCount",
    "total_diary_count",
  ]);

  const sessionCount = pickNumber(source, [
    "systemOverview.totalSessions",
    "systemOverview.total_sessions",
    "consultationStats.totalSessions",
    "consultationStats.total_sessions",
    "sessionCount",
    "session_count",
    "consultationSessionCount",
    "consultation_session_count",
  ]);

  const avgMoodScore = pickFirst(source, [
    "systemOverview.avgMoodScore",
    "systemOverview.avg_mood_score",
    "avgMoodScore",
    "avg_mood_score",
    "averageMoodScore",
    "moodAverage",
  ]);

  const activeUsers = pickNumber(source, [
    "systemOverview.activeUsers",
    "systemOverview.active_users",
    "activeUserCount",
    "active_user_count",
  ]);
  const newUserCount = pickNumber(source, [
    "systemOverview.newUsers",
    "systemOverview.new_users",
    "newUserCount",
    "new_user_count",
  ]);

  return [
    {
      key: "user",
      title: "总用户数",
      value: userCount,
      desc: `活跃用户: ${activeUsers}`,
      icon: User,
    },
    {
      key: "diary",
      title: "情绪日志",
      value: diaryCount,
      desc: `新增用户: ${newUserCount}`,
      icon: ChatDotRound,
    },
    {
      key: "session",
      title: "咨询会话",
      value: sessionCount,
      desc: `平均时长: ${consultationAvgDuration.value}分钟`,
      icon: Message,
    },
    {
      key: "mood",
      title: "平均情绪",
      value: formatScore(avgMoodScore),
      desc: "情绪健康指数",
      icon: Sunny,
    },
  ];
});

const initEmotionChart = () => {
  if (!emotionChartRef.value) return;

  const emotionTrend = pickArray(overview.value, [
    "emotionTrend",
    "emotion_trend",
    "moodTrend",
    "mood_trend",
  ]);
  if (!Array.isArray(emotionTrend) || emotionTrend.length === 0) {
    return;
  }

  const dates = emotionTrend.map(
    (item) => pickFirst(item, ["date", "statDate", "stat_date", "day"]) || "",
  );
  const scores = emotionTrend.map((item) =>
    pickNumber(item, [
      "avgMoodScore",
      "avg_mood_score",
      "moodScore",
      "mood_score",
    ]),
  );
  const positiveRatio = emotionTrend.map((item) =>
    formatRatioPercentage(
      pickFirst(item, [
        "positiveRatio",
        "positive_ratio",
        "positiveRate",
        "positive_rate",
      ]),
    ),
  );

  if (!emotionChart) {
    emotionChart = echarts.init(emotionChartRef.value);
  }

  const option = {
    tooltip: {
      trigger: "axis",
      backgroundColor: "rgba(50, 50, 50, 0.8)",
      borderColor: "#333",
      textStyle: { color: "#fff" },
    },
    grid: {
      left: "3%",
      right: "3%",
      bottom: "10%",
      top: "10%",
      containLabel: true,
    },
    xAxis: {
      type: "category",
      data: dates,
      axisLabel: { fontSize: 12 },
    },
    yAxis: [
      {
        type: "value",
        name: "平均评分",
        position: "left",
        axisLabel: { formatter: "{value}" },
      },
      {
        type: "value",
        name: "正向比例",
        position: "right",
        axisLabel: { formatter: "{value}%" },
      },
    ],
    series: [
      {
        name: "平均评分",
        type: "line",
        data: scores,
        smooth: true,
        yAxisIndex: 0,
        itemStyle: { color: "#5568e8" },
        areaStyle: { color: "rgba(85, 104, 232, 0.2)" },
      },
      {
        name: "正向比例",
        type: "line",
        data: positiveRatio,
        smooth: true,
        yAxisIndex: 1,
        itemStyle: { color: "#63d9a2" },
      },
    ],
  };

  emotionChart.setOption(option);
};

const initSessionChart = () => {
  if (!sessionChartRef.value) return;

  const dailyTrend = dailyTrendData.value || [];
  if (!Array.isArray(dailyTrend) || dailyTrend.length === 0) {
    return;
  }

  const dates = dailyTrend.map((item) => item.date || "");
  const sessionCounts = dailyTrend.map((item) =>
    pickNumber(item, ["sessionCount"]),
  );
  const userCounts = dailyTrend.map((item) => pickNumber(item, ["userCount"]));

  if (!sessionChart) {
    sessionChart = echarts.init(sessionChartRef.value);
  }

  const option = {
    tooltip: {
      trigger: "axis",
      backgroundColor: "rgba(50, 50, 50, 0.8)",
      borderColor: "#333",
      textStyle: { color: "#fff" },
    },
    grid: {
      left: "3%",
      right: "3%",
      bottom: "10%",
      top: "10%",
      containLabel: true,
    },
    xAxis: {
      type: "category",
      data: dates,
      axisLabel: { fontSize: 12 },
    },
    yAxis: {
      type: "value",
      name: "数量",
    },
    series: [
      {
        name: "会话数",
        type: "bar",
        data: sessionCounts,
        itemStyle: { color: "#62b8ff" },
      },
      {
        name: "用户数",
        type: "bar",
        data: userCounts,
        itemStyle: { color: "#63d9a2" },
      },
    ],
  };

  sessionChart.setOption(option);
};

const initActivityChart = () => {
  if (!activityChartRef.value) return;

  const userActivity = pickArray(overview.value, [
    "userActivity",
    "user_activity",
    "activityTrend",
    "activity_trend",
  ]);
  if (!Array.isArray(userActivity) || userActivity.length === 0) {
    return;
  }

  const dates = userActivity.map(
    (item) => pickFirst(item, ["date", "statDate", "stat_date", "day"]) || "",
  );
  const activeUsers = userActivity.map((item) =>
    pickNumber(item, [
      "activeUsers",
      "active_users",
      "activeUserCount",
      "active_user_count",
    ]),
  );
  const newUsers = userActivity.map((item) =>
    pickNumber(item, [
      "newUsers",
      "new_users",
      "newUserCount",
      "new_user_count",
    ]),
  );
  const diaryUsers = userActivity.map((item) =>
    pickNumber(item, [
      "diaryUsers",
      "diary_users",
      "journalUsers",
      "journal_users",
    ]),
  );
  const consultationUsers = userActivity.map((item) =>
    pickNumber(item, [
      "consultationUsers",
      "consultation_users",
      "sessionUsers",
      "session_users",
    ]),
  );

  if (!activityChart) {
    activityChart = echarts.init(activityChartRef.value);
  }

  const option = {
    tooltip: {
      trigger: "axis",
      backgroundColor: "rgba(50, 50, 50, 0.8)",
      borderColor: "#333",
      textStyle: { color: "#fff" },
    },
    legend: {
      top: "0%",
      left: "center",
    },
    grid: {
      left: "3%",
      right: "3%",
      bottom: "10%",
      top: "12%",
      containLabel: true,
    },
    xAxis: {
      type: "category",
      data: dates,
      axisLabel: { fontSize: 12 },
    },
    yAxis: {
      type: "value",
      name: "用户数",
    },
    series: [
      {
        name: "活跃用户",
        type: "line",
        data: activeUsers,
        smooth: true,
        itemStyle: { color: "#5568e8" },
        areaStyle: { color: "rgba(85, 104, 232, 0.2)" },
      },
      {
        name: "新用户",
        type: "line",
        data: newUsers,
        smooth: true,
        itemStyle: { color: "#ff89a6" },
        areaStyle: { color: "rgba(255, 137, 166, 0.2)" },
      },
      {
        name: "日记用户",
        type: "line",
        data: diaryUsers,
        smooth: true,
        itemStyle: { color: "#63d9a2" },
        areaStyle: { color: "rgba(99, 217, 162, 0.2)" },
      },
      {
        name: "咨询用户",
        type: "line",
        data: consultationUsers,
        smooth: true,
        itemStyle: { color: "#fac858" },
        areaStyle: { color: "rgba(250, 200, 88, 0.2)" },
      },
    ],
  };

  activityChart.setOption(option);
};

const fetchOverview = async () => {
  loading.value = true;
  try {
    const res = await getDataAnalyticsOverview();
    const payload = res?.data || {};
    overview.value =
      pickFirst(payload, ["data", "result", "overview", "analyticsOverview"]) ||
      payload ||
      {};

    // 绘制图表
    await nextTick();
    initEmotionChart();
    initSessionChart();
    initActivityChart();
  } catch (error) {
    overview.value = {};
    ElMessage.error(error?.message || "获取数据分析总览失败");
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchOverview();

  // 窗口大小改变时重新渲染图表
  const handleResize = () => {
    emotionChart?.resize();
    sessionChart?.resize();
    activityChart?.resize();
  };
  window.addEventListener("resize", handleResize);

  // 在组件卸载时清理事件监听
  onUnmounted(() => {
    window.removeEventListener("resize", handleResize);
  });
});
</script>

<style scoped>
.dashboard-page {
  background: #fff;
  border-radius: 8px;
}

.dashboard-content {
  padding: 20px;
}

.overview-row,
.chart-row {
  margin-bottom: 16px;
}

.overview-card {
  border-radius: 10px;
}

.card-inner {
  display: flex;
  align-items: center;
  gap: 14px;
}

.card-icon {
  width: 46px;
  height: 46px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.card-icon-user {
  background: linear-gradient(135deg, #6f80ff, #5568e8);
}

.card-icon-diary {
  background: linear-gradient(135deg, #ff89a6, #f05a8a);
}

.card-icon-session {
  background: linear-gradient(135deg, #62b8ff, #469eea);
}

.card-icon-mood {
  background: linear-gradient(135deg, #63d9a2, #35b97b);
}

.card-text {
  min-width: 0;
}

.card-title {
  margin: 0;
  color: #8a94a6;
  font-size: 13px;
}

.card-value {
  margin: 6px 0 4px;
  font-size: 24px;
  line-height: 1;
  font-weight: 700;
  color: #1f2a44;
}

.card-desc {
  margin: 0;
  color: #9aa5b5;
  font-size: 12px;
}

.chart-card {
  border-radius: 10px;
}

.chart-container {
  width: 100%;
  height: 280px;
}

.chart-container-large {
  width: 100%;
  height: 340px;
}

.large-card .chart-placeholder {
  min-height: 300px;
}

@media (max-width: 768px) {
  .dashboard-content {
    padding: 12px;
  }

  .card-value {
    font-size: 20px;
  }

  .chart-container {
    height: 240px;
  }

  .chart-container-large {
    height: 280px;
  }
}
</style>
