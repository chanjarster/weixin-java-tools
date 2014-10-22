package me.chanjar.weixin.cp.api;

public class WxCpConsts {

  ///////////////////////
  // 微信推送过来的消息的类型，和发送给微信xml格式消息的消息类型
  ///////////////////////
  public static final String XML_MSG_TEXT = "text";
  public static final String XML_MSG_IMAGE = "image";
  public static final String XML_MSG_VOICE = "voice";
  public static final String XML_MSG_VIDEO = "video";
  public static final String XML_MSG_NEWS = "news";
  public static final String XML_MSG_LOCATION = "location";
  public static final String XML_MSG_LINK = "link";
  public static final String XML_MSG_EVENT = "event";

  ///////////////////////
  // 消息的消息类型
  ///////////////////////
  public static final String CUSTOM_MSG_TEXT = "text";
  public static final String CUSTOM_MSG_IMAGE = "image";
  public static final String CUSTOM_MSG_VOICE = "voice";
  public static final String CUSTOM_MSG_VIDEO = "video";
  public static final String CUSTOM_MSG_NEWS = "news";
  public static final String CUSTOM_MSG_FILE = "file";

  ///////////////////////
  // 微信端推送过来的事件类型
  ///////////////////////
  public static final String EVT_SUBSCRIBE = "subscribe";
  public static final String EVT_UNSUBSCRIBE = "unsubscribe";
  public static final String EVT_SCAN = "SCAN";
  public static final String EVT_LOCATION = "LOCATION";
  public static final String EVT_CLICK = "CLICK";
  public static final String EVT_VIEW = "VIEW";
  public static final String EVT_MASS_SEND_JOB_FINISH = "MASSSENDJOBFINISH";
  public static final String EVT_SCANCODE_PUSH = "scancode_push";
  public static final String EVT_SCANCODE_WAITMSG = "scancode_waitmsg";
  public static final String EVT_PIC_SYSPHOTO = "pic_sysphoto";
  public static final String EVT_PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
  public static final String EVT_PIC_WEIXIN = "pic_weixin";
  public static final String EVT_LOCATION_SELECT = "location_select";

  ///////////////////////
  // 上传多媒体文件的类型
  ///////////////////////
  public static final String MEDIA_IMAGE = "image";
  public static final String MEDIA_VOICE = "voice";
  public static final String MEDIA_VIDEO = "video";
  public static final String MEDIA_FILE = "file";
  
  ///////////////////////
  // 文件类型
  ///////////////////////
  public static final String FILE_JPG = "jpeg";
  public static final String FILE_MP3 = "mp3";
  public static final String FILE_ARM = "arm";
  public static final String FILE_MP4 = "mp4";


  ///////////////////////
  // 自定义菜单的按钮类型
  ///////////////////////
  /** 点击推事件 */
  public static final String BUTTON_CLICK = "click";
  /** 跳转URL */
  public static final String BUTTON_VIEW = "view";
  /** 扫码推事件 */
  public static final String BUTTON_SCANCODE_PUSH = "scancode_push";
  /** 扫码推事件且弹出“消息接收中”提示框 */
  public static final String BUTTON_SCANCODE_WAITMSG = "scancode_waitmsg";
  /** 弹出系统拍照发图 */
  public static final String PIC_SYSPHOTO = "pic_sysphoto";
  /** 弹出拍照或者相册发图 */
  public static final String PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
  /** 弹出微信相册发图器 */
  public static final String PIC_WEIXIN = "pic_weixin";
  /** 弹出地理位置选择器 */
  public static final String LOCATION_SELECT = "location_select";

}
