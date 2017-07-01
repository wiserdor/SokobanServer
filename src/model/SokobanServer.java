package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class SokobanServer extends Observable {
	private int port;
	private int usersConnected;
	private volatile boolean isConnected;
	private volatile boolean stop;
	private ExecutorService threadPool;
	private static final int THREADS_NUM = 30;
	private ClientHandler cli;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public ClientHandler getCli() {
		return cli;
	}

	public void setCli(ClientHandler cli) {
		this.cli = cli;
	}

	public int getUsersConnected() {
		return usersConnected;
	}

	public void setUsersConnected(int usersConnected) {
		this.usersConnected = usersConnected;
	}

	public SokobanServer(int port, ClientHandler cli) {
		super();
		this.port = port;
		this.isConnected = false;
		this.stop = false;
		this.cli = cli;
	}

	private void runServer() throws IOException {
		ServerSocket server = new ServerSocket(port);
		server.setSoTimeout(10000);
		while (!stop) {
			try {
				Socket aClient = server.accept();
				this.isConnected = true;
				threadPool.execute(new Runnable() {
					@Override
					public void run() {
						try {

							cli.ClientIO(aClient,aClient.getInputStream(), aClient.getOutputStream());
							aClient.getInputStream().close();
							aClient.getOutputStream().close();
							aClient.close();

						} catch (IOException e) {
							System.out.println("invalid I/O of client");
						}
					}
				});

			} catch (SocketTimeoutException e) {
			}
			server.close();
		}
	}
	public void stopServer() {		
		threadPool.shutdown();
		try {
			threadPool.awaitTermination(5, TimeUnit.SECONDS);			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			stop = true;
		}
		
	}
	public void start() {
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

	public int addUser(int usersConnected) {

		return usersConnected++;

	}
}
