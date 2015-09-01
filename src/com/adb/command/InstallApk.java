package com.adb.command;


import java.io.IOException;

import javax.swing.JOptionPane;
import com.cg.utilities.utilities;

public class InstallApk {
	CommandPrompt cmd = new CommandPrompt();
	utilities util = new utilities();
	public boolean installMain(){
		boolean blDevice;
		String strPath = util.getCellValue("Test1", "ApkPath");
		String DeviceID = util.getCellValue("Test1", "DeviceId");
		String strPacName = util.getCellValue("Test1", "PackageName");
		System.out.println(strPath + " " + DeviceID + " " + strPacName);
		try {
			blDevice = ChkDeviceCon(DeviceID);
			System.out.println(strPath);
			if(blDevice == true){
				uninstall(DeviceID, strPacName);
				install(DeviceID, strPath);
				return true;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
			
	}
	public void uninstall(String strDeviceId, String strAppPath) throws InterruptedException, IOException{
		//String strDeviceName = "emulator-5554";
		//String strAppPackageName = "cap.travel";
		// TODO Auto-generated method stub
		String output = cmd.runCommand("adb -s " +strDeviceId+ " uninstall " + strAppPath);
		String[] lines = output.split("\n");
		System.out.println(output);
		if(lines[0].contains("Failure"))
			System.out.println("Process Failed may be package name is wrong");
		else if(lines[0].equalsIgnoreCase("Success")){
			System.out.println("App uninstallation successful");
		}
	}
	public void install(String strDeviceId, String strAppPath) throws InterruptedException, IOException{
		//String strDeviceName = "emulator-5554";
		//String strAppPath = "D:\\Android\\Apk_Files\\Captravel.apk";
		// TODO Auto-generated method stub
		String command = "adb -s " +strDeviceId+ " install " + "\""+ strAppPath+"\"";
		String output = cmd.runCommand(command);
		String[] lines = output.split("\n");
		System.out.println(output);
		if(lines[0].contains("Invalid")){
			System.out.println("Either Apk file path is wrong or It is an invalid file");
			System.out.println("Stopping Execution");
			System.exit(0);
		}
		else if(lines[2].contains("Failure")){
			System.out.println("Process Failed may be app already installed");
			System.out.println("Stopping Execution");
			System.exit(0);
		}
		else if(lines[2].equalsIgnoreCase("Success")){
			System.out.println("App installation successful");
		}
		
	}
	public void startADB() throws Exception{
		String output = cmd.runCommand("adb start-server");
		String[] lines = output.split("\n");
		if(lines.length==1)
			System.out.println("adb service already started");
		else if(lines[1].equalsIgnoreCase("* daemon started successfully *"))
			System.out.println("adb service started");
		else if(lines[0].contains("internal or external command")){
			System.out.println("adb path not set in system varibale");
			//System.exit(0);
		}
	}
	public void stopADB() throws Exception{
		cmd.runCommand("adb kill-server");
	}
	public boolean ChkDeviceCon(String strDeviceID) throws Exception{
		startADB(); // start adb service
		String output = cmd.runCommand("adb devices");
		String[] lines = output.split("\n");
		if(lines.length<=1){
			System.out.println("No Device Connected");
			stopADB();
			System.exit(0);	// exit if no connected devices found
		}
		
		for(int i=1;i<lines.length;i++){
			lines[i]=lines[i].replaceAll("\\s+", "");
			
			if(lines[i].contains("device")){
				lines[i]=lines[i].replaceAll("device", "");
				String deviceID = lines[i];
				if(strDeviceID.equalsIgnoreCase(deviceID)){
					System.out.println("Expected Device is connected");
					return true;
				}
				String model = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.product.model").replaceAll("\\s+", "");
				String brand = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.product.brand").replaceAll("\\s+", "");
				String osVersion = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.build.version.release").replaceAll("\\s+", "");
				String deviceName = brand+" "+model;
				System.out.println("Following device is connected");
				System.out.println(deviceID+" "+deviceName+" "+osVersion+"\n");
			}else if(lines[i].contains("unauthorized")){
				lines[i]=lines[i].replaceAll("unauthorized", "");
				String deviceID = lines[i];
				System.out.println("Following device is unauthorized");
				System.out.println(deviceID+"\n");
			}else if(lines[i].contains("offline")){
				lines[i]=lines[i].replaceAll("offline", "");
				String deviceID = lines[i];
				System.out.println("Following device is offline");
				System.out.println(deviceID+"\n");
			}
		}
		return false;
	}
}
