package rest.dto;

import java.util.ArrayList;
import java.util.List;

public class FruitDto {
    private long id = 0;
    private String name = "";
    private int price = 0;
    private List<SellersDto> sellers = new ArrayList<>();
    private String seller = "";

    public FruitDto() {
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

    public FruitDto(long id, String seller) {
        this.id = id;
        this.seller = seller;
    }

    public FruitDto(long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public FruitDto(long id, String name, int price, List<SellersDto> sellers) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sellers = new ArrayList<>();
        sellers.forEach(e -> {
            this.sellers.add(e);
        });
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

    public String getSeller() {
        return seller;
    }

}