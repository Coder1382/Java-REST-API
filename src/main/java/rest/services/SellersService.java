package rest.services;

import rest.dao.SellersDao;
import rest.dto.SellersDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SellersService {
    private final SellersDao sellersDao = new SellersDao();

    public List<SellersDto> find(long id) throws IOException {
        return sellersDao.find(id);
    }

    public long save(SellersDto seller) throws IOException {
        return sellersDao.save(seller.getName(), seller.getSupplier());
    }

    public void update(SellersDto seller) throws IOException, SQLException {
        sellersDao.update(seller.getId(), seller.getFruit());
    }

    public void delete(SellersDto seller) throws IOException {
        sellersDao.delete(seller.getId());
    }
}
