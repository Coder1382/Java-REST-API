package rest.dao;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SuppliersDaoTest {
    SuppliersDao suppliersDao = new SuppliersDao();

    @Test
    public void FindTest() throws IOException {
        suppliersDao.find(1);
        suppliersDao.find(-1);
        suppliersDao.find(0);
    }

    @Test
    public void SaveTest() {
        suppliersDao.save("new");
    }

    @Test
    public void DeleteTest() {
        suppliersDao.delete(1);
    }
}
