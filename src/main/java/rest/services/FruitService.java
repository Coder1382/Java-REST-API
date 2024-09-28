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
        return fruitDao.save(fruit);
    }

    public long update(FruitDto fruit) throws IOException, SQLException {
        return fruitDao.update(fruit);
    }

    public long delete(FruitDto fruit) throws IOException {
        return fruitDao.delete(fruit);
    }
}
