package rest.servlets;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rest.dto.SellersDto;
import rest.dto.SuppliersDto;
import rest.services.SuppliersService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class SuppliersServletTest {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);
    SuppliersServlet suppliersServlet = new SuppliersServlet();
    SuppliersService suppliersService = mock(SuppliersService.class);
    Gson jsn = mock(Gson.class);

    @ParameterizedTest
    @ValueSource(strings = {"/myREST/suppliers", "/myREST/suppliers/all", "/myREST/suppliers/1"})
    public void findTest(String str) throws IOException, ServletException {
        when(req.getRequestURI()).thenReturn(str);
        when(res.getWriter()).thenReturn(pw);
        suppliersServlet.doGet(req, res);
        verify(pw).write("{\"id\":1,\"name\":\"big\",\"sellers\":[],\"seller\":\"\"}\n\n");
    }


    @Test
    public void saveTest() throws IOException {
        SuppliersDto suppliersDto = new SuppliersDto("big");
        long id = 1;
        when(req.getRequestURI()).thenReturn("/myREST/suppliers");
        when(jsn.fromJson(req.getReader(), SuppliersDto.class)).thenReturn(suppliersDto);
        when(suppliersService.save(suppliersDto)).thenReturn(id);
        when(res.getWriter()).thenReturn(pw);
        try {
            suppliersServlet.doPost(req, res);
            verify(pw).write("saved with id: 1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateTest_1() throws IOException, SQLException {
        SQLException ex = mock(SQLException.class);
        SuppliersDto suppliersDto = new SuppliersDto("big", "petr");
        when(req.getRequestURI()).thenReturn("/myREST/suppliers");
        when(jsn.fromJson(req.getReader(), SuppliersDto.class)).thenReturn(suppliersDto);
        when(res.getWriter()).thenReturn(pw);
        try {
            suppliersServlet.doPut(req, res);
            verify(pw).write("updated under id: 4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateTest_2() throws IOException, SQLException {
        SQLException ex = mock(SQLException.class);
        SuppliersDto suppliersDto = new SuppliersDto("big", "ignat");
        when(req.getRequestURI()).thenReturn("/myREST/supplier");
        when(jsn.fromJson(req.getReader(), SuppliersDto.class)).thenReturn(suppliersDto);
        when(res.getWriter()).thenReturn(pw);
        doThrow(RuntimeException.class).when(ex);
        try {
            suppliersServlet.doPut(req, res);
            verify(doThrow(RuntimeException.class).when(ex));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteTest() throws IOException {
        SuppliersDto suppliersDto = new SuppliersDto(1);
        when(req.getRequestURI()).thenReturn("/myREST/suppliers");
        when(jsn.fromJson(req.getReader(), SuppliersDto.class)).thenReturn(suppliersDto);
        when(res.getWriter()).thenReturn(pw);
        try {
            suppliersServlet.doDelete(req, res);
            verify(pw).write("deleted under id: 4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
