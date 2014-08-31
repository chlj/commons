package cn.trinea.android.common.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;

public class ProgressDialogUtils {
	 private static ProgressDialog mProgressDialog;

     /**
      * ���"ProgressDialog��������򲻻�"ProgressDialog dismiss"����������˼�"��������"ProgressDialog dismiss"
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
      * <li>false ���"ProgressDialog���������"��"���˼�"������������"ProgressDialog dismiss"</li>
      * <li>true  ���"ProgressDialog���������"��"���˼�"����������"ProgressDialog dismiss"</li>
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
      * �ر�ProgressDialog
      */
     public static void dismissProgressDialog() {
            if ( mProgressDialog != null) {
                 mProgressDialog.dismiss();
                 mProgressDialog = null;
           }
     }
}
