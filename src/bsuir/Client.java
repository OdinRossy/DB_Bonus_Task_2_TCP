package bsuir;
/*
        Created by Gleb Remniov and Christina Kovalevich
        Cooperate: remniov.work@gmail.com
 */
// Импортируем необходимые классы
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        while (true) {
            System.out.println("****************************************************************************************");
            System.out.println("Выберите действие:\n" +
                    "1. Подобрать квартиру.\n" +
                    "2. Показать все квартиры.\n" +
                    "3. Выйти.\n");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": {
                    System.out.println("Укажите Вашу сумму денег (у.е.):");
                    String cash = scanner.nextLine();
                    try {
                        String answer = sendMessage(cash);
                        if (answer.equals(Configuration.ERROR_IN_SERVER_01)){
                            System.err.println("Пожалуйста, вводите только цифры.");
                        }
                        else System.out.println(answer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "2":{
                    try {
                        System.out.println(sendMessage(Configuration.SHOW_ALL_APARTMENTS));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "3": {
                    try {
                        System.out.println("Завершение работы.");
                        System.out.println(sendMessage(Configuration.TURN_OFF));
                    } catch (IOException e) {
                        System.err.println("Сервер уже отключен.");
                    }
                    System.exit(0);
                }
                default: {
                    System.err.println("Выберите 1, 2 или 3:");
                    break;
                }
            }
        }
    }

    private static String sendMessage(String message) throws IOException{ //Непосредственная реализация для отправки сообщения серверу
        Socket socket = new Socket(Configuration.HOST, Configuration.PORT);
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(message.getBytes());
        byte [] bytes = new byte[1024];
        int length = inputStream.read(bytes);
        String answer = new String(bytes,0,length);
        inputStream.close();
        outputStream.close();
        socket.close();
        return answer; // Результатом будет являться ответ от сервера, который неприменно увидит пользователь
    }
}
