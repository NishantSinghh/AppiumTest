package com.cg.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class utilities {
	
	public void StartAppium()throws InterruptedException, IOException {
		
		String os_name = null;
		os_name = System.getProperty("os.name");
		System.out.println(os_name);
		CommandLine command = null;
		
		if(os_name.contains("Windows")){
			// Starting Appium on Windows-----------------------------------------------------------
						System.out.print("Attempting to start appium server..");
						command = new CommandLine("cmd");
						command.addArgument("/c");
						command.addArgument("D:\\Automation_Tools\\Appium\\node.exe");
						command.addArgument("D:\\Automation_Tools\\Appium\\node_modules\\appium\\bin\\appium.js");
						command.addArgument("--address");
						command.addArgument("127.0.0.1");
						command.addArgument("--port");
						command.addArgument("4723");
						command.addArgument("--command-timeout");
						command.addArgument("180");
						command.addArgument("--chromedriver-port");
						command.addArgument("9157");
						command.addArgument("--no-reset");
						command.addArgument("--log");
						command.addArgument("D:\\log\\appiumLogs.txt");  
						
						
			/*			System.out.print("Attempting to start appium server ..");
						String[] command1 = {"cmd.exe", "/C", "Start", strStartServerPath};
						Runtime.getRuntime().exec(command1);		*/
			//-----------------------------------------------------------------------
			
		} else if (os_name.contains("Mac")){
			
		//	Runtime.getRuntime().exec("/bin/bash export ANDROID_HOME=/Users/admincg/MTCoE/android/sdk");
			
						command = new CommandLine("/Applications/Appium.app/Contents/Resources/node/bin/node");
						command.addArgument("/Applications/Appium.app/Contents/Resources/node_modules/appium/bin/appium.js",false);
						command.addArgument("--address",false);
						command.addArgument("127.0.0.1",false);
						command.addArgument("--port",false);
						command.addArgument("4723",false);
						command.addArgument("--full-reset",false);
						command.addArgument("--automation-name",false);
						command.addArgument("Appium",false);
		//				command.addArgument("--no-reset",false);
		//				command.addArgument("--platform-name",false);
		//				command.addArgument("iOS",false);
		//				command.addArgument("--browser-name",false);
		//				command.addArgument("Safari",false);
												
		}
		
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		DefaultExecutor executor = new DefaultExecutor();
		executor.setExitValue(1);
		try {
			executor.execute(command, resultHandler);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread.sleep(10000);
		System.out.print("Appium Started..");
		
		// TODO Auto-generated method stub
		
		
		}

	public void StopAppium(){
		try{
			CommandLine command = new CommandLine("cmd");
			command.addArgument("/c");
			command.addArgument("taskkill");
			command.addArgument("/F");
			command.addArgument("/IM");
			command.addArgument("node.exe");
			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
			DefaultExecutor executor = new DefaultExecutor();
			executor.setExitValue(1);
			executor.execute(command, resultHandler);
			Thread.sleep(4000);
			System.out.println("StopAppiumExecuted");
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public String getCellValue(String SheetName, String ColumnName){
		File file = new File("./data/DataSheet.xlsx");
		String cellValue=null;
		try {
			FileInputStream ins = new FileInputStream(file);
			Workbook wrkData = new XSSFWorkbook(ins);
			Sheet shData = wrkData.getSheet(SheetName);
			Row row = shData.getRow(0);
			Row row2 = shData.getRow(1);
			for(int i=0; i<row.getLastCellNum();i++){
				String strColName = row.getCell(i).getStringCellValue();
				if(strColName.equalsIgnoreCase(ColumnName)){
					cellValue = row2.getCell(i).getStringCellValue();
					return cellValue;
				}
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
