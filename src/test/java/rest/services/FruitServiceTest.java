package rest.services;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import rest.dto.FruitDto;
import rest.model.Fruit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class FruitServiceTest {
    FruitService fruitService = new FruitService();

    @Test
    public void findTest_1() throws IOException {
        assertEquals((fruitService.find().get(0)).getPrice(), 10);
        assertEquals((fruitService.find().get(0)).getName(), "mango");
    }

    @Test
    public void findTest_2() throws IOException {
        assertEquals(fruitService.find(1).getPrice(), 10);
        assertEquals(fruitService.find(1).getName(), "mango");
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
