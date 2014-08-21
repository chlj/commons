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
	 * 读取文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @param charsetName
	 *            字符集 {@link java.nio.charset.Charset </code>charset<code>}
	 * @return 如果文件不存在返回null,如果文件存在则返回文件的内容
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
	 * 写入内容到文件中去
	 * 
	 * @param filePath
	 *            文件路径,如果文件不存在则会被创建
	 * @param content
	 *            要写入的内容 eg: writeFile(filePathString, "好\r\n452\r\n跑", false);
	 * @param append
	 *            是否为追加内容 如果true 则把内容追加在文件内容的最后面，如果false则先清除文件内容，然后再写入新内容
	 * @return return 如果要写入的内容为empty返回false，其它返回true
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
	 * 写入内容到文件中去 每一个内容后面都会加一个\r\n
	 * 
	 * @param filePath
	 *            文件路径,如果文件不存在则会被创建
	 * @param contentList
	 *            要写入的内容 list列表
	 * 
	 * @param append
	 *            是否为追加内容 如果true 则把内容追加在文件内容的最后面，如果false则先清除文件内容，然后再写入新内容
	 * @return return 如果要写入的内容为empty返回false，其它返回true
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
	 * 写入内容到文件中去 会清除原有文件中的内容
	 * 
	 * 
	 * @param filePath
	 *            文件路径,如果文件不存在则会被创建
	 * @param content
	 *            要写入的内容
	 * @return 如果要写入的内容为empty返回false，其它返回true
	 */
	public static boolean writeFile(String filePath, String content) {
		return writeFile(filePath, content, false);
	}

	/**
	 * 
	 * 写入内容到文件中去 每一个内容后面都会加一个\r\n 会清除原有文件中的内容
	 * 
	 * @param filePath
	 *            文件路径,如果文件不存在则会被创建
	 * @param contentList
	 *            要写入的内容 list列表
	 * @return 如果要写入的内容为empty返回false，其它返回true
	 */
	public static boolean writeFile(String filePath, List<String> contentList) {
		return writeFile(filePath, contentList, false);
	}

	/**
	 * 写入内容到文件中去 会清除原有文件中的内容
	 * 
	 * 
	 * @param filePath
	 *            文件路径,如果文件不存在则会被创建
	 * @param stream
	 *            InputStream 流
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
	 *            the file to be opened for writing. 如果文件不存在则返回null,文件 用于打开写入
	 * @param stream
	 *            InputStream 流
	 * @param append
	 *            if <code>true</code>, then bytes will be written to the end of
	 *            the file rather than the beginning 是否为追加内容 如果true
	 *            则把内容追加在文件内容的最后面，如果false则先清除文件内容，然后再写入新内容
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
	 * 文件拷贝
	 * 
	 * @param sourceFilePath
	 *            源(原)文件路径 eg:"crash_wcp/xml/1234.png"
	 * @param destFilePath
	 *            新文件路径 eg: "crash_wcp/xmls/1234.png"
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
	 * 读取文件中的内容保存到List<String>中去，文件中每一行为一个list元素 。如txt文本中以\r\n区别
	 * 
	 * @param filePath
	 *            文件路径
	 * @param charsetName
	 *            字符集 The name of a supported {@link java.nio.charset.Charset
	 *            </code>charset<code>}
	 * @return 如果文件不存在返回null,文件存在则读取文件返回内容
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
	 * 得到文件名,不包括后缀名
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
	 * 得到文件夹 包括后缀名
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
	 * 得到文件或文件夹所在的上一级路径
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
	 * 从路径获得文件的后缀
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
	 * 创建文件夹 根据上一级路径来确定的
	 * <ul>
	 * <strong>Attentions:</strong>
	 * <li>makeDirs("C:\\Users\\Trinea") 只能创建Users文件夹</li>
	 * <li>makeFolder("C:\\Users\\Trinea\\") 如果Users文件夹不存在 创建Users和 Trinea文件夹。
	 * 如果Users文件夹存在 创建Trinea文件夹</li>
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
	 * 创建文件夹
	 * <ul>
	 * <li>eg:创建文件夹ddc1234 makeFolders("ddc1234/")</li>
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
	 * 判断文件是否存在
	 * <ul>
	 * <li>文件存在返回true,不存在返回false</li>
	 * <ul>
	 * 
	 * @param filePath
	 *            文件路径
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
	 * 判断文件目录是否存在
	 * <ul>
	 * <li>文件目录存在返回true,不存在返回false</li>
	 * <ul>
	 * 
	 * @param directoryPath
	 *            文件目录 只是个目录而已
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
	 * 删除文件或文件夹下所有的文件
	 * <ul>
	 * <li>如果路径为null or empty 返回 true</li>
	 * <li>如果路径不存在 返回 true</li>
	 * <li>如果文件存在, 递归删除(文件/文件夹中所有的文件). 返回 true</li>
	 * <ul>
	 * 
	 * @param path
	 *            文件路径
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
	 * 得到文件大小 字节数(Size)
	 * <ul>
	 * <li>如果文件路径 为null or empty 返回 -1</li>
	 * <li>如果文件路径存在并且是个文件则返回文件大小 否则返回-1</li>
	 * <ul>
	 * 
	 * @param path
	 *            文件路径
	 * @return returns 如果文件不存在返回-1，否则返回文件的大小
	 */
	public static long getFileSize(String path) {
		if (StringUtils.isBlank(path)) {
			return -1;
		}
		File file = new File(path);
		return (file.exists() && file.isFile() ? file.length() : -1);
	}
}
