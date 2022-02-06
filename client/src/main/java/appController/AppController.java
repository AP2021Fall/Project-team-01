package appController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class AppController {
    private static DataInputStream inputStream;
    private static DataOutputStream outputStream;

    public static void setupConnection() {
        try {
            Socket socket = new Socket("localhost", 7777);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DataInputStream getInputStream() {
        return inputStream;
    }

    public static DataOutputStream getOutputStream() {
        return outputStream;
    }

    public static String getResult(String command) throws IOException {
        outputStream.writeUTF(command);
        outputStream.flush();
        return inputStream.readUTF();
    }

    public static ArrayList<String> getArraylistResult(String command) throws IOException {
        outputStream.writeUTF(command);
        outputStream.flush();
        String json = inputStream.readUTF();
        return new Gson().fromJson(json, new Gson().fromJson(json,
                new TypeToken<List<String>>() {
                }.getType()));
    }

}
