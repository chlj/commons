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
	 * 将Bitmap转换为byte[]
	 * 
	 * @param b
	 *            Bitmap 对象
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
	 * 将byte[]转换为Bitmap对象
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
	 * 将Drawable对象转换为Bitmap
	 * 
	 * @param d
	 *            Drawable 对象
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable d) {
		return d == null ? null : ((BitmapDrawable) d).getBitmap();
	}

	/**
	 * 将Bitmap对象转化为Drawable
	 * 
	 * @param b
	 *            Bitmap 对象
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap b) {
		return b == null ? null : new BitmapDrawable(b);
	}

	/**
	 * 将Drawable对象转换为byte[]
	 * 
	 * @param d
	 *            Drawable对象
	 * @return
	 */
	public static byte[] drawableToByte(Drawable d) {
		return bitmapToByte(drawableToBitmap(d));
	}

	/**
	 * 将byte[]转换为Drawable
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
	 * 从URL中得到InputStream,您需要手动的关闭InputStream
	 * 
	 * @param imageUrl
	 *            网络地址
	 * @param readTimeOutMillis
	 *            设置从主机读取数据超时（单位：毫秒）
	 * @return
	 * @see ImageUtils#getInputStreamFromUrl(String, int, boolean)
	 */
	public static InputStream getInputStreamFromUrl(String imageUrl,
			int readTimeOutMillis) {
		return getInputStreamFromUrl(imageUrl, readTimeOutMillis, null);
	}

	/**
	 * 从URL中得到InputStream,您需要手动的关闭InputStream
	 * 
	 * 
	 * @param imageUrl
	 *            网络地址
	 * @param readTimeOutMillis
	 *            设置从主机读取数据超时（单位：毫秒）
	 * @param requestProperties
	 *            其他参数说明
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
	 * 从URL中得到Drawable
	 * 
	 * @param imageUrl
	 *            地址
	 * @param readTimeOutMillis
	 *            设置从主机读取数据超时（单位：毫秒）
	 * @return
	 * @see ImageUtils#getDrawableFromUrl(String, int, boolean)
	 */
	public static Drawable getDrawableFromUrl(String imageUrl,
			int readTimeOutMillis) {
		return getDrawableFromUrl(imageUrl, readTimeOutMillis, null);
	}

	/**
	 * 从URL中得到Drawable
	 * 
	 * @param imageUrl
	 *            地址
	 * @param readTimeOutMillis
	 *            设置从主机读取数据超时（单位：毫秒）
	 * @param requestProperties
	 *            其他参数说明
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
	 * 从URL中得到Bitmap
	 * 
	 * @param imageUrl
	 *            地址
	 * @param readTimeOut
	 *            设置从主机读取数据超时（单位：毫秒）
	 * @return
	 * @see ImageUtils#getBitmapFromUrl(String, int, boolean)
	 */
	public static Bitmap getBitmapFromUrl(String imageUrl, int readTimeOut) {
		return getBitmapFromUrl(imageUrl, readTimeOut, null);
	}

	/**
	 * 从URL中得到Bitmap
	 * 
	 * @param imageUrl
	 *            地址
	 * @param readTimeOut
	 *            设置从主机读取数据超时（单位：毫秒）
	 * @param requestProperties
	 *            其他参数说明
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
	 * scale image 图片缩放
	 * 
	 * @param org
	 *            Bitmap对象
	 * @param newWidth
	 *            缩放后的宽
	 * @param newHeight
	 *            缩放后的高
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
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 缩放图片动作
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
	 * [无效] 特点: 通过设置采样率, 减少图片的像素, 达到对内存中的Bitmap进行压缩
	 * 
	 * @param image
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 100;
		image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baofs即清空baos
			options -= 10;// 每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}

		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * 将InputStream转换为byte[]
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
	 * 把一个View的对象转换成bitmaps
	 * 
	 * @param v
	 *            View对象
	 * @return
	 */
	public static Bitmap getBitmapByView(View v) {
		v.clearFocus();
		v.setPressed(false);
		// 能画缓存就返回false
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
	 * 生成水印图片
	 * 
	 * @param src
	 *            原图
	 * @param watermark
	 *            水印图
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
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);
		// draw src into
		cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
		// draw watermark into
		cv.drawBitmap(watermark, w - ww, h - wh, null);// 在src的右下角画入水印
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		// store
		cv.restore();// 存储
		return newb;
	}

	/***
	 * 生成水印图片 +文字
	 * 
	 * @param src
	 *            原图
	 * @param watermark
	 *            水印图
	 * @param title
	 *            文字
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
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);
		// draw src into
		cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
		// draw watermark into

		cv.drawBitmap(watermark, w - ww - 20, h - wh - 10, null);// 在src的右下角画入水印
																	// 高度靠上(-10)

		// 加入文字
		if (title != null) {
			// 方法1
			String familyName = "宋体";
			Typeface font = Typeface.create(familyName, Typeface.BOLD);
			TextPaint textPaint = new TextPaint();
			textPaint.setColor(Color.RED);
			textPaint.setTypeface(font);
			textPaint.setTextSize(22);
			// 这里是自动换行的
			StaticLayout layout = new StaticLayout(title, textPaint, w,
					Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
			cv.translate(10, 50);// layout是默认画在Canvas的(0,0 [左上角])
									// 点的，如果需要调整位置只能在draw之前移Canvas的起始坐标
			layout.draw(cv);

			// 方法2
			// Paint p = new Paint();
			// String familyName = "宋体";
			// Typeface font = Typeface.create(familyName,Typeface.BOLD);
			// p.setColor(Color.RED);
			// p.setTypeface(font);
			// p.setTextSize(22);
			// cv.drawText(title,10,50,p);// (x,y) (0,0)为左上角顶角

		}
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		cv.restore();// 存储

		return newb;
	}

	/**
	 * 生成水印文字
	 * 
	 * @param src
	 *            原图
	 * @param title
	 *            文字
	 * @return
	 */
	public static Bitmap createWaterMarkBitmap(Bitmap src, String title) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();

		// create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);
		// draw src into
		cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
		// draw watermark into

		// 加入文字
		if (title != null) {
			// 方法1
			String familyName = "宋体";
			Typeface font = Typeface.create(familyName, Typeface.BOLD);
			TextPaint textPaint = new TextPaint();
			textPaint.setColor(Color.RED);
			textPaint.setTypeface(font);
			textPaint.setTextSize(22);
			// 这里是自动换行的
			StaticLayout layout = new StaticLayout(title, textPaint, w,
					Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
			cv.translate(10, 50);// layout是默认画在Canvas的(0,0 [左上角])
									// 点的，如果需要调整位置只能在draw之前移Canvas的起始坐标
			layout.draw(cv);

			// 方法2
			// Paint p = new Paint();
			// String familyName = "宋体";
			// Typeface font = Typeface.create(familyName,Typeface.BOLD);
			// p.setColor(Color.RED);
			// p.setTypeface(font);
			// p.setTextSize(22);
			// cv.drawText(title,10,50,p);// (x,y) (0,0)为左上角顶角

		}
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		cv.restore();// 存储

		return newb;
	}

	/***
	 * 将ImageView转化为 Bitmap对象
	 * 
	 * @param imgView
	 *            ImageView对象
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
	 * 将Bitmap对象 转化为字符串
	 * 
	 * @param bitmap
	 *            bitmap 对象
	 * @param charset
	 *            字符集
	 * @return
	 */
	public static String getStringByBitmap(Bitmap bitmap, String charset) {
		if (bitmap == null) {
			return "";
		} else {
			// BLOB类型
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			// 将Bitmap压缩成PNG编码，质量为100%存储
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
			byte[] bt = os.toByteArray(); // 得到字节数组
			try {
				return new String(bt, charset);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			} // 将字节数组转化为字符串
		}
	}

	/**
	 * 将ImageView对象转换为Base64字符串
	 * 
	 * @param imgView
	 *            ImageView 对象
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
	 * 将Bitmap对象转换成Base64字符串
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
	 * 将Base64字符串转换成Bitmap类型
	 * 
	 * @param string
	 *            字符串
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
	 * 根据ImageView 得到二进制流
	 * 
	 * @param imgView
	 *            ImageView 对象
	 * @return
	 */
	public static byte[] getByteByImageView(ImageView imgView) {
		Bitmap btBitmap = getBitmapByImageView(imgView);
		return getIconData(btBitmap);
	}

	/**
	 * 根据Bitmap得到byte[]
	 * 
	 * @param bitmap
	 *            Bitmap对象
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
	 * byte[]转换成Bitmap
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
	 * 将byte[] 显示在ImageView中
	 * 
	 * @param imageView
	 * @param b
	 *            byte [] 二进制
	 */
	public static void setImageViewByByte(ImageView imageView, byte[] b) {
		Bitmap bt = Bytes2Bimap(b);
		if (bt != null) {
			imageView.setImageBitmap(bt);
		}
	}

	/**
	 * 获取本地图片并指定高度和宽度
	 */
	public static Bitmap getNativeImage(String imagePath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高
		Bitmap myBitmap = BitmapFactory.decodeFile(imagePath, options); // 此时返回myBitmap为空
		// 计算缩放比
		int be = (int) (options.outHeight / (float) 200);
		int ys = options.outHeight % 200;// 求余数
		float fe = ys / (float) 200;
		if (fe >= 0.5)
			be = be + 1;
		if (be <= 0)
			be = 1;
		options.inSampleSize = be;
		// 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false
		options.inJustDecodeBounds = false;
		myBitmap = BitmapFactory.decodeFile(imagePath, options);
		return myBitmap;
	}

	/**
	 * 以最省内存的方式读取图片
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
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/**
	 * 以最省内存的方式读取本地资源的图片 或者SDCard中的图片
	 * 
	 * @param imagePath
	 *            指定的路径
	 * @return
	 */
	public static Bitmap getBitMapByPath(String imagePath) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		return BitmapFactory.decodeFile(imagePath, opt);
	}

	/***
	 * 从InputStream中 以最省内存的方式读取本地资源的图片
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
	 * 将指定路径中的图片转换BitMap 不会造成内存溢出
	 * 
	 * @param path
	 * @param w
	 *            宽度
	 * @param h
	 *            高度
	 * @return
	 */
	public static Bitmap getBitMapByPath(String path, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大小
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		// 返回为空
		BitmapFactory.decodeFile(path, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
		if (width > w || height > h) {
			// 缩放
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
