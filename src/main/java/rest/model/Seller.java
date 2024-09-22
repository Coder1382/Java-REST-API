package rest.model;

import java.util.ArrayList;
import java.util.List;

public class Seller {
    long id;
    long supplier_id;
    String name;
    String supplier;
    int rating;
    List<String> fruits;

    public Seller() {
        name = "";
        rating = 0;
        id = 0;
        supplier = "";
        supplier_id = 0;
        fruits = new ArrayList<>();
    }

    public Seller(String name, int rating, long supplier_id) {
        this.name = name;
        this.rating = rating;
        this.supplier_id = supplier_id;
        this.supplier = "";
        this.fruits = new ArrayList<>();
    }

    public Seller(long id, String name, int rating, long supplier_id) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.supplier_id = supplier_id;
        this.supplier = "";
        this.fruits = new ArrayList<>();
    }

    public Seller(long id, String name, int rating, long supplier_id, List<String> fruits) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.supplier_id = supplier_id;
        this.supplier = "";
        this.fruits = new ArrayList<>();
        fruits.forEach(e -> {
            this.fruits.add(e);
        });
    }

    public Seller(long id, String name, int rating, long supplier_id, String supplier) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.supplier_id = supplier_id;
        this.supplier = supplier;
        this.fruits = new ArrayList<>();
    }

    public Seller(long id, String name, int rating, long supplier_id, String supplier, List<String> fruits) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.supplier_id = supplier_id;
        this.supplier = "";
        this.supplier = supplier;
        this.fruits = new ArrayList<>();
        fruits.forEach(e -> {
            this.fruits.add(e);
        });
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        String s = "";
        if (supplier.equals("")) {
            if (fruits.size() > 0) {
                for (int i = 0; i < fruits.size(); ++i) {
                    s += fruits.get(i);
                    s += " ";
                }
                return "id: " + id + ", name: " + name + ", rating: " + rating + ", supplier_id: " + supplier_id + "\nfruit: " + s;
            } else return "id: " + id + ", name: " + name + ", rating: " + rating + ", supplier_id: " + supplier_id;
        } else {
            if (fruits.size() > 0) {
                for (int i = 0; i < fruits.size(); ++i) {
                    s += fruits.get(i);
                    s += " ";
                }
                return "id: " + id + ", name: " + name + ", rating: " + rating + ", supplier_id: " + "(id=" + supplier_id + ")" + "\nfruit: " + s;
            } else
                return "id: " + id + ", name: " + name + ", rating: " + rating + ", supplier: " + supplier + "(id=" + supplier_id + ")";
        }
    }
}
