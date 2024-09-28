package rest.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;

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
        fruitDao.find(id);
    }

    @Test
    public void saveTest() {
        fruitDao.save("tomato", 7);
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
