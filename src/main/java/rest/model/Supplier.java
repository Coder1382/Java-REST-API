package rest.model;

import java.util.ArrayList;
import java.util.List;

public class Supplier {
    long id;
    String name;
    List<Seller> clients;

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

    public Supplier(long id, String company, List<Seller> clients) {
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


    public void setName(String company) {
        this.name = company;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        if (clients.size() > 0) {
            for (int i = 0; i < clients.size(); ++i) {
                s.append(clients.get(i).getName() + ", ");
            }
            s.delete(s.length() - 2, s.length() - 1);
            return "id: " + id + ", name: " + name + "\nsellers: " + s;
        }
        return "id: " + id + ", name: " + name;
    }
}
