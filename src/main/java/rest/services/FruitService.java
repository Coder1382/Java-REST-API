package rest.services;

import com.google.gson.Gson;
import rest.dao.DatabaseConnector;
import rest.dao.FruitDao;
import rest.dto.FruitDto;
import rest.model.Fruit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FruitService {
    private final FruitDao fruitDao = new FruitDao();

    public List<FruitDto> find(long id) throws IOException {
        return fruitDao.find(id);
    }

    public long save(FruitDto fruit) throws IOException {
        return fruitDao.save(fruit.getName(), fruit.getColor(), fruit.getPrice());
    }

    public void update(FruitDto fruit) throws IOException, SQLException {
        fruitDao.update(fruit.getId(), fruit.getPrice());
    }

    public void delete(FruitDto fruit) throws IOException {
        fruitDao.delete(fruit.getId());
    }
}
