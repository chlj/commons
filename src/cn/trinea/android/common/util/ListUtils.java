package cn.trinea.android.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * List Utils
 */
public class ListUtils {

	/** 默认的分割字符串, **/
	public static final String DEFAULT_JOIN_SEPARATOR = ",";

	/**
	 * 得到list的长度
	 * 
	 * <pre>
	 * getSize(null)   =   0;
	 * getSize({})     =   0;
	 * getSize({1})    =   1;
	 * </pre>
	 * 
	 * @param <V>
	 * @param sourceList
	 * @return 如果list==null or 为空 则返回0 ,否则返回list的长度
	 * 
	 */
	public static <V> int getSize(List<V> sourceList) {
		return sourceList == null ? 0 : sourceList.size();
	}

	/**
	 * 判断list是为空
	 * 
	 * <pre>
	 * isEmpty(null)   =   true;
	 * isEmpty({})     =   true;
	 * isEmpty({1})    =   false;
	 * </pre>
	 * 
	 * @param <V>
	 * @param sourceList
	 * @return 如果list==null or list.size()=0 则表示list为空 返回true ,否则返回false（不为空）
	 * 
	 */
	public static <V> boolean isEmpty(List<V> sourceList) {
		return (sourceList == null || sourceList.size() == 0);
	}

	/**
	 * 
	 * 判断两个list是否相等
	 * 
	 * <pre>
	 * isEquals(null, null) = true;
	 * isEquals(new ArrayList&lt;String&gt;(), null) = false;
	 * isEquals(null, new ArrayList&lt;String&gt;()) = false;
	 * isEquals(new ArrayList&lt;String&gt;(), new ArrayList&lt;String&gt;()) = true;
	 * </pre>
	 * 
	 * @param <V>
	 * @param actual
	 * @param expected
	 * @return
	 */
	public static <V> boolean isEquals(ArrayList<V> actual,
			ArrayList<V> expected) {
		if (actual == null) {
			return expected == null;
		}
		if (expected == null) {
			return false;
		}
		if (actual.size() != expected.size()) {
			return false;
		}

		for (int i = 0; i < actual.size(); i++) {
			if (!ObjectUtils.isEquals(actual.get(i), expected.get(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * 将List<String>链接成为一个字符串，已","分割
	 * 
	 * <pre>
	 * join(null)      =   "";
	 * join({})        =   "";
	 * join({a,b})     =   "a,b";
	 * </pre>
	 * 
	 * @param list
	 * @return join list to string, separator is ",". if list is empty, return
	 *         ""
	 */
	public static String join(List<String> list) {
		return join(list, DEFAULT_JOIN_SEPARATOR);
	}

	/**
	 * 
	 * 将List<String>链接成为一个字符串，已指定的单字符char分割
	 * 
	 * <pre>
	 * join(null, '#')     =   "";
	 * join({}, '#')       =   "";
	 * join({a,b,c}, ' ')  =   "abc";
	 * join({a,b,c}, '#')  =   "a#b#c";
	 * </pre>
	 * 
	 * @param list
	 * @param separator
	 * @return join list to string. if list is empty, return ""
	 */
	public static String join(List<String> list, char separator) {
		return join(list, new String(new char[] { separator }));
	}

	/**
	 * 将List<String>链接成为一个字符串，已指定的字符串separator分割 if separator is null, use
	 * {@link #DEFAULT_JOIN_SEPARATOR}
	 * 
	 * <pre>
	 * join(null, "#")     =   "";
	 * join({}, "#$")      =   "";
	 * join({a,b,c}, null) =   "a,b,c";
	 * join({a,b,c}, "")   =   "abc";
	 * join({a,b,c}, "#")  =   "a#b#c";
	 * join({a,b,c}, "#$") =   "a#$b#$c";
	 * </pre>
	 * 
	 * @param list
	 * @param separator
	 * @return join list to string with separator. if list is empty, return ""
	 */
	public static String join(List<String> list, String separator) {
		if (isEmpty(list)) {
			return "";
		}
		if (separator == null) {
			separator = DEFAULT_JOIN_SEPARATOR;
		}

		StringBuilder joinStr = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			joinStr.append(list.get(i));
			if (i != list.size() - 1) {
				joinStr.append(separator);
			}
		}
		return joinStr.toString();
	}

	/**
	 * 像List<V>中添加不存在的元素V
	 * @param <V>
	 * @param sourceList
	 *            List<V> 数据源
	 * @param entry
	 *            V 元素
	 * @return 像List<V>中添加元素V,如果元素V已经存在，则返回false,如果元素V不存在，则添加到List<V>中并返回true
	 */
	public static <V> boolean addDistinctEntry(List<V> sourceList, V entry) {
		return (sourceList != null && !sourceList.contains(entry)) ? sourceList
				.add(entry) : false;
	}

	/**
	 * 
	 * 先把entryList中 sourceList没有的元素添加到sourceList中去，
	 * 然后再返回 sourceList 添加了多少个不同的新元素
	 * @param <V>
	 * @param sourceList
	 * @param entryList
	 * @return the count of entries be added
	 */
	public static <V> int addDistinctList(List<V> sourceList, List<V> entryList) {
		if (sourceList == null || isEmpty(entryList)) {
			return 0;
		}

		int sourceCount = sourceList.size();
		for (V entry : entryList) {
			if (!sourceList.contains(entry)) {
				sourceList.add(entry);
			}
		}
		return sourceList.size() - sourceCount;
	}

	/**
	 * 
	 * 移除sourceList中相同的元素，并返回移除后sourceList的长度
	 * @param <V>
	 * @param sourceList
	 * @return the count of entries be removed
	 */
	public static <V> int distinctList(List<V> sourceList) {
		if (isEmpty(sourceList)) {
			return 0;
		}

		int sourceCount = sourceList.size();
		int sourceListSize = sourceList.size();
		for (int i = 0; i < sourceListSize; i++) {
			for (int j = (i + 1); j < sourceListSize; j++) {
				if (sourceList.get(i).equals(sourceList.get(j))) {
					sourceList.remove(j);
					sourceListSize = sourceList.size();
					j--;
				}
			}
		}
		return sourceCount - sourceList.size();
	}

	/**
	 * 像List<V>中添加元素<V>
	 * @param sourceList
	 * @param value
	 * @return <ul>
	 *         <li>如果 sourceList is null, 返回 false</li>
	 *         <li>如果 value is null, 返回 false</li>
	 *         <li>return {@link List#add(Object)}</li>
	 *         </ul>
	 */
	public static <V> boolean addListNotNullValue(List<V> sourceList, V value) {
		return (sourceList != null && value != null) ? sourceList.add(value)
				: false;
	}

	/**
	 * 得到指定元素的上一个元素
	 * <ul>
	 * <li>eg: list {1,2,3}  getLast(list,2)=1,getLast(list,3)=2,getLast(list,1)=3 </li>
	 * <li>会形成一个封闭的循环查找空间 </li>
	 * </ul>
	 * @see {@link ArrayUtils#getLast(Object[], Object, Object, boolean)}
	 *      defaultValue is null, isCircle is true
	 */
	@SuppressWarnings("unchecked")
	public static <V> V getLast(List<V> sourceList, V value) {
		return (sourceList == null) ? null : (V) ArrayUtils.getLast(
				sourceList.toArray(), value, true);
	}

	/**得到指定元素的下一个元素
	 * <ul>
	 * <li>eg: list {1,2,3}  getLast(list,2)=3,getLast(list,3)=1,getLast(list,1)=2 </li>
	 * <li>会形成一个封闭的循环查找空间 </li>
	 * </ul>
	 * @see {@link ArrayUtils#getNext(Object[], Object, Object, boolean)}
	 *      defaultValue is null, isCircle is true
	 */
	@SuppressWarnings("unchecked")
	public static <V> V getNext(List<V> sourceList, V value) {
		return (sourceList == null) ? null : (V) ArrayUtils.getNext(
				sourceList.toArray(), value, true);
	}

	/**
	 * 
	 * 将List<V>反向
	 * <ul>
	 * <li>eg: list {1,2, 3} invertList(list) 后 list 变为 {3,2 1}</li>
	 * </ul>
	 * @param <V>
	 * @param sourceList
	 * @return
	 */
	public static <V> List<V> invertList(List<V> sourceList) {
		if (isEmpty(sourceList)) {
			return sourceList;
		}

		List<V> invertList = new ArrayList<V>(sourceList.size());
		for (int i = sourceList.size() - 1; i >= 0; i--) {
			invertList.add(sourceList.get(i));
		}
		return invertList;
	}
}
