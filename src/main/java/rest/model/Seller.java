package rest.model;

import java.util.ArrayList;
import java.util.List;

public class Seller {
    long id;
    long supplier_id;
    String name;
    String supplier;
    String fruit;
    List<String> fruits;

    public Seller() {
        name = "";
        id = 0;
        supplier = "";
        supplier_id = 0;
        fruits = new ArrayList<>();
    }

    public Seller(String name, long supplier_id) {
        this.name = name;
        this.supplier_id = supplier_id;
        this.supplier = "";
        this.fruits = new ArrayList<>();
    }

    public Seller(long id, String name, long supplier_id) {
        this.id = id;
        this.name = name;
        this.supplier_id = supplier_id;
        this.supplier = "";
        this.fruits = new ArrayList<>();
    }

    public Seller(long id, String name, long supplier_id, List<String> fruits) {
        this.id = id;
        this.name = name;
        this.supplier_id = supplier_id;
        this.supplier = "";
        this.fruits = new ArrayList<>();
        fruits.forEach(e -> {
            this.fruits.add(e);
        });
    }

    public Seller(long id, String name, long supplier_id, String supplier) {
        this.id = id;
        this.name = name;
        this.supplier_id = supplier_id;
        this.supplier = supplier;
        this.fruits = new ArrayList<>();
    }

    public Seller(long id, String name, long supplier_id, String supplier, List<String> fruits) {
        this.id = id;
        this.name = name;
        this.supplier_id = supplier_id;
        this.supplier = supplier;
        this.fruits = new ArrayList<>();
        fruits.forEach(e -> {
            this.fruits.add(e);
        });
    }

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public long getId() {
        return id;
    }


    public long getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(long supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        if (supplier.equals("")) {
            if (fruits.size() > 0) {
                for (int i = 0; i < fruits.size(); ++i) {
                    s.append(fruits.get(i));
                }
                s.delete(s.length()-2, s.length()-1);
                return "id: " + id + ", name: " + name + "\nfruits: " + s;
            } else return "id: " + id + ", name: " + name;
        } else {
            if (fruits.size() > 0) {
                for (int i = 0; i < fruits.size(); ++i) {
                    s.append(fruits.get(i));
                }
                s.delete(s.length()-2, s.length()-1);
                return "id: " + id + ", name: " + name + ", supplier: " + supplier + "\nfruits: " + s;
            } else
                return "id: " + id + ", name: " + name + ", supplier: " + supplier;
        }
    }
}