package bsuir;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        // Заполняем коллекцию 10 квартирами
        Apartment.apartments.add(new Apartment(88000, "Космонавтов ул., 5, к. 1"));
        Apartment.apartments.add(new Apartment(115000, "Ломоносова ул., 4"));
        Apartment.apartments.add(new Apartment(17500, "Сурганова ул., 57"));
        Apartment.apartments.add(new Apartment(19500, "Уборевича ул., 132"));
        Apartment.apartments.add(new Apartment(35000, "Передовая ул., 5"));
        Apartment.apartments.add(new Apartment(39000, "Уручская ул., 13"));
        Apartment.apartments.add(new Apartment(40500, "Народная ул., 53, к. 1"));
        Apartment.apartments.add(new Apartment(40900, "Платонова ул., 35, к. 1"));
        Apartment.apartments.add(new Apartment(44900, "Жудро ул., 47"));
        Apartment.apartments.add(new Apartment(95500, "Боровляны, Лесной пос., 29"));
        //Создаем serversocket с портом, указанным в интерфейсе Configuration
        ServerSocket serverSocket = new ServerSocket(Configuration.PORT);
        Socket socket;
        while (true){ //Цикл для многократного подключения клиента
            System.out.println("Waiting for a connection..");
            socket = serverSocket.accept();// Ожидание подключения клиента
            InputStream inputStream = socket.getInputStream(); // Входящий поток данных от клиента
            OutputStream outputStream = socket.getOutputStream(); // Исходящий поток данных от сервера
            byte [] bytes = new byte[1024];
            int length = inputStream.read(bytes); // Записываем данные в массив bytes
            String request = new String(bytes,0,length); //Переводим в строку
            String answer = answerToClient(request); //Вызываем функцию и записываем ее результат в переменную answer
            System.out.println("Answer to client:\n" + answer);
            outputStream.write(answer.getBytes());// Отправляем данные, т.к answer - это String, преобразовываем в массив байтов с помощью метода getBytes()
            inputStream.close();// Закрываем все открытые потоки и сокеты
            outputStream.close();
            socket.close();
            if (answer.equals("Server is turned off.")){ // Если сообщение-ответ клиенту такое, то отстанавливаем программу
                System.exit(0);
            }
        }
    }
    private static String answerToClient(String request){ // тут решаем что отправлять клиенту
        switch (request){ // в зависимости от того, что отправил клиент, решаем, какой дать ответ
            case Configuration.TURN_OFF: {
                return "Server is turned off."; //ответ о отключении сервера
            }
            case Configuration.SHOW_ALL_APARTMENTS: {
                String answer = "";
                for (int i = 0; i < Apartment.apartments.size(); i++) {
                    answer += Apartment.apartments.get(i).toString() + "\n";
                }
                return answer;// ответ-строка со всеми квартирами
            }
            default:{
                String answer = "";
                try {
                    int cash = Integer.parseInt(request);
                    for (int i = 0; i<Apartment.apartments.size(); i++){
                        if (Apartment.apartments.get(i).getCost() <= cash){
                            answer+=Apartment.apartments.get(i).toString() + "\n";
                        }
                    }
                    if (answer.trim().length()==0){
                        return "Увы, квартир по такой цене нет..";
                    }
                    else return answer;
                } catch (NumberFormatException e){
                    return Configuration.ERROR_IN_SERVER_01;// Если пользователь будет вводить не цифры
                    // то сревер даст специальный ответ, с помощью которого программа-клиент уведомит пользователя об этом
                }
            }
        }
    }
}
