package rest.dto;

import java.util.ArrayList;
import java.util.List;

public class SellersDto {
    long id;
    String name;
    String supply;
    SuppliersDto supplier;
    List<FruitDto> fruits;

    public SellersDto() {
        name = "";
        supply = "";
        id = 0;
        supplier = new SuppliersDto();
        fruits = new ArrayList<>();
    }

    public SellersDto(long id) {
        this.id = id;
    }

    public SellersDto(long id, String name) {
        this.id = id;
        this.supply = "";
        this.name = name;
        supplier = new SuppliersDto();
        fruits = new ArrayList<>();
    }

    public SellersDto(String name, String sup) {
        this.name = name;
        this.supply = sup;
        supplier = new SuppliersDto();
        fruits = new ArrayList<>();
    }

    public SellersDto(String name, SuppliersDto supplier) {
        this.name = name;
        this.supplier = supplier;
        fruits = new ArrayList<>();
    }

    public SellersDto(String name, SuppliersDto supplier, List<FruitDto> fruits) {
        this.name = name;
        this.supply = "";
        this.supplier = supplier;
        this.fruits = new ArrayList<>();
        fruits.forEach(e -> {
            this.fruits.add(e);
        });
    }

    public SellersDto(long id, String name, SuppliersDto supplier) {
        this.id = id;
        this.name = name;
        this.supply = "";
        this.supplier = supplier;
        this.fruits = new ArrayList<>();
    }

    public SellersDto(long id, String name, SuppliersDto supplier, List<FruitDto> fruits) {
        this.id = id;
        this.name = name;
        this.supply = "";
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
        return supply;
    }

    public String getName() {
        return name;
    }

}