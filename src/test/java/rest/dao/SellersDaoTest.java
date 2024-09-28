package rest.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testcontainers.containers.PostgreSQLContainer;
import rest.dto.SellersDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SellersDaoTest {
    private SellersDao sellersDao = new SellersDao();

    @BeforeEach
    public void before() {
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
        postgres.start();
    }

    @ParameterizedTest
    @ValueSource(longs = {1, -1, 0})
    public void findTest(long id) {
        List<SellersDto> sellersDtos = new ArrayList<SellersDto>();
        assertEquals(sellersDtos.getClass(), sellersDao.find(id).getClass());
    }

    @Test
    public void saveTest() {
        assertEquals(Long.class, ((Long) sellersDao.save("akim", "big")).getClass());
    }

    @Test
    public void updateTest() throws SQLException {
        sellersDao.update(1, "mango");
    }

    @Test
    public void deleteTest() {
        sellersDao.delete(1);
    }

    @AfterEach
    public void after() {
        PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
        postgres.stop();
    }
}
