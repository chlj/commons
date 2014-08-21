package cn.trinea.android.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;

/**
 * ResourceUtils
 * 
 * 
 */
public class ResourceUtils {

	/**
	 * ��Assets�ж�ȡ�ı�
	 * 
	 * @param context
	 * @param fileName
	 * @param charset
	 * @return
	 */
	public static String getStringFromAssetsFile(Context context,
			String fileName, String charset) {
		if (context == null || StringUtils.isEmpty(fileName)) {
			return null;
		}
		try {
			InputStreamReader in = new InputStreamReader(
					getInputStreamFromAssetsFile(context, fileName), charset);
			String str = getStringFromInputStreamReader(in);
			return str;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ��Assets�ж�ȡͼƬ
	 * 
	 * @param context
	 * @param fileName
	 *            getImageFromAssetsFile("Cat_Blink/cat_blink0000.png");
	 * @return
	 */
	public static Bitmap getImageFromAssetsFile(Context context, String fileName) {

		Bitmap image = null;
		InputStream is = getInputStreamFromAssetsFile(context, fileName);
		if (is != null) {
			image = ImageUtils.getBitMapByInputStream(is);
		}
		return image;
	}

	/***
	 * ��Assets�ж�ȡxml pull������
	 * 
	 * @param context
	 * 
	 * @param fileName
	 *            �ļ���
	 * @param charset
	 *            �ַ���
	 * @return
	 */
	public static XmlPullParser getXmlFromAssetsFile(Context context,
			String fileName, String charset) {
		XmlPullParser xpp = null;
		InputStream is = getInputStreamFromAssetsFile(context, fileName);
		if (is != null) {
			xpp = getXmlFromInputStream(is, charset);
		}
		return xpp;

	}

	/**
	 * ��Assets�е��ļ�ת��ΪInputStream
	 * 
	 * @param context
	 * @param fileName
	 *            �ļ���
	 * @return
	 */
	public static InputStream getInputStreamFromAssetsFile(Context context,
			String fileName) {
		AssetManager am = context.getResources().getAssets();
		InputStream is = null;
		try {
			is = am.open(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return is;
	}

	/**
	 * ��Raw�ж�ȡ�ı�
	 * 
	 * @param context
	 * @param fileName
	 *            �ļ���
	 * @param charset
	 *            �ַ���
	 * @return
	 */
	public static String getStringFromRawFile(Context context, int resId,
			String charset) {
		if (context == null) {
			return null;
		}
		try {
			InputStreamReader in = new InputStreamReader(getInputStreamFromRaw(
					context, resId), charset);
			String str = getStringFromInputStreamReader(in);
			return str;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ��Raw�ж�ȡͼƬ
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap getImageFromRawFile(Context context, int resId) {
		Bitmap image = null;
		InputStream is = getInputStreamFromRaw(context, resId);
		if (is != null) {
			image = ImageUtils.getBitMapByInputStream(is);
		}
		return image;
	}

	/**
	 * ��Raw�ж�ȡxml pull������
	 * 
	 * @param context
	 * @param resId
	 * @param charset
	 * @return
	 */
	public static XmlPullParser getXmlFromRawFile(Context context, int resId,
			String charset) {
		XmlPullParser xpp = null;
		InputStream is = getInputStreamFromRaw(context, resId);
		if (is != null) {
			xpp = getXmlFromInputStream(is, charset);
		}
		return xpp;
	}

	/**
	 * ��Raw�ļ����е��ļ�ת��Ϊ InputStream
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static InputStream getInputStreamFromRaw(Context context, int resId) {
		InputStream is = context.getResources().openRawResource(resId);
		return is;
	}

	/**
	 * ��InputStreamReaderת��Ϊ�ַ���
	 * 
	 * @param in
	 *            InputStreamReader ������
	 * @return �ַ���
	 */
	public static String getStringFromInputStreamReader(InputStreamReader in) {
		if (in != null) {
			StringBuilder s = new StringBuilder("");
			try {
				BufferedReader br = new BufferedReader(in);
				String line;
				while ((line = br.readLine()) != null) {
					s.append(line);
				}
				return s.toString();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}

	}

	/**
	 * ��InputStreamת��Ϊ�ַ���
	 * 
	 * @param is
	 *            InputStream����
	 * @return
	 */
	public static String getStringFromInputStream(InputStream is) {
		if (is != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int i = -1;
			try {
				while ((i = is.read()) != -1) {
					baos.write(i);
				}
			} catch (IOException e) {
				
			}
			return baos.toString();

		} else {
			return null;
		}
	}

	/**
	 * ��InputStreamת��ΪXmlPullParser
	 * 
	 * @param is
	 *            InputStream��
	 * @param charset
	 *            �ַ���
	 * @return
	 */
	public static XmlPullParser getXmlFromInputStream(InputStream is,
			String charset) {
		if (is != null) {
			XmlPullParserFactory factory = null;
			XmlPullParser xpp = null;
			try {
				factory = XmlPullParserFactory.newInstance();
				factory.setNamespaceAware(true);
				xpp = factory.newPullParser();
				if (is != null) {
					xpp.setInput(is, charset);
				}
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			}
			return xpp;

		} else {

			return null;
		}
	}

}
