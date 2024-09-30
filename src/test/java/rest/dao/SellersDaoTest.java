package rest.dao;

import org.junit.jupiter.api.*;

import org.testcontainers.containers.PostgreSQLContainer;
import rest.database.DatabaseTest;
import rest.model.Seller;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SellersDaoTest {
    private SellersDao sellersDao = new SellersDao();

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
    public void findTest_1() {
        assertEquals((sellersDao.find().get(0)).getName(), "ignat");
        assertEquals((sellersDao.find().get(0)).getId(), 1);
    }

    @Test
    public void findTest_2() {
        assertEquals(sellersDao.find(1).getName(), "ignat");
        assertEquals(sellersDao.find(1).getId(), 1);
    }

    @Test
    public void saveTest() {
        assertEquals(sellersDao.save(new Seller("akim", "big")), 4);
    }

    @Test
    public void updateTest() throws SQLException {
        assertEquals(sellersDao.update(new Seller(4, "mango")), 4);
    }

    @Test
    public void deleteTest() {
        assertEquals(sellersDao.delete(new Seller(4)), 4);
    }

}
