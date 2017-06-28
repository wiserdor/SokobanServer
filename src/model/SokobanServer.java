package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Observable;

public class SokobanServer extends Observable {
	private int port;
	private volatile boolean isConnected;
	private volatile boolean stop;
	private ClientHandler cli;

	public SokobanServer(int port, ClientHandler cli) {
		super();
		this.port = port;
		this.isConnected = false;
		this.stop = false;
		this.cli= cli;
	}

	private void runServer() throws IOException {
		ServerSocket server = new ServerSocket(port);
		server.setSoTimeout(10000);
		while (!stop) {
			try {
				Socket aClient = server.accept();
				this.isConnected = true;
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {

							cli.ClientIO(aClient.getInputStream(), aClient.getOutputStream());
							if (stop) {

								aClient.getInputStream().close();
								aClient.getOutputStream().close();
								aClient.close();
							}

						} catch (IOException e) {
							System.out.println("invalid I/O of client");
						}
					}
				}).start();

			} catch (SocketTimeoutException e) {
			}
			server.close();
		}
	}
	public void start(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					runServer();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
	}


}
