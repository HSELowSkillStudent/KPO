package org.example.multiple.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {

  private final ServerSocket serverSocket;
  private final List<String> usernames = new ArrayList<>();

  public Server(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
  }

  public void startServer() {
    try {
      while (!serverSocket.isClosed()) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("A new client has connected!");
        ClientHandler clientHandler = new ClientHandler(clientSocket, serverSocket);
        if (usernames.contains(clientHandler.getClientUsername())) {
          clientHandler.usernameIsTaken();
          clientHandler.closeEverything();
          System.out.println("This username is already taken!");
          continue;
        }
        usernames.add(clientHandler.getClientUsername());

        Thread clientThread = new Thread(clientHandler);
        clientThread.start();
      }
    } catch (IOException e) {
      closeServerSocket();
    }
  }

  private void closeServerSocket() {
    try {
      if (serverSocket != null) {
        serverSocket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
