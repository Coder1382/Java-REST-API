package rest.dao;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class SellersDaoTest {
    private SellersDao sellersDao = new SellersDao();

    @Test
    public void FindTest() {
        sellersDao.find(1);
        sellersDao.find(-1);
        sellersDao.find(0);
    }

    @Test
    public void SaveTest() {
        sellersDao.save("akim", "big");
    }

    @Test
    public void UpdateTest() throws SQLException {
        sellersDao.update(1, "mango");
    }

    @Test
    public void DeleteTest() {
        sellersDao.delete(1);
    }
}
