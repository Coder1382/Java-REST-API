package rest.servlets;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import rest.dto.SellersDto;
import rest.services.SellersService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class SellersServletTest {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);
    SellersServlet sellersServlet = new SellersServlet();
    SellersService sellersService = mock(SellersService.class);
    Gson jsn = mock(Gson.class);

    @Test
    public void FindTest() throws IOException, ServletException {
        when(req.getRequestURI()).thenReturn("/myREST/sellers");
        when(res.getWriter()).thenReturn(pw);
        sellersServlet.doGet(req, res);
        when(req.getRequestURI()).thenReturn("/myREST/sellers/all");
        sellersServlet.doGet(req, res);
        when(req.getRequestURI()).thenReturn("/myREST/sellers/1");
        sellersServlet.doGet(req, res);
        when(req.getRequestURI()).thenReturn("/myREST/sellers/q");
        sellersServlet.doGet(req, res);
    }

    @Test
    public void SaveTest() throws IOException {
        SellersDto sellersDto = new SellersDto("afonia", "big");
        long id = 1;
        when(req.getRequestURI()).thenReturn("/myREST/sellers");
        when(jsn.fromJson(req.getReader(), SellersDto.class)).thenReturn(sellersDto);
        when(sellersService.save(sellersDto)).thenReturn(id);
        when(res.getWriter()).thenReturn(pw);
        try {
            sellersServlet.doPost(req, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void UpdateTest() throws IOException, SQLException {
        SellersDto sellersDto = new SellersDto(1, "carrot");
        when(req.getRequestURI()).thenReturn("/myREST/sellers");
        when(jsn.fromJson(req.getReader(), SellersDto.class)).thenReturn(sellersDto);
        doNothing().when(sellersService).update(sellersDto);
        try {
            sellersServlet.doPut(req, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void DeleteTest() throws IOException {
        SellersDto sellersDto = new SellersDto(1);
        when(req.getRequestURI()).thenReturn("/myREST/sellers");
        when(jsn.fromJson(req.getReader(), SellersDto.class)).thenReturn(sellersDto);
        doNothing().when(sellersService).delete(sellersDto);
        try {
            sellersServlet.doDelete(req, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
