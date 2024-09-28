package rest.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rest.dto.FruitDto;

import java.io.IOException;
import java.sql.SQLException;

public class FruitServiceTest {
    FruitService fruitService = new FruitService();

    @ParameterizedTest
    @ValueSource(longs = {2, -1, 0})
    public void findTest(long id) throws IOException {
        fruitService.find(id);
    }

    @Test

    public void saveTest() throws IOException {
        fruitService.save(new FruitDto("carrot", 7));
    }

    @Test
    public void updateTest() throws SQLException, IOException {
        fruitService.update(new FruitDto(2, 8));
    }

    @Test
    public void deleteTest() throws IOException {
        fruitService.delete(new FruitDto(2));
    }
}
