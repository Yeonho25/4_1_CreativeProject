package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import com.mysql.cj.protocol.Message;

import DTO.TourDTO;
import controller.Tour_CRUD;
import javafx.application.Platform;

public class  Client 
{
	public static Socket socket;
	
	public void startclient(String IP, int port)
	{
		Thread thread = new Thread()
		{
			public void run()
			{
				try
				{
					socket  = new Socket(IP, port);
//					receive();
				} catch (Exception e)
				{
					if (! socket.isClosed())
					{
						stopClient();
						System.out.println("접속 실패");
						Platform.exit();
					}
				}
			}
		};
		thread.start();
	}
	
	public void stopClient()
	{
		try
		{
			if (socket != null && ! socket.isClosed())
			{
				socket.close();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Object receive()
	{
			try
			{
				InputStream in  = socket.getInputStream();
				byte[] buffer = new byte[512];
				int length = in.read(buffer);
				if(length == -1) throw new IOException();
				String message = new String(buffer, 0, length, "UTF-8");
				
				if (message.equals("F"))
					return null;
				else
				{
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		
					return ois.readObject();
				}
			} catch (Exception e)
			{
				stopClient();
				
			}
			
		return null;
	
	}
	
	public void send(String message, Object tour)
	{

		try
		{
			
			OutputStream out = socket.getOutputStream();
			byte[] buffer = message.getBytes("UTF-8");
			out.write(buffer);
			out.flush();
			System.out.println(message);
			
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			
			ArrayList<Object> tourList = new ArrayList<>();
			tourList.add(tour);
			
			System.out.println("클라이언트 - send : " + tourList.get(0).toString());
			
			oos.writeObject(tourList);
			oos.flush();

		} catch (IOException e) {
		    System.out.println(e.getMessage());
		}
		
	}
	
	
}
