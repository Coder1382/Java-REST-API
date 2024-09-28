package rest.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rest.dto.FruitDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FruitServiceTest {
    FruitService fruitService = new FruitService();

    @ParameterizedTest
    @ValueSource(longs = {2, -1, 0})
    public void findTest(long id) throws IOException {
        List<FruitDto> fruitDtos = new ArrayList<FruitDto>();
        assertEquals(fruitDtos.getClass(), fruitService.find(id).getClass());
    }

    @Test
    public void saveTest() throws IOException {
        assertEquals(Long.class, ((Long) fruitService.save(new FruitDto("carrot", 7))).getClass());
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
