package rest.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rest.dto.SellersDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SellersServiceTest {
    SellersService sellersService = new SellersService();

    @ParameterizedTest
    @ValueSource(longs = {2, -1, 0})
    public void findTest(long id) throws IOException {
        List<SellersDto> sellersDtos = new ArrayList<SellersDto>();
        assertEquals(sellersDtos.getClass(), sellersService.find(id).getClass());
    }

    @Test
    public void saveTest() throws IOException {
        assertEquals(Long.class, ((Long) sellersService.save(new SellersDto("gena", "big"))).getClass());
    }

    @Test
    public void updateTest() throws SQLException, IOException {
        sellersService.update(new SellersDto(2, "tomato"));
    }

    @Test
    public void deleteTest() throws IOException {
        sellersService.delete(new SellersDto(2));
    }
}
