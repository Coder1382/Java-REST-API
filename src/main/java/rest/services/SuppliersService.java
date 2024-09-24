package rest.services;

import com.google.gson.Gson;
import rest.dao.DatabaseConnector;
import rest.dao.FruitDao;
import rest.dao.SuppliersDao;
import rest.model.Fruit;
import rest.model.Seller;
import rest.model.Supplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SuppliersService {
    private final SuppliersDao suppliersDao = new SuppliersDao();

    public List<Object> show(long id) throws IOException {
        return suppliersDao.showData(id);
    }

    public long save(Supplier supplier) throws IOException {
        return suppliersDao.save(supplier.getName());
    }


    public void delete(Supplier supplier) throws IOException {
        suppliersDao.delete(supplier.getId());
    }
}
