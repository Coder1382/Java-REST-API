package rest.dao;

public class Supplier {
    long id;
    String company;
    public Supplier(){};
    public Supplier(String company){
        this.company=company;
    }
    public Supplier(long id, String company){
        this.id=id;
        this.company=company;
    }
    public String getCompany() {
        return company;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    @Override
    public String toString() {
        return "id: "+id+", supplier: "+company;
    }
}
