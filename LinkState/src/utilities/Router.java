package utilities;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Router {
	
	public ConcurrentLinkedQueue<Message> messageBuffer;
	public RouterId routerId;
	public Map<RouterId, Map<RouterId, Integer>> routingTable;

	public Router(int serverPort) throws UnknownHostException {
		messageBuffer = new ConcurrentLinkedQueue<>();
		ServerThread serverThread = new ServerThread(serverPort);
		new Thread(serverThread).start();
		MessageHandler messageHandler = new MessageHandler();
		new Thread(messageHandler).start();

		//
		routingTable = new HashMap<>();
		routerId = new RouterId(InetAddress.getLocalHost(), serverPort);
	}

	class ServerThread implements Runnable {
		private int serverPort;
		private ServerSocket server;

		public ServerThread(int serverPort) {
			this.serverPort = serverPort;
		}

		public void run() {
			try {
				server = new ServerSocket(serverPort);
				while (true) {
					Socket socket = server.accept();
					ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
					messageBuffer.add(new Message(socket.getInetAddress(), (String)in.readObject()));
					in.close();
					socket.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void handleMessage(Message m) {
		//System.out.println(m.address.getHostAddress() + " " + m.port + " " + m.message);
	}

	class MessageHandler implements Runnable {
		public void run() {
			while (true) {
				if (!messageBuffer.isEmpty()) {
					handleMessage(messageBuffer.poll());
					try {
						Thread.sleep(10);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	protected boolean send(String ip, int port, String message) {
		try {
			Socket socket = new Socket(ip, port);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(routerId.port + "!" + message);
			out.flush();
			out.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void connect(String ip, int port) {
		//send(ip, port, "Connect");
	}

	public void sendMessage(String ip, int port, String message) {
		//send(ip, port, message);
	}

//	public static void main(String[] args) {
//		try {
//			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//			System.out.println("Please input port");
//			Router router = new Router(Integer.valueOf(reader.readLine()));
//			System.out.println("Connect a router : -c ip port");
//			System.out.println("Send a message : -s ip port message");
//			String s = null;
//			while ((s = reader.readLine()) != null) {
//				String[] temps = s.split(" ");
//				if (temps[0].equals("-c")) {
//					router.connect(temps[1], Integer.valueOf(temps[2]));
//				} else if (temps[0].equals("-s")) {
//					router.sendMessage(temps[1], Integer.valueOf(temps[2]), temps[3]);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}