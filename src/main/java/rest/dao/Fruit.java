package rest.dao;
public class Fruit {
    long id;
    String name, color;
    int price;
    public Fruit(){};
    public Fruit(String name, String color, int price){
        this.name=name;
        this.color=color;
        this.price=price;
    }
    public Fruit(long id, String name, String color, int price){
        this.id=id;
        this.name=name;
        this.color=color;
        this.price=price;
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
    public void setId(long id) {
        this.id = id;
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
        return "id: "+id+", name: "+name+", color: "+color+", price: "+price;
    }
}