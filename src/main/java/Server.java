import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws InterruptedException {

        try (ServerSocket server = new ServerSocket(8000)) {
            Socket client = server.accept();
            System.out.println("Подклюние установлено");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(client.getInputStream());

            while (!client.isClosed()){
                System.out.println("Сервер читает данные");
                String entry = dataInputStream.readUTF();
                System.out.println("Client: " + entry);

                if (entry.equals("quit")){
                    System.out.println("Соединение с клиентом прервано...");
                    break;
                }

                String serverCommand = bufferedReader.readLine();

                if (serverCommand.equals("quit")){
                    System.out.println("Сервер заканчивает работу");
                    dataOutputStream.writeUTF("quit");
                    break;
                } else {
                    dataOutputStream.writeUTF(serverCommand);
                    dataOutputStream.flush();
                }

            }

            dataInputStream.close();
            dataOutputStream.close();
            bufferedReader.close();
            client.close();
            System.out.println("Соединение завершено");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
