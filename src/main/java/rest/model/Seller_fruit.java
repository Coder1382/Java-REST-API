package rest.model;

public class
Seller_fruit {
    long seller_id;
    long fruit_id;

    public Seller_fruit() {
        seller_id = 0;
        fruit_id = 0;
    }

    public Seller_fruit(long seller_id, long fruit_id) {
        this.seller_id = seller_id;
        this.fruit_id = fruit_id;
    }

    public long getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(long seller_id) {
        this.seller_id = seller_id;
    }

    public long getFruit_id() {
        return fruit_id;
    }

    public void setFruit_id(long fruit_id) {
        this.fruit_id = fruit_id;
    }


    @Override
    public String toString() {
        return "seller_id: " + seller_id + ", fruit_id: " + fruit_id;
    }
}
