# pdf-service

基于 Spring Boot 和 Playwright 的 PDF 服务

## 简介
本镜像集成了 Spring Boot 21 和 Playwright，支持网页截图、PDF 生成等自动化浏览器操作，适用于自动化测试、网页转 PDF 等场景。

## 使用方法
### 1. 拉取镜像
```
docker pull lihongjie0209/pdf-service:latest
```

### 2. 运行容器
```
docker run -d -p 8080:8080 lihongjie0209/pdf-service:latest
```

### 3. 访问服务
服务启动后，访问 http://localhost:8080 即可。

## 环境变量
如需自定义端口或其他 Spring Boot 配置，可通过环境变量覆盖：
```
docker run -d -p 8080:8080 -e SERVER_PORT=8080 lihongjie0209/pdf-service:latest
```

## Playwright 支持
本镜像已内置 Playwright 及其浏览器依赖，无需额外安装。

## 构建说明
本镜像采用多阶段构建，利用 Spring Boot 分层 jar，极大减少镜像体积并提升构建效率。

## 典型用例
- 网页转 PDF
- 自动化网页截图
- 自动化浏览器测试

## 反馈与支持
如有问题请提交 issue 或联系维护者。

