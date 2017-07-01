package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public interface ClientHandler {
	public void ClientIO(InputStream in, OutputStream out) throws IOException;
	public void addMessageToQueue(String line);
	void display(Character[][] board);
	void display(Character[][] board,Integer steps);
	List<String> getSolution(String level);
	public void ClientIO(Socket aClient, InputStream inputStream, OutputStream outputStream);
}
