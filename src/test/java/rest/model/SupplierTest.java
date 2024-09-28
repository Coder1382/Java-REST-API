package rest.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SupplierTest {
    @Test
    public void sellersDtoTest() {
        Supplier supplier = new Supplier();
        supplier = new Supplier("comp");
        assertEquals(supplier.getName(), "comp");
        supplier = new Supplier(1, "comp");
        assertEquals(supplier.getId(), 1);
    }
}
