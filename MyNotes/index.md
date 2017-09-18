[TOC]

## 项目构建步骤

### 一、构建整体框架

相关笔记

[整体架构.md](架构/整体架构.md)

* 创建项目和各个Model
* 构建各个Model的依赖关系
* 删除多余依赖

### 二、版本控制

相关笔记

[ignore文件.md](版本控制/git/ignore文件.md)

* 配置ignore文件（配置项目根目录下的ignore文件即可）
* 进行代码托管


### 三、初始化架构

#### 编写核心Model（latte-core）

##### latte类

是**核心Model**统一对外提供方法调用的工具类，即外部使用核心Model的功能都要通过`latte`类。

##### Configurator类

使用hashmap管理各种配置项，便于在自定义的`Application`类中初始化整个项目。

#### 字体图标库的集成和封装










