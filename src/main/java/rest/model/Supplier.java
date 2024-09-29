package rest.model;

public class Supplier {
    long id;
    String name;

    public Supplier() {
        id = 0;
        name = "";
    }

    public Supplier(long id) {
        this.id = id;
        this.name="";
    }

    public Supplier(String company) {
        this.name = company;
    }

    public Supplier(long id, String company) {
        this.id = id;
        this.name = company;
    }


    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "id: " + id + ", name: " + name;
    }

}
