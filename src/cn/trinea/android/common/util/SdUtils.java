package cn.trinea.android.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.os.Environment;

/**
 * 对sd卡进行操作
 * 
 * @author Administrator
 * 
 */
public class SdUtils {

	/**
	 * 判断SD卡是否存在，并且是否具有读写权限
	 * 
	 * @return 存在返回true，不存在返回false
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
	 * 将 字符串保存到 手机内存卡中
	 * 
	 * <pre>
	 * eg: SdUtils.InputStreamSaveSD("将 字符串保存到 手机内存卡中试", "123.txt", "NN_Log");
	 * </pre>
	 * 
	 * @param str
	 *            字符串
	 * @param fileName
	 *            文件名 2012-15-12.txt
	 * @param rootdir
	 *            eg： NN_Log 文件夹
	 */
	public static Boolean StringSaveSD(String str, String fileName,
			String rootdir) {
		// 判断SD卡是否存在，并且是否具有读写权限
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
	 * 将InputStream所对应的内容写入到sd卡中去
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
}
