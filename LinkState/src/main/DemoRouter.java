package main;

import utilities.Message;
import utilities.Router;
import java.io.*;
import java.net.UnknownHostException;

public class DemoRouter extends Router {
	public DemoRouter(int serverPort) throws UnknownHostException {
		super(serverPort);
	}

	/*
	 * 这个是用来处理收到的消息的
	 */
	@Override
	protected void handleMessage(Message m) {
		System.out.println(m.address.getHostAddress() + " " + m.port + " " + m.message);
	}

	/*
	 * 这个是用来跟其他路由建立连接的
	 */
	@Override
	public void connect(String ip, int port) {
		send(ip, port, "Connect");
	}

	/*
	 * 这个是用来传消息的
	 */
	@Override
	public void sendMessage(String ip, int port, String message) {
		send(ip, port, message);
	}

	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Please input port");
			Router router = new DemoRouter(Integer.valueOf(reader.readLine()));
			System.out.println("Connect a router : -c ip port");
			System.out.println("Send a message : -s ip port message");
			String s = null;
			while ((s = reader.readLine()) != null) {
				String[] temps = s.split(" ");
				if (temps[0].equals("-c")) {
					router.connect(temps[1], Integer.valueOf(temps[2]));
				} else if (temps[0].equals("-s")) {
					router.sendMessage(temps[1], Integer.valueOf(temps[2]), temps[3]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}