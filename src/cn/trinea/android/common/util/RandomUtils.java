package cn.trinea.android.common.util;

import java.util.Random;

/**
 * Random Utils
 * <ul>
 * Shuffling algorithm
 * <li>{@link #shuffle(Object[])} Shuffling algorithm, Randomly permutes the
 * specified array using a default source of randomness</li>
 * <li>{@link #shuffle(Object[], int)} Shuffling algorithm, Randomly permutes
 * the specified array</li>
 * <li>{@link #shuffle(int[])} Shuffling algorithm, Randomly permutes the
 * specified int array using a default source of randomness</li>
 * <li>{@link #shuffle(int[], int)} Shuffling algorithm, Randomly permutes the
 * specified int array</li>
 * </ul>
 * <ul>
 * get random int
 * <li>{@link #getRandom(int)} get random int between 0 and max</li>
 * <li>{@link #getRandom(int, int)} get random int between min and max</li>
 * </ul>
 * <ul>
 * get random numbers or letters
 * <li>{@link #getRandomCapitalLetters(int)} get a fixed-length random string,
 * its a mixture of uppercase letters</li>
 * <li>{@link #getRandomLetters(int)} get a fixed-length random string, its a
 * mixture of uppercase and lowercase letters</li>
 * <li>{@link #getRandomLowerCaseLetters(int)} get a fixed-length random string,
 * its a mixture of lowercase letters</li>
 * <li>{@link #getRandomNumbers(int)} get a fixed-length random string, its a
 * mixture of numbers</li>
 * <li>{@link #getRandomNumbersAndLetters(int)} get a fixed-length random
 * string, its a mixture of uppercase, lowercase letters and numbers</li>
 * <li>{@link #getRandom(String, int)} get a fixed-length random string, its a
 * mixture of chars in source</li>
 * <li>{@link #getRandom(char[], int)} get a fixed-length random string, its a
 * mixture of chars in sourceChar</li>
 * </ul>
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2012-5-12
 */
public class RandomUtils {

	public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String NUMBERS = "0123456789";
	public static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";

	/**
	 * 
	 * 得到指定长度的随机数
	 * [0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ]
	 * 
	 * @param length
	 *            指定的长度
	 * @return
	 * @see RandomUtils#getRandom(String source, int length)
	 */
	public static String getRandomNumbersAndLetters(int length) {
		return getRandom(NUMBERS_AND_LETTERS, length);
	}

	/**
	 * 得到指定长度的随机数[0123456789]
	 * 
	 * @param length
	 *            指定的长度
	 * @return
	 * @see RandomUtils#getRandom(String source, int length)
	 */
	public static String getRandomNumbers(int length) {
		return getRandom(NUMBERS, length);
	}

	/**
	 * 得到指定长度的随机数[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ]
	 * 
	 * @param length
	 *            指定的长度
	 * @return
	 * @see RandomUtils#getRandom(String source, int length)
	 */
	public static String getRandomLetters(int length) {
		return getRandom(LETTERS, length);
	}

	/**
	 * 
	 * 得到指定长度的随机数[ABCDEFGHIJKLMNOPQRSTUVWXYZ]
	 * 
	 * @param length
	 *            指定的长度
	 * @return
	 * @see RandomUtils#getRandom(String source, int length)
	 */
	public static String getRandomCapitalLetters(int length) {
		return getRandom(CAPITAL_LETTERS, length);
	}

	/**
	 * 得到指定长度的随机数[abcdefghijklmnopqrstuvwxyz]
	 * 
	 * @param length
	 *            指定的长度
	 * @return
	 * @see RandomUtils#getRandom(String source, int length)
	 */
	public static String getRandomLowerCaseLetters(int length) {
		return getRandom(LOWER_CASE_LETTERS, length);
	}

	/**
	 * 
	 * 从指定的字符串中得到指定长度的随机数
	 * @param source
	 *    指定的 字符串
	 * @param length
	 *  指定的长度
	 * @return <ul>
	 *         <li> 如果source字符串为null or empty, 返回 null</li>
	 *         <li>else see
	 *         {@link RandomUtils#getRandom(char[] sourceChar, int length)}</li>
	 *         </ul>
	 */
	public static String getRandom(String source, int length) {
		return StringUtils.isEmpty(source) ? null : getRandom(
				source.toCharArray(), length);
	}

	/**
	 * 从指定的char[]数组中得到指定长度的随机数
	 * 
	 * @param sourceChar
	 * char[]数组 
	 * @param length
	 * 指定的长度
	 * @return <ul>
	 *         <li>如果 sourceChar 为 null or empty, 返回 null</li>
	 *         <li>如果length小于0, 返回 null</li>
	 *         </ul>
	 */
	public static String getRandom(char[] sourceChar, int length) {
		if (sourceChar == null || sourceChar.length == 0 || length < 0) {
			return null;
		}

		StringBuilder str = new StringBuilder(length);
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			str.append(sourceChar[random.nextInt(sourceChar.length)]);
		}
		return str.toString();
	}

	/**
	 * 
	 * 得到0到指定数直接的随机数
	 * @param max
	 * @return <ul>
	 *         <li>如果max <= 0, 返回 0。否则返回0到指定数直接的随机数</li>
	 *         </ul>
	 */
	public static int getRandom(int max) {
		return getRandom(0, max);
	}

	/**
	 * 
	 * 得到两个指定范围数字之间的随机数
	 * @param min
	 * @param max
	 * @return <ul>
	 *         <li>如果 min> max, 返回 0</li>
	 *         <li>如果 min == max, 返回 min</li>
	 *         <li>否则返回   min 与 max之间的随机数</li>
	 *         </ul>
	 */
	public static int getRandom(int min, int max) {
		if (min > max) {
			return 0;
		}
		if (min == max) {
			return min;
		}
		return min + new Random().nextInt(max - min);
	}

	/**
	 * 洗牌算法
	 * Shuffling algorithm, Randomly permutes the specified array using a
	 * default source of randomness
	 * 
	 * @param objArray
	 * @return
	 */
	public static boolean shuffle(Object[] objArray) {
		if (objArray == null) {
			return false;
		}

		return shuffle(objArray, getRandom(objArray.length));
	}

	/**
	 * 洗牌算法
	 * Shuffling algorithm,  随机置换的指定的数组
	 *
	 * @param objArray
	 * @param shuffleCount
	 * @return
	 */
	public static boolean shuffle(Object[] objArray, int shuffleCount) {
		int length;
		if (objArray == null || shuffleCount < 0
				|| (length = objArray.length) < shuffleCount) {
			return false;
		}

		for (int i = 1; i <= shuffleCount; i++) {
			int random = getRandom(length - i);
			Object temp = objArray[length - i];
			objArray[length - i] = objArray[random];
			objArray[random] = temp;
		}
		return true;
	}

	/**
	 * 洗牌算法
	 * Shuffling algorithm, Randomly permutes the specified int array using a
	 * default source of randomness
	 * 
	 * @param intArray
	 * @return
	 */
	public static int[] shuffle(int[] intArray) {
		if (intArray == null) {
			return null;
		}

		return shuffle(intArray, getRandom(intArray.length));
	}

	/**
	 * 洗牌算法
	 * Shuffling algorithm, Randomly permutes the specified int array
	 * 
	 * @param intArray
	 * @param shuffleCount
	 * @return
	 */
	public static int[] shuffle(int[] intArray, int shuffleCount) {
		int length;
		if (intArray == null || shuffleCount < 0
				|| (length = intArray.length) < shuffleCount) {
			return null;
		}

		int[] out = new int[shuffleCount];
		for (int i = 1; i <= shuffleCount; i++) {
			int random = getRandom(length - i);
			out[i - 1] = intArray[random];
			int temp = intArray[length - i];
			intArray[length - i] = intArray[random];
			intArray[random] = temp;
		}
		return out;
	}
}
