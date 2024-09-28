package rest.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testcontainers.containers.PostgreSQLContainer;
import rest.dto.FruitDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FruitDaoTest {
    FruitDao fruitDao = new FruitDao();

    @BeforeEach
    public void before() {
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
        postgres.start();
    }


    @ParameterizedTest
    @ValueSource(longs = {1, -1, 0})
    public void findTest(long id) throws IOException {
        List<FruitDto> fruitDtos = new ArrayList<FruitDto>();
        assertEquals(fruitDtos.getClass(), fruitDao.find(id).getClass());
    }

    @Test
    public void saveTest() {
        assertEquals(Long.class, ((Long) fruitDao.save("tomato", 7)).getClass());
    }

    @Test
    public void updateTest() {
        fruitDao.update(1, 8);
    }

    @Test
    public void deleteTest() {
        fruitDao.delete(1);
    }

    @AfterEach
    public void after() {
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
        postgres.stop();
    }
}
