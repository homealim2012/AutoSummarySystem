package edu.as.sys.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

/**
 * Created by jianying.lin@foxmail.com on 2016/10/24.
 */
public class FileDirectory {
	
	/**
	 * 获取文件夹下的一层文件和目录
	 * @param folderPath
	 * @return 文件夹下的一层文件和目录的绝对路径
	 */
	public static ArrayList<String> getFileAndDirectoryPathUnderFolder(String folderPath) {
		File folder = new File(folderPath);
		if (!folder.isDirectory()) {
			System.err.println("Please pass the folder path inside the 'getFileAndDirectoryPathUnderFolder' function!!!");
			return null;
		}
		ArrayList<String> fileAndDirectoryPathList = new ArrayList<String>();
		String[] fileList = folder.list();
		for (int i=0; i<fileList.length; i++) {
			fileAndDirectoryPathList.add(filePathJoin(folder.getAbsolutePath(), fileList[i]));
		}
		return fileAndDirectoryPathList;
	}

	/**
	 * 创建文件夹
	 * @param folderPath
	 * @return true
	 */
	public static ArrayList<String> getFilePathUnderFolder(String folderPath) {
		File folder = new File(folderPath);
		if (!folder.isDirectory()) {
			System.err.println("Please pass the folder path inside the 'getFileAndDirectoryPathUnderFolder' function!!!");
			return null;
		}
		ArrayList<String> filePathList = new ArrayList<String>();
		String[] fileList = folder.list();
		for (int i=0; i<fileList.length; i++) {
			File fileNow = new File(filePathJoin(folder.getAbsolutePath(), fileList[i]));
			if (fileNow.isFile()) {
				filePathList.add(fileNow.getAbsolutePath());
			}
		}
		return filePathList;
	}
	
	/**
	 * 创建文件夹
	 * @param folderPath
	 */
	public static void createFolder(String folderPath) {
		File folder = new File(folderPath);
		if (folder.exists()) {
			System.err.println("Folder path is exist inside the 'createFolder' function!!!");
			return;
		}
		folder.mkdir();
	}
	
	/**
	 * 获取文件夹下的一层目录
	 * @param folderPath
	 * @return 文件夹下的一层目录的绝对路径
	 */
	public static ArrayList<String> getDirectoryPathUnderFolder(String folderPath) {
		File folder = new File(folderPath);
		if (!folder.isDirectory()) {
			System.err.println("Please pass the folder path inside the 'getFileAndDirectoryPathUnderFolder' function!!!");
			return null;
		}
		ArrayList<String> directoryPathList = new ArrayList<String>();
		String[] fileList = folder.list();
		for (int i=0; i<fileList.length; i++) {
			File fileNow = new File(filePathJoin(folder.getAbsolutePath(), fileList[i]));
			if (fileNow.isDirectory()) {
				directoryPathList.add(fileNow.getAbsolutePath());
			}
		}
		return directoryPathList;
	}
	
	/**
	 * 将文件夹路径与文件名拼接在一起形成文件路径，能够适应Windows和Linux的拼接
	 * @param folder
	 * @param fileName
	 * @return folder与fileName的拼接
	 */
	public static String filePathJoin(String folder, String fileName) {
		Path path = Paths.get(folder, fileName);
		return path.toString();
	}
	
	/**
	 * 获取文件路径对应的文件名，能够适应Windows和Linux的拼接
	 * @param path
	 * @return fileName
	 */
	public static String getFileOrFolderNameFromPath(String path) {
		File file = new File(path);
		return file.getName();
	}
	
	/**
	 * 移动文件
	 * @param sourceFilePath
	 * @param destinationFilePath
	 */
	public static void moveFile(String sourceFilePath, String destinationFilePath) {
		/*不能替换已存在的文件
		try {
			File file = new File(sourceFilePath);
			File destFile = new File(destinationFilePath);
			file.renameTo(destFile);
		}
		catch (Exception e) {
			System.err.println("Move file error!");
			e.printStackTrace();
		}
		*/
		//way2
		final int tryTime = 5;
		int t = 0;
		while (t < tryTime) {
			try {
				File file = new File(sourceFilePath);
				File destFile = new File(destinationFilePath);
				Files.move(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING); //替换已存在
				break;
			}
			catch (IOException e) {
				t++;
				System.err.println("Move file error! try:"+t);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 复制文件
	 * @param sourceFilePath
	 * @param destinationFilePath
	 */
	public static void copyFile(String sourceFilePath, String destinationFilePath) {
		try {
			File file = new File(sourceFilePath);
			File destFile = new File(destinationFilePath);
			Files.copy(file.toPath(), destFile.toPath());
		}
		catch (Exception e) {
			System.err.println("Copy file error!");
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除文件
	 * @param path
	 */
	public static void deleteFile(String path) {
		try {
			File file = new File(path);
			file.delete();
		}
		catch (Exception e) {
			System.err.println("Delete file error!");
			e.printStackTrace();
		}
	}
	
	/**  
     * 删除文件夹  
     * @param path 
     */  
   public static void deleteFolder(String path)  {  
       try {  
           deleteAllFileUnderFolder(path);
           File file = new File(path);
           file.delete();
       }  
       catch (Exception e)  {  
           System.out.println("删除文件夹操作出错");  
           e.printStackTrace();
       } 
   }  
 
   /**  
     * 删除文件夹里面的所有文件  
     * @param path
     */  
   public static void deleteAllFileUnderFolder(String path)  {  
	   File file = new File(path);
       if (!file.exists())  {  
           return;  
       }  
       if (!file.isDirectory())  {  
           return;  
       }
       ArrayList<String> pathList = getFileAndDirectoryPathUnderFolder(path);
       for (int i=0; i<pathList.size(); i++) {
    	   File fileTemp = new File(pathList.get(i));
    	   if (fileTemp.isFile()) {
    		   deleteFile(pathList.get(i));
    	   }
    	   else if (fileTemp.isDirectory()) {
    		   deleteFolder(pathList.get(i));
    	   }
       }       
   }
   

	/**
	 * 根据修改时间移动一个文件夹下的文件（修改时间比传入的时间点大）
	 * @param sourceFolder
	 * @param destinationFolder
	 */
	public static void moveFileUnderFolder(String sourceFolder, String destinationFolder, long timePoint) {
		try {
			ArrayList<String> filePathList = getFilePathUnderFolder(sourceFolder);
			for (int i=0; i<filePathList.size(); i++) {
				File fileTemp = new File(filePathList.get(i));
				if (fileTemp.lastModified() > timePoint) {
					moveFile(fileTemp.getAbsolutePath(), filePathJoin(destinationFolder, fileTemp.getName()));
				}
			}
		}
		catch (Exception e) {
			System.err.println("Move file error!");
			e.printStackTrace();
		}
	}
}
