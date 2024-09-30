package rest.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuppliersDtoTest {
    @Test
    public void sellersDtoTest() {
        SuppliersDto supplier = new SuppliersDto();
        assertEquals(supplier.getId(), 0);
        supplier=new SuppliersDto(1);
        assertEquals(supplier.getId(), 1);
        supplier = new SuppliersDto("comp");
        assertEquals(supplier.getName(), "comp");
        supplier = new SuppliersDto(1, "comp");
        assertEquals(supplier.getId(), 1);
        supplier = new SuppliersDto("comp", "ignat");
        assertEquals(supplier.getSeller(), "ignat");
        List<SellersDto> list=new ArrayList<>();
        list.add(new SellersDto());
        supplier = new SuppliersDto(1, "supplier", list);
        assertEquals(supplier.getName(), "supplier");
    }
}
