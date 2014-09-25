package cn.trinea.android.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {

	/**
	 * ��֤�ֻ�+����
	 * @param str
	 * @return ��֤ͨ������true 
	 */
	public static boolean isMobileAndPhone(String str){
		Boolean flag=false;
		 if(isMobile(str)){
			 flag=true;
		 }
		 else{
			 if(isPhone(str)){
				 flag=true;
			 }else{
				 flag=false;
			 }
		 }
		
		return flag;
	}
	/** 
     * �ֻ�����֤ 
     *  
     * @param  str 
     * @return ��֤ͨ������true 
     */  
    public static boolean isMobile(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // ��֤�ֻ���  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }  
    /** 
     * ��������֤ 
     *  
     * @param  str 
     * @return ��֤ͨ������true 
     */  
    public static boolean isPhone(String str) {   
        Pattern p1 = null,p2 = null;  
        Matcher m = null;  
        boolean b = false;    
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // ��֤�����ŵ�  
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // ��֤û�����ŵ�  
        if(str.length() >9)  
        {   m = p1.matcher(str);  
            b = m.matches();    
        }else{  
            m = p2.matcher(str);  
            b = m.matches();   
        }    
        return b;  
    }  
    
    /**
     * ��֤�����ʽ�Ƿ���ȷ
     */
    public boolean emailValidation(String email) {
          String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
          return email.matches(regex);
    }
    
}
