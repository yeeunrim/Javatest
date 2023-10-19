package kr.or.iei.run;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) {
		
		try {
			Socket socket = new Socket("127.0.0.1", 7777); 
			System.out.println("[클라이언트] 연결 성공");
			
			//데이터 보내기
			String sendMessage = "오늘도 좋은 하루 되세요!";
			OutputStream os = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF(sendMessage);
			dos.flush();
			System.out.println("[클라이언트] 데이터를 보냄: " + sendMessage);
			
			//데이터 받기
			InputStream is = socket.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			String receiveMessage = dis.readUTF(); //수정4 is -> dis
			System.out.println("[클라이언트] 데이터를 받음: " + receiveMessage);
			
			socket.close();
			System.out.println("[클라이언트] 연결 끊음");
			
			} catch (UnknownHostException e) {
			System.out.println(e.toString());
			} catch (IOException e) {
			System.out.println(e.toString());
			}
		
	}
}
