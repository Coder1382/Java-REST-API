package rest.services;

import com.google.gson.Gson;
import rest.dao.DatabaseConnector;
import rest.dao.FruitDao;
import rest.dao.SellersDao;
import rest.model.Fruit;
import rest.model.Seller;

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

public class SellersService {
    private final SellersDao sellersDao = new SellersDao();

    public List<Object> show(long id) throws IOException {
        return sellersDao.show(id);
    }

    public long save(Seller seller) throws IOException {
        return sellersDao.save(seller.getName(), seller.getSupplier_id());
    }

    public void update(Seller seller) throws IOException, SQLException {
        sellersDao.update(seller.getId(), seller.getName());
    }

    public void delete(Seller seller) throws IOException {
        Gson jsn = new Gson();
        sellersDao.delete(seller.getId());
    }
}
