package rest.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testcontainers.containers.PostgreSQLContainer;
import rest.dto.SuppliersDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        List<SuppliersDto> suppliersDtos = new ArrayList<SuppliersDto>();
        assertEquals(suppliersDtos.getClass(), suppliersDao.find(id).getClass());
    }

    @Test
    public void saveTest() {
        assertEquals(Long.class, ((Long) suppliersDao.save("new")).getClass());
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
