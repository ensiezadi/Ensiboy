FROM eclipse-temurin:23-jdk

# 设置工作目录
WORKDIR /app

COPY target/langchain4j-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 7777

ENTRYPOINT ["java", "-jar", "app.jar"]