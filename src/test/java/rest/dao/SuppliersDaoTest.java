package rest.dao;

import org.junit.jupiter.api.*;

import org.testcontainers.containers.PostgreSQLContainer;
import rest.database.DatabaseTest;
import rest.model.Supplier;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuppliersDaoTest {
    SuppliersDao suppliersDao = new SuppliersDao();

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
        assertEquals((suppliersDao.find().get(0)).getId(), 2);
        assertEquals((suppliersDao.find().get(0)).getName(), "small");
    }

    @Test
    public void findTest_2() throws IOException {
        assertEquals(suppliersDao.find(1).getId(), 1);
        assertEquals(suppliersDao.find(1).getName(), "big");
    }

    @Test
    public void saveTest() {
        assertEquals(suppliersDao.save(new Supplier("new")), 4);
    }


    @Test
    public void updateTest() throws SQLException {
        assertEquals(suppliersDao.update(new Supplier(1, "ignat")), 1);
    }

    @Test
    public void deleteTest() {
        assertEquals(suppliersDao.delete(new Supplier(4)), 4);
    }

}
