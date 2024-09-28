package rest.servlets;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

    @ParameterizedTest
    @ValueSource(strings = {"/myREST/sellers", "/myREST/sellers/all", "/myREST/sellers/1"})
    public void findTest(String str) throws IOException, ServletException {
        when(req.getRequestURI()).thenReturn(str);
        when(res.getWriter()).thenReturn(pw);
        sellersServlet.doGet(req, res);
        verify(pw).write("{\"id\":1,\"name\":\"ignat\",\"supply\":\"\",\"fruit\":\"\",\"supplier\":{\"id\":1,\"name\":\"big\"},\"fruits\":[]}\n\n");
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
    public void updateTest() throws IOException, SQLException {
        SellersDto sellersDto = new SellersDto(1, "carrot");
        when(req.getRequestURI()).thenReturn("/myREST/sellers");
        when(jsn.fromJson(req.getReader(), SellersDto.class)).thenReturn(sellersDto);
        doNothing().when(sellersService).update(sellersDto);
        try {
            sellersServlet.doPut(req, res);
            verify(pw).write("updated");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteTest() throws IOException {
        SellersDto sellersDto = new SellersDto(1);
        when(req.getRequestURI()).thenReturn("/myREST/sellers");
        when(jsn.fromJson(req.getReader(), SellersDto.class)).thenReturn(sellersDto);
        doNothing().when(sellersService).delete(sellersDto);
        try {
            sellersServlet.doDelete(req, res);
            verify(pw).write("deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
