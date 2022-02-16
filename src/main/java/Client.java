import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {

    public static void main(String[] args) throws InterruptedException {

        try (Socket socket = new Socket("localhost", 8000);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
             DataInputStream dataInputStream = new DataInputStream(socket.getInputStream()))
        {
            System.out.println("Клиент подключается к сокету");


            while (!socket.isOutputShutdown()){
                String clienCommand = bufferedReader.readLine();
                dataOutputStream.writeUTF(clienCommand);
                dataOutputStream.flush();

                if (clienCommand.equals("quit")){
                    System.out.println("Клиент разорвал соединение");
                    break;
                }

                String in = dataInputStream.readUTF();
                System.out.println("Server: " + in);

                if (in.equals("quit")){
                    break;
                }
            }

            System.out.println("Соединение прервано");

        } catch (UnknownHostException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
