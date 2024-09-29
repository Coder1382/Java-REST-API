package rest.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.testcontainers.containers.PostgreSQLContainer;
import rest.dto.FruitDto;
import rest.model.Fruit;

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


    @Test
    public void findTest() throws IOException {
        List<FruitDto> fruitDtos = new ArrayList<FruitDto>();
        assertEquals((fruitDao.find(1).get(0)).getName(), "mango");
        assertEquals((fruitDao.find(1).get(0)).getPrice(), 10);
    }

    @Test
    public void saveTest() {
        assertEquals(fruitDao.save(new Fruit("tomato", 1)), 4);
    }

    @Test
    public void updateTest() {
        assertEquals(fruitDao.update(new Fruit(4, 8)), 4);
    }

    @Test
    public void deleteTest() {
        assertEquals(fruitDao.delete(new Fruit(4)), 4);
    }

    @AfterEach
    public void after() {
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
        postgres.stop();
    }
}
