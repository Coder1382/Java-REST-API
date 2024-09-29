package rest.model;

import java.util.ArrayList;
import java.util.List;

public class Seller {
    long id;
    String name;
    String supply;
    String fruit;
    Supplier supplier;
    List<Fruit> fruits;

    public Seller() {
        name = "";
        supply = "";
        id = 0;
        supplier = new Supplier();
        fruits = new ArrayList<>();
    }

    public Seller(long id) {
        this.id = id;
    }

    public Seller(long id, String fruit) {
        this.id = id;
        this.fruit = fruit;
        this.name = "";
        this.supply = "";
        supplier = new Supplier();
        fruits = new ArrayList<>();
    }

    public Seller(String name, String supply) {
        this.name = name;
        this.supply = supply;
        this.fruit = "";
        supplier = new Supplier();
        fruits = new ArrayList<>();
    }

    public Seller(String name, Supplier supplier) {
        this.name = name;
        this.supplier = supplier;
        this.fruit = "";
        fruits = new ArrayList<>();
    }

    public Seller(String name, Supplier supplier, List<Fruit> fruits) {
        this.name = name;
        this.supply = "";
        this.supplier = supplier;
        this.fruit = "";
        this.fruits = new ArrayList<>();
        fruits.forEach(e -> {
            this.fruits.add(e);
        });
    }

    public Seller(long id, String name, Supplier supplier) {
        this.id = id;
        this.name = name;
        this.supply = "";
        this.fruit = "";
        this.supplier = supplier;
        this.fruits = new ArrayList<>();
    }

    public Seller(long id, String name, Supplier supplier, List<Fruit> fruits) {
        this.id = id;
        this.name = name;
        this.supply = "";
        this.fruit = "";
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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < fruits.size(); ++i) {
            s.append(fruits.get(i).getName() + ", ");
        }
        s.delete(s.length() - 2, s.length() - 1);
        return "id: " + id + ", name: " + name + ", supplier: " + supplier.getName() + "\nfruits: " + s;
    }

}
