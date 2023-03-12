package org.example.multiple.server;

import org.example.multiple.client.ClientApp;
import org.example.multiple.database.Database;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerApp {

  private static final int PORT = 8080;
  private static final int PORT_FOR_KIDS = 4040;

  public static ArrayList<String> usernames = new ArrayList<>();

  public static void main(String[] args) throws IOException {
    Database.clearDatabase(ClientApp.DATABASE_FILE_NAME);
    var serverSocket = new ServerSocket(PORT);
    var server = new Server(serverSocket);
    var serverSocketForKids = new ServerSocket(PORT_FOR_KIDS);
    var serverForKids = new Server(serverSocketForKids);

    Thread thread = new Thread(server::startServer);
    Thread threadForKids = new Thread(serverForKids::startServer);

    thread.start();
    threadForKids.start();

    try {
      thread.join();
      threadForKids.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
