package rest.services;

import rest.dao.SuppliersDao;
import rest.dto.SuppliersDto;

import java.io.IOException;

import java.util.List;

public class SuppliersService {
    private final SuppliersDao suppliersDao = new SuppliersDao();

    public List<SuppliersDto> find(long id) throws IOException {
        return suppliersDao.find(id);
    }

    public long save(SuppliersDto supplier) throws IOException {
        return suppliersDao.save(supplier);
    }


    public long delete(SuppliersDto supplier) throws IOException {
        return suppliersDao.delete(supplier);
    }
}
