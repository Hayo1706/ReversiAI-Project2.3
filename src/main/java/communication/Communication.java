package communication;

import communication.states.CommunicationState;
import communication.states.NotConnected;

public class Communication {
    private static Communication instance;

    private CommunicationState communicationState;

    public static Communication getInstance() {
        if(instance == null) {
            instance = new Communication();
        }

        return instance;
    }

    private Communication() {
        communicationState = new NotConnected();
    }
}
