package com.verysu.basic.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
/**
 * MySQL数据工具类
 * @author Sjg
 * 2014-10-2
 * 下午06:55:49
 */
public class MySQLUtil {
	private static MySQLUtil util = null;
	/**
	 * 要备份的文件名
	 */
	private String filename;
	/**
	 * 需要备份到哪个文件夹中
	 */
	private String backupDir;
	/**
	 * 需要备份的几个基本信息
	 */
	private String database;
	private String username;
	private String password;
	
	
	private MySQLUtil(){}
	
	public static MySQLUtil getInstance() {
		if(util==null) util = new MySQLUtil();
		return util;
	}
	
	public void setCfg(String filename,String backupDir,String database,String username,String password) {
		this.filename = filename;
		this.backupDir = backupDir;
		this.database = database;
		this.username = username;
		this.password = password;
	}
	
	public void backup() {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			String osCmd = null;
			if("\\".equals(File.separator)){
				osCmd = "cmd /c ";
			}else{
				osCmd = "";
			}
			String cmd = osCmd + "mysqldump -u"+username+" -p"+password+" "+database;
			Process proc = Runtime.getRuntime().exec(cmd);
			br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			bw = new BufferedWriter(
					new FileWriter(backupDir+File.separator+filename+".sql"));
			String str = null;
			while((str=br.readLine())!=null) {
				bw.write(str);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br!=null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(bw!=null) bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void resume() {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			String osCmd = null;
			if("\\".equals(File.separator)){
				osCmd = "cmd /c ";
			}else{
				// if("/".equals(File.separator))
				osCmd = "";
			}
			String cmd = osCmd + "mysql -u"+username+" -p"+password+" "+database;
			Process proc = Runtime.getRuntime().exec(cmd);
			bw = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream()));
			br = new BufferedReader(new FileReader(backupDir+File.separator+filename+".sql"));
			String str = null;
			while((str=br.readLine())!=null) {
				bw.write(str);
				bw.newLine();
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br!=null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(bw!=null) bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
