package rest.model;

import java.util.ArrayList;
import java.util.List;

public class Supplier {
    long id;
    String name;
    List<String> clients;

    public Supplier() {
        id = 0;
        name = "";
        this.clients = new ArrayList<>();
    }

    public Supplier(String company) {
        this.id = 0;
        this.name = company;
        this.clients = new ArrayList<>();
    }

    public Supplier(long id, String company) {
        this.id = id;
        this.name = company;
        this.clients = new ArrayList<>();
    }

    public Supplier(long id, String company, List<String> clients) {
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

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String company) {
        this.name = company;
    }

    @Override
    public String toString() {
        String s = "";
        if (clients.size() > 0) {
            for (int i = 0; i < clients.size(); ++i) {
                s += clients.get(i);
                s += " ";
            }
            return "id: " + id + ", supplier: " + name + "\nsellers: " + s;
        }
        return "id: " + id + ", supplier: " + name;
    }
}
