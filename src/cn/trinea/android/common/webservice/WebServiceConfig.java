package cn.trinea.android.common.webservice;

import android.content.Context;

/**
 * <ul>
 *   ��Ҫ��string�����������ֶ�
 * <li>jar_webserviceurl �ӿڷ�����ַ http://192.168.72.108:901/Service1.asmx</li>
 * <li>jar_webservicenamespace �����ռ�(��Ҫ����б��/)http://microsoft.com/webservices</li>
 * <li>jar_webserviceuser </li>
 * <li>jar_webservicepwd  </li>
 * <li>jar_webservicerooturl  �ӿڷ�������ַ http://192.168.72.108:901</li>
 * <li>jar_webservicerootnamespace �����ռ�  Sample </li>
 * <li>jar_webserviceclassname ���� Service1 </li>
 * </ul>
 */

public class WebServiceConfig {
	
	private String webserviceurl = "";
	private String webservicenamespace = "";
	private String webserviceuser = "";
	private String webservicepwd = "";
	private String webservicerootnamespace = "";
	private String webservicerooturl="";
	private String webserviceclassname="";
	
	public String getWebserviceurl() {
		return webserviceurl;
	}

	public void setWebserviceurl(String webserviceurl) {
		this.webserviceurl = webserviceurl;
	}

	public String getWebservicenamespace() {
		return webservicenamespace;
	}

	public void setWebservicenamespace(String webservicenamespace) {
		this.webservicenamespace = webservicenamespace;
	}

	public String getWebserviceuser() {
		return webserviceuser;
	}

	public void setWebserviceuser(String webserviceuser) {
		this.webserviceuser = webserviceuser;
	}

	public String getWebservicepwd() {
		return webservicepwd;
	}

	public void setWebservicepwd(String webservicepwd) {
		this.webservicepwd = webservicepwd;
	}
	
	public String getWebservicerooturl() {
		return webservicerooturl;
	}

	public void setWebservicerooturl(String webservicerooturl) {
		this.webservicerooturl = webservicerooturl;
	}

	public String getWebserviceclassname() {
		return webserviceclassname;
	}

	public void setWebserviceclassname(String webserviceclassname) {
		this.webserviceclassname = webserviceclassname;
	}
	
	public String getWebservicerootnamespace() {
		return webservicerootnamespace;
	}

	public void setWebservicerootnamespace(String webservicerootnamespace) {
		this.webservicerootnamespace = webservicerootnamespace;
	}
	

	public WebServiceConfig() {

	}

	public WebServiceConfig(Context context) {
		this.webserviceurl = context.getString(MResource.getIdByName(context,
				"string", "jar_webserviceurl"));
		this.webservicenamespace = context.getString(MResource.getIdByName(
				context, "string", "jar_webservicenamespace"));
		this.webserviceuser = context.getString(MResource.getIdByName(context,
				"string", "jar_webserviceuser"));
		this.webservicepwd = context.getString(MResource.getIdByName(context,
				"string", "jar_webservicepwd"));
	}
	
	public WebServiceConfig(Context context,String httpwebservice) {
		this.webservicerooturl = context.getString(MResource.getIdByName(context,
				"string", "jar_webservicerooturl"));
		this.webservicerootnamespace = context.getString(MResource.getIdByName(
				context, "string", "jar_webservicerootnamespace"));
		this.webservicenamespace = context.getString(MResource.getIdByName(
				context, "string", "jar_webservicenamespace"));
		this.webserviceclassname = context.getString(MResource.getIdByName(
				context, "string", "jar_webserviceclassname"));
		this.webserviceuser = context.getString(MResource.getIdByName(context,
				"string", "jar_webserviceuser"));
		this.webservicepwd = context.getString(MResource.getIdByName(context,
				"string", "jar_webservicepwd"));
	}
	
}
