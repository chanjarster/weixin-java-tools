weixin-java-tools

[![Build Status](https://travis-ci.org/chanjarster/weixin-java-tools.svg?branch=develop)](https://travis-ci.org/chanjarster/weixin-java-tools)
![Maven Central](https://img.shields.io/maven-central/v/me.chanjar/weixin-java-parent.svg)

===========

微信公众号、企业号Java SDK。

详细文档请看 [wiki](https://github.com/chanjarster/weixin-java-tools/wiki)。

## Quick Start

如果要开发公众号（订阅号、服务号）应用，在你的maven项目中添加：

```xml
<dependency>
  <groupId>me.chanjar</groupId>
  <artifactId>weixin-java-mp</artifactId>
  <version>1.1.8</version>
</dependency>
```

如果要开发企业号应用，在你的maven项目中添加：

```xml
<dependency>
  <groupId>me.chanjar</groupId>
  <artifactId>weixin-java-cp</artifactId>
  <version>1.1.8</version>
</dependency>
```

## SNAPSHOT版

本项目的BUG修复和新特性一般会先发布在*-SNAPSHOT版里供大家预览，如果要使用*-SNAPSHOT版，则需要在你的pom.xml中添加这段：

```xml
<repositories>
  <repository>
      <snapshots />
      <id>sonatype snapshots</id>
      <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
  </repository>
</repositories>
```

## 升级指南

* [1.0.3升级指南](https://github.com/chanjarster/weixin-java-tools/wiki/1_0_3升级指南)
* [1.1.0升级指南](https://github.com/chanjarster/weixin-java-tools/wiki/1_1_0升级指南)
* [1.1.1升级指南](https://github.com/chanjarster/weixin-java-tools/wiki/1_1_1升级指南)
