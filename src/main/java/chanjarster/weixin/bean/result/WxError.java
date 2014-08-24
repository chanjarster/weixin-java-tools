package chanjarster.weixin.bean.result;

import java.util.HashMap;
import java.util.Map;

import chanjarster.weixin.util.json.WxGsonBuilder;

/**
 * 微信错误码说明
 * http://mp.weixin.qq.com/wiki/index.php?title=全局返回码说明
 * @author chanjarster
 *
 */
public class WxError {

  protected static final Map<Integer, String> errMap = new HashMap<Integer, String>();
  
  static {
    errMap.put(-1, "系统繁忙");
    errMap.put(0, "请求成功");
    errMap.put(40001, "获取access_token时AppSecret错误，或者access_token无效");
    errMap.put(40002, "不合法的凭证类型");
    errMap.put(40003, "不合法的OpenID");
    errMap.put(40004, "不合法的媒体文件类型");
    errMap.put(40005, "不合法的文件类型");
    errMap.put(40006, "不合法的文件大小");
    errMap.put(40007, "不合法的媒体文件id");
    errMap.put(40008, "不合法的消息类型");
    errMap.put(40009, "不合法的图片文件大小");
    errMap.put(40010, "不合法的语音文件大小");
    errMap.put(40011, "不合法的视频文件大小");
    errMap.put(40012, "不合法的缩略图文件大小");
    errMap.put(40013, "不合法的APPID");
    errMap.put(40014, "不合法的access_token");
    errMap.put(40015, "不合法的菜单类型");
    errMap.put(40016, "不合法的按钮个数");
    errMap.put(40017, "不合法的按钮个数");
    errMap.put(40018, "不合法的按钮名字长度");
    errMap.put(40019, "不合法的按钮KEY长度");
    errMap.put(40020, "不合法的按钮URL长度");
    errMap.put(40021, "不合法的菜单版本号");
    errMap.put(40022, "不合法的子菜单级数");
    errMap.put(40023, "不合法的子菜单按钮个数");
    errMap.put(40024, "不合法的子菜单按钮类型");
    errMap.put(40025, "不合法的子菜单按钮名字长度");
    errMap.put(40026, "不合法的子菜单按钮KEY长度");
    errMap.put(40027, "不合法的子菜单按钮URL长度");
    errMap.put(40028, "不合法的自定义菜单使用用户");
    errMap.put(40029, "不合法的oauth_code");
    errMap.put(40030, "不合法的refresh_token");
    errMap.put(40031, "不合法的openid列表");
    errMap.put(40032, "不合法的openid列表长度");
    errMap.put(40033, "不合法的请求字符，不能包含\\uxxxx格式的字符");
    errMap.put(40035, "不合法的参数");
    errMap.put(40038, "不合法的请求格式");
    errMap.put(40039, "不合法的URL长度");
    errMap.put(40050, "不合法的分组id");
    errMap.put(40051, "分组名字不合法");
    errMap.put(41001, "缺少access_token参数");
    errMap.put(41002, "缺少appid参数");
    errMap.put(41003, "缺少refresh_token参数");
    errMap.put(41004, "缺少secret参数");
    errMap.put(41005, "缺少多媒体文件数据");
    errMap.put(41006, "缺少media_id参数");
    errMap.put(41007, "缺少子菜单数据");
    errMap.put(41008, "缺少oauth code");
    errMap.put(41009, "缺少openid");
    errMap.put(42001, "access_token超时");
    errMap.put(42002, "refresh_token超时");
    errMap.put(42003, "oauth_code超时");
    errMap.put(43001, "需要GET请求");
    errMap.put(43002, "需要POST请求");
    errMap.put(43003, "需要HTTPS请求");
    errMap.put(43004, "需要接收者关注");
    errMap.put(43005, "需要好友关系");
    errMap.put(44001, "多媒体文件为空");
    errMap.put(44002, "POST的数据包为空");
    errMap.put(44003, "图文消息内容为空");
    errMap.put(44004, "文本消息内容为空");
    errMap.put(45001, "多媒体文件大小超过限制");
    errMap.put(45002, "消息内容超过限制");
    errMap.put(45003, "标题字段超过限制");
    errMap.put(45004, "描述字段超过限制");
    errMap.put(45005, "链接字段超过限制");
    errMap.put(45006, "图片链接字段超过限制");
    errMap.put(45007, "语音播放时间超过限制");
    errMap.put(45008, "图文消息超过限制");
    errMap.put(45009, "接口调用超过限制");
    errMap.put(45010, "创建菜单个数超过限制");
    errMap.put(45015, "回复时间超过限制");
    errMap.put(45016, "系统分组，不允许修改");
    errMap.put(45017, "分组名字过长");
    errMap.put(45018, "分组数量超过上限");
    errMap.put(46001, "不存在媒体数据");
    errMap.put(46002, "不存在的菜单版本");
    errMap.put(46003, "不存在的菜单数据");
    errMap.put(46004, "不存在的用户");
    errMap.put(47001, "解析JSON/XML内容错误");
    errMap.put(48001, "api功能未授权");
    errMap.put(50001, "用户未授权该api");
  }
  
  protected int errcode;
  
  protected String errmsg;

  public int getErrcode() {
    return errcode;
  }

  public void setErrcode(int errcode) {
    this.errcode = errcode;
  }

  public String getErrmsg() {
    return errmsg;
  }

  public void setErrmsg(String errmsg) {
    this.errmsg = errmsg;
  }

  public String getDescription() {
    return errMap.get(errcode);
  }

  public static WxError fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxError.class);
  }

  @Override
  public String toString() {
    return "微信错误 errcode=" + errcode + ", errmsg=" + errmsg + ", description=" + getDescription();
  }

}
