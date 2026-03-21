# ========= 构建阶段 =========
FROM node:20-alpine AS build
WORKDIR /app

# 先安装依赖
COPY package*.json ./
RUN npm config set registry https://registry.npmmirror.com \
 && npm install

# 拷贝源码并构建
COPY . .
RUN npm run build

# ========= 运行阶段 =========
FROM nginx:alpine

RUN rm -rf /usr/share/nginx/html/*

COPY --from=build /app/dist /usr/share/nginx/html

COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80