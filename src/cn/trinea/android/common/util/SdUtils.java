package cn.trinea.android.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;
import android.util.Log;

/**
 * ��sd�����в���
 * 
 * @author Administrator
 * 
 */
public class SdUtils {

	/**
	 * �ж�SD���Ƿ���ڣ������Ƿ���ж�дȨ��
	 * 
	 * @return ���ڷ���true�������ڷ���false
	 */
	public static Boolean IsExist() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;

		}

	}

	/**
	 * �� �ַ������浽 �ֻ��ڴ濨��
	 * 
	 * <pre>
	 * eg: SdUtils.InputStreamSaveSD("�� �ַ������浽 �ֻ��ڴ濨����", "123.txt", "NN_Log");
	 * </pre>
	 * 
	 * @param str
	 *            �ַ���
	 * @param fileName
	 *            �ļ��� 2012-15-12.txt
	 * @param rootdir
	 *            eg�� NN_Log �ļ���
	 */
	public static Boolean StringSaveSD(String str, String fileName,
			String rootdir) {
		// �ж�SD���Ƿ���ڣ������Ƿ���ж�дȨ��
		Boolean bool = false;
		if (IsExist()) {
			try {
				File dir = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + File.separator + rootdir);
				if (!dir.exists())
					dir.mkdir();
				FileOutputStream fos = new FileOutputStream(new File(dir,
						fileName));
				fos.write(str.toString().getBytes());
				fos.write("\r\n".getBytes());
				fos.close();
				bool = true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bool;
	}

	/**
	 * ��InputStream����Ӧ������д�뵽sd����ȥ
	 * 
	 * <pre>
	 * eg: SdUtils.InputStreamSaveSD(is, "123.docx", "crash_wcp");
	 * </pre>
	 * 
	 * @param in
	 * @param fileName
	 * @param rootdir
	 * @return
	 */
	public static Boolean InputStreamSaveSD(InputStream in, String fileName,
			String rootdir) {
		Boolean bool = false;
		if (IsExist()) {
			File dir = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + File.separator + rootdir);
			if (!dir.exists())
				dir.mkdir();
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(new File(dir, fileName));
				byte[] buffer = new byte[1024];
				int length;
				try {
					while ((length = in.read(buffer)) > 0) {
						fos.write(buffer, 0, length);
					}
					fos.close();
					in.close();
					bool = true;
				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return bool;
	}
	
	
	/**
	 * ��ָ��·�����ļ�������sd����ȥ
	 * <pre>
	 *  eg: SdUtils.pathSaveSD("/data/data/datebases/test.db", "123.db", "crash_wcp");
	 * </pre>
	 * @param sourcePath
	 * @param fileName
	 * @param rootdir
	 * @return
	 */
	public static Boolean pathSaveSD(String sourcePath, String fileName, String rootdir){
		Boolean bool = false;
		FileInputStream fis; 
		try {
			 fis = new FileInputStream(sourcePath);
			if(InputStreamSaveSD(fis, fileName, rootdir)){
				bool = true;
			}
			else{
				bool = false;
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return bool;
	}
	
	
	/**
	 * ��SD���е��ļ�,��ԭ��data�е��ļ���ȥ
	 * 
	 * @param sourcePath
	 *            Ҫ��ԭ������Դ /mnt/sdcard/baoyue8/zhx_bak
	 * @param DATABASE_PATH
	 *            ���ݿ�·�� /data/data/com.example.android_jqss/databases
	 * @param DATABASE_NAME
	 *            ���ݿ����� zhx
	 * @return
	 */
	public static Boolean pathSaveToDatabases(String sourcePath,
			String DATABASE_PATH, String DATABASE_NAME) {
		Boolean bool = false;

		File dir = new File(DATABASE_PATH);
		if (!dir.exists() || !dir.isDirectory()) { // ���·�������� ���� ��·������Ŀ¼
			dir.mkdir(); // ����·��
		}
		File file = new File(DATABASE_PATH + "/" + DATABASE_NAME);
		if (file.exists()) {
			file.delete();
		}
		FileInputStream fis;
		try {
			fis = new FileInputStream(sourcePath);
			try {
				OutputStream myOutput = new FileOutputStream(DATABASE_PATH
						+ "/" + DATABASE_NAME);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = fis.read(buffer)) > 0) {
					myOutput.write(buffer, 0, length);
				}
				myOutput.close();
				fis.close();
				bool = true;
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return bool;
	}
	
}
