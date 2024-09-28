package rest.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rest.dto.SellersDto;

import java.io.IOException;
import java.sql.SQLException;

public class SellersServiceTest {
    SellersService sellersService = new SellersService();

    @ParameterizedTest
    @ValueSource(longs = {2, -1, 0})
    public void findTest(long id) throws IOException {
        sellersService.find(id);
    }

    @Test
    public void saveTest() throws IOException {
        sellersService.save(new SellersDto("afonia", "big"));
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
