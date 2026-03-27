# 蔬菜销售系统 (Vegetable Sales System)

## 项目简介

蔬菜销售系统是一个基于Spring Boot的现代化电商平台，集成了AI智能推荐功能。系统采用前后端分离的架构设计，为用户提供从商品浏览、购物车管理、订单处理到智能推荐的完整购物体验。

### 主要功能
- 用户注册、登录和个人信息管理
- 商品浏览、搜索和分类筛选
- 购物车管理和订单处理
- 余额充值和支付功能
- AI智能推荐和对话服务
- 订单历史查询和管理

### 技术特色
- 采用分层架构，代码结构清晰
- 集成AI助手，提供个性化推荐
- 支持多种支付方式
- 响应式设计，适配多种设备

## 技术栈

### 后端技术
- **Java 8+** - 主要开发语言
- **Spring Boot 2.7.0** - 应用框架
- **Spring MVC** - Web层框架
- **Spring Data JPA** - 数据访问层
- **MySQL 8.0** - 主数据库
- **Redis** - 缓存数据库
- **LangChain4j** - AI集成框架
- **OpenAI API** - 智能推荐服务

### 前端技术
- **HTML5/CSS3/JavaScript** - 基础前端技术
- **Thymeleaf** - 模板引擎
- **Bootstrap** - UI框架
- **Ajax** - 异步数据交互

### 开发工具
- **IntelliJ IDEA** - 集成开发环境
- **Maven** - 项目构建工具
- **Git** - 版本控制
- **Postman** - API测试工具
- **JMeter** - 性能测试工具

## 项目依赖配置

### Maven依赖 (pom.xml)

项目使用Maven进行依赖管理，主要依赖包括：

#### Spring Boot相关依赖
```xml
<!-- Spring Boot Web Starter -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Spring Boot Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Spring Boot Thymeleaf -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

<!-- Spring Boot Test -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

#### 数据库相关依赖
```xml
<!-- MySQL Connector -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.28</version>
</dependency>

<!-- Flyway Database Migration -->
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-mysql</artifactId>
</dependency>
```

#### AI集成依赖
```xml
<!-- LangChain4j Core -->
<dependency>
    <groupId>dev.langchain4j</groupId>
    <artifactId>langchain4j</artifactId>
    <version>0.27.1</version>
</dependency>

<!-- LangChain4j OpenAI Integration -->
<dependency>
    <groupId>dev.langchain4j</groupId>
    <artifactId>langchain4j-open-ai</artifactId>
    <version>0.27.1</version>
</dependency>
```

#### 开发工具依赖
```xml
<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

### 依赖说明
- **spring-boot-starter-web**: 提供Web开发基础，包括Spring MVC、内嵌Tomcat等
- **spring-boot-starter-data-jpa**: 提供JPA和Hibernate支持，简化数据库操作
- **spring-boot-starter-thymeleaf**: 模板引擎，用于服务端页面渲染
- **mysql-connector-java**: MySQL数据库驱动
- **flyway-core/flyway-mysql**: 数据库版本管理和迁移工具
- **langchain4j**: AI集成框架，支持与OpenAI等大模型交互
- **lombok**: 简化Java代码，自动生成getter/setter等方法
- **spring-boot-starter-test**: 测试框架支持

## 环境要求

### 系统要求
- **操作系统**: Windows 10/11, macOS, Linux
- **Java版本**: JDK 8 或更高版本
- **内存**: 至少 4GB RAM
- **磁盘空间**: 至少 2GB 可用空间

### 软件依赖
- **MySQL 8.0+** - 数据库服务器
- **Redis 6.0+** - 缓存服务器（可选）
- **Maven 3.6+** - 构建工具

## 基础设施搭建

### 1. Java环境配置

#### 安装JDK
1. 下载并安装Oracle JDK 8或OpenJDK 8+
2. 配置环境变量：
   ```bash
   # Windows
   JAVA_HOME=C:\Program Files\Java\jdk1.8.0_xxx
   PATH=%JAVA_HOME%\bin;%PATH%
   
   # macOS/Linux
   export JAVA_HOME=/usr/lib/jvm/java-8-openjdk
   export PATH=$JAVA_HOME/bin:$PATH
   ```
3. 验证安装：
   ```bash
   java -version
   javac -version
   ```

#### 安装Maven
1. 下载Apache Maven 3.6+
2. 解压到指定目录
3. 配置环境变量：
   ```bash
   # Windows
   MAVEN_HOME=C:\apache-maven-3.6.x
   PATH=%MAVEN_HOME%\bin;%PATH%
   
   # macOS/Linux
   export MAVEN_HOME=/opt/apache-maven-3.6.x
   export PATH=$MAVEN_HOME/bin:$PATH
   ```
4. 验证安装：
   ```bash
   mvn -version
   ```

### 2. 数据库环境配置

#### 安装MySQL
1. 下载并安装MySQL 8.0+
2. 启动MySQL服务：
   ```bash
   # Windows
   net start mysql80
   
   # macOS
   brew services start mysql
   
   # Linux
   sudo systemctl start mysql
   ```
3. 创建数据库和用户：
   ```sql
   CREATE DATABASE Vegetable_management_system;
   CREATE USER 'vegetable_user'@'localhost' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON Vegetable_management_system.* TO 'vegetable_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

#### 配置Redis（可选）
1. 下载并安装Redis 6.0+
2. 启动Redis服务：
   ```bash
   # Windows
   redis-server
   
   # macOS
   brew services start redis
   
   # Linux
   sudo systemctl start redis
   ```

### 3. 集成开发环境配置

#### 安装IntelliJ IDEA
1. 下载并安装IntelliJ IDEA（推荐使用Ultimate版本）
2. 配置JDK和Maven：
   - 打开 File → Project Structure
   - 在 Project Settings → Project 中设置Project SDK
   - 在 Project Settings → Modules 中设置Module SDK
   - 在 Build Tools → Maven 中配置Maven home directory

#### 配置Git
1. 安装Git
2. 配置用户信息：
   ```bash
   git config --global user.name "Your Name"
   git config --global user.email "your.email@example.com"
   ```

## 项目运行

### 1. 克隆项目
```bash
git clone <repository-url>
cd new_vegetable_system
```

### 2. 配置数据库连接
编辑 `src/main/resources/application.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/Vegetable_management_system?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: your_username
    password: your_password
```

### 3. 配置AI服务
在 `src/main/java/com/vegetable/AI/Chat.java` 中配置OpenAI API密钥：
```java
.apiKey("your_openai_api_key")
```

### 4. 构建和运行
```bash
# 清理并编译
mvn clean compile

# 运行项目
mvn spring-boot:run
```

### 5. 访问系统
打开浏览器访问：`http://localhost:8080`

## 项目结构

```
new_vegetable_system/
├── src/
│   ├── main/
│   │   ├── java/com/vegetable/
│   │   │   ├── controller/     # 控制器层
│   │   │   ├── service/        # 服务层
│   │   │   ├── repository/     # 数据访问层
│   │   │   ├── entity/         # 实体类
│   │   │   ├── dto/           # 数据传输对象
│   │   │   ├── config/        # 配置类
│   │   │   └── AI/            # AI相关功能
│   │   └── resources/
│   │       ├── templates/     # 前端页面
│   │       ├── static/        # 静态资源
│   │       └── application.yml # 配置文件
├── target/                    # 编译输出
├── pom.xml                   # Maven配置
└── README.md                 # 项目说明
```

## 测试

### API测试
使用Postman测试各个接口：
- 用户注册：`POST /user/api/register`
- 用户登录：`POST /user/api/login`
- 商品列表：`GET /api/products`
- 购物车操作：`POST /api/cart/add`
- 订单创建：`POST /api/orders/create`

### 性能测试
使用JMeter进行压力测试：
```bash
jmeter -n -t test-plan.jmx -l results.jtl
```

## 部署

### 开发环境
```bash
mvn spring-boot:run
```

### 生产环境
```bash
# 打包
mvn clean package

# 运行jar包
java -jar target/vegetable-management-system-1.0-SNAPSHOT.jar
```
