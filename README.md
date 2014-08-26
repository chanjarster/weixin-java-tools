weixin-java-tools
===========

微信java开发工具集，本项目提供了两个主要特性：微信消息路由器、微信Java API

本项目的特性：

1. 不基于Servlet，提供更多的灵活性
2. 详尽的单元测试代码，可以拿来当example用
3. 详尽的javadoc
4. access token过期自动刷新的功能
5. 微信错误信息的异常处理机制

## 微信消息路由器

你可以使用``WxMessageRouter``来对微信推送过来的消息、事件进行路由，交给的``WxMessageHandler``处理。

使用方法：

```java
WxMessageRouter router = new WxMessageRouter();
router
  .rule()
      .msgType("MSG_TYPE")
      .event("EVENT")
      .eventKey("EVENT_KEY")
      .content("CONTENT")
      .interceptor(interceptor).handler(handler)
  .end()
  .rule()
      .msgType("MSG_TYPE")
      .handler(handler)
  .end()
;

// 将WxXmlMessage交给消息路由器
WxXmlMessage message = WxXmlMessage.fromXml(xml);
router.route(message);
```

1. 开发人员需实现自己的``WxMessageHandler``和``WxMessageInterceptor``
1. 配置路由规则时要按照从细到粗的原则，否则可能消息可能会被提前处理
2. 默认情况下消息只会被处理一次，除非使用 ``Rule.next()``
3. 规则的结束必须用``Rule.end()``或者``Rule.next()``，否则不会生效
4. 具体使用可以看源代码中的``WxMessageRouterTest``单元测试，或者查看Javadoc

## 微信Java API

使用``WxService``可以调用微信API。目前实现的功能有：

### 构造WxService

``WxService``依赖于``WxConfigStorage``，``WxConfigStorage``是微信客户端配置所存储的地方，本工具提供了默认的``WxInMemoryConfigStorage``，这是一个存储在内存中的实现，生产环境中需要开发人员提供自己的实现。

```java
WxConfigStorage config = new WxInMemoryConfigStorage();
config.setAppId(...);
config.setSecret(...);
config.setToken(...);
WxServiceImpl wxService = new WxServiceImpl();
wxService.setWxConfigStorage(config);
```

### 验证消息

```java
if(!wxService.checkSignature(timestamp, nonce, signature)) {
   // 验证失败
}
```

### 刷新access_token

```java
wxService.accessTokenRefresh();
```

刷新后的accessToken存在WxConfigStorage中。

### 多媒体文件

#### 上传多媒体文件
```java
InputStream inputStream = ...;
File file = ...;
WxMediaUploadResult res = wxService.mediaUpload(mediaType, fileType, inputStream);
// 或者
res = wxService.mediaUpload(mediaType, file);
res.getType();
res.getCreated_at();
res.getMedia_id();
res.getThumb_media_id();
```

#### 下载多媒体文件
```java
// 获得一个在系统临时目录的文件
File file = wxService.mediaDownload(media_id);
```

### 分组管理

#### 创建分组
```java
WxGroup res = wxService.groupCreate("测试分组1");
```

#### 获得分组列表
```java
List<WxGroup> groupList = wxService.groupGet();
```

#### 更新分组名
```java
WxGroup g = new WxGroup();
g.setId(...);
g.setName(...);
wxService.groupUpdate(group);
```

### 用户管理

#### 更新用户备注名
```java
wxService.userUpdateRemark(openid, "测试备注名");
```

#### 获得用户信息
```java
String lang = "zh_CN"; //语言
WxUser user = wxService.userInfo(openid, lang);
```

#### 获得用户列表
```java
WxUserList wxUserList = wxService.userList(next_openid);
```

#### 查询用户所在分组
```java
long groupid = wxService.userGetGroup(openid);
```

#### 将用户移到分组
```java
wxService.userUpdateGroup(openid, to_groupid);
```

### 发送客服消息

```java
WxCustomMessage message = new WxCustomMessage();
// 设置消息的内容等信息
wxService.customMessageSend(message);
```

### 群发消息

下面用用户列表群发（``WxMassOpenIdsMessage``）做例子，如果要使用分组群发，则使用``WxMassGroupMessage``即可。

#### 文本消息
```java
WxMassOpenIdsMessage massMessage = new WxMassOpenIdsMessage();
massMessage.setMsgtype(WxConsts.MASS_MSG_TEXT);
massMessage.setContent("消息内容");
massMessage.getTouser().add(openid);

WxMassSendResult massResult = wxService.massOpenIdsMessageSend(massMessage);
```

#### 视频消息
```java
WxMediaUploadResult uploadMediaRes = wxService.mediaUpload(WxConsts.MEDIA_VIDEO, WxConsts.FILE_MP4, inputStream);

// 把视频变成可被群发的媒体
WxMassVideo video = new WxMassVideo();
video.setTitle("测试标题");
video.setDescription("测试描述");
video.setMedia_id(uploadMediaRes.getMedia_id());
WxMassUploadResult uploadResult = wxService.massVideoUpload(video);

WxMassOpenIdsMessage massMessage = new WxMassOpenIdsMessage();
massMessage.setMsgtype(WxConsts.MASS_MSG_VIDEO);
massMessage.setMedia_id(uploadResult.getMedia_id());
massMessage.getTouser().add(openid);

WxMassSendResult massResult = wxService.massOpenIdsMessageSend(massMessage);
```

#### 图片消息
```java
WxMediaUploadResult uploadMediaRes = wxService.mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, inputStream);

WxMassOpenIdsMessage massMessage = new WxMassOpenIdsMessage();
massMessage.setMsgtype(WxConsts.MASS_MSG_IMAGE);
massMessage.setMedia_id(uploadMediaRes.getMedia_id());
massMessage.getTouser().add(openid);

WxMassSendResult massResult = wxService.massOpenIdsMessageSend(massMessage);
```

#### 语音消息
```java
WxMediaUploadResult uploadMediaRes = wxService.mediaUpload(WxConsts.MEDIA_VOICE, WxConsts.FILE_MP3, inputStream);

WxMassOpenIdsMessage massMessage = new WxMassOpenIdsMessage();
massMessage.setMsgtype(WxConsts.MASS_MSG_VOICE);
massMessage.setMedia_id(uploadMediaRes.getMedia_id());
massMessage.getTouser().add(openid);

WxMassSendResult massResult = wxService.massOpenIdsMessageSend(massMessage);
```

#### 图文消息
```java
// 先上传图文消息里需要的图片
WxMediaUploadResult uploadMediaRes = wxService.mediaUpload(WxConsts.MEDIA_IMAGE, WxConsts.FILE_JPG, inputStream);

WxMassNews news = new WxMassNews();
WxMassNewsArticle article1 = new WxMassNewsArticle();
article1.setTitle("标题1");
article1.setContent("内容1");
article1.setThumb_media_id(uploadMediaRes.getMedia_id());
news.addArticle(article1);

WxMassNewsArticle article2 = new WxMassNewsArticle();
article2.setTitle("标题2");
article2.setContent("内容2");
article2.setThumb_media_id(uploadMediaRes.getMedia_id());
article2.setShow_cover_pic(true);
article2.setAuthor("作者2");
article2.setContent_source_url("www.baidu.com");
article2.setDigest("摘要2");
news.addArticle(article2);

WxMassUploadResult massUploadResult = wxService.massNewsUpload(news);

WxMassOpenIdsMessage massMessage = new WxMassOpenIdsMessage();
massMessage.setMsgtype(WxConsts.MASS_MSG_NEWS);
massMessage.setMedia_id(uploadResult.getMedia_id());
massMessage.getTouser().add(openid);

WxMassSendResult massResult = wxService.massOpenIdsMessageSend(massMessage);
```

### 自定义菜单

#### 创建自定义菜单
```java
WxMenu wxMenu = new WxMenu();
// 设置菜单
wxService.menuCreate(wxMenu);
```

#### 删除自定义菜单
```java
wxService.menuDelete();
```

#### 获得自定义菜单
```java
WxMenu wxMenu = wxService.menuGet()
```

### 二维码

#### 获得二维码ticket
```java
// 临时ticket
WxQrCodeTicket ticket = wxService.qrCodeCreateTmpTicket(scene, expire_seconds);
// 永久ticket
WxQrCodeTicket ticket = wxService.qrCodeCreateLastTicket(scene);
```

#### 换取二维码图片
```java
WxQrCodeTicket ticket = ...;
// 获得一个在系统临时目录下的文件，是jpg格式的
File file = wxService.qrCodePicture(ticket);
```

### 短链接

```java
String shortUrl = wxService.shortUrl("www.baidu.com");
```

## 如何执行单元测试
将 ``src/test/resources/test-config.sample.xml`` 改成 ``test-config.xml`` 设置appId, secret, accessToken(可选), openId

```bash
mvn clean test
```
