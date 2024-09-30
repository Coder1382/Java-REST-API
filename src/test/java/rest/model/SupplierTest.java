package rest.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SupplierTest {
    @Test
    public void sellerTest() {
        Supplier supplier = new Supplier();
        assertEquals(supplier.getId(), 0);
        supplier = new Supplier(1);
        assertEquals(supplier.getId(), 1);
        supplier = new Supplier("comp");
        assertEquals(supplier.getName(), "comp");
        supplier = new Supplier(1, "comp");
        assertEquals(supplier.getId(), 1);
        supplier = new Supplier("comp", "ignat");
        assertEquals(supplier.getSeller(), "ignat");
        List<Seller> list = new ArrayList<>();
        list.add(new Seller());
        supplier = new Supplier(1, "supplier", list);
        assertEquals(supplier.getName(), "supplier");
    }
}
