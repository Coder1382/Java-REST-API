package rest.services;

import rest.dao.FruitDao;
import rest.dto.FruitDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FruitService {
    private final FruitDao fruitDao = new FruitDao();

    public List<FruitDto> find(long id) throws IOException {
        return fruitDao.find(id);
    }

    public long save(FruitDto fruit) throws IOException {
        return fruitDao.save(fruit.getName(), fruit.getPrice());
    }

    public void update(FruitDto fruit) throws IOException, SQLException {
        fruitDao.update(fruit.getId(), fruit.getPrice());
    }

    public void delete(FruitDto fruit) throws IOException {
        fruitDao.delete(fruit.getId());
    }
}
