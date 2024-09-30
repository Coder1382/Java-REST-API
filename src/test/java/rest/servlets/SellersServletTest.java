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
    public void findTest_1() throws IOException, ServletException {
        when(req.getRequestURI()).thenReturn("/myREST/sellers");
        when(res.getWriter()).thenReturn(pw);
        sellersServlet.doGet(req, res);
        verify(pw).write("{\"id\":1,\"name\":\"ignat\",\"supplier\":{\"id\":1,\"name\":\"big\"}}\n\n");
    }

    @Test
    public void findTest_2() throws IOException, ServletException {
        when(req.getRequestURI()).thenReturn("/myREST/sellers/all");
        when(res.getWriter()).thenReturn(pw);
        sellersServlet.doGet(req, res);
        verify(pw).write("{\"id\":1,\"name\":\"ignat\",\"supplier\":{\"id\":1,\"name\":\"big\"}}\n\n");
    }

    @Test
    public void findTest_3() throws IOException, ServletException {
        when(req.getRequestURI()).thenReturn("/myREST/sellers/1");
        when(res.getWriter()).thenReturn(pw);
        sellersServlet.doGet(req, res);
        verify(pw).write("{\"id\":1,\"name\":\"ignat\",\"supplier\":{\"id\":1,\"name\":\"big\"}}");
    }

    @Test
    public void saveTest() throws IOException {
        SellersDto sellersDto = new SellersDto("afonia", "big");
        long id = 1;
        when(req.getRequestURI()).thenReturn("/myREST/sellers");
        when(jsn.fromJson(req.getReader(), SellersDto.class)).thenReturn(sellersDto);
        when(sellersService.save(sellersDto)).thenReturn(id);
        when(res.getWriter()).thenReturn(pw);
        try {
            sellersServlet.doPost(req, res);
            verify(pw).write("saved with id: 1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateTest_1() throws IOException, SQLException {
        SQLException ex = mock(SQLException.class);
        SellersDto sellersDto = new SellersDto(1, "carrot");
        when(req.getRequestURI()).thenReturn("/myREST/sellers");
        when(jsn.fromJson(req.getReader(), SellersDto.class)).thenReturn(sellersDto);
        when(res.getWriter()).thenReturn(pw);
        try {
            sellersServlet.doPut(req, res);
            verify(pw).write("updated under id: 4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateTest_2() throws IOException, SQLException {
        SQLException ex = mock(SQLException.class);
        SellersDto sellersDto = new SellersDto(1, "fruit");
        when(req.getRequestURI()).thenReturn("/myREST/seller");
        when(jsn.fromJson(req.getReader(), SellersDto.class)).thenReturn(sellersDto);
        when(res.getWriter()).thenReturn(pw);
        doThrow(RuntimeException.class).when(ex);
        try {
            sellersServlet.doPut(req, res);
            verify(doThrow(RuntimeException.class).when(ex));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteTest() throws IOException {
        SellersDto sellersDto = new SellersDto(1);
        when(req.getRequestURI()).thenReturn("/myREST/sellers");
        when(jsn.fromJson(req.getReader(), SellersDto.class)).thenReturn(sellersDto);
        when(res.getWriter()).thenReturn(pw);
        try {
            sellersServlet.doDelete(req, res);
            verify(pw).write("deleted under id: 4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
