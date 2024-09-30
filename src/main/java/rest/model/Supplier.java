package rest.model;

import java.util.ArrayList;
import java.util.List;

public class Supplier {
    private long id;
    private String name;
    private List<Seller> sellers;
    private String seller;

    public Supplier() {}

    public Supplier(long id) {
        this.id = id;
    }

    public Supplier(String company, String seller) {
        this.name = company;
        this.seller = seller;
    }

    public Supplier(String company) {
        this.name = company;
    }

    public Supplier(long id, String company) {
        this.id = id;
        this.name = company;
    }

    public Supplier(long id, String company, List<Seller> sellers) {
        this.id = id;
        this.name = company;
        this.sellers=new ArrayList<>();
        sellers.forEach(e -> {
            this.sellers.add(e);
        });
    }

    public String getSeller() {
        return seller;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

}
