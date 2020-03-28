package communication;

import communication.commands.Command;
import communication.commands.GetGameList;
import communication.commands.GetPlayerList;
import communication.commands.Login;
import communication.states.CommunicationState;
import communication.states.Connected;
import communication.states.LoggedIn;
import communication.states.NotConnected;
import org.json.JSONArray;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Dylan Hiemstra
 */
public class StrategicGameClient {
    /**
     * The instance of StrategicGameClient (singleton)
     */
    private static StrategicGameClient instance;

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
     * Get the instance of the StrategicGameClient
     *
     * @return CommunicationManager
     */
    public static StrategicGameClient getInstance() {
        if(instance == null) {
            instance = new StrategicGameClient();
        }

        return instance;
    }


    private StrategicGameClient() {
         setState(new NotConnected(this));
    }

    /**
     * Execute a command
     *
     * @param command The command to be executed
     */
    private void executeCommand(Command command) {
        command.execute();
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
        executeCommand(new Login(username));

        try {
            connection.expectOK();
        } catch (NotOKResponseException e) {
            System.out.println("Login failed! " + e.getMessage());
            return;
        }

        setState(new LoggedIn(this));
    }

    /**
     * Get game list
     * @return the games as json array
     */
    public JSONArray getGameList() {
        executeCommand(new GetGameList());

        String result = "[]";
        try {
            connection.expectOK();
            result = connection.getSVRResponse("GAMELIST");
        } catch (NoSVRResponseException | NotOKResponseException e) {
            e.printStackTrace();
        }

        return new JSONArray(result);
    }

    /**
     * Get player list
     *
     * @return the player list as json array
     */
    public JSONArray getPlayerList() {
        executeCommand(new GetPlayerList());

        String result = "[]";
        try {
            connection.expectOK();
            result = connection.getSVRResponse("PLAYERLIST");
        } catch (NoSVRResponseException | NotOKResponseException e) {
            e.printStackTrace();
        }

        return new JSONArray(result);
    }

    /**
     * Update the state
     * @param state The new communication state
     */
    public void setState(CommunicationState state) {
        System.out.println("Communication State: " + state.getClass().getSimpleName());
        communicationState = state;
    }

    public CommunicationState getState() {
        return communicationState;
    }

    public Connection getConnection() {
        return connection;
    }
}
