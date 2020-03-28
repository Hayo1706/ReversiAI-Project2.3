package communication;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by Dylan Hiemstra
 */
public class Connection {
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Thread listeningThread = null;

    public Connection(Socket socket) throws IOException {
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
    }

    /**
     * Skip lines
     *
     * @param lines The amount of line to be skipped
     */
    public void skipLines(int lines) {
        for(int i = 0; i < lines; i++) {
            readLine();
        }
    }

    /**
     * Read a single line
     *
     * @return the Line
     */
    public String readLine() {
        StringBuilder line = new StringBuilder();
        int incomingByte;

        try {
            while((incomingByte = inputStream.read()) != 10) {
                if(incomingByte == -1) break; // disconnected
                if(incomingByte == 13) continue; // skip carriage return
                line.append((char) incomingByte);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Response from server: " + line);

        return line.toString();
    }

    /**
     * Send a command to the server
     *
     * @param arguments The arguments of the command (see protocol.txt on BlackBoard)
     * @throws IOException If something goes wrong
     */
    public void sendCommand(String... arguments) throws IOException {
        System.out.println("Executing: " + Arrays.toString(arguments));

        for(String argument : arguments) {
            for(char letter : argument.toCharArray()) {
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

        if(!line.equals("OK")) {
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

        if(line.substring(0, expected.length()).equals(expected)) {
            return line.substring(expected.length()).stripLeading();
        }

        throw new NoSVRResponseException(line);
    }

    public void startListening() {
        if(listeningThread == null) {
            listeningThread = new Thread(new ListeningThread(this));
            listeningThread.start();
        }
    }

    public void stopListening() {
        if(listeningThread != null) {
            listeningThread.interrupt();
            listeningThread = null;
        }
    }

    public boolean hasBytesToRead() {
        try {
            return inputStream.available() > 0;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
