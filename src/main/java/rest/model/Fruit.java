package rest.model;

import java.util.ArrayList;
import java.util.List;

public class Fruit {
    long id;
    String name;
    String color;
    int price;
    List<String> sel;


    public Fruit() {
        id = 0;
        name = "";
        color = "";
        price = 0;
        sel = new ArrayList<>();
    }

    public Fruit(String name, String color, int price) {
        this.id = 0;
        this.name = name;
        this.color = color;
        this.price = price;
        this.sel = new ArrayList<>();
    }

    public Fruit(long id, String name, String color, int price) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.price = price;
        this.sel = new ArrayList<>();
    }

    public Fruit(long id, String name, String color, int price, List<String> sel) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.price = price;
        this.sel = new ArrayList<>();
        sel.forEach(e -> {
            this.sel.add(e);
        });
    }

    public long getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        if (sel.size() > 0) {
            for (int i = 0; i < sel.size(); ++i) {
                s.append(sel.get(i));
            }
            s.delete(s.length() - 2, s.length() - 1);
            return "id: " + id + ", name: " + name + ", color: " + color + ", price: " + price + "\nsellers: " + s;
        } else return "id: " + id + ", name: " + name + ", color: " + color + ", price: " + price;
    }
}