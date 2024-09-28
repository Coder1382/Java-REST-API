package rest.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.SQLException;

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
        sellersDao.find(id);
    }

    @Test
    public void saveTest() {
        sellersDao.save("akim", "big");
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
