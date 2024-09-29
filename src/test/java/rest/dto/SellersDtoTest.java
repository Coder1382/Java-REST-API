package rest.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SellersDtoTest {
    @Test
    public void sellersDtoTest() {
        SellersDto seller = new SellersDto();
        assertEquals(seller.getId(), 0);
        seller = new SellersDto(1);
        assertEquals(seller.getId(), 1);
        seller = new SellersDto("name", "comp");
        assertEquals(seller.getId(), 0);
        List<FruitDto> flist = new ArrayList<>();
        SuppliersDto supply = new SuppliersDto();
        seller = new SellersDto(1, "name", supply);
        assertEquals(seller.getName(), "name");
        seller = new SellersDto(1, "name", supply, flist);
        assertEquals(seller.getName(), "name");
        seller = new SellersDto(1, "mango");
        assertEquals(seller.getFruit(), "mango");
        seller = new SellersDto("name", "sup");
        assertEquals(seller.getSupplier(), "sup");
        seller = new SellersDto("name", supply);
        assertEquals(seller.getName(), "name");
        seller = new SellersDto("name", supply, flist);
        assertEquals(seller.getName(), "name");
        seller = new SellersDto(1, "fruit");
        assertEquals(seller.getFruit(), "fruit");
    }
}
