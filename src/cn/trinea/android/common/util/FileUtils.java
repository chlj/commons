package cn.trinea.android.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * File Utils
 * <ul>
 * Read or write file
 * <li>{@link #readFile(String)} read file</li>
 * <li>{@link #readFileToList(String)} read file to string list</li>
 * <li>{@link #writeFile(String, String, boolean)} write file from String</li>
 * <li>{@link #writeFile(String, String)} write file from String</li>
 * <li>{@link #writeFile(String, List, boolean)} write file from String List</li>
 * <li>{@link #writeFile(String, List)} write file from String List</li>
 * <li>{@link #writeFile(String, InputStream)} write file</li>
 * <li>{@link #writeFile(String, InputStream, boolean)} write file</li>
 * <li>{@link #writeFile(File, InputStream)} write file</li>
 * <li>{@link #writeFile(File, InputStream, boolean)} write file</li>
 * </ul>
 * <ul>
 * Operate file
 * <li>{@link #copyFile(String, String)}</li>
 * <li>{@link #getFileExtension(String)}</li>
 * <li>{@link #getFileName(String)}</li>
 * <li>{@link #getFileNameWithoutExtension(String)}</li>
 * <li>{@link #getFileSize(String)}</li>
 * <li>{@link #deleteFile(String)}</li>
 * <li>{@link #isFileExist(String)}</li>
 * <li>{@link #isFolderExist(String)}</li>
 * <li>{@link #makeFolders(String)}</li>
 * <li>{@link #makeDirs(String)}</li>
 * </ul>
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2012-5-12
 */
public class FileUtils {

	public final static String FILE_EXTENSION_SEPARATOR = ".";

	/**
	 * ��ȡ�ļ�
	 * 
	 * @param filePath
	 *            �ļ�·��
	 * @param charsetName
	 *            �ַ��� {@link java.nio.charset.Charset </code>charset<code>}
	 * @return ����ļ������ڷ���null,����ļ������򷵻��ļ�������
	 * 
	 * @throws RuntimeException
	 *             if an error occurs while operator BufferedReader
	 */
	public static StringBuilder readFile(String filePath, String charsetName) {
		File file = new File(filePath);
		StringBuilder fileContent = new StringBuilder("");
		if (file == null || !file.isFile()) {
			return null;
		}

		BufferedReader reader = null;
		try {
			InputStreamReader is = new InputStreamReader(new FileInputStream(
					file), charsetName);
			reader = new BufferedReader(is);
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (!fileContent.toString().equals("")) {
					fileContent.append("\r\n");
				}
				fileContent.append(line);
			}
			reader.close();
			return fileContent;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * д�����ݵ��ļ���ȥ
	 * 
	 * @param filePath
	 *            �ļ�·��,����ļ���������ᱻ����
	 * @param content
	 *            Ҫд������� eg: writeFile(filePathString, "��\r\n452\r\n��", false);
	 * @param append
	 *            �Ƿ�Ϊ׷������ ���true �������׷�����ļ����ݵ�����棬���false��������ļ����ݣ�Ȼ����д��������
	 * @return return ���Ҫд�������Ϊempty����false����������true
	 * @throws RuntimeException
	 *             if an error occurs while operator FileWriter
	 */
	public static boolean writeFile(String filePath, String content,
			boolean append) {
		if (StringUtils.isEmpty(content)) {
			return false;
		}

		FileWriter fileWriter = null;
		try {
			makeDirs(filePath);
			fileWriter = new FileWriter(filePath, append);
			fileWriter.write(content);
			fileWriter.close();
			return true;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * д�����ݵ��ļ���ȥ ÿһ�����ݺ��涼���һ��\r\n
	 * 
	 * @param filePath
	 *            �ļ�·��,����ļ���������ᱻ����
	 * @param contentList
	 *            Ҫд������� list�б�
	 * 
	 * @param append
	 *            �Ƿ�Ϊ׷������ ���true �������׷�����ļ����ݵ�����棬���false��������ļ����ݣ�Ȼ����д��������
	 * @return return ���Ҫд�������Ϊempty����false����������true
	 * @throws RuntimeException
	 *             if an error occurs while operator FileWriter
	 */
	public static boolean writeFile(String filePath, List<String> contentList,
			boolean append) {
		if (ListUtils.isEmpty(contentList)) {
			return false;
		}

		FileWriter fileWriter = null;
		try {
			makeDirs(filePath);
			fileWriter = new FileWriter(filePath, append);
			int i = 0;
			for (String line : contentList) {
				if (i++ > 0) {
					fileWriter.write("\r\n");
				}
				fileWriter.write(line);
			}
			fileWriter.close();
			return true;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * д�����ݵ��ļ���ȥ �����ԭ���ļ��е�����
	 * 
	 * 
	 * @param filePath
	 *            �ļ�·��,����ļ���������ᱻ����
	 * @param content
	 *            Ҫд�������
	 * @return ���Ҫд�������Ϊempty����false����������true
	 */
	public static boolean writeFile(String filePath, String content) {
		return writeFile(filePath, content, false);
	}

	/**
	 * 
	 * д�����ݵ��ļ���ȥ ÿһ�����ݺ��涼���һ��\r\n �����ԭ���ļ��е�����
	 * 
	 * @param filePath
	 *            �ļ�·��,����ļ���������ᱻ����
	 * @param contentList
	 *            Ҫд������� list�б�
	 * @return ���Ҫд�������Ϊempty����false����������true
	 */
	public static boolean writeFile(String filePath, List<String> contentList) {
		return writeFile(filePath, contentList, false);
	}

	/**
	 * д�����ݵ��ļ���ȥ �����ԭ���ļ��е�����
	 * 
	 * 
	 * @param filePath
	 *            �ļ�·��,����ļ���������ᱻ����
	 * @param stream
	 *            InputStream ��
	 * @return
	 * @see {@link #writeFile(String, InputStream, boolean)}
	 */
	public static boolean writeFile(String filePath, InputStream stream) {
		return writeFile(filePath, stream, false);
	}

	/**
	 * write file
	 * 
	 * @param file
	 *            the file to be opened for writing. ����ļ��������򷵻�null,�ļ� ���ڴ�д��
	 * @param stream
	 *            InputStream ��
	 * @param append
	 *            if <code>true</code>, then bytes will be written to the end of
	 *            the file rather than the beginning �Ƿ�Ϊ׷������ ���true
	 *            �������׷�����ļ����ݵ�����棬���false��������ļ����ݣ�Ȼ����д��������
	 * 
	 * @return return true
	 * @throws RuntimeException
	 *             if an error occurs while operator FileOutputStream
	 */
	public static boolean writeFile(String filePath, InputStream stream,
			boolean append) {
		return writeFile(filePath != null ? new File(filePath) : null, stream,
				append);
	}

	/**
	 * write file, the bytes will be written to the begin of the file
	 * 
	 * @param file
	 * @param stream
	 * @return
	 * @see {@link #writeFile(File, InputStream, boolean)}
	 */
	public static boolean writeFile(File file, InputStream stream) {
		return writeFile(file, stream, false);
	}

	/**
	 * write file
	 * 
	 * @param file
	 *            the file to be opened for writing.
	 * @param stream
	 *            the input stream
	 * @param append
	 *            if <code>true</code>, then bytes will be written to the end of
	 *            the file rather than the beginning
	 * @return return true
	 * @throws RuntimeException
	 *             if an error occurs while operator FileOutputStream
	 */
	public static boolean writeFile(File file, InputStream stream,
			boolean append) {
		OutputStream o = null;
		try {
			makeDirs(file.getAbsolutePath());
			o = new FileOutputStream(file, append);
			byte data[] = new byte[1024];
			int length = -1;
			while ((length = stream.read(data)) != -1) {
				o.write(data, 0, length);
			}
			o.flush();
			return true;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("FileNotFoundException occurred. ", e);
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (o != null) {
				try {
					o.close();
					stream.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * �ļ�����
	 * 
	 * @param sourceFilePath
	 *            Դ(ԭ)�ļ�·�� eg:"crash_wcp/xml/1234.png"
	 * @param destFilePath
	 *            ���ļ�·�� eg: "crash_wcp/xmls/1234.png"
	 * @return
	 * @throws RuntimeException
	 *             if an error occurs while operator FileOutputStream
	 */
	public static boolean copyFile(String sourceFilePath, String destFilePath) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(sourceFilePath);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("FileNotFoundException occurred. ", e);
		}
		return writeFile(destFilePath, inputStream);
	}

	/**
	 * ��ȡ�ļ��е����ݱ��浽List<String>��ȥ���ļ���ÿһ��Ϊһ��listԪ�� ����txt�ı�����\r\n����
	 * 
	 * @param filePath
	 *            �ļ�·��
	 * @param charsetName
	 *            �ַ��� The name of a supported {@link java.nio.charset.Charset
	 *            </code>charset<code>}
	 * @return ����ļ������ڷ���null,�ļ��������ȡ�ļ���������
	 * @throws RuntimeException
	 *             if an error occurs while operator BufferedReader
	 */
	public static List<String> readFileToList(String filePath,
			String charsetName) {
		File file = new File(filePath);
		List<String> fileContent = new ArrayList<String>();
		if (file == null || !file.isFile()) {
			return null;
		}

		BufferedReader reader = null;
		try {
			InputStreamReader is = new InputStreamReader(new FileInputStream(
					file), charsetName);
			reader = new BufferedReader(is);
			String line = null;
			while ((line = reader.readLine()) != null) {
				fileContent.add(line);
			}
			reader.close();
			return fileContent;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * 
	 * �õ��ļ���,��������׺��
	 * 
	 * <pre>
	 *      getFileNameWithoutExtension(null)               =   null
	 *      getFileNameWithoutExtension("")                 =   ""
	 *      getFileNameWithoutExtension("   ")              =   "   "
	 *      getFileNameWithoutExtension("abc")              =   "abc"
	 *      getFileNameWithoutExtension("a.mp3")            =   "a"
	 *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
	 *      getFileNameWithoutExtension("c:\\")              =   ""
	 *      getFileNameWithoutExtension("c:\\a")             =   "a"
	 *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
	 *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
	 *      getFileNameWithoutExtension("/home/admin")      =   "admin"
	 *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
	 * </pre>
	 * 
	 * @param filePath
	 * @return file name from path, not include suffix
	 * @see
	 */
	public static String getFileNameWithoutExtension(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return filePath;
		}

		int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		int filePosi = filePath.lastIndexOf(File.separator);
		if (filePosi == -1) {
			return (extenPosi == -1 ? filePath : filePath.substring(0,
					extenPosi));
		}
		if (extenPosi == -1) {
			return filePath.substring(filePosi + 1);
		}
		return (filePosi < extenPosi ? filePath.substring(filePosi + 1,
				extenPosi) : filePath.substring(filePosi + 1));
	}

	/**
	 * �õ��ļ��� ������׺��
	 * 
	 * <pre>
	 *      getFileName(null)               =   null
	 *      getFileName("")                 =   ""
	 *      getFileName("   ")              =   "   "
	 *      getFileName("a.mp3")            =   "a.mp3"
	 *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
	 *      getFileName("abc")              =   "abc"
	 *      getFileName("c:\\")              =   ""
	 *      getFileName("c:\\a")             =   "a"
	 *      getFileName("c:\\a.b")           =   "a.b"
	 *      getFileName("c:a.txt\\a")        =   "a"
	 *      getFileName("/home/admin")      =   "admin"
	 *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
	 * </pre>
	 * 
	 * @param filePath
	 * @return file name from path, include suffix
	 */
	public static String getFileName(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return filePath;
		}

		int filePosi = filePath.lastIndexOf(File.separator);
		return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
	}

	/**
	 * �õ��ļ����ļ������ڵ���һ��·��
	 * 
	 * <pre>
	 *      getFolderName(null)               =   null
	 *      getFolderName("")                 =   ""
	 *      getFolderName("   ")              =   ""
	 *      getFolderName("a.mp3")            =   ""
	 *      getFolderName("a.b.rmvb")         =   ""
	 *      getFolderName("abc")              =   ""
	 *      getFolderName("c:\\")              =   "c:"
	 *      getFolderName("c:\\a")             =   "c:"
	 *      getFolderName("c:\\a.b")           =   "c:"
	 *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
	 *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
	 *      getFolderName("/home/admin")      =   "/home"
	 *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
	 * </pre>
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFolderName(String filePath) {

		if (StringUtils.isEmpty(filePath)) {
			return filePath;
		}

		int filePosi = filePath.lastIndexOf(File.separator);
		return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
	}

	/**
	 * ��·������ļ��ĺ�׺
	 * 
	 * <pre>
	 *      getFileExtension(null)               =   ""
	 *      getFileExtension("")                 =   ""
	 *      getFileExtension("   ")              =   "   "
	 *      getFileExtension("a.mp3")            =   "mp3"
	 *      getFileExtension("a.b.rmvb")         =   "rmvb"
	 *      getFileExtension("abc")              =   ""
	 *      getFileExtension("c:\\")              =   ""
	 *      getFileExtension("c:\\a")             =   ""
	 *      getFileExtension("c:\\a.b")           =   "b"
	 *      getFileExtension("c:a.txt\\a")        =   ""
	 *      getFileExtension("/home/admin")      =   ""
	 *      getFileExtension("/home/admin/a.txt/b")  =   ""
	 *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
	 * </pre>
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileExtension(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			return filePath;
		}

		int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		int filePosi = filePath.lastIndexOf(File.separator);
		if (extenPosi == -1) {
			return "";
		}
		return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
	}

	/**
	 * Creates the directory named by the trailing filename of this file,
	 * including the complete directory path required to create this directory. <br/>
	 * <br/>
	 * �����ļ��� ������һ��·����ȷ����
	 * <ul>
	 * <strong>Attentions:</strong>
	 * <li>makeDirs("C:\\Users\\Trinea") ֻ�ܴ���Users�ļ���</li>
	 * <li>makeFolder("C:\\Users\\Trinea\\") ���Users�ļ��в����� ����Users�� Trinea�ļ��С�
	 * ���Users�ļ��д��� ����Trinea�ļ���</li>
	 * </ul>
	 * 
	 * @param filePath
	 * @return true if the necessary directories have been created or the target
	 *         directory already exists, false one of the directories can not be
	 *         created.
	 *         <ul>
	 *         <li>if {@link FileUtils#getFolderName(String)} return null,
	 *         return false</li>
	 *         <li>if target directory already exists, return true</li>
	 *         <li>return {@link java.io.File#makeFolder}</li>
	 *         </ul>
	 */
	public static boolean makeDirs(String filePath) {
		String folderName = getFolderName(filePath);
		if (StringUtils.isEmpty(folderName)) {
			return false;
		}

		File folder = new File(folderName);
		return (folder.exists() && folder.isDirectory()) ? true : folder
				.mkdirs();
	}

	/**
	 * �����ļ���
	 * <ul>
	 * <li>eg:�����ļ���ddc1234 makeFolders("ddc1234/")</li>
	 * </ul>
	 * 
	 * @param filePath
	 * @return
	 * @see #makeDirs(String)
	 */
	public static boolean makeFolders(String filePath) {
		return makeDirs(filePath);
	}

	/**
	 * �ж��ļ��Ƿ����
	 * <ul>
	 * <li>�ļ����ڷ���true,�����ڷ���false</li>
	 * <ul>
	 * 
	 * @param filePath
	 *            �ļ�·��
	 * @return
	 */
	public static boolean isFileExist(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			return false;
		}

		File file = new File(filePath);
		return (file.exists() && file.isFile());
	}

	/**
	 * �ж��ļ�Ŀ¼�Ƿ����
	 * <ul>
	 * <li>�ļ�Ŀ¼���ڷ���true,�����ڷ���false</li>
	 * <ul>
	 * 
	 * @param directoryPath
	 *            �ļ�Ŀ¼ ֻ�Ǹ�Ŀ¼����
	 * @return
	 */
	public static boolean isFolderExist(String directoryPath) {
		if (StringUtils.isBlank(directoryPath)) {
			return false;
		}
		File dire = new File(directoryPath);
		return (dire.exists() && dire.isDirectory());
	}

	/**
	 * ɾ���ļ����ļ��������е��ļ�
	 * <ul>
	 * <li>���·��Ϊnull or empty ���� true</li>
	 * <li>���·�������� ���� true</li>
	 * <li>����ļ�����, �ݹ�ɾ��(�ļ�/�ļ��������е��ļ�). ���� true</li>
	 * <ul>
	 * 
	 * @param path
	 *            �ļ�·��
	 * @return
	 */
	public static boolean deleteFile(String path) {
		if (StringUtils.isBlank(path)) {
			return true;
		}

		File file = new File(path);
		if (!file.exists()) {
			return true;
		}
		if (file.isFile()) {
			return file.delete();
		}
		if (!file.isDirectory()) {
			return false;
		}
		for (File f : file.listFiles()) {
			if (f.isFile()) {
				f.delete();
			} else if (f.isDirectory()) {
				deleteFile(f.getAbsolutePath());
			}
		}
		return file.delete();
	}

	/**
	 * �õ��ļ���С �ֽ���(Size)
	 * <ul>
	 * <li>����ļ�·�� Ϊnull or empty ���� -1</li>
	 * <li>����ļ�·�����ڲ����Ǹ��ļ��򷵻��ļ���С ���򷵻�-1</li>
	 * <ul>
	 * 
	 * @param path
	 *            �ļ�·��
	 * @return returns ����ļ������ڷ���-1�����򷵻��ļ��Ĵ�С
	 */
	public static long getFileSize(String path) {
		if (StringUtils.isBlank(path)) {
			return -1;
		}
		File file = new File(path);
		return (file.exists() && file.isFile() ? file.length() : -1);
	}
}
