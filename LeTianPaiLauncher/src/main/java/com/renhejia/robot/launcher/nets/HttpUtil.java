package com.renhejia.robot.launcher.nets;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
 
public class HttpUtil {
	private HttpUtil() {
	}
 
	/**
	 * 发送get请求
	 *
	 * @param url    地址
	 * @param params 参数
	 * @return 请求结果
	 */
	public static String get(String url, Map<String, String> params) {
		return request("get", url, params);
	}
 
	/**
	 * 发送post请求
	 *
	 * @param url    地址
	 * @param params 参数
	 * @return 请求结果
	 */
	public static String post(String url, Map<String, String> params) {
		return request("post", url, params);
	}
 
	/**
	 * 发送http请求
	 *
	 * @param method 请求方法
	 * @param url    地址
	 * @param params 参数
	 * @return 请求结果
	 */
	public static String request(String method, String url, Map<String, String> params) {
 
		if (method == null) {
			throw new RuntimeException("请求方法不能为空");
		}
 
		if (url == null) {
			throw new RuntimeException("url不能为空");
		}
 
		HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
 
		if (params != null) {
			for (Map.Entry<String, String> param : params.entrySet()) {
				httpBuilder.addQueryParameter(param.getKey(), param.getValue());
			}
		}
 
		Request request = new Request.Builder()
				.url(httpBuilder.build())
				.method(method, new FormBody.Builder().build())
				.build();
 
		try {
			OkHttpClient client = new OkHttpClient.Builder()
					.readTimeout(20, TimeUnit.SECONDS)
					.build();
			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch (IOException e) {
			return null;
		}
	}
 
	/**
	 * 发送post请求（json格式）
	 *
	 * @param url  url
	 * @param json json字符串
	 * @return 请求结果
	 */
	public static String postJson(String url, String json) {
 
		Request request = new Request.Builder()
				.url(url)
				.post(RequestBody.Companion.create(json, MediaType.Companion.parse("application/json")))
				.build();
 
		try {
			OkHttpClient client = new OkHttpClient();
			Response response = client.newCall(request).execute();
			return response.body().string();
		} catch (IOException e) {
			return null;
		}
	}
}
