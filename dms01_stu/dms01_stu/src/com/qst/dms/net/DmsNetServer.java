package com.qst.dms.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.ArrayList;

import com.qst.dms.entity.MatchedLogRec;
import com.qst.dms.entity.MatchedTransport;
import com.qst.dms.service.LogRecService;
import com.qst.dms.service.TransportService;
//服务器应用程序，接收客户端发送来的数据保存到数据库
public class DmsNetServer{
	public DmsNetServer() {
		//通过使用不同端口区分接收不同数据：6666端口是日志，6668端口是物流
		//开启监听6666端口的线程,接收日志数据
		new AcceptLogThread(6666).start();
		//开启监听6668端口的线程，接收物流数据
		new AcceptTranThread(6668).start();
		System.out.println("网络服务器已开启...");
	}
	//接收日志数据的线程类
	private class AcceptLogThread extends Thread{
		private ServerSocket serverSocket;
		private Socket socket;
		private LogRecService logRecService;
		private ObjectInputStream ois;
		
		public AcceptLogThread(int port) {
			logRecService = new LogRecService();
			try {
				serverSocket = new ServerSocket(port);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		//重写run()方法
		public void run() {
			while(this.isAlive()) {
				try {
					//接收客户端发送过来的套接字
					socket =serverSocket.accept();
					if(socket !=null) {
						ois =new ObjectInputStream(socket.getInputStream());
						//反序列化，得到匹配日志列表
						ArrayList<MatchedLogRec> matchedLogs =(ArrayList<MatchedLogRec>) ois
								.readObject();
						//将客户端发送来的匹配日志信息保存到数据库
						logRecService.saveMatchLogToDB(matchedLogs);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			try {
				ois.close();
				socket.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	//接收物流数据的线程类
	private class AcceptTranThread extends Thread{
		private ServerSocket serverSocket;
		private Socket socket;
		private TransportService traSportService;
		private ObjectInputStream ois;
		
		public AcceptTranThread(int port) {
			traSportService = new TransportService();
			try {
				serverSocket = new ServerSocket(port);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		//重写run()方法
		public void run() {
			while(this.isAlive()) {
				try {
					//接收客户端发送过来的套接字
					socket =serverSocket.accept();
					if(socket !=null) {
						ois =new ObjectInputStream(socket.getInputStream());
						//反序列化，得到匹配日志列表
						ArrayList<MatchedTransport> matchedTrans =(ArrayList<MatchedTransport>) ois
								.readObject();
						//将客户端发送来的匹配日志信息保存到数据库
						traSportService.saveMatchTransportToDB(matchedTrans);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			try {
				ois.close();
				socket.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		new DmsNetServer();
	}
}