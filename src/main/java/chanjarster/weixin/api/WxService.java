package chanjarster.weixin.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import chanjarster.weixin.bean.WxCustomMessage;
import chanjarster.weixin.bean.WxMassMessage;
import chanjarster.weixin.bean.WxMassNews;
import chanjarster.weixin.bean.WxMassVideo;
import chanjarster.weixin.bean.WxMenu;
import chanjarster.weixin.bean.result.WxMassMaterialUploadResult;
import chanjarster.weixin.bean.result.WxMassSendResult;
import chanjarster.weixin.bean.result.WxMediaUploadResult;
import chanjarster.weixin.exception.WxErrorException;

/**
 * 微信API的Service
 */
public interface WxService {
  
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
   * <pre>
   * 获取access_token，本方法线程安全
   * 且在多线程同时刷新时只刷新一次，避免超出2000次/日的调用次数上限
   * 
   * 另：本service的所有方法都会在access_token过期是调用此方法
   * 
   * 程序员在非必要情况下尽量不要主动调用此方法

   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=获取access_token
   * </pre>
   * @throws WxErrorException
   */
  public void refreshAccessToken() throws WxErrorException;
  
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
   * @param mediaType         媒体类型, 请看{@link WxConsts}
   * @param fileType          文件类型，请看{@link WxConsts}
   * @param inputStream       输入流
   * @throws WxErrorException
   */
  public WxMediaUploadResult uploadMedia(String mediaType, String fileType, InputStream inputStream) throws WxErrorException, IOException;

  /**
   * @see #uploadMedia(String, String, InputStream)
   * @param mediaType
   * @param file
   * @throws WxErrorException
   */
  public WxMediaUploadResult uploadMedia(String mediaType, File file) throws WxErrorException;
  
  /**
   * <pre>
   * 下载多媒体文件
   * 
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=上传下载多媒体文件
   * </pre>
   * @params media_id
   * @return 保存到本地的临时文件
   * @throws WxErrorException
   */
  public File downloadMedia(String media_id) throws WxErrorException;
  
  /**
   * <pre>
   * 发送客服消息
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=发送客服消息
   * </pre>
   * @param message
   * @throws WxErrorException
   */
  public void sendCustomMessage(WxCustomMessage message) throws WxErrorException;
  
  /**
   * <pre>
   * 上传群发用的图文消息，上传后才能群发图文消息 {@link #sendMassMessage(WxMassMessage)}
   * 
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口
   * </pre>
   * @param news
   * @throws WxErrorException
   */
  public WxMassMaterialUploadResult uploadMassNews(WxMassNews news) throws WxErrorException;
  
  /**
   * <pre>
   * 上传群发用的视频，上传后才能群发视频消息 {@link #sendMassMessage(WxMassMessage)}
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口
   * </pre>
   * @return
   * @throws WxErrorException
   */
  public WxMassMaterialUploadResult uploadMassVideo(WxMassVideo video) throws WxErrorException;

  /**
   * <pre>
   * 群发消息
   * 如果发送图文消息，必须先使用 {@link #uploadMassNews(WxMassNews)} 获得media_id，然后再发送
   * 如果发送视频消息，必须先使用 {@link #uploadMassVideo(WxMassVideo)} 获得media_id，然后再发送
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口
   * </pre>
   * @param message
   * @throws WxErrorException
   * @return 
   */
  public WxMassSendResult sendMassMessage(WxMassMessage message) throws WxErrorException;
  
  /**
   * <pre>
   * 自定义菜单创建接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单创建接口
   * </pre>
   * @param menu
   * @throws WxErrorException
   */
  public void createMenu(WxMenu menu) throws WxErrorException;
  
  /**
   * <pre>
   * 自定义菜单删除接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单删除接口
   * </pre>
   * @throws WxErrorException
   */
  public void deleteMenu() throws WxErrorException;
  
  /**
   * <pre>
   * 自定义菜单查询接口
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单查询接口
   * </pre>
   * @return
   * @throws WxErrorException
   */
  public WxMenu getMenu() throws WxErrorException;

  public void setWxConfigStorage(WxConfigStorage wxConfigProvider);
}
