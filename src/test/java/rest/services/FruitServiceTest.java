package rest.services;

import org.junit.jupiter.api.Test;
import rest.dto.FruitDto;

import java.io.IOException;
import java.sql.SQLException;

public class FruitServiceTest {
    FruitService fruitService = new FruitService();

    @Test
    public void FindTest() throws IOException {
        fruitService.find(2);
        fruitService.find(-1);
        fruitService.find(0);
    }

    @Test

    public void SaveTest() throws IOException {
        fruitService.save(new FruitDto("carrot", 7));
    }

    @Test
    public void UpdateTest() throws SQLException, IOException {
        fruitService.update(new FruitDto(2, 8));
    }

    @Test
    public void DeleteTest() throws IOException {
        fruitService.delete(new FruitDto(2));
    }
}
