package communication;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Dylan Hiemstra
 */
public class Connection {
    private BufferedReader inputStream;
    private DataOutputStream outputStream;
    private BlockingQueue<String> responses = new LinkedBlockingDeque<>();

    public Connection(Socket socket) throws IOException {
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = new DataOutputStream(socket.getOutputStream());

        new Thread(new ListeningThread(inputStream, responses)).start();
    }

    /**
     * Skip lines
     *
     * @param lines The amount of line to be skipped
     */
    public void skipLines(int lines) {
        for (int i = 0; i < lines; i++) {
            readLine();
        }
    }

    /**
     * Read a single line
     *
     * @return the Line
     */
    public String readLine() {
        String line = null;

        try {
            line = responses.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return line;
    }

    /**
     * Send a command to the server
     *
     * @param arguments The arguments of the command (see protocol.txt on BlackBoard)
     * @throws IOException If something goes wrong
     */
    public void sendCommand(String... arguments) throws IOException {
        System.out.println("Executing: " + Arrays.toString(arguments));

        for (String argument : arguments) {
            for (char letter : argument.toCharArray()) {
                outputStream.writeByte((byte) letter);
            }

            outputStream.writeByte((byte) ' ');
        }

        outputStream.writeByte((byte) '\n');
    }

    /**
     * Expects to the result to be "OK"
     *
     * @throws NotOKResponseException If the result is not "OK"
     */
    public void expectOK() throws NotOKResponseException {
        String line = readLine();

        if (!line.equals("OK")) {
            throw new NotOKResponseException(line.substring(3).stripLeading());
        }
    }

    /**
     * Get a SVR Response
     *
     * @param type the type of SVR
     * @return the result of the SVR response
     * @throws NoSVRResponseException If the expected result was not returned
     */
    public String getSVRResponse(String type) throws NoSVRResponseException {
        String line = readLine();
        String expected = "SVR " + type;

        if (line.substring(0, expected.length()).equals(expected)) {
            return line.substring(expected.length()).stripLeading();
        }

        throw new NoSVRResponseException(line);
    }

}
