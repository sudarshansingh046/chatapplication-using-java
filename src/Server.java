
import java.net.*;
import java.sql.DriverManager;
import java.util.Vector;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.io.*;
public class Server implements Runnable{
Socket s;
public static Vector client =new Vector();
	public void run(){
		try{
			BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			client.add(bw);
			while(true)
			{
				String str=br.readLine().trim();
				System.out.println(str);
				try{
				Class.forName("com.mysql.jdbc.Driver");  // loading driver  or class 
		        Connection con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","");  // connection

		           
		           
		            PreparedStatement ps = (PreparedStatement) con.prepareStatement("insert into server values(?)");
		            
		            ps.setString(1,str);
		            
		            int n = ps.executeUpdate();
				}
				
				catch(Exception e)
				{
					System.out.println(e);
				}
				for(int i=0;i<client.size();i++)
				{
					try{
						BufferedWriter b=(BufferedWriter)client.get(i);
						b.write(str+"\n");
						b.flush();
						
					}
					catch(Exception e)
					{
						
					}
					
				}
				
			}
		}
		catch(Exception e)
		{
		}
	}
	public Server(Socket s) {
		try{
		this.s = s;
		}
		catch(Exception e)
		{
			
		}
	}

	public static void main(String args[])
	{
		try
		{
		ServerSocket ss=new ServerSocket(3005);
		while(true)
		{
			Socket s=ss.accept();
			Server server=new Server(s);
			Thread t=new Thread(server);
			t.start();	
		}
		}
		catch(Exception e)
		{
			
		}
			
		
		
		
		}
}
