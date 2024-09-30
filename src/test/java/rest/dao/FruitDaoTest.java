package rest.dao;

import org.junit.jupiter.api.*;

import org.testcontainers.containers.PostgreSQLContainer;
import rest.database.DatabaseTest;
import rest.model.Fruit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.sql.SQLException;

public class FruitDaoTest {
    FruitDao fruitDao = new FruitDao();
    static DatabaseTest dbt = new DatabaseTest();
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @BeforeAll
    public static void before() throws SQLException {
        postgres.start();
        dbt.createTablesTest();
        dbt.resetTablesTest();
    }

    @AfterAll
    public static void after() {
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
        postgres.stop();
    }

    @Test
    public void findTest_1() throws IOException {
        assertEquals((fruitDao.find().get(0)).getName(), "mango");
        assertEquals((fruitDao.find().get(0)).getPrice(), 10);
    }

    @Test
    public void findTest_2() throws IOException {
        assertEquals(fruitDao.find(1).getName(), "mango");
        assertEquals(fruitDao.find(1).getPrice(), 10);
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

}
