package com.verysu.basic.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * IP地址工具类
 * 
 * @author Sjg 2014-9-15 上午08:40:54
 */
public class AddressUtil {

	private static AddressUtil util = null;

	public static AddressUtil getInstance() {
		if (util == null) {
			util = new AddressUtil();
		}
		return util;
	}

	/**
	 * 获取IP归属地
	 * @param params ip=***
	 * @param encoding 编码（UTF-8）
	 * @return
	 * @throws JSONException
	 */
	public String getAddress(String params, String encoding) throws JSONException {
		String path = "http://ip.taobao.com/service/getIpInfo.php";
		String result = this.getResult(path, params, encoding);
		JSONObject json = null;
		if (result != null) {
			json = new JSONObject(result);
			// 正常获取
			if ("0".equals(json.get("code").toString())) {

				StringBuffer buffer = new StringBuffer();

				buffer.append(decodeUnicode(json.optJSONObject("data").getString("country")));// 国家

				buffer.append(decodeUnicode(json.optJSONObject("data").getString("area")));// 地区

				buffer.append(decodeUnicode(json.optJSONObject("data").getString("region")));// 省份

				buffer.append(decodeUnicode(json.optJSONObject("data").getString("city")));// 市区

//				buffer.append(decodeUnicode(json.optJSONObject("data").getString("county")));// 地区

//				buffer.append(decodeUnicode(json.optJSONObject("data").getString("isp")));// ISP公司

//				System.out.println(buffer.toString());

				return buffer.toString();

			} else {
				return "获取地址失败";
			}
		}
		return null;
	}

	/**
	 * Unicode转中文
	 * 
	 * @param string
	 * @return
	 */
	private String decodeUnicode(String string) {
		char aChar;

		int len = string.length();

		StringBuffer buffer = new StringBuffer(len);

		for (int i = 0; i < len;) {

			aChar = string.charAt(i++);

			if (aChar == '\\') {

				aChar = string.charAt(i++);

				if (aChar == 'u') {

					int val = 0;

					for (int j = 0; j < 4; j++) {

						aChar = string.charAt(i++);

						switch (aChar) {

						case '0':

						case '1':

						case '2':

						case '3':

						case '4':

						case '5':

						case '6':

						case '7':

						case '8':

						case '9':

							val = (val << 4) + aChar - '0';

							break;

						case 'a':

						case 'b':

						case 'c':

						case 'd':

						case 'e':

						case 'f':

							val = (val << 4) + 10 + aChar - 'a';

							break;

						case 'A':

						case 'B':

						case 'C':

						case 'D':

						case 'E':

						case 'F':

							val = (val << 4) + 10 + aChar - 'A';

							break;

						default:

							throw new IllegalArgumentException(

							"Malformed      encoding.");
						}

					}

					buffer.append((char) val);

				} else {

					if (aChar == 't') {

						aChar = '\t';
					}

					if (aChar == 'r') {

						aChar = '\r';
					}

					if (aChar == 'n') {

						aChar = '\n';
					}

					if (aChar == 'f') {

						aChar = '\f';

					}

					buffer.append(aChar);
				}

			} else {

				buffer.append(aChar);

			}
		}
//		System.out.println(buffer.toString());
		return buffer.toString();
	}

	/**
	 * 从URL获取结果（JSON数据格式）
	 * 
	 * @param path
	 *            url
	 * @param params
	 *            参数
	 * @param encoding
	 *            编码
	 * @return
	 */
	private String getResult(String path, String params, String encoding) {
		URL url = null;
		HttpURLConnection connection = null;

		try {
			url = new URL(path);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(2000);// 连接超时2000毫秒
			connection.setReadTimeout(2000);// 读取数据超时时间2000毫秒
			connection.setDoInput(true);
			connection.setDoOutput(true);// 打开输入输出流
			connection.setRequestMethod("POST");// 提交方法POST
			connection.setUseCaches(false);
			connection.connect();// 打开连接端口

			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(params);
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),encoding));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
//			System.out.println(buffer.toString());
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		return null;
	}
	
	public static void main(String[] args) throws JSONException {
//		System.out.println(AddressUtil.getInstance().getAddress("ip=8.8.8.8", "utf-8"));
//		System.out.println(new AddressUtil().getAddress("ip=222.222.222.222", "utf-8"));
		System.out.println(new AddressUtil().getAddress("ip=66.249.65.137", "utf-8"));
	}
	
	
}
