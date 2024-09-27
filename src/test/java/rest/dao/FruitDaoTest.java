package rest.dao;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FruitDaoTest {
    FruitDao fruitDao = new FruitDao();

    @Test
    public void FindTest() throws IOException {
        fruitDao.find(1);
        fruitDao.find(-1);
        fruitDao.find(0);
    }

    @Test
    public void SaveTest() {
        fruitDao.save("tomato", 7);
    }

    @Test
    public void UpdateTest() {
        fruitDao.update(1, 8);
    }

    @Test
    public void DeleteTest() {
        fruitDao.delete(1);
    }
}
