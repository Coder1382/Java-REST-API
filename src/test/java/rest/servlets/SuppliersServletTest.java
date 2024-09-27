package rest.servlets;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import rest.dto.SuppliersDto;
import rest.services.SuppliersService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

public class SuppliersServletTest {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);
    SuppliersServlet suppliersServlet = new SuppliersServlet();
    SuppliersService suppliersService = mock(SuppliersService.class);
    Gson jsn = mock(Gson.class);

    @Test
    public void FindTest() throws IOException, ServletException {
        when(req.getRequestURI()).thenReturn("/myREST/suppliers");
        when(res.getWriter()).thenReturn(pw);
        suppliersServlet.doGet(req, res);
        when(req.getRequestURI()).thenReturn("/myREST/suppliers/all");
        suppliersServlet.doGet(req, res);
        when(req.getRequestURI()).thenReturn("/myREST/suppliers/1");
        suppliersServlet.doGet(req, res);
        when(req.getRequestURI()).thenReturn("/myREST/suppliers/q");
        suppliersServlet.doGet(req, res);
    }

    @Test
    public void SaveTest() throws IOException {
        SuppliersDto suppliersDto = new SuppliersDto("big");
        long id = 1;
        when(req.getRequestURI()).thenReturn("/myREST/suppliers");
        when(jsn.fromJson(req.getReader(), SuppliersDto.class)).thenReturn(suppliersDto);
        when(suppliersService.save(suppliersDto)).thenReturn(id);
        when(res.getWriter()).thenReturn(pw);
        try {
            suppliersServlet.doPost(req, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void DeleteTest() throws IOException {
        SuppliersDto suppliersDto = new SuppliersDto(1);
        when(req.getRequestURI()).thenReturn("/myREST/suppliers");
        when(jsn.fromJson(req.getReader(), SuppliersDto.class)).thenReturn(suppliersDto);
        doNothing().when(suppliersService).delete(suppliersDto);
        try {
            suppliersServlet.doDelete(req, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
