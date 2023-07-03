package org.hejia.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 */
@Slf4j
public final class HttpUtils {

	static final String POST = "POST";
	static final String GET = "GET";
	static final int CONN_TIMEOUT = 30000;// ms
	static final int READ_TIMEOUT = 30000;// ms

	/**
	 * post 方式发送http请求.
	 * 
	 * @param strUrl 请求地址
	 * @param reqData 请求数据
	 * @return 请求结果
	 */
	public static byte[] doPost(String strUrl, byte[] reqData) {
		return send(strUrl, POST, reqData);
	}

	/**
	 * get方式发送http请求.
	 * 
	 * @param strUrl 请求地址
	 * @return 请求结果
	 */
	public static byte[] doGet(String strUrl) {
		return send(strUrl, GET, null);
	}

	/**
	 * @param strUrl 请求地址
	 * @param reqMethod 请求方式
	 * @param reqData 请求数据
	 * @return 请求结果
	 */
	public static byte[] send(String strUrl, String reqMethod, byte[] reqData) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setDoInput(true);
			httpCon.setUseCaches(false);
			httpCon.setInstanceFollowRedirects(true);
			httpCon.setConnectTimeout(CONN_TIMEOUT);
			httpCon.setReadTimeout(READ_TIMEOUT);
			httpCon.setRequestMethod(reqMethod);
			httpCon.connect();
			if (reqMethod.equalsIgnoreCase(POST)) {
				OutputStream os = httpCon.getOutputStream();
				os.write(reqData);
				os.flush();
				os.close();
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream(),"utf-8"));
			String inputLine;
			StringBuilder bankXmlBuffer = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {  
			    bankXmlBuffer.append(inputLine);  
			}  
			in.close();
			httpCon.disconnect();
			return bankXmlBuffer.toString().getBytes();
		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			return null;
		}
	}
	
	/**
	 * 从输入流中读取数据
	 * 
	 * @param inStream 输入流
	 * @return 读取结果
	 * @throws Exception 读取错误
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();// 网页的二进制数据
		outStream.close();
		inStream.close();
		return data;
	}
}
