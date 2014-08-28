weixin-java-tools
===========

微信java开发工具集，本项目主要分为两大块：微信消息路由器、微信Java API

特性列表：

1. 不基于Servlet、和其他MVC框架，仅作为工具使用，提供更多的灵活性
2. 详尽的单元测试代码，可以拿来当example用
3. 详尽的javadoc
4. access token过期自动刷新的功能
5. 微信服务端繁忙自动重试的功能
6. 提供微信错误信息的异常处理机制

# Quickstart

## 打包

如果你可以跳过单元测试直接打包：
```bash
mvn clean install -Dmaven.test.skip=true
```

如果你需要执行本项目的单元测试代码，那么你需要将 ``src/test/resources/test-config.sample.xml`` 改成 ``test-config.xml`` ，并设置appId, secret, accessToken(可选), openId，然后执行：

```bash
mvn clean install
```

在你的maven项目中添加：
```xml
<dependency>
  <groupId>chanjarster.weixin</groupId>
  <artifactId>weixin-java-tools</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## Hello World
```java
WxConfigStorage config = new WxInMemoryConfigStorage();
config.setAppId(...); // 设置微信公众号的appid
config.setSecret(...); // 设置微信公众号的app secret
config.setToken(...); // 设置微信公众号的token

WxServiceImpl wxService = new WxServiceImpl();
wxService.setWxConfigStorage(config);

// 用户的openid在下面地址获得 
// https://mp.weixin.qq.com/debug/cgi-bin/apiinfo?t=index&type=用户管理&form=获取关注者列表接口%20/user/get 
String openid = ...; 
WxCustomMessage message = WxCustomMessage.TEXT().toUser(openid).content("Hello World").build();
wxService.customMessageSend(message);
```
