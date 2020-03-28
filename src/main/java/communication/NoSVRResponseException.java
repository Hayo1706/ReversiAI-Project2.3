package communication;

/**
 * Created by Dylan Hiemstra
 */
public class NoSVRResponseException extends Exception {
    public NoSVRResponseException(String response) {
        super(response);
    }
}
