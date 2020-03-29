package communication;

import communication.commands.*;
import communication.events.Event;
import communication.events.ReceivedChallenge;
import communication.states.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

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

    private BlockingQueue<Event> eventBus = new LinkedBlockingDeque<>();

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
     * Logout of the server
     */
    public void logout() {
        executeCommand(new Logout());
    }

    public void challenged(JSONObject data) {
        setState(new Challenged(this));
        Event receivedChallenge = new ReceivedChallenge(data);
        eventBus.add(receivedChallenge);
    }

    public void acceptChallenge(ReceivedChallenge event) {
        connection.stopListening();

        executeCommand(new AcceptChallenge(event.getChallengeNumber()));

        try {
            connection.expectOK();
        } catch (NotOKResponseException e) {
            e.printStackTrace();
        }

        connection.startListening();
    }

    public void denyChallenge(ReceivedChallenge event) {
        setState(new WaitingMode(this));
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

    public void startWaitingMode() {
        setState(new WaitingMode(this));
        connection.startListening();
    }

    public void challenge(String player, String game) {
        connection.stopListening();
        executeCommand(new SendChallenge(player, game));

        try {
            connection.expectOK();
        } catch (NotOKResponseException e) {
            e.printStackTrace();
            return;
        }

        setState(new ChallengeSent(this));
        connection.startListening();
    }

    public void subscribe(String game) {
        connection.stopListening();
        executeCommand(new Subscribe(game));

        try {
            connection.expectOK();
        } catch (NotOKResponseException e) {
            e.printStackTrace();
            return;
        }

        setState(new Subscribed(this));
        connection.startListening();
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

    public BlockingQueue<Event> getEventBus() {
        return eventBus;
    }
}
