package rest.model;

import java.util.ArrayList;
import java.util.List;

public class Seller {
    private long id;
    private String name;
    private String supply;
    private String fruit;
    private Supplier supplier;
    private List<Fruit> fruits;

    public Seller() {}

    public Seller(long id) {
        this.id = id;
    }

    public Seller(long id, String fruit) {
        this.id = id;
        this.fruit = fruit;
    }

    public Seller(String name, String supply) {
        this.name = name;
        this.supply = supply;
    }

    public Seller(String name, Supplier supplier) {
        this.name = name;
        this.supplier = supplier;
    }

    public Seller(String name, Supplier supplier, List<Fruit> fruits) {
        this.name = name;
        this.supplier = supplier;
        this.fruits = new ArrayList<>();
        fruits.forEach(e -> {
            this.fruits.add(e);
        });
    }

    public Seller(long id, String name, Supplier supplier) {
        this.id = id;
        this.name = name;
        this.supplier = supplier;
    }

    public Seller(long id, String name, Supplier supplier, List<Fruit> fruits) {
        this.id = id;
        this.name = name;
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

    public String getFruit() {
        return fruit;
    }


}
