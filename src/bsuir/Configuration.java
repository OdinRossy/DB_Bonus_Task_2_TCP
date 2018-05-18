package bsuir;
// Интерфес с константами которые справедливы и для сервера и для клиента.
// Почему интерфейс? Потому что нам нужные static final поля, которые такими являются тут по умолчанию

public interface Configuration {
    int PORT = 3001; //Ипользуемый сокетом порт
    String HOST = "localhost"; //Ипользуемый сокетом хост
    String ERROR_IN_SERVER_01 = "01";
    String SHOW_ALL_APARTMENTS = "show_all_apartments";
    String TURN_OFF = "exit(0)"; // Интструкция для выключения
}
