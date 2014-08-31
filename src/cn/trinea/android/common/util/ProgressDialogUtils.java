package cn.trinea.android.common.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;

public class ProgressDialogUtils {
	 private static ProgressDialog mProgressDialog;

     /**
      * 点击"ProgressDialog以外的区域不会"ProgressDialog dismiss"，但点击后退键"进度条会"ProgressDialog dismiss"
      * @param context
      * @param message
      */
     public static void showProgressDialog(Context context, CharSequence message) {
            if ( mProgressDialog == null) {
                 mProgressDialog = ProgressDialog. show(context, "", message); 
                 mProgressDialog.setOnKeyListener( new OnKeyListener() {
                      @Override
                      public boolean onKey(DialogInterface dialog, int keyCode,
                                KeyEvent event) {
                            if (keyCode == KeyEvent. KEYCODE_BACK
                                     && event.getAction() == KeyEvent.ACTION_DOWN) {
                                 dismissProgressDialog();
                           }
                            return false;
                     }
                }); 

           } else {
                 mProgressDialog.show();
           }
     }

     /**
      * 
      * @param context
      * @param message
      * @param bool
      * <ul>
      * <li>false 点击"ProgressDialog以外的区域"或"后退键"进度条都不会"ProgressDialog dismiss"</li>
      * <li>true  点击"ProgressDialog以外的区域"或"后退键"进度条都会"ProgressDialog dismiss"</li>
      * </ul>
      * 
      * 
      */
     public static void showProgressDialog(Context context, CharSequence message,Boolean bool) {
         if ( mProgressDialog == null) {
              mProgressDialog = ProgressDialog. show(context, "", message, false,
            		  bool); 
        } else {
              mProgressDialog.show();
        }
  }
     
     /**
      * 关闭ProgressDialog
      */
     public static void dismissProgressDialog() {
            if ( mProgressDialog != null) {
                 mProgressDialog.dismiss();
                 mProgressDialog = null;
           }
     }
}
