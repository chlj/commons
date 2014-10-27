package cn.trinea.android.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.Layout.Alignment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * ImageUtils
 * <ul>
 * convert between Bitmap, byte array, Drawable
 * <li>{@link #bitmapToByte(Bitmap)}</li>
 * <li>{@link #bitmapToDrawable(Bitmap)}</li>
 * <li>{@link #byteToBitmap(byte[])}</li>
 * <li>{@link #byteToDrawable(byte[])}</li>
 * <li>{@link #drawableToBitmap(Drawable)}</li>
 * <li>{@link #drawableToByte(Drawable)}</li>
 * </ul>
 * <ul>
 * get image
 * <li>{@link #getInputStreamFromUrl(String, int)}</li>
 * <li>{@link #getBitmapFromUrl(String, int)}</li>
 * <li>{@link #getDrawableFromUrl(String, int)}</li>
 * </ul>
 * <ul>
 * scale image
 * <li>{@link #scaleImageTo(Bitmap, int, int)}</li>
 * <li>{@link #scaleImage(Bitmap, float, float)}</li>
 * </ul>
 * 
 * 
 */
public class ImageUtils {

	/**
	 * ��Bitmapת��Ϊbyte[]
	 * 
	 * @param b
	 *            Bitmap ����
	 * @return byte[]
	 */
	public static byte[] bitmapToByte(Bitmap b) {
		if (b == null) {
			return null;
		}

		ByteArrayOutputStream o = new ByteArrayOutputStream();
		b.compress(Bitmap.CompressFormat.PNG, 100, o);
		return o.toByteArray();
	}

	/**
	 * ��byte[]ת��ΪBitmap����
	 * 
	 * @param b
	 *            byte[]
	 * @return Bitmap
	 */
	public static Bitmap byteToBitmap(byte[] b) {
		return (b == null || b.length == 0) ? null : BitmapFactory
				.decodeByteArray(b, 0, b.length);
	}

	/**
	 * ��Drawable����ת��ΪBitmap
	 * 
	 * @param d
	 *            Drawable ����
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable d) {
		return d == null ? null : ((BitmapDrawable) d).getBitmap();
	}

	/**
	 * ��Bitmap����ת��ΪDrawable
	 * 
	 * @param b
	 *            Bitmap ����
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap b) {
		return b == null ? null : new BitmapDrawable(b);
	}

	/**
	 * ��Drawable����ת��Ϊbyte[]
	 * 
	 * @param d
	 *            Drawable����
	 * @return
	 */
	public static byte[] drawableToByte(Drawable d) {
		return bitmapToByte(drawableToBitmap(d));
	}

	/**
	 * ��byte[]ת��ΪDrawable
	 * 
	 * @param b
	 *            byte[]
	 * @return
	 */
	public static Drawable byteToDrawable(byte[] b) {
		return bitmapToDrawable(byteToBitmap(b));
	}

	/**
	 * 
	 * ��URL�еõ�InputStream,����Ҫ�ֶ��Ĺر�InputStream
	 * 
	 * @param imageUrl
	 *            �����ַ
	 * @param readTimeOutMillis
	 *            ���ô�������ȡ���ݳ�ʱ����λ�����룩
	 * @return
	 * @see ImageUtils#getInputStreamFromUrl(String, int, boolean)
	 */
	public static InputStream getInputStreamFromUrl(String imageUrl,
			int readTimeOutMillis) {
		return getInputStreamFromUrl(imageUrl, readTimeOutMillis, null);
	}

	/**
	 * ��URL�еõ�InputStream,����Ҫ�ֶ��Ĺر�InputStream
	 * 
	 * 
	 * @param imageUrl
	 *            �����ַ
	 * @param readTimeOutMillis
	 *            ���ô�������ȡ���ݳ�ʱ����λ�����룩
	 * @param requestProperties
	 *            ��������˵��
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static InputStream getInputStreamFromUrl(String imageUrl,
			int readTimeOutMillis, Map<String, String> requestProperties) {
		InputStream stream = null;
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			HttpUtils.setURLConnection(requestProperties, con);
			if (readTimeOutMillis > 0) {
				con.setReadTimeout(readTimeOutMillis);
			}
			stream = con.getInputStream();
		} catch (MalformedURLException e) {
			closeInputStream(stream);
			throw new RuntimeException("MalformedURLException occurred. ", e);
		} catch (IOException e) {
			closeInputStream(stream);
			throw new RuntimeException("IOException occurred. ", e);
		}
		return stream;
	}

	/**
	 * ��URL�еõ�Drawable
	 * 
	 * @param imageUrl
	 *            ��ַ
	 * @param readTimeOutMillis
	 *            ���ô�������ȡ���ݳ�ʱ����λ�����룩
	 * @return
	 * @see ImageUtils#getDrawableFromUrl(String, int, boolean)
	 */
	public static Drawable getDrawableFromUrl(String imageUrl,
			int readTimeOutMillis) {
		return getDrawableFromUrl(imageUrl, readTimeOutMillis, null);
	}

	/**
	 * ��URL�еõ�Drawable
	 * 
	 * @param imageUrl
	 *            ��ַ
	 * @param readTimeOutMillis
	 *            ���ô�������ȡ���ݳ�ʱ����λ�����룩
	 * @param requestProperties
	 *            ��������˵��
	 * @return
	 */
	public static Drawable getDrawableFromUrl(String imageUrl,
			int readTimeOutMillis, Map<String, String> requestProperties) {
		InputStream stream = getInputStreamFromUrl(imageUrl, readTimeOutMillis,
				requestProperties);
		Drawable d = Drawable.createFromStream(stream, "src");
		closeInputStream(stream);
		return d;
	}

	/**
	 * ��URL�еõ�Bitmap
	 * 
	 * @param imageUrl
	 *            ��ַ
	 * @param readTimeOut
	 *            ���ô�������ȡ���ݳ�ʱ����λ�����룩
	 * @return
	 * @see ImageUtils#getBitmapFromUrl(String, int, boolean)
	 */
	public static Bitmap getBitmapFromUrl(String imageUrl, int readTimeOut) {
		return getBitmapFromUrl(imageUrl, readTimeOut, null);
	}

	/**
	 * ��URL�еõ�Bitmap
	 * 
	 * @param imageUrl
	 *            ��ַ
	 * @param readTimeOut
	 *            ���ô�������ȡ���ݳ�ʱ����λ�����룩
	 * @param requestProperties
	 *            ��������˵��
	 * @return
	 */

	public static Bitmap getBitmapFromUrl(String imageUrl, int readTimeOut,
			Map<String, String> requestProperties) {
		InputStream stream = getInputStreamFromUrl(imageUrl, readTimeOut,
				requestProperties);
		Bitmap b = getBitMapByInputStream(stream);
		closeInputStream(stream);
		return b;
	}

	/**
	 * scale image ͼƬ����
	 * 
	 * @param org
	 *            Bitmap����
	 * @param newWidth
	 *            ���ź�Ŀ�
	 * @param newHeight
	 *            ���ź�ĸ�
	 * @return
	 */
	public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
		return scaleImage(org, (float) newWidth / org.getWidth(),
				(float) newHeight / org.getHeight());
	}

	/**
	 * scale image
	 * 
	 * @param org
	 * @param scaleWidth
	 *            sacle of width
	 * @param scaleHeight
	 *            scale of height
	 * @return
	 */
	public static Bitmap scaleImage(Bitmap org, float scaleWidth,
			float scaleHeight) {
		if (org == null) {
			return null;
		}
		// ��������ͼƬ�õ�matrix����
		Matrix matrix = new Matrix();
		// ����ͼƬ����
		matrix.postScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(),
				matrix, true);
	}

	/**
	 * close inputStream
	 * 
	 * @param s
	 */
	private static void closeInputStream(InputStream s) {
		if (s == null) {
			return;
		}

		try {
			s.close();
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		}
	}

	/***
	 * [��Ч] �ص�: ͨ�����ò�����, ����ͼƬ������, �ﵽ���ڴ��е�Bitmap����ѹ��
	 * 
	 * @param image
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 100;
		image.compress(Bitmap.CompressFormat.JPEG, options, baos);// ����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��

		while (baos.toByteArray().length / 1024 > 100) { // ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��
			baos.reset();// ����baofs�����baos
			options -= 10;// ÿ�ζ�����10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// ����ѹ��options%����ѹ��������ݴ�ŵ�baos��
		}

		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// ��ѹ���������baos��ŵ�ByteArrayInputStream��
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// ��ByteArrayInputStream��������ͼƬ
		return bitmap;
	}

	/**
	 * ��InputStreamת��Ϊbyte[]
	 * 
	 * @param is
	 *            InputStream
	 * @return
	 */
	public static byte[] getBytesByInputStream(InputStream is) {
		ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
		byte[] buffer = new byte[1024];
		int len;
		try {
			while ((len = is.read(buffer)) >= 0) {
				os.write(buffer, 0, len);
			}
		} catch (java.io.IOException e) {

		}
		return os.toByteArray();
	}

	/**
	 * ��һ��View�Ķ���ת����bitmaps
	 * 
	 * @param v
	 *            View����
	 * @return
	 */
	public static Bitmap getBitmapByView(View v) {
		v.clearFocus();
		v.setPressed(false);
		// �ܻ�����ͷ���false
		boolean willNotCache = v.willNotCacheDrawing();
		v.setWillNotCacheDrawing(false);
		int color = v.getDrawingCacheBackgroundColor();
		v.setDrawingCacheBackgroundColor(0);
		if (color != 0) {
			v.destroyDrawingCache();
		}
		v.buildDrawingCache();
		Bitmap cacheBitmap = v.getDrawingCache();
		if (cacheBitmap == null) {
			Log.i("xx", "failed getViewBitmap(" + v + ")",
					new RuntimeException());
			return null;
		}
		Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
		// Restore the view
		v.destroyDrawingCache();
		v.setWillNotCacheDrawing(willNotCache);
		v.setDrawingCacheBackgroundColor(color);

		return bitmap;
	}

	/**
	 * ����ˮӡͼƬ
	 * 
	 * @param src
	 *            ԭͼ
	 * @param watermark
	 *            ˮӡͼ
	 * @return
	 */
	public static Bitmap createWaterMarkBitmap(Bitmap src, Bitmap watermark) {
		if (src == null) {
			return null;
		}

		int w = src.getWidth();
		int h = src.getHeight();
		int ww = watermark.getWidth();
		int wh = watermark.getHeight();
		// create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// ����һ���µĺ�SRC���ȿ��һ����λͼ
		Canvas cv = new Canvas(newb);
		// draw src into
		cv.drawBitmap(src, 0, 0, null);// �� 0��0���꿪ʼ����src
		// draw watermark into
		cv.drawBitmap(watermark, w - ww, h - wh, null);// ��src�����½ǻ���ˮӡ
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// ����
		// store
		cv.restore();// �洢
		return newb;
	}

	/***
	 * ����ˮӡͼƬ +����
	 * 
	 * @param src
	 *            ԭͼ
	 * @param watermark
	 *            ˮӡͼ
	 * @param title
	 *            ����
	 * @return
	 */
	public static Bitmap createWaterMarkBitmap(Bitmap src, Bitmap watermark,
			String title) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		int ww = watermark.getWidth();
		int wh = watermark.getHeight();
		// create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// ����һ���µĺ�SRC���ȿ��һ����λͼ
		Canvas cv = new Canvas(newb);
		// draw src into
		cv.drawBitmap(src, 0, 0, null);// �� 0��0���꿪ʼ����src
		// draw watermark into

		cv.drawBitmap(watermark, w - ww - 20, h - wh - 10, null);// ��src�����½ǻ���ˮӡ
																	// �߶ȿ���(-10)

		// ��������
		if (title != null) {
			// ����1
			String familyName = "����";
			Typeface font = Typeface.create(familyName, Typeface.BOLD);
			TextPaint textPaint = new TextPaint();
			textPaint.setColor(Color.RED);
			textPaint.setTypeface(font);
			textPaint.setTextSize(22);
			// �������Զ����е�
			StaticLayout layout = new StaticLayout(title, textPaint, w,
					Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
			cv.translate(10, 50);// layout��Ĭ�ϻ���Canvas��(0,0 [���Ͻ�])
									// ��ģ������Ҫ����λ��ֻ����draw֮ǰ��Canvas����ʼ����
			layout.draw(cv);

			// ����2
			// Paint p = new Paint();
			// String familyName = "����";
			// Typeface font = Typeface.create(familyName,Typeface.BOLD);
			// p.setColor(Color.RED);
			// p.setTypeface(font);
			// p.setTextSize(22);
			// cv.drawText(title,10,50,p);// (x,y) (0,0)Ϊ���ϽǶ���

		}
		cv.save(Canvas.ALL_SAVE_FLAG);// ����
		cv.restore();// �洢

		return newb;
	}

	/**
	 * ����ˮӡ����
	 * 
	 * @param src
	 *            ԭͼ
	 * @param title
	 *            ����
	 * @return
	 */
	public static Bitmap createWaterMarkBitmap(Bitmap src, String title) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();

		// create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// ����һ���µĺ�SRC���ȿ��һ����λͼ
		Canvas cv = new Canvas(newb);
		// draw src into
		cv.drawBitmap(src, 0, 0, null);// �� 0��0���꿪ʼ����src
		// draw watermark into

		// ��������
		if (title != null) {
			// ����1
			String familyName = "����";
			Typeface font = Typeface.create(familyName, Typeface.BOLD);
			TextPaint textPaint = new TextPaint();
			textPaint.setColor(Color.RED);
			textPaint.setTypeface(font);
			textPaint.setTextSize(22);
			// �������Զ����е�
			StaticLayout layout = new StaticLayout(title, textPaint, w,
					Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
			cv.translate(10, 50);// layout��Ĭ�ϻ���Canvas��(0,0 [���Ͻ�])
									// ��ģ������Ҫ����λ��ֻ����draw֮ǰ��Canvas����ʼ����
			layout.draw(cv);

			// ����2
			// Paint p = new Paint();
			// String familyName = "����";
			// Typeface font = Typeface.create(familyName,Typeface.BOLD);
			// p.setColor(Color.RED);
			// p.setTypeface(font);
			// p.setTextSize(22);
			// cv.drawText(title,10,50,p);// (x,y) (0,0)Ϊ���ϽǶ���

		}
		cv.save(Canvas.ALL_SAVE_FLAG);// ����
		cv.restore();// �洢

		return newb;
	}

	/***
	 * ��ImageViewת��Ϊ Bitmap����
	 * 
	 * @param imgView
	 *            ImageView����
	 * @return
	 */
	public static Bitmap getBitmapByImageView(ImageView imgView) {

		Bitmap obmp = ((BitmapDrawable) imgView.getDrawable()).getBitmap();

		// imgView.setDrawingCacheEnabled(true);
		// Bitmap obmp = Bitmap.createBitmap(imgView.getDrawingCache());
		// imgView.setDrawingCacheEnabled(false);

		return obmp;
	}

	/**
	 * ��Bitmap���� ת��Ϊ�ַ���
	 * 
	 * @param bitmap
	 *            bitmap ����
	 * @param charset
	 *            �ַ���
	 * @return
	 */
	public static String getStringByBitmap(Bitmap bitmap, String charset) {
		if (bitmap == null) {
			return "";
		} else {
			// BLOB����
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			// ��Bitmapѹ����PNG���룬����Ϊ100%�洢
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
			byte[] bt = os.toByteArray(); // �õ��ֽ�����
			try {
				return new String(bt, charset);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			} // ���ֽ�����ת��Ϊ�ַ���
		}
	}

	/**
	 * ��ImageView����ת��ΪBase64�ַ���
	 * 
	 * @param imgView
	 *            ImageView ����
	 * @return
	 */
	public static String getBase64StringByImageView(ImageView imgView) {
		try {
			Bitmap obmp = getBitmapByImageView(imgView);
			return getBase64StringByBitmap(obmp);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ��Bitmap����ת����Base64�ַ���
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String getBase64StringByBitmap(Bitmap bitmap) {

		String string = null;
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100, bStream);
		byte[] bytes = bStream.toByteArray();
		string = Base64.encodeToString(bytes, Base64.DEFAULT);
		return string;
	}

	/**
	 * ��Base64�ַ���ת����Bitmap����
	 * 
	 * @param string
	 *            �ַ���
	 * @return
	 */
	public static Bitmap getBitmapByBase64String(String string) {
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(string, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
					bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/***
	 * ����ImageView �õ���������
	 * 
	 * @param imgView
	 *            ImageView ����
	 * @return
	 */
	public static byte[] getByteByImageView(ImageView imgView) {
		Bitmap btBitmap = getBitmapByImageView(imgView);
		return getIconData(btBitmap);
	}

	/**
	 * ����Bitmap�õ�byte[]
	 * 
	 * @param bitmap
	 *            Bitmap����
	 * @return
	 */
	public static byte[] getIconData(Bitmap bitmap) {
		int size = bitmap.getWidth() * bitmap.getHeight() * 4;
		ByteArrayOutputStream out = new ByteArrayOutputStream(size);
		try {
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	/**
	 * byte[]ת����Bitmap
	 * 
	 * @param b
	 *            byte[]
	 * @return
	 */
	public static Bitmap Bytes2Bimap(byte[] b) {

		if (b.length != 0 && b != null) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	/***
	 * ��byte[] ��ʾ��ImageView��
	 * 
	 * @param imageView
	 * @param b
	 *            byte [] ������
	 */
	public static void setImageViewByByte(ImageView imageView, byte[] b) {
		Bitmap bt = Bytes2Bimap(b);
		if (bt != null) {
			imageView.setImageBitmap(bt);
		}
	}

	/**
	 * ��ȡ����ͼƬ��ָ���߶ȺͿ��
	 */
	public static Bitmap getNativeImage(String imagePath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// ��ȡ���ͼƬ�Ŀ�͸�
		Bitmap myBitmap = BitmapFactory.decodeFile(imagePath, options); // ��ʱ����myBitmapΪ��
		// �������ű�
		int be = (int) (options.outHeight / (float) 200);
		int ys = options.outHeight % 200;// ������
		float fe = ys / (float) 200;
		if (fe >= 0.5)
			be = be + 1;
		if (be <= 0)
			be = 1;
		options.inSampleSize = be;
		// ���¶���ͼƬ��ע�����Ҫ��options.inJustDecodeBounds ��Ϊ false
		options.inJustDecodeBounds = false;
		myBitmap = BitmapFactory.decodeFile(imagePath, options);
		return myBitmap;
	}

	/**
	 * ����ʡ�ڴ�ķ�ʽ��ȡͼƬ
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap getBitMapByResources(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// ��ȡ��ԴͼƬ
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/**
	 * ����ʡ�ڴ�ķ�ʽ��ȡ������Դ��ͼƬ ����SDCard�е�ͼƬ
	 * 
	 * @param imagePath
	 *            ָ����·��
	 * @return
	 */
	public static Bitmap getBitMapByPath(String imagePath) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// ��ȡ��ԴͼƬ
		return BitmapFactory.decodeFile(imagePath, opt);
	}

	/***
	 * ��InputStream�� ����ʡ�ڴ�ķ�ʽ��ȡ������Դ��ͼƬ
	 * 
	 * @param is
	 * @return
	 */
	public static Bitmap getBitMapByInputStream(InputStream is) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/***
	 * ��ָ��·���е�ͼƬת��BitMap ��������ڴ����
	 * 
	 * @param path
	 * @param w
	 *            ���
	 * @param h
	 *            �߶�
	 * @return
	 */
	public static Bitmap getBitMapByPath(String path, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// ����Ϊtureֻ��ȡͼƬ��С
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		// ����Ϊ��
		BitmapFactory.decodeFile(path, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
		if (width > w || height > h) {
			// ����
			scaleWidth = ((float) width) / w;
			scaleHeight = ((float) height) / h;
		}
		opts.inJustDecodeBounds = false;
		float scale = Math.max(scaleWidth, scaleHeight);
		opts.inSampleSize = (int) scale;
		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(
				BitmapFactory.decodeFile(path, opts));
		return Bitmap.createScaledBitmap(weak.get(), w, h, true);
	}

}
