package FinalWuZiQi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetHelper {
	public int PORT = 8000;

	/*
	 * PORT=8000 是指定的端口号。端口号是用于标识计算机上正在运行的进程（应用程序）的数字。 在这里，8888 是你的服务器将监听的端口号。
	 * 在网络通信中，服务器端需要监听一个特定的端口，以便客户端能够连接到服务器。端口号的范围是 0 到 65535，其中 0 到 1023
	 * 是系统保留端口，通常用于一些知名的服务，如 HTTP 使用的端口 80，FTP 使用的端口 21
	 * 等。因此，为了避免与系统保留端口冲突，我们可以选择使用较大的端口号，如 8000。 客户端会通过指定的 IP
	 * 地址和端口号来连接到服务器。在你的程序中，客户端可能会使用类似这样的代码： Socket clientSocket = new
	 * Socket("localhost", 8000); 这里的 "localhost" 是服务器所在的主机名或 IP 地址，8000
	 * 是服务器监听的端口号。客户端通过这个端口号连接到服务器，建立通信通道。
	 */
	private Socket s;// Socket是客户端
	private BufferedReader in;
	private PrintStream out;

	public void startListen() {
		new Thread() {
			public void run() {
				listen();
			}
		}.start();
	}

	private void listen() {
		try {

			// ServerSocket是服务端
			ServerSocket ss = new ServerSocket(PORT);
			s = ss.accept();
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintStream(s.getOutputStream(), true);
			startReadThread();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void startReadThread() {
		new Thread() {
			public void run() {
				while (true) {
					try {
						String line = in.readLine();
						System.out.println(line);
						if (line.startsWith("chess:")) {
							otherChess(line.substring("chess:".length()));
						} else if(line.startsWith("chat:")) {
							otherChat(line.substring("chat:".length()));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		}.start();
	}

	

	protected void otherChess(String line) {
		String[] param = line.split(",");
		int row = Integer.parseInt(param[0]);
		int col = Integer.parseInt(param[1]);
		Vars.control3.otherChess(row, col);

	}

	 public void sendChess(int row,int col){
		 if(out!=null){
	        out.println("chess:"+row+","+col); 
	      } 
     } 
	 
	 protected void otherChat(String line) {
			Vars.paintPanel3.chatArea.append("Other:"+line+"\n");
	 }
	 
	 public void sendChat(String line){
	     if(out!=null){ 
	    	 out.println("chat:"+line); 
	     } 
	 }

	public void connect(String ip, int port) {
		try {
			Socket s = new Socket(ip, port);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintStream(s.getOutputStream(), true);
			startReadThread();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
