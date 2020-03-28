package communication;

/**
 * Created by Dylan Hiemstra
 */
public class NotOKResponseException extends Exception {
    public NotOKResponseException(String response) {
        super(response);
    }
}
