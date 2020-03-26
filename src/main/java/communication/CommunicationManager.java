package communication;

import communication.commands.Command;
import communication.commands.Login;
import communication.states.CommunicationState;
import communication.states.Connected;
import communication.states.LoggedIn;
import communication.states.NotConnected;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Dylan Hiemstra
 */
public class CommunicationManager {
    private static CommunicationManager instance;

    private CommunicationState communicationState;
    private Socket socket = new Socket();

    private DataInputStream socketIn;
    private DataOutputStream socketOut;

    public static CommunicationManager getInstance() {
        if(instance == null) {
            instance = new CommunicationManager();
        }

        return instance;
    }

    private CommunicationManager() {
        communicationState = new NotConnected(this);
    }

    private void executeCommand(Command command) {
        command.execute();

        // TODO: Wait for command result
    }

    private void readInputStream() {
        new Thread(() -> {
            while (true) {
                try {
                    int incomingByte = socketIn.read();

                    if(incomingByte == -1) {
                        System.out.println("Disconnected");
                        break;
                    } else if(incomingByte != 10) {
                        System.out.print((char) incomingByte);
                    } else {
                        System.out.println();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void connect(String host, Integer port) throws IOException {
        socket.connect(new InetSocketAddress(host, port), 1000);

        socketIn = new DataInputStream(socket.getInputStream());
        socketOut = new DataOutputStream(socket.getOutputStream());

        readInputStream();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setState(new Connected(this));
    }

    public void login(String username) {
        executeCommand(new Login(socketOut, username));
        setState(new LoggedIn(this));
    }

    public void setState(CommunicationState state) {
        communicationState = state;
    }

    public CommunicationState getState() {
        return communicationState;
    }
}
