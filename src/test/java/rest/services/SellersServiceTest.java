package rest.services;

import org.junit.jupiter.api.Test;
import rest.dto.SellersDto;

import java.io.IOException;
import java.sql.SQLException;

public class SellersServiceTest {
    SellersService sellersService = new SellersService();

    @Test
    public void FindTest() throws IOException {
        sellersService.find(2);
        sellersService.find(-1);
        sellersService.find(0);
    }

    @Test
    public void SaveTest() throws IOException {
        sellersService.save(new SellersDto("afonia", "big"));
    }

    @Test
    public void UpdateTest() throws SQLException, IOException {
        sellersService.update(new SellersDto(2, "tomato"));
    }

    @Test
    public void DeleteTest() throws IOException {
        sellersService.delete(new SellersDto(2));
    }
}
