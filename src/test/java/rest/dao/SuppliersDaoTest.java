package rest.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;

public class SuppliersDaoTest {
    SuppliersDao suppliersDao = new SuppliersDao();

    @BeforeEach
    public void before() {
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
        postgres.start();
    }

    @ParameterizedTest
    @ValueSource(longs = {1, -1, 0})
    public void findTest(long id) throws IOException {
        suppliersDao.find(id);
    }

    @Test
    public void saveTest() {
        suppliersDao.save("new");
    }

    @Test
    public void deleteTest() {
        suppliersDao.delete(1);
    }

    @AfterEach
    public void after() {
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
        postgres.stop();
    }
}
