package rest.dto;

import rest.model.Seller;

import java.util.ArrayList;
import java.util.List;

public class FruitDto {
    long id;
    String name;
    String color;
    int price;


    public FruitDto() {
        id = 0;
        name = "";
        color = "";
        price = 0;
    }

    public FruitDto(String name, String color, int price) {
        this.name = name;
        this.color = color;
        this.price = price;
    }

    public FruitDto(long id) {
        this.id = id;
    }

    public FruitDto(long id, int price) {
        this.id = id;
        this.price = price;
    }

    public FruitDto(long id, String name, String color, int price) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

}