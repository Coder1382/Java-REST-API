package rest.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SellerTest {
    @Test
    public void SellersDtoTest() {
        Seller seller = new Seller();
        seller = new Seller("name", "comp");
        assertEquals(seller.getId(), 0);
        List<Fruit> flist = new ArrayList<>();
        Supplier supply = new Supplier();
        seller = new Seller(1, "name", supply);
        seller = new Seller(1, "name", supply, flist);
        assertEquals(seller.getName(), "name");
        seller = new Seller(1, "mango");
        seller = new Seller("name", "sup");
        assertEquals(seller.getSupplier(), "sup");
        seller = new Seller("name", supply);
        seller = new Seller(1, "name", supply, flist);
    }
}
