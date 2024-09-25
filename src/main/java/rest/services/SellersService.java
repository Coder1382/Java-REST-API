package rest.services;

import com.google.gson.Gson;
import rest.dao.DatabaseConnector;
import rest.dao.FruitDao;
import rest.dao.SellersDao;
import rest.dto.SellersDto;
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

    public List<SellersDto> find(long id) throws IOException {
        return sellersDao.find(id);
    }

    public long save(SellersDto seller) throws IOException {
        return sellersDao.save(seller.getName(), seller.getSupplier_id());
    }

    public void update(SellersDto seller) throws IOException, SQLException {
        sellersDao.update(seller.getId(), seller.getName());
    }

    public void delete(SellersDto seller) throws IOException {
        Gson jsn = new Gson();
        sellersDao.delete(seller.getId());
    }
}
