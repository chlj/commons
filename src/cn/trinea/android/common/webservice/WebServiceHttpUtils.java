package cn.trinea.android.common.webservice;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.util.Log;
import cn.trinea.android.common.util.ResourceUtils;
import cn.trinea.android.common.util.StringUtils;

/**
 * 模拟http post提交 与Webservice交互
 * @author Administrator
 *
 */
public class WebServiceHttpUtils {

	/**
	 * 传递给.net接口的 base64字符串
	 * 
	 * @param method
	 *            方法名
	 * @param list
	 *            参数名
	 * @param wsURL
	 *            接口发布地址 http://192.168.72.108:901/Service1.asmx
	 * @param wsNAMESPACE
	 *            接口命名空间
	 * @param wsUser
	 *            用户名
	 * @param wspwd
	 *            密码
	 * @param classname
	 *            接口类名称
	 * @return 返回已经加密过的base64字符串
	 */
	public static String GetWebService(String method, List<String> list,
			String wsURL, String wsNAMESPACE, String wsUser, String wspwd,
			String classname) {

		StringBuilder str = new StringBuilder();
		str.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		str.append("<content>");

		str.append("<command>");
		str.append(method);
		str.append("</command>");

		str.append("<params>");

		str.append("<param>");
		str.append("<type>");
		str.append("string");
		str.append("</type>");
		str.append("<value>");
		str.append(wsURL);
		str.append("</value>");
		str.append("</param>");

		str.append("<param>");
		str.append("<type>");
		str.append("string");
		str.append("</type>");
		str.append("<value>");
		str.append(wsNAMESPACE);// 命名空间
		str.append("</value>");
		str.append("</param>");

		str.append("<param>");
		str.append("<type>");
		str.append("string");
		str.append("</type>");
		str.append("<value>");
		str.append(classname);// 类名称
		str.append("</value>");
		str.append("</param>");

		str.append("<param>");
		str.append("<type>");
		str.append("string");
		str.append("</type>");
		str.append("<value>");
		str.append(method);// 函数方法
		str.append("</value>");
		str.append("</param>");

		str.append("<param>");
		str.append("<type>");
		str.append("string");
		str.append("</type>");
		str.append("<value>");
		str.append(wsUser);// 用户名
		str.append("</value>");
		str.append("</param>");

		str.append("<param>");
		str.append("<type>");
		str.append("string");
		str.append("</type>");
		str.append("<value>");
		str.append(wspwd);// 密码
		str.append("</value>");
		str.append("</param>");

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				str.append("<param>");
				str.append("<type>");
				str.append("string");
				str.append("</type>");
				str.append("<value>");
				str.append(list.get(i).trim());
				str.append("</value>");
				str.append("</param>");
			}
		}
		str.append("</params>");
		str.append("</content>");

		return StringUtils.encodeToBase64String(str.toString());
	}

	/**
	 * 调用.net接口
	 * 
	 * @param AD
	 *            接口挂载的根地址 eg:http://10.149.1.70:901
	 * @param contextString
	 *            传递给.net接口的 base64加密字符串
	 * @return 接口返回的值，已经进行了base64解密
	 */
	public static String ASPLowRquest(String AD, String contextString) {
		StringBuilder sb_result = new StringBuilder();//
		try {
			String BOUNDARY = "---------7d4a6d158c9";//

			URL urll = new URL(AD);
			String MULTIPART_FORM_DATA = "multipart/form-data";
			HttpURLConnection conn = (HttpURLConnection) urll.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "Keep-Alive");
			// conn.setRequestProperty("content-type", MULTIPART_FORM_DATA
			// + ";boundary=" + BOUNDARY); // 模拟器使用
			conn.setRequestProperty("content-type", "text/html"); // 真机使用
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setRequestProperty("Charset", "UTF-8");
			OutputStream out = new DataOutputStream(conn.getOutputStream());
			StringBuilder sb = new StringBuilder();
			sb.append("\r\n--" + BOUNDARY + "\r\n");
			sb.append("Content-Disposition: form-data;name=\"Input\"\r\n\r\n");
			sb.append(contextString);
			sb.append("\r\n");
			byte[] data = sb.toString().getBytes("UTF-8");
			out.write(data);
			byte[] end_data = ("--" + BOUNDARY + "--\r\n").getBytes();// 结束掉
			out.write(end_data);
			out.flush();
			out.close();
			InputStream is = conn.getInputStream(); // 获取到数据
			String string = ResourceUtils.getStringFromInputStream(is);
			string = StringUtils.decodeToBase64String(string);// base64解码
			sb_result.append(string);
			// ------------------------------------------------
			is.close();

			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb_result.toString().trim();

	}

	/**
	 * 调用.net接口
	 * 
	 * @param AD
	 *            接口挂载的根地址 eg:http://10.149.1.70:901
	 * @param contextString
	 *            传递给.net接口的 base64加密字符串
	 * @param filename
	 *            附件所在的路径 
	 * 
	 * @return 接口返回的值，已经进行了base64解密
	 */
	public static String ASPLowRquest(String AD, String contextString,
			String filename) {
		StringBuilder sb_result = new StringBuilder();
		try {
			String BOUNDARY = "---------7d4a6d158c9";
			URL urll = new URL(AD);
			String MULTIPART_FORM_DATA = "multipart/form-data";
			HttpURLConnection conn = (HttpURLConnection) urll.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "Keep-Alive");
//			conn.setRequestProperty("content-type", MULTIPART_FORM_DATA
//					+ ";boundary=" + BOUNDARY); // 模拟器使用
		    conn.setRequestProperty("content-type", "text/html"); // 真机使用
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setRequestProperty("Charset", "UTF-8");
			OutputStream out = new DataOutputStream(conn.getOutputStream());
			StringBuilder sb = new StringBuilder();
			sb.append("\r\n--" + BOUNDARY + "\r\n");
			sb.append("Content-Disposition: form-data;name=\"Input\"\r\n\r\n");
			sb.append(contextString);
			sb.append("\r\n");
			byte[] data = sb.toString().getBytes("UTF-8");
			out.write(data);

			File file = null;
			if (filename != null && !"".equals(filename)) {
				sb = new StringBuilder();
				file = new File(filename);
				Log.i("xx", file.getName());
				sb.append("\r\n--" + BOUNDARY + "\r\n");
				sb.append("Content-Disposition: form-data;name=\"file1\";filename=\""
						+ file.getName() + "\"\r\n");
				sb.append("Content-Type:application/octet-stream");
				sb.append("\r\n\r\n");
				data = sb.toString().getBytes();
				out.write(data);
			}
			if (filename != null && !"".equals(filename)) {
				DataInputStream in = new DataInputStream(new FileInputStream(
						file));
				int bytes = 0;
				byte[] bufferOut = new byte[1024];
				while ((bytes = in.read(bufferOut)) != -1) {
					out.write(bufferOut, 0, bytes);
				}
				out.write("\r\n".getBytes());
				in.close();
			}

			byte[] end_data = ("--" + BOUNDARY + "--\r\n").getBytes();// 结束掉
			out.write(end_data);
			out.flush();
			out.close();
			InputStream is = conn.getInputStream(); // 获取到数据
			String string = ResourceUtils.getStringFromInputStream(is);
			string = StringUtils.decodeToBase64String(string);// base64解码
			sb_result.append(string);
			// ------------------------------------------------
			is.close();

			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb_result.toString().trim();
	}
}
