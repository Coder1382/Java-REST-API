package Servlets;

public class Suppliers {
    long id;
    String company;
    public Suppliers(){};
    public Suppliers(String company){
        this.company=company;
    }
    public Suppliers(long id, String company){
        this.id=id;
        this.company=company;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    @Override
    public String toString() {
        return "id: "+id+", supplier: "+company;
    }
}
