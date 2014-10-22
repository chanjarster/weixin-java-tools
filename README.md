weixin-java-cp
===========

微信公众号、企业号Java SDK。

从``1.0.3``开始，本项目拆分成3个部分：

1. weixin-java-common，公共lib
2. weixin-java-cp，企业号SDK
3. weixin-java-mp，公众号（订阅号、服务号）SDK

详细文档请看 [wiki](https://github.com/chanjarster/weixin-java-tools/wiki)

# Quick Start

如果要开发公众号（订阅号、服务号）应用，在你的maven项目中添加：

```xml
<dependency>
  <groupId>me.chanjar</groupId>
  <artifactId>weixin-java-mp</artifactId>
  <version>1.0.3</version>
</dependency>
```

如果要开发企业号应用，在你的maven项目中添加：

```xml
<dependency>
  <groupId>me.chanjar</groupId>
  <artifactId>weixin-java-cp</artifactId>
  <version>1.0.3</version>
</dependency>
```
