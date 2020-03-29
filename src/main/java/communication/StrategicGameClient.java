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
public class StrategicGameClient implements GameClient {
    /**
     * The instance of StrategicGameClient (singleton)
     */
    private static GameClient instance;

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

    private StrategicGameClient() {
        setState(new NotConnected(this));
    }

    /**
     * Get the instance of the StrategicGameClient
     *
     * @return CommunicationManager
     */
    public static GameClient getInstance() {
        if (instance == null) {
            instance = new StrategicGameClientProxy(new StrategicGameClient());
        }

        return instance;
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
        executeCommand(new Login(this, username));

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
        connection.stopListening();
        executeCommand(new Logout(this));

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get game list
     *
     * @return the games as json array
     */
    public JSONArray getGameList() {
        executeCommand(new GetGameList(this));

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
        executeCommand(new GetPlayerList(this));

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
     * Start listening for events
     */
    public void startWaiting() {
        setState(new Waiting(this));
        connection.startListening();
    }

    /**
     * Challenge someone
     *
     * @param player the player
     * @param game   the game
     */
    public void challenge(String player, String game) {
        connection.stopListening();
        executeCommand(new SendChallenge(this, player, game));

        try {
            connection.expectOK();
        } catch (NotOKResponseException e) {
            e.printStackTrace();
            return;
        }

        setState(new ChallengeSent(this));
        connection.startListening();
    }

    /**
     * Subscribe to a game
     *
     * @param game the game
     */
    public void subscribe(String game) {
        connection.stopListening();
        executeCommand(new Subscribe(this, game));

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
     * Challenged by someone
     *
     * @param data event data
     */
    public void challenged(JSONObject data) {
        setState(new Challenged(this));
        Event receivedChallenge = new ReceivedChallenge(data);
        eventBus.add(receivedChallenge);
    }

    /**
     * Accept the challenge
     *
     * @param event
     */
    public void acceptChallenge(ReceivedChallenge event) {
        connection.stopListening();

        executeCommand(new AcceptChallenge(this, event.getChallengeNumber()));

        try {
            connection.expectOK();
        } catch (NotOKResponseException e) {
            e.printStackTrace();
        }

        connection.startListening();
    }

    /**
     * Deny the challenge (there is not command for this)
     */
    public void denyChallenge() {
        setState(new Waiting(this));
    }

    public CommunicationState getState() {
        return communicationState;
    }

    /**
     * Update the state
     *
     * @param state The new communication state
     */
    private void setState(CommunicationState state) {
        System.out.println("Communication State: " + state.getClass().getSimpleName());
        communicationState = state;
    }

    public Connection getConnection() {
        return connection;
    }

    public BlockingQueue<Event> getEventBus() {
        return eventBus;
    }
}
