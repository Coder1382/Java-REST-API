package rest.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rest.dto.SuppliersDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuppliersServiceTest {
    SuppliersService suppliersService = new SuppliersService();

    @ParameterizedTest
    @ValueSource(longs = {2, -1, 0})
    public void findTest(long id) throws IOException {
        List<SuppliersDto> suppliersDtos = new ArrayList<SuppliersDto>();
        assertEquals(suppliersDtos.getClass(), suppliersService.find(id).getClass());
    }

    @Test
    public void saveTest() throws IOException {
        assertEquals(Long.class, ((Long) suppliersService.save(new SuppliersDto("old"))).getClass());
    }

    @Test
    public void deleteTest() throws IOException {
        suppliersService.delete(new SuppliersDto(2));
    }
}
