package rest.dto;

import rest.model.Fruit;

import java.util.ArrayList;
import java.util.List;

public class SellersDto {
    long id;
    long supplier_id;
    String name;
    String supplier;
    List<Fruit> fruits;

    public SellersDto() {
        name = "";
        id = 0;
        supplier = "";
        supplier_id = 0;
        fruits = new ArrayList<>();
    }

    public SellersDto(long id) {
        this.id = id;
    }

    public SellersDto(String name, long supplier_id) {
        this.name = name;
        this.supplier_id = supplier_id;
    }

    public SellersDto(long id, String name, String supplier) {
        this.id = id;
        this.name = name;
        this.supplier_id = supplier_id;
        this.supplier = supplier;
    }

    public SellersDto(long id, String name, String supplier, List<Fruit> fruits) {
        this.id = id;
        this.name = name;
        this.supplier_id = supplier_id;
        this.supplier = supplier;
        this.fruits = new ArrayList<>();
        fruits.forEach(e -> {
            this.fruits.add(e);
        });
    }


    public long getId() {
        return id;
    }

    public String getSupplier() {
        return supplier;
    }

    public long getSupplier_id() {
        return supplier_id;
    }

    public String getName() {
        return name;
    }

}