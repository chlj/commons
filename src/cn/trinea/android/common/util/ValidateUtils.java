package cn.trinea.android.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {

	/**
	 * 验证手机+座机
	 * @param str
	 * @return 验证通过返回true 
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
     * 手机号验证 
     *  
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isMobile(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }  
    /** 
     * 座机号验证 
     *  
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isPhone(String str) {   
        Pattern p1 = null,p2 = null;  
        Matcher m = null;  
        boolean b = false;    
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的  
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的  
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
     * 验证邮箱格式是否正确
     */
    public boolean emailValidation(String email) {
          String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
          return email.matches(regex);
    }
    
}
