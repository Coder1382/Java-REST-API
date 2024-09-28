package rest.model;

import rest.dto.SuppliersDto;

import java.util.ArrayList;
import java.util.List;

public class Seller {
    long id;
    String supply;
    String name;
    Supplier supplier;
    List<Fruit> fruits;

    public Seller() {
        name = "";
        supply = "";
        id = 0;
        supplier = new Supplier();
        fruits = new ArrayList<>();
    }

    public Seller(String name, String sup) {
        this.name = name;
        this.supply = sup;
        supplier = new Supplier();
        this.fruits = new ArrayList<>();
    }

    public Seller(String name, Supplier supplier) {
        this.name = name;
        this.supplier = supplier;
        fruits = new ArrayList<>();
    }

    public Seller(long id, String sup) {
        this.id = id;
        this.name = "";
        this.supply = sup;
        supplier = new Supplier();
        fruits = new ArrayList<>();
    }

    public Seller(long id, String name, Supplier supplier) {
        this.id = id;
        this.name = name;
        this.supply = "";
        this.supplier = supplier;
        this.fruits = new ArrayList<>();
    }

    public Seller(long id, String name, Supplier supplier, List<Fruit> fruits) {
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

    public String getName() {
        return name;
    }

    public String getSupplier() {
        return supply;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < fruits.size(); ++i) {
            s.append(fruits.get(i).getName() + ", ");
        }
        //s.delete(s.length() - 2, s.length() - 1);
        return "id: " + id + ", name: " + name + ", supplier: " + supplier.getName() + "\nfruits: " + s;
    }
}