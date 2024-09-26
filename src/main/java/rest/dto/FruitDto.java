package rest.dto;


public class FruitDto {
    long id;
    String name;
    int price;

    public FruitDto() {
        id = 0;
        name = "";
        price = 0;
    }

    public FruitDto(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public FruitDto(long id) {
        this.id = id;
    }

    public FruitDto(String name) {
        this.name = name;
    }

    public FruitDto(long id, int price) {
        this.id = id;
        this.price = price;
    }

    public FruitDto(long id, String name, int price) {
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

}