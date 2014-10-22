weixin-java-cp
===========

微信企业号Java SDK开发工具集，本项目主要分为两大块：微信消息路由器、微信Java API

详细文档请看 [wiki](https://github.com/chanjarster/weixin-java-tools/wiki)

# Quickstart

在你的maven项目中添加：
```xml
<dependency>
  <groupId>me.chanjar</groupId>
  <artifactId>weixin-java-cp-tools</artifactId>
  <version>1.0.3</version>
</dependency>
```

如果要使用``*-SNAPSHOT``版，则需要在你的``pom.xml``中添加这段：

```xml
<repositories>
  <repository>
      <snapshots />
      <id>sonatype snapshots</id>
      <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
  </repository>
</repositories>
```

## Hello World
```java
WxCpInMemoryConfigStorage config = new WxCpInMemoryConfigStorage();
config.getCorpId(...);      // 设置微信企业号的appid
config.getCorpSecret(...);  // 设置微信企业号的app corpSecret
config.setAgentId(...);     // 设置微信企业号应用ID
config.setToken(...);       // 设置微信企业号应用的token
config.setAesKey(...);      // 设置微信企业号应用的EncodingAESKey

WxCpServiceImpl wxCpService = new WxCpServiceImpl();
wxCpService.setWxCpConfigStorage(config);

String userId = ...; 
WxCpMessage message = WxCpMessage.TEXT().agentId(...).toUser(userId).content("Hello World").build();
wxService.messageSend(message);
```
