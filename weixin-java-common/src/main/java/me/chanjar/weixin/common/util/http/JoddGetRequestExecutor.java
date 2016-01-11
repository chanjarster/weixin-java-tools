package me.chanjar.weixin.common.util.http;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.http.net.SocketHttpConnectionProvider;
import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

/**
 * 简单的GET请求执行器，请求的参数是String, 返回的结果也是String
 *
 * @author Daniel Qian
 */
public class JoddGetRequestExecutor implements RequestExecutor<String, String> {

    @Override
    public String execute(CloseableHttpClient httpclient, HttpHost httpProxy, String uri,
        String queryParam) throws WxErrorException, IOException {
        if (queryParam != null) {
            if (uri.indexOf('?') == -1) {
                uri += '?';
            }
            uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
        }

        SocketHttpConnectionProvider provider = new SocketHttpConnectionProvider();

        if (httpProxy != null) {
            ProxyInfo proxyInfoObj = new ProxyInfo(
                ProxyInfo.ProxyType.HTTP,
                httpProxy.getAddress().getHostAddress(),
                httpProxy.getPort(), "", "");
            provider.useProxy(proxyInfoObj);
        }

        HttpRequest request = HttpRequest.get(uri);
        request.method("GET");
        request.charset("UTF-8");

        HttpResponse response = request.open(provider).send();
        response.charset("UTF-8");
        String result = response.bodyText();

        WxError error = WxError.fromJson(result);
        if (error.getErrorCode() != 0) {
            throw new WxErrorException(error);
        }
        return result;
    }

}
