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
 * ����android Ksoap ��Webservice����
 * 
 */
public class WebServiceKsoapUtils {
	

	/***
	 * ����SoapHeader��WebService����ʵ�� ����WebService �������� tempMapдnull ,�������Ĵ���������ϡ�
	 * ����ֵ�����˵�WebService���еķ���������Ҫһ��
	 * 
	 * @param methodName
	 *            ������ HelloWorld
	 * @param url
	 *            ������ַ http://192.168.72.108:901/Service1.asmx
	 * @param nameSpace
	 *            �����ռ�(��Ҫ����б��/) http://tempuri.org 
	 * @param tempMap
	 *            ���� �����磺 Map tempMap = new HashMap(); tempMap.put("a", "1213");
	 *            tempMap.put("b", "3435");
	 * @param uname
	 *            ����webService�˺�
	 * @param pwd
	 *            ����webService����
	 * @return
	 */

	public static String GetWebService(String method, Map tempMap,
			String wsURL, String wsNAMESPACE, String wsUser, String wspwd) {

		String METHOD_NAME = method;
		// �˴��������ռ�+������
		String SOAP_ACTION = wsNAMESPACE + "/" + method + "";
		// ��Ҫ����������
		Element[] header = new Element[1];
		header[0] = new Element().createElement(wsNAMESPACE,
				"ServiceSoapHeader");
		Element userName = new Element().createElement(wsNAMESPACE, "UserName");
		userName.addChild(Node.TEXT, wsUser);
		header[0].addChild(Node.ELEMENT, userName);

		Element pass = new Element().createElement(wsNAMESPACE, "Password");
		pass.addChild(Node.TEXT, DigestUtils.md5NET(wspwd));
		header[0].addChild(Node.ELEMENT, pass);
		// ָ��WebService�������ռ�͵��õķ�����
		SoapObject rpc = new SoapObject(wsNAMESPACE, METHOD_NAME);
		// ���������WebService�ӿ���Ҫ�������������mobileCode��userId
		Iterator it = tempMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			rpc.addProperty(entry.getKey().toString(), entry.getValue());
		}
		// ���ɵ���WebService������SOAP������Ϣ,��ָ��SOAP�İ汾
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.headerOut = header;
		envelope.bodyOut = rpc;
		// �����Ƿ���õ���dotNet������WebService
		envelope.dotNet = true;
		envelope.setOutputSoapObject(rpc);
		HttpTransportSE transport = new HttpTransportSE(wsURL);
		Object object = null;
		String sResult = "";
		try {
			// ����WebService
			transport.call(SOAP_ACTION, envelope);
			object = envelope.getResponse();
			if (object == null) { // ����ֵΪ null
				sResult = "";
			} else if ("anyType{}".equals(object.toString())) { // ����ֵΪ���ַ���
				sResult = "";
			} else {
				sResult = object.toString();
			}
		} catch (Exception e) {
			Log.i("xx", "Exception�ӿ��쳣=" + e.toString());
			e.printStackTrace();
		}
		return sResult;

	}

	/***
	 * ������SoapHeader��WebService����ʵ�� ����WebService �����������һ��дnull ,�������Ĵ���������ϡ�
	 * ����ֵ�����˵�WebService���еķ���������Ҫһ��
	 * 
	 * @param methodName
	 *            ������ HelloWorld
	 * @param url
	 *            ������ַ http://192.168.72.108:901/Service1.asmx
	 * @param nameSpace
	 *            �����ռ�(��Ҫ����б��/) http://tempuri.org 
	 * @param tempMap
	 *            ���� �����磺 Map tempMap = new HashMap(); tempMap.put("a", "1213");
	 *            tempMap.put("b", "3435");
	 * @return
	 */
	public static String GetWebService(String method, Map tempMap,
			String wsURL, String wsNAMESPACE) {
		String soapAction = wsNAMESPACE +"/"+ method; // �����ռ�/����
		SoapObject rpc = new SoapObject(wsNAMESPACE, method); // ����SoapObjectʵ��,ָ��WebService�������ռ�͵��õķ�����
		if (tempMap != null && tempMap.size() > 0) {
			// ���������WebService�ӿ���Ҫ����Ĳ���
			Iterator it = tempMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				rpc.addProperty(entry.getKey().toString(), entry.getValue());
			}
		}
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // ���ɵ���WebService������SOAP������Ϣ,��ָ��SOAP�İ汾
		envelope.bodyOut = rpc;
		envelope.dotNet = true; // �����Ƿ���õ���dotNet������WebService
		envelope.setOutputSoapObject(rpc);// �������� SoapObjectʵ��
		HttpTransportSE transport = new HttpTransportSE(wsURL);
		Object object = null;
		String sResult = ""; // ���صĽ��
		try {
			transport.call(soapAction, envelope); // ����WebService
			object = envelope.getResponse(); // �õ�����ֵ
			if (object == null) { // ����ֵΪ null
				sResult = "";
			} else if (object.toString().equals("anyType{}")) { // ����ֵΪ���ַ���
				sResult = "";
			} else {
				sResult = object.toString();
			}
		} catch (IOException e) {
			Log.i("xx", "IOException�ӿ��쳣=" + e.toString());
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			Log.i("xx", "XmlPullParserException�ӿ��쳣=" + e.toString());
			e.printStackTrace();
		}
		return sResult;
	}
}
