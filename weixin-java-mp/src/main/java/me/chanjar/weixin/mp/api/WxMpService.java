package me.chanjar.weixin.mp.api;

import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSession;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.mp.bean.*;
import me.chanjar.weixin.mp.bean.result.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 微信API的Service
 */
public interface WxMpService {
  
  /**
   * <pre>
   * 验证推送过来的消息的正确性
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=验证消息真实性
   * </pre>
   * @param timestamp
   * @param nonce
   * @param signature
   * @return
   */
  public boolean checkSignature(String timestamp, String nonce, String signature);

  /**
   * 获取access_token, 不强制刷新access_token
   * @see #getAccessToken(boolean)
   * @return
   * @throws WxErrorException
   */
  public String getAccessToken() throws WxErrorException;

  /**
   * <pre>
   * 获取access_token，本方法线程安全
   * 且在多线程同时刷新时只刷新一次，避免超出2000次/日的调用次数上限
   * 
   * 另：本service的所有方法都会在access_token过期是调用此方法
   * 
   * 程序员在非必要情况下尽量不要主动调用此方法

   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=获取access_token
   * </pre>
   * @param forceRefresh 强制刷新
   * @return
   * @throws me.chanjar.weixin.common.exception.WxErrorException
   */
  public String getAccessToken(boolean forceRefresh) throws WxErrorException;

  /**
   * 获得jsapi_ticket,不强制刷新jsapi_ticket
   * @see #getJsapiTicket(boolean)
   * @return
   * @throws WxErrorException
   */
  public String getJsapiTicket() throws WxErrorException;

  /**
   * <pre>
   * 获得jsapi_ticket
   * 获得时会检查jsapiToken是否过期，如果过期了，那么就刷新一下，否则就什么都不干
   *
   * 详情请见：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E9.99.84.E5.BD.951-JS-SDK.E4.BD.BF.E7.94.A8.E6.9D.83.E9.99.90.E7.AD.BE.E5.90.8D.E7.AE.97.E6.B3.95
   * </pre>
   * @param forceRefresh 强制刷新
   * @return
   * @throws WxErrorException
   */
  public String getJsapiTicket(boolean forceRefresh) throws WxErrorException;

  /**
   * <pre>
   * 创建调用jsapi时所需要的签名
   *
   * 详情请见：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html#.E9.99.84.E5.BD.951-JS-SDK.E4.BD.BF.E7.94.A8.E6.9D.83.E9.99.90.E7.AD.BE.E5.90.8D.E7.AE.97.E6.B3.95
   * </pre>
   * @param timestamp 时间戳
   * @param noncestr  用户自己生成的随机字符串
   * @param url       url
   * @return
   */
  public String createJsapiSignature(long timestamp, String noncestr, String url) throws WxErrorException;

  /**
   * <pre>
   * 上传多媒体文件
   *
   * 上传的多媒体文件有格式和大小限制，如下：
   *   图片（image）: 1M，支持JPG格式
   *   语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
   *   视频（video）：10MB，支持MP4格式
   *   缩略图（thumb）：64KB，支持JPG格式
   *
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=上传下载多媒体文件
   * </pre>
   * @param mediaType         媒体类型, 请看{@link me.chanjar.weixin.common.api.WxConsts}
   * @param fileType          文件类型，请看{@link me.chanjar.weixin.common.api.WxConsts}
   * @param inputStream       输入流
   * @throws WxErrorException
   */
  public WxMediaUploadResult mediaUpload(String mediaType, String fileType, InputStream inputStream) throws WxErrorException, IOException;

  /**
   * @see #mediaUpload(String, String, InputStream)
   * @param mediaType
   * @param file
   * @throws WxErrorException
   */
  public WxMediaUploadResult mediaUpload(String mediaType, File file) throws WxErrorException;
  
  /**
   * <pre>
   * 下载多媒体文件
   * 根据微信文档，视频文件下载不了，会返回null
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=上传下载多媒体文件
   * </pre>
   * @params media_id
   * @return 保存到本地的临时文件
   * @throws WxErrorException
   */
  public File mediaDownload(String media_id) throws WxErrorException;
  
  /**
   * <pre>
   * 发送客服消息
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=发送客服消息
   * </pre>
   * @param message
   * @throws WxErrorException
   */
  public void customMessageSend(WxMpCustomMessage message) throws WxErrorException;
  
  /**
   * <pre>
   * 上传群发用的图文消息，上传后才能群发图文消息 
   * 
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口
   * </pre>
   * @param news
   * @throws WxErrorException
   * @see #massGroupMessageSend(me.chanjar.weixin.mp.bean.WxMpMassGroupMessage)
   * @see #massOpenIdsMessageSend(me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage)
   */
  public WxMpMassUploadResult massNewsUpload(WxMpMassNews news) throws WxErrorException;
  
  /**
   * <pre>
   * 上传群发用的视频，上传后才能群发视频消息
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口
   * </pre>
   * @return
   * @throws WxErrorException
   * @see #massGroupMessageSend(me.chanjar.weixin.mp.bean.WxMpMassGroupMessage)
   * @see #massOpenIdsMessageSend(me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage)
   */
  public WxMpMassUploadResult massVideoUpload(WxMpMassVideo video) throws WxErrorException;

  /**
   * <pre>
   * 分组群发消息
   * 如果发送图文消息，必须先使用 {@link #massNewsUpload(me.chanjar.weixin.mp.bean.WxMpMassNews)} 获得media_id，然后再发送
   * 如果发送视频消息，必须先使用 {@link #massVideoUpload(me.chanjar.weixin.mp.bean.WxMpMassVideo)} 获得media_id，然后再发送
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口
   * </pre>
   * @param message
   * @throws WxErrorException
   * @return 
   */
  public WxMpMassSendResult massGroupMessageSend(WxMpMassGroupMessage message) throws WxErrorException;
  
  /**
   * <pre>
   * 按openId列表群发消息
   * 如果发送图文消息，必须先使用 {@link #massNewsUpload(me.chanjar.weixin.mp.bean.WxMpMassNews)} 获得media_id，然后再发送
   * 如果发送视频消息，必须先使用 {@link #massVideoUpload(me.chanjar.weixin.mp.bean.WxMpMassVideo)} 获得media_id，然后再发送
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口
   * </pre>
   * @param message
   * @return
   * @throws WxErrorException
   */
  public WxMpMassSendResult massOpenIdsMessageSend(WxMpMassOpenIdsMessage message) throws WxErrorException;

  /**
   * <pre>
   * 自定义菜单创建接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单创建接口
   * </pre>
   * @param menu
   * @throws WxErrorException
   */
  public void menuCreate(WxMenu menu) throws WxErrorException;
  
  /**
   * <pre>
   * 自定义菜单删除接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单删除接口
   * </pre>
   * @throws WxErrorException
   */
  public void menuDelete() throws WxErrorException;
  
  /**
   * <pre>
   * 自定义菜单查询接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单查询接口
   * </pre>
   * @return
   * @throws WxErrorException
   */
  public WxMenu menuGet() throws WxErrorException;

  /**
   * <pre>
   * 分组管理接口 - 创建分组
   * 最多支持创建500个分组
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
   * </pre>
   * @param name 分组名字（30个字符以内） 
   * @throws WxErrorException
   */
  public WxMpGroup groupCreate(String name) throws WxErrorException;
  
  /**
   * <pre>
   * 分组管理接口 - 查询所有分组
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
   * </pre>
   * @return
   * @throws WxErrorException
   */
  public List<WxMpGroup> groupGet() throws WxErrorException;
  
  /**
   * <pre>
   * 分组管理接口 - 查询用户所在分组
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
   * </pre>
   * @param openid 微信用户的openid 
   * @throws WxErrorException
   */
  public long userGetGroup(String openid) throws WxErrorException;
  
  /**
   * <pre>
   * 分组管理接口 - 修改分组名
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
   * 
   * 如果id为0(未分组),1(黑名单),2(星标组)，或者不存在的id，微信会返回系统繁忙的错误
   * </pre>
   * @param group 要更新的group，group的id,name必须设置 
   * @throws WxErrorException
   */
  public void groupUpdate(WxMpGroup group) throws WxErrorException;
  
  /**
   * <pre>
   * 分组管理接口 - 移动用户分组
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
   * 
   * 如果to_groupid为0(未分组),1(黑名单),2(星标组)，或者不存在的id，微信会返回系统繁忙的错误
   * </pre>
   * @param openid      用户openid
   * @param to_groupid  移动到的分组id
   * @throws WxErrorException
   */
  public void userUpdateGroup(String openid, long to_groupid) throws WxErrorException;

  /**
   * <pre>
   * 设置用户备注名接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=设置用户备注名接口
   * </pre>
   * @param openid    用户openid
   * @param remark    备注名
   * @throws WxErrorException
   */
  public void userUpdateRemark(String openid, String remark) throws WxErrorException;

  /**
   * <pre>
   * 获取用户基本信息
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=获取用户基本信息
   * </pre>
   * @param openid  用户openid
   * @param lang    语言，zh_CN 简体(默认)，zh_TW 繁体，en 英语 
   * @return
   * @throws WxErrorException
   */
  public WxMpUser userInfo(String openid, String lang) throws WxErrorException;

  /**
   * <pre>
   * 获取关注者列表
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=获取关注者列表
   * </pre>
   * @param next_openid  可选，第一个拉取的OPENID，null为从头开始拉取
   * @return
   * @throws WxErrorException
   */
  public WxMpUserList userList(String next_openid) throws WxErrorException;

  /**
   * <pre>
   * 换取临时二维码ticket
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=生成带参数的二维码
   * </pre>
   * @param scene_id          参数。
   * @param expire_seconds    过期秒数，默认60秒，最小60秒，最大1800秒
   * @return
   * @throws WxErrorException
   */
  public WxMpQrCodeTicket qrCodeCreateTmpTicket(int scene_id, Integer expire_seconds) throws WxErrorException;

  /**
   * <pre>
   * 换取永久二维码ticket
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=生成带参数的二维码
   * </pre>
   * @param scene_id    参数。永久二维码时最大值为100000（目前参数只支持1--100000）
   * @return
   * @throws WxErrorException
   */
  public WxMpQrCodeTicket qrCodeCreateLastTicket(int scene_id) throws WxErrorException;

  /**
   * <pre>
   * 换取二维码图片文件，jpg格式
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=生成带参数的二维码
   * </pre>
   * @param ticket    二维码ticket
   * @return
   * @throws WxErrorException
   */
  public File qrCodePicture(WxMpQrCodeTicket ticket) throws WxErrorException;

  /**
   * <pre>
   * 长链接转短链接接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=长链接转短链接接口
   * </pre>
   * @param long_url
   * @return
   * @throws WxErrorException
   */
  public String shortUrl(String long_url) throws WxErrorException;

  /**
   * <pre>
   * 发送模板消息
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=模板消息接口
   * </pre>
   * @param templateMessage
   * @throws WxErrorException
   * @return msgid
   */
  public String templateSend(WxMpTemplateMessage templateMessage) throws WxErrorException;

  /**
   * <pre>
   * 语义查询接口
   * 详情请见：http://mp.weixin.qq.com/wiki/index.php?title=语义理解
   * </pre>
   * @param semanticQuery
   * @return
   * @throws WxErrorException
   */
  WxMpSemanticQueryResult semanticQuery(WxMpSemanticQuery semanticQuery) throws WxErrorException;

  /**
   * <pre>
   * 构造oauth2授权的url连接
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息
   * </pre>
   * @param scope
   * @param state
   * @return code
   */
  public String oauth2buildAuthorizationUrl(String scope, String state);

  /**
   * <pre>
   * 用code换取oauth2的access token
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息
   * </pre>
   * @param code
   * @return
   */
  public WxMpOAuth2AccessToken oauth2getAccessToken(String code) throws WxErrorException;

  /**
   * <pre>
   * 刷新oauth2的access token
   * </pre>
   * @param refreshToken
   * @return
   */
  public WxMpOAuth2AccessToken oauth2refreshAccessToken(String refreshToken) throws WxErrorException;

  /**
   * <pre>
   * 用oauth2获取用户信息, 当前面引导授权时的scope是snsapi_userinfo的时候才可以
   * </pre>
   * @param oAuth2AccessToken
   * @param lang zh_CN, zh_TW, en
   */
  public WxMpUser oauth2getUserInfo(WxMpOAuth2AccessToken oAuth2AccessToken, String lang) throws WxErrorException;

  /**
   * <pre>
   * 验证oauth2的access token是否有效
   * </pre>
   * @param oAuth2AccessToken
   * @return
   */
  public boolean oauth2validateAccessToken(WxMpOAuth2AccessToken oAuth2AccessToken);

  /**
   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的GET请求
   * @param url
   * @param queryParam
   * @return
   * @throws WxErrorException
   */
  String get(String url, String queryParam) throws WxErrorException;

  /**
   * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的POST请求
   * @param url
   * @param postData
   * @return
   * @throws WxErrorException
   */
  String post(String url, String postData) throws WxErrorException;

  /**
   * <pre>
   * Service没有实现某个API的时候，可以用这个，
   * 比{@link #get}和{@link #post}方法更灵活，可以自己构造RequestExecutor用来处理不同的参数和不同的返回类型。
   * 可以参考，{@link me.chanjar.weixin.common.util.http.MediaUploadRequestExecutor}的实现方法
   * </pre>
   * @param executor
   * @param uri
   * @param data
   * @param <T>
   * @param <E>
   * @return
   * @throws WxErrorException
   */
  public <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) throws WxErrorException;

  /**
   * 注入 {@link WxMpConfigStorage} 的实现
   * @param wxConfigProvider
   */
  public void setWxMpConfigStorage(WxMpConfigStorage wxConfigProvider);

  /**
   * <pre>
   * 设置当微信系统响应系统繁忙时，要等待多少 retrySleepMillis(ms) * 2^(重试次数 - 1) 再发起重试
   * 默认：1000ms
   * </pre>
   * @param retrySleepMillis
   */
  void setRetrySleepMillis(int retrySleepMillis);

  /**
   * <pre>
   * 设置当微信系统响应系统繁忙时，最大重试次数
   * 默认：5次
   * </pre>
   * @param maxRetryTimes
   */
  void setMaxRetryTimes(int maxRetryTimes);

}
