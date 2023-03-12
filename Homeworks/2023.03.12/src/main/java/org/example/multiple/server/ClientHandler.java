package org.example.multiple.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.System.exit;
import static org.example.multiple.utils.IOUtils.close;
import static org.example.multiple.utils.IOUtils.writeAndFlush;

public class ClientHandler implements Runnable {

    public static HashMap<Integer, List<ClientHandler>> clientHandlersMap = new HashMap<>();

    private Socket socket;
    private ServerSocket serverSocket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String clientUsername;

    public ClientHandler(Socket socket, ServerSocket serverSocket) {
        try {
            this.socket = socket;
            this.serverSocket = serverSocket;
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            var serverSocketPort = this.serverSocket.getLocalPort();
            System.out.println("Socket port: " + socket.getPort());
            System.out.println("Server socket port: " + serverSocketPort);
            if (!clientHandlersMap.containsKey(serverSocketPort)) {
                System.out.println("Creating new list for server socket port: " + serverSocketPort);
                clientHandlersMap.put(serverSocketPort, new ArrayList<>());
            } else {
                System.out.println("List for server socket port: " + serverSocketPort + " already exists, values: " + clientHandlersMap.get(serverSocketPort).toString() + " size: " + clientHandlersMap.get(serverSocketPort).size());
            }
            clientHandlersMap.get(serverSocketPort).add(this);
            publishMessage("SERVER " + serverSocketPort + ": " + clientUsername + " has entered the chat!");
        } catch (IOException e) {
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                publishMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything(socket, bufferedWriter, bufferedReader);
                break;
            }
        }
    }

    private void publishMessage(final String messageToPublish) {
        List<ClientHandler> clientHandlers = clientHandlersMap.get(serverSocket.getLocalPort());
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUsername.equals(clientUsername)) {
                    writeAndFlush(clientHandler.bufferedWriter, messageToPublish);
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedWriter, bufferedReader);
            }
        }
    }

    private void removeClientHandler() {
        clientHandlersMap.get(serverSocket.getLocalPort()).remove(this);
        publishMessage("SERVER: " + clientUsername + " has left the chat!");
    }

    private void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        removeClientHandler();
        close(socket);
        close(bufferedWriter);
        close(bufferedReader);
    }

    public void closeEverything() {
        closeEverything(socket, bufferedWriter, bufferedReader);
    }

    public String getClientUsername() {
        return clientUsername;
    }

    void usernameIsTaken() {
        try {
            writeAndFlush(bufferedWriter, "Username is already taken!");
            exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isNicknameAlreadyTaken(String nickname, int serverPort) {
        // я не знаю, оно просто возвращает null постоянно(не видит, что там что-то есть),
        // хотя в конструкторе все оно видит
        if (clientHandlersMap.get(serverPort) == null) {
            return false;
        }
        System.out.println(clientHandlersMap.get(serverPort).stream().anyMatch(clientHandler -> clientHandler.getClientUsername().equals(nickname)));
        return clientHandlersMap.get(serverPort).stream().anyMatch(clientHandler -> clientHandler.getClientUsername().equals(nickname));
    }

}
