package bsuir;

import java.util.ArrayList;
// Класс для создания объектов квартир
public class Apartment {
    private int cost;
    private String address;
    public static ArrayList<Apartment> apartments = new ArrayList<>(); // Объекты будует храниться в коллеции

    public Apartment(int cost, String address) { // Конструктор со всеми полями
        this.cost = cost;
        this.address = address;
    }

    @Override // Переопределяем метод toString
    public String toString() {
        return "Адрес: " + address + ", Стоимость: " + cost + " у.е." ;
    }

    public int getCost() {
        return cost;
    } // Геттер для поля cost
}
