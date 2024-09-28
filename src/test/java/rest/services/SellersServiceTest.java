package rest.services;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import rest.dto.SellersDto;

import java.io.IOException;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class SellersServiceTest {
    SellersService sellersService = new SellersService();

    @Test
    public void findTest() throws IOException {
        assertEquals((sellersService.find(2).get(0)).getId(), 2);
        assertEquals((sellersService.find(2).get(0)).getName(), "petr");
    }

    @Test
    public void saveTest() throws IOException {
        assertEquals(sellersService.save(new SellersDto("gena", "big")), 4);
    }

    @Test
    public void updateTest() throws SQLException, IOException {
        assertEquals(sellersService.update(new SellersDto(4, "tomato")), 4);
    }

    @Test
    public void deleteTest() throws IOException {
        assertEquals(sellersService.delete(new SellersDto(4)), 4);
    }
}
