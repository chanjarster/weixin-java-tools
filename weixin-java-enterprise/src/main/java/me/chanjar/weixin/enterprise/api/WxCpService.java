package me.chanjar.weixin.enterprise.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import me.chanjar.weixin.enterprise.bean.*;
import me.chanjar.weixin.enterprise.bean.WxCpDepart;
import me.chanjar.weixin.enterprise.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.enterprise.bean.result.WxUser;
import me.chanjar.weixin.enterprise.exception.WxErrorException;

/**
 * 微信API的Service
 */
public interface WxCpService {
  
  /**
   * <pre>
   * 验证推送过来的消息的正确性
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=验证消息真实性
   * </pre>
   * @param msgSignature
   * @param timestamp
   * @param nonce
   * @param data       微信传输过来的数据，有可能是echoStr，有可能是xml消息
   * @return
   */
  public boolean checkSignature(String msgSignature, String timestamp, String nonce, String data);
  
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
   * @throws me.chanjar.weixin.enterprise.exception.WxErrorException
   */
  public void accessTokenRefresh() throws WxErrorException;
  
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
  public void messageSend(WxCpMessage message) throws WxErrorException;
  
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
  public WxCpDepart departmentCreate(String name) throws WxErrorException;
  
  /**
   * <pre>
   * 分组管理接口 - 查询所有分组
   * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
   * </pre>
   * @return
   * @throws WxErrorException
   */
  public List<WxCpDepart> departmentGet() throws WxErrorException;
  
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
  public void departmentUpdate(WxCpDepart group) throws WxErrorException;

  /**
   * <pre>
   * 部门管理接口 - 删除部门
   *
   * </pre>
   * @param department
   * @throws WxErrorException
   */
  public void departmentDelete(WxCpDepart department) throws WxErrorException;

  public void userCreate(WxUser user) throws WxErrorException;

  public void userUpdate(WxUser user) throws WxErrorException;

  public void userDelete(String userid) throws WxErrorException;

  public WxUser userGet(String userid) throws WxErrorException;

  public List<WxUser> userGetByDepartment(String departmentId) throws WxErrorException;

  /**
   * 注入 {@link WxCpConfigStorage} 的实现
   * @param wxConfigProvider
   */
  public void setWxCpConfigStorage(WxCpConfigStorage wxConfigProvider);
}
