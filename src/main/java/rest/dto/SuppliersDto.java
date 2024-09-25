package rest.dto;

import rest.model.Seller;

import java.util.ArrayList;
import java.util.List;

public class SuppliersDto {
    long id;
    String name;
    List<Seller> clients;

    public SuppliersDto() {
        id = 0;
        name = "";
        this.clients = new ArrayList<>();
    }

    public SuppliersDto(long id) {
        this.id = id;
    }

    public SuppliersDto(String company) {
        this.name = company;
    }

    public SuppliersDto(long id, String company) {
        this.id = id;
        this.name = company;
    }

    public SuppliersDto(long id, String company, List<Seller> clients) {
        this.id = id;
        this.name = company;
        this.clients = new ArrayList<>();
        clients.forEach(e -> {
            this.clients.add(e);
        });
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

}
