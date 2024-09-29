package rest.services;

import rest.dao.SellersDao;
import rest.dto.SellersDto;
import rest.model.Fruit;
import rest.model.Seller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class
SellersService {
    private final SellersDao sellersDao = new SellersDao();

    public List<SellersDto> find(long id) throws IOException {
        return sellersDao.find(id);
    }

    public long save(SellersDto sellersDto) throws IOException {
        Seller seller = new Seller(sellersDto.getName(), sellersDto.getSupplier());
        return sellersDao.save(seller);
    }

    public long update(SellersDto sellersDto) throws IOException, SQLException {
        Seller seller = new Seller(sellersDto.getId(), sellersDto.getFruit());
        return sellersDao.update(seller);
    }

    public long delete(SellersDto sellersDto) throws IOException {
        Seller seller = new Seller(sellersDto.getId());
        return sellersDao.delete(seller);
    }
}
