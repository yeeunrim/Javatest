package kr.or.iei.run;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	
	private static ServerSocket serverSocket;
	
	public static void main(String[] args) {
		
		System.out.println("====================================================");
		System.out.println("서버를 종료하려면 q 또는 Q를 입력하고 Enter를 누르세요.");
		System.out.println("====================================================");

		//TCP 서버 시작
		startServer();
		
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
		String key = scanner.nextLine();
		if(key.toLowerCase().equals("q")) {
		break;
		}
		
		scanner.close();
		
		stopServer();
		
		}
	}
		
	public static void startServer() {
		
		//작업 스레드 정의
		Thread thread = new Thread() {
			@Override
			public void run() {
				//ServerSocket 생성 및 Port 바인딩
				try {
					serverSocket = new ServerSocket(7777); //수정1 : 7000 -> 7777
					System.out.println("[서버] 시작됨");
					
					while(true) {
						//통신할 소켓 객체 선언
						Socket socket = serverSocket.accept(); //수정2 - 추가
						System.out.println("\n[서버] 연결 요청을 기다림\n");
						
						InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
						String clientIp = isa.getHostString();
						System.out.println("[서버] " + clientIp + "의 연결 요청을 수락함");
						
						//데이터 받기
						InputStream is = socket.getInputStream();
						DataInputStream dis = new DataInputStream(is);
						String message = dis.readUTF();
						//System.out.println("[서버] 데이터를 받음: " + message);
						
						//데이터 보내기
						OutputStream os = socket.getOutputStream();
						DataOutputStream dos = new DataOutputStream(os);
						dos.writeUTF(message);
						dos.flush();
						System.out.println("[서버] 받은 데이터를 다시 보냄: " + message);
						
						//연결 끊기
						socket.close();
						System.out.println("[서버] " + clientIp + "의 연결을 끊음");
					}
				} catch (IOException e) {
					System.out.println("[서버] " + e.toString());
				}
			} //run 끝
		}; //thread 끝
		
		thread.start(); //수정3 - 추가
		
	} //startServer 끝
	
	public static void stopServer() {
		try {
		serverSocket.close();
		} catch (IOException e) {
		e.printStackTrace();
		}
	} //stopServer 끝

} //class 끝
		
