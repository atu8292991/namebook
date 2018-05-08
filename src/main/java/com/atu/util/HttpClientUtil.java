package com.atu.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;

import com.google.common.base.Stopwatch;
import lombok.Builder;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:panhao.ph@alibaba-inc.com">mojin<a/>
 * @date 2018/05/04
 */
public class HttpClientUtil {
    private static final int DEFAULT_SOCKET_TIMEOUT  = 60000;
    private static final int DEFAULT_REQUEST_TIMEOUT = 10000;
    private static final int DEFAULT_CONNECT_TIMEOUT = 1000;

    private static final Logger LOGGER            = LoggerFactory
        .getLogger(HttpClientUtil.class);
    private static final PoolingHttpClientConnectionManager httpClientManager = new
        PoolingHttpClientConnectionManager();

    static {
        httpClientManager.setMaxTotal(1000);
        httpClientManager.setDefaultMaxPerRoute(200);
    }

    @Builder
    public static class Options {
        private int    socketTimeout;
        private int    requestTimeout;
        private int    connectTimeout;
        private int    timeoutRetryCnt;
        private String userAgent;
    }

    public static final Options DEFAULT_OPTION = Options.builder().connectTimeout(DEFAULT_CONNECT_TIMEOUT)
        .requestTimeout(DEFAULT_REQUEST_TIMEOUT).socketTimeout(DEFAULT_SOCKET_TIMEOUT).build();

    public static String executePost(String url, Map<String, String> params) {
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "text/html;charset=GB2312");
            if ((MapUtils.isNotEmpty(params))) {
                List<NameValuePair> pairs = params.entrySet()
                    .stream()
                    .map(m -> new BasicNameValuePair(m.getKey(), m.getValue()))
                    .collect(Collectors.toList());
                httpPost.setEntity(new UrlEncodedFormEntity(pairs));
            }
            return execute(httpPost, DEFAULT_OPTION);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("executePost error", e);
            return null;
        }
    }

    public static String executePostForJson(String url, Map<String, String> params) {
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            if (MapUtils.isNotEmpty(params)) {
                List<NameValuePair> pairs = params.entrySet()
                    .stream()
                    .map(m -> new BasicNameValuePair(m.getKey(), m.getValue()))
                    .collect(Collectors.toList());
                httpPost.setEntity(new UrlEncodedFormEntity(pairs));
            }
            return execute(httpPost,DEFAULT_OPTION);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("executePost error", e);
            return null;
        }
    }

    public static String executePostWithJson(String url, JSONObject postJson) {
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            StringEntity se = new StringEntity(postJson.toString());
            se.setContentType("application/json");
            httpPost.setEntity(se);
            return execute(httpPost, DEFAULT_OPTION);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("executePost error", e);
            return null;
        }
    }

    public static String executeGet(String url) {
        return executeGet(url, DEFAULT_OPTION);
    }

    public static String executeGet(String url, Options options) {
        HttpGet httpGet = new HttpGet(url);
        return execute(httpGet, options);
    }

    public static String executeDelete(String url) {
        HttpDelete httpDelete = new HttpDelete(url);
        return execute(httpDelete, DEFAULT_OPTION);
    }

    public static CloseableHttpResponse internalExecute(HttpRequestBase request,
                                                        Options options) throws IOException {
        int retryCount = options.timeoutRetryCnt == 0 ? 1 : options.timeoutRetryCnt;
        SocketTimeoutException exception = null;
        while (retryCount > 0) {
            try {
                return internalExecute_(request, options);
            } catch (java.net.SocketTimeoutException e) {
                exception = e;
                retryCount--;
            }
        }
        throw exception;
    }

    private static CloseableHttpResponse internalExecute_(HttpRequestBase request,
                                                          Options options) throws IOException {
        CloseableHttpClient httpClient = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(options.socketTimeout)
                .setConnectTimeout(options.connectTimeout)
                .setConnectionRequestTimeout(options.requestTimeout)

                .build();
            request.setConfig(requestConfig);
            httpClient = HttpClients.custom()
                .setConnectionManager(httpClientManager)
                .setConnectionManagerShared(true)
                .setUserAgent(options.userAgent)
                .build();
            return httpClient.execute(request);
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private static String execute(HttpRequestBase request, Options options) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        CloseableHttpResponse response = null;
        try {
            response = internalExecute(request, options);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                LOGGER.error(String.format("[%s] http response code is [%d]", request.getURI(),
                    response.getStatusLine().getStatusCode()));
                return null;
            }
            return EntityUtils.toString(response.getEntity(), "GB2312");
        } catch (IOException e) {
            LOGGER.error("http request error", e);
            return null;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
            stopwatch.stop();
            LOGGER.info(
                String.format("http url=[%s],cost=%s", request.getURI(), stopwatch.elapsed(TimeUnit.MILLISECONDS)));
        }
    }
}
