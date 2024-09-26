package rest.model;


public class Fruit {
    long id;
    String name;
    int price;

    public Fruit() {
        id = 0;
        name = "";
        price = 0;
    }

    public Fruit(String name) {
        this.name = name;
    }

    public Fruit(String name, int price) {
        this.id = 0;
        this.name = name;
        this.price = price;
    }

    public Fruit(long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }


    @Override
    public String toString() {
        return "id: " + id + ", name: " + name + ", price: " + price;
    }
}