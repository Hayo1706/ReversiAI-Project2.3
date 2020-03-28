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

/**
 * Created by Dylan Hiemstra
 */
public class CommunicationManager {
    /**
     * The instance of CommunicationManager (singleton)
     */
    private static CommunicationManager instance;

    /**
     * The state of the communication
     */
    private CommunicationState communicationState;

    /**
     * The socket to the server
     */
    private Socket socket = new Socket();

    /**
     * The connection
     */
    private Connection connection;

    /**
     * Get the instance of the CommunicationManager
     *
     * @return CommunicationManager
     */
    public static CommunicationManager getInstance() {
        if(instance == null) {
            instance = new CommunicationManager();
        }

        return instance;
    }


    private CommunicationManager() {
         setState(new NotConnected(this));
    }

    /**
     * Execute a command. Expects the response of the server to be "OK"
     *
     * @param command The command to be executed
     * @throws NotOKResponseException If the response is not "OK"
     */
    private void executeCommand(Command command) throws NotOKResponseException {
        command.execute();
        connection.expectOK();
    }

    /**
     * Connect to the server
     *
     * @param host The host of the server
     * @param port The port of the server
     * @throws IOException If the connection could not be made
     */
    public void connect(String host, Integer port) throws IOException {
        socket.connect(new InetSocketAddress(host, port), 1000);
        connection = new Connection(socket);
        connection.skipLines(2);

        setState(new Connected(this));
    }

    /**
     * Login on the server
     *
     * @param username The username
     */
    public void login(String username) {
        try {
            executeCommand(new Login(username));
            setState(new LoggedIn(this));
        } catch (NotOKResponseException e) {
            System.out.println("Login failed! " + e.getMessage());
        }
    }


    public void setState(CommunicationState state) {
        communicationState = state;
    }

    public CommunicationState getState() {
        return communicationState;
    }

    public Connection getConnection() {
        return connection;
    }
}
