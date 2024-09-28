package rest.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuppliersDtoTest {
    @Test
    public void sellersDtoTest() {
        SuppliersDto supplier = new SuppliersDto();
        supplier = new SuppliersDto("comp");
        assertEquals(supplier.getName(), "comp");
        supplier = new SuppliersDto(1, "comp");
        assertEquals(supplier.getId(), 1);
    }
}
