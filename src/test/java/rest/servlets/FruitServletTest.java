package rest.servlets;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import rest.dto.FruitDto;
import rest.services.FruitService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class FruitServletTest {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);
    FruitServlet fruitServlet = new FruitServlet();
    FruitService fruitService = mock(FruitService.class);
    Gson jsn = mock(Gson.class);

    @Test
    public void FindTest() throws IOException, ServletException {
        when(req.getRequestURI()).thenReturn("/myREST/fruit");
        when(res.getWriter()).thenReturn(pw);
        fruitServlet.doGet(req, res);
        when(req.getRequestURI()).thenReturn("/myREST/fruit/all");
        fruitServlet.doGet(req, res);
        when(req.getRequestURI()).thenReturn("/myREST/fruit/1");
        fruitServlet.doGet(req, res);
        when(req.getRequestURI()).thenReturn("/myREST/fruit/q");
        fruitServlet.doGet(req, res);
    }

    @Test
    public void SaveTest() throws IOException {
        FruitDto fruitDto = new FruitDto("carrot", 2);
        long id = 1;
        when(req.getRequestURI()).thenReturn("/myREST/fruit");
        when(jsn.fromJson(req.getReader(), FruitDto.class)).thenReturn(fruitDto);
        when(fruitService.save(fruitDto)).thenReturn(id);
        when(res.getWriter()).thenReturn(pw);
        try {
            fruitServlet.doPost(req, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void UpdateTest() throws IOException, SQLException {
        FruitDto fruitDto = new FruitDto(1, 10);
        when(req.getRequestURI()).thenReturn("/myREST/fruit");
        when(jsn.fromJson(req.getReader(), FruitDto.class)).thenReturn(fruitDto);
        doNothing().when(fruitService).update(fruitDto);
        try {
            fruitServlet.doPut(req, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void DeleteTest() throws IOException {
        FruitDto fruitDto = new FruitDto(1);
        when(req.getRequestURI()).thenReturn("/myREST/fruit");
        when(jsn.fromJson(req.getReader(), FruitDto.class)).thenReturn(fruitDto);
        doNothing().when(fruitService).delete(fruitDto);
        try {
            fruitServlet.doDelete(req, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
