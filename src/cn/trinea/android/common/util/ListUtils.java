package cn.trinea.android.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * List Utils
 */
public class ListUtils {

	/** Ĭ�ϵķָ��ַ���, **/
	public static final String DEFAULT_JOIN_SEPARATOR = ",";

	/**
	 * �õ�list�ĳ���
	 * 
	 * <pre>
	 * getSize(null)   =   0;
	 * getSize({})     =   0;
	 * getSize({1})    =   1;
	 * </pre>
	 * 
	 * @param <V>
	 * @param sourceList
	 * @return ���list==null or Ϊ�� �򷵻�0 ,���򷵻�list�ĳ���
	 * 
	 */
	public static <V> int getSize(List<V> sourceList) {
		return sourceList == null ? 0 : sourceList.size();
	}

	/**
	 * �ж�list��Ϊ��
	 * 
	 * <pre>
	 * isEmpty(null)   =   true;
	 * isEmpty({})     =   true;
	 * isEmpty({1})    =   false;
	 * </pre>
	 * 
	 * @param <V>
	 * @param sourceList
	 * @return ���list==null or list.size()=0 ���ʾlistΪ�� ����true ,���򷵻�false����Ϊ�գ�
	 * 
	 */
	public static <V> boolean isEmpty(List<V> sourceList) {
		return (sourceList == null || sourceList.size() == 0);
	}

	/**
	 * 
	 * �ж�����list�Ƿ����
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
	 * ��List<String>���ӳ�Ϊһ���ַ�������","�ָ�
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
	 * ��List<String>���ӳ�Ϊһ���ַ�������ָ���ĵ��ַ�char�ָ�
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
	 * ��List<String>���ӳ�Ϊһ���ַ�������ָ�����ַ���separator�ָ� if separator is null, use
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
	 * ��List<V>����Ӳ����ڵ�Ԫ��V
	 * @param <V>
	 * @param sourceList
	 *            List<V> ����Դ
	 * @param entry
	 *            V Ԫ��
	 * @return ��List<V>�����Ԫ��V,���Ԫ��V�Ѿ����ڣ��򷵻�false,���Ԫ��V�����ڣ�����ӵ�List<V>�в�����true
	 */
	public static <V> boolean addDistinctEntry(List<V> sourceList, V entry) {
		return (sourceList != null && !sourceList.contains(entry)) ? sourceList
				.add(entry) : false;
	}

	/**
	 * 
	 * �Ȱ�entryList�� sourceListû�е�Ԫ����ӵ�sourceList��ȥ��
	 * Ȼ���ٷ��� sourceList ����˶��ٸ���ͬ����Ԫ��
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
	 * �Ƴ�sourceList����ͬ��Ԫ�أ��������Ƴ���sourceList�ĳ���
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
	 * ��List<V>�����Ԫ��<V>
	 * @param sourceList
	 * @param value
	 * @return <ul>
	 *         <li>��� sourceList is null, ���� false</li>
	 *         <li>��� value is null, ���� false</li>
	 *         <li>return {@link List#add(Object)}</li>
	 *         </ul>
	 */
	public static <V> boolean addListNotNullValue(List<V> sourceList, V value) {
		return (sourceList != null && value != null) ? sourceList.add(value)
				: false;
	}

	/**
	 * �õ�ָ��Ԫ�ص���һ��Ԫ��
	 * <ul>
	 * <li>eg: list {1,2,3}  getLast(list,2)=1,getLast(list,3)=2,getLast(list,1)=3 </li>
	 * <li>���γ�һ����յ�ѭ�����ҿռ� </li>
	 * </ul>
	 * @see {@link ArrayUtils#getLast(Object[], Object, Object, boolean)}
	 *      defaultValue is null, isCircle is true
	 */
	@SuppressWarnings("unchecked")
	public static <V> V getLast(List<V> sourceList, V value) {
		return (sourceList == null) ? null : (V) ArrayUtils.getLast(
				sourceList.toArray(), value, true);
	}

	/**�õ�ָ��Ԫ�ص���һ��Ԫ��
	 * <ul>
	 * <li>eg: list {1,2,3}  getLast(list,2)=3,getLast(list,3)=1,getLast(list,1)=2 </li>
	 * <li>���γ�һ����յ�ѭ�����ҿռ� </li>
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
	 * ��List<V>����
	 * <ul>
	 * <li>eg: list {1,2, 3} invertList(list) �� list ��Ϊ {3,2 1}</li>
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
