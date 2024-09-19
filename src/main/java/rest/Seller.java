package rest;

public class Seller {
    long id, supplier_id;
    String name;
    int rating;
    public Seller(){}
    public Seller(String name, int rating, long supplier_id){
        this.name=name;
        this.rating=rating;
        this.supplier_id=supplier_id;
    }
    public Seller(long id, String name, int rating, long supplier_id){
        this.id=id;
        this.name=name;
        this.rating=rating;
        this.supplier_id=supplier_id;
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
        return "id: "+id+", name: "+name+", rating: "+rating+", supplier_id: "+supplier_id;
    }
}
