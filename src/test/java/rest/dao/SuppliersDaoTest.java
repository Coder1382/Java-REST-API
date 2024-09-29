package rest.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.testcontainers.containers.PostgreSQLContainer;
import rest.dto.SuppliersDto;
import rest.model.Supplier;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuppliersDaoTest {
    SuppliersDao suppliersDao = new SuppliersDao();

    @BeforeEach
    public void before() {
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
        postgres.start();
    }

    @Test
    public void findTest() throws IOException {
        assertEquals(suppliersDao.find(1).get(0).getId(), 1);
        assertEquals(suppliersDao.find(1).get(0).getName(), "big");
    }

    @Test
    public void saveTest() {
        assertEquals(suppliersDao.save(new Supplier("new")), 4);
    }

    @Test
    public void deleteTest() {
        assertEquals(suppliersDao.delete(new Supplier(4)), 4);
    }

    @AfterEach
    public void after() {
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
        postgres.stop();
    }
}
