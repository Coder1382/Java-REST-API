package rest.dao;

public class Seller {
    long id, supplier_id;
    String name, supplier;
    int rating;
    public Seller(){
        name="";
        rating=0;
        id=0;
        supplier="";
        supplier_id=0;
    }
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
        this.supplier="";
    }
    public Seller(long id, String name, int rating, long supplier_id, String supplier){
        this.id=id;
        this.name=name;
        this.rating=rating;
        this.supplier_id=supplier_id;
        this.supplier=supplier;
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
        if(supplier!="")
            return "id: "+id+", name: "+name+", rating: "+rating+", supplier: "+supplier+"(id="+supplier_id+")";
        else
            return "id: "+id+", name: "+name+", rating: "+rating+", supplier_id: "+supplier_id;
    }
}
