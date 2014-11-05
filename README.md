weixin-java-tools
===========

微信公众号、企业号Java SDK。

从``1.0.3``开始，本项目拆分成3个部分：

1. weixin-java-common，公共lib
2. weixin-java-cp，企业号SDK
3. weixin-java-mp，公众号（订阅号、服务号）SDK

详细文档请看 [wiki](https://github.com/chanjarster/weixin-java-tools/wiki)。

## Quick Start

如果要开发公众号（订阅号、服务号）应用，在你的maven项目中添加：

```xml
<dependency>
  <groupId>me.chanjar</groupId>
  <artifactId>weixin-java-mp</artifactId>
  <version>1.0.5</version>
</dependency>
```

如果要开发企业号应用，在你的maven项目中添加：

```xml
<dependency>
  <groupId>me.chanjar</groupId>
  <artifactId>weixin-java-cp</artifactId>
  <version>1.0.5</version>
</dependency>
```

## 升级指南

原``1.0.0~1.0.2``版本用户无法平滑升级到``1.0.3``。需要做的是：

1. maven引用``weixin-java-mp``
2. 将原来``WxXXX``的类，改成``WxMpXXX``
3. ``WxConsts``, ``WxError``, ``WxMediaUploadResult``, ``WxAccessToken``, ``WxMenu``, ``WxErrorException``不要改
