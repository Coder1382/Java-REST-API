package rest.services;

import org.junit.jupiter.api.Test;

import rest.dto.FruitDto;

import java.io.IOException;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class FruitServiceTest {
    FruitService fruitService = new FruitService();

    @Test
    public void findTest() throws IOException {
        assertEquals((fruitService.find(1).get(0)).getPrice(), 10);
        assertEquals((fruitService.find(1).get(0)).getName(), "mango");
    }

    @Test
    public void saveTest() throws IOException {
        assertEquals(fruitService.save(new FruitDto("carrot", 7)), 4);
    }

    @Test
    public void updateTest() throws SQLException, IOException {
        assertEquals(fruitService.update(new FruitDto(2, 8)), 2);
    }

    @Test
    public void deleteTest() throws IOException {
        assertEquals(fruitService.delete(new FruitDto(2)), 2);
    }
}
