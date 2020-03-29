package communication.events;

import communication.StrategicGameClient;
import org.json.JSONObject;

/**
 * Created by Dylan Hiemstra
 */
public class ReceivedChallenge extends Event {
    private String challenger;
    private int challengeNumber;
    private String gameType;

    public ReceivedChallenge(JSONObject json) {
        challenger = json.getString("CHALLENGER");
        challengeNumber = Integer.parseInt(json.getString("CHALLENGENUMBER"));
        gameType = json.getString("GAMETYPE");
    }

    public void acceptChallenge() {
        StrategicGameClient.getInstance().acceptChallenge(this);
    }

    public void denyChallenge() {
        StrategicGameClient.getInstance().denyChallenge();
    }

    public String getChallenger() {
        return challenger;
    }

    public int getChallengeNumber() {
        return challengeNumber;
    }

    public String getGameType() {
        return gameType;
    }
}
