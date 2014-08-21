package cn.trinea.android.common.webservice;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParserException;

import cn.trinea.android.common.util.DigestUtils;
import android.util.Log;

/**
 * 利用android Ksoap 与Webservice交互
 * 
 */
public class WebServiceKsoapUtils {
	

	/***
	 * 基于SoapHeader的WebService调用实现 调用WebService 不带参数 tempMap写null ,带参数的传入参数集合。
	 * 参数值与服务端的WebService类中的方法参数名要一致
	 * 
	 * @param methodName
	 *            方法名 HelloWorld
	 * @param url
	 *            发布地址 http://192.168.72.108:901/Service1.asmx
	 * @param nameSpace
	 *            命名空间(不要带反斜杠/) http://tempuri.org 
	 * @param tempMap
	 *            参数 调用如： Map tempMap = new HashMap(); tempMap.put("a", "1213");
	 *            tempMap.put("b", "3435");
	 * @param uname
	 *            访问webService账号
	 * @param pwd
	 *            访问webService密码
	 * @return
	 */

	public static String GetWebService(String method, Map tempMap,
			String wsURL, String wsNAMESPACE, String wsUser, String wspwd) {

		String METHOD_NAME = method;
		// 此处是命名空间+方法名
		String SOAP_ACTION = wsNAMESPACE + "/" + method + "";
		// 主要代码在这里
		Element[] header = new Element[1];
		header[0] = new Element().createElement(wsNAMESPACE,
				"ServiceSoapHeader");
		Element userName = new Element().createElement(wsNAMESPACE, "UserName");
		userName.addChild(Node.TEXT, wsUser);
		header[0].addChild(Node.ELEMENT, userName);

		Element pass = new Element().createElement(wsNAMESPACE, "Password");
		pass.addChild(Node.TEXT, DigestUtils.md5NET(wspwd));
		header[0].addChild(Node.ELEMENT, pass);
		// 指定WebService的命名空间和调用的方法名
		SoapObject rpc = new SoapObject(wsNAMESPACE, METHOD_NAME);
		// 设置需调用WebService接口需要传入的两个参数mobileCode、userId
		Iterator it = tempMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			rpc.addProperty(entry.getKey().toString(), entry.getValue());
		}
		// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.headerOut = header;
		envelope.bodyOut = rpc;
		// 设置是否调用的是dotNet开发的WebService
		envelope.dotNet = true;
		envelope.setOutputSoapObject(rpc);
		HttpTransportSE transport = new HttpTransportSE(wsURL);
		Object object = null;
		String sResult = "";
		try {
			// 调用WebService
			transport.call(SOAP_ACTION, envelope);
			object = envelope.getResponse();
			if (object == null) { // 返回值为 null
				sResult = "";
			} else if ("anyType{}".equals(object.toString())) { // 返回值为空字符串
				sResult = "";
			} else {
				sResult = object.toString();
			}
		} catch (Exception e) {
			Log.i("xx", "Exception接口异常=" + e.toString());
			e.printStackTrace();
		}
		return sResult;

	}

	/***
	 * 不基于SoapHeader的WebService调用实现 调用WebService 不带参数最后一个写null ,带参数的传入参数集合。
	 * 参数值与服务端的WebService类中的方法参数名要一致
	 * 
	 * @param methodName
	 *            方法名 HelloWorld
	 * @param url
	 *            发布地址 http://192.168.72.108:901/Service1.asmx
	 * @param nameSpace
	 *            命名空间(不要带反斜杠/) http://tempuri.org 
	 * @param tempMap
	 *            参数 调用如： Map tempMap = new HashMap(); tempMap.put("a", "1213");
	 *            tempMap.put("b", "3435");
	 * @return
	 */
	public static String GetWebService(String method, Map tempMap,
			String wsURL, String wsNAMESPACE) {
		String soapAction = wsNAMESPACE +"/"+ method; // 命名空间/方法
		SoapObject rpc = new SoapObject(wsNAMESPACE, method); // 创建SoapObject实例,指定WebService的命名空间和调用的方法名
		if (tempMap != null && tempMap.size() > 0) {
			// 设置需调用WebService接口需要传入的参数
			Iterator it = tempMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				rpc.addProperty(entry.getKey().toString(), entry.getValue());
			}
		}
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		envelope.bodyOut = rpc;
		envelope.dotNet = true; // 设置是否调用的是dotNet开发的WebService
		envelope.setOutputSoapObject(rpc);// 发送请求 SoapObject实例
		HttpTransportSE transport = new HttpTransportSE(wsURL);
		Object object = null;
		String sResult = ""; // 返回的结果
		try {
			transport.call(soapAction, envelope); // 调用WebService
			object = envelope.getResponse(); // 得到返回值
			if (object == null) { // 返回值为 null
				sResult = "";
			} else if (object.toString().equals("anyType{}")) { // 返回值为空字符串
				sResult = "";
			} else {
				sResult = object.toString();
			}
		} catch (IOException e) {
			Log.i("xx", "IOException接口异常=" + e.toString());
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			Log.i("xx", "XmlPullParserException接口异常=" + e.toString());
			e.printStackTrace();
		}
		return sResult;
	}
}
