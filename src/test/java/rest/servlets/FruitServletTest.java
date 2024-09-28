package rest.servlets;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

    @ParameterizedTest
    @ValueSource(strings = {"/myREST/fruit", "/myREST/fruit/1", "/myREST/fruit/all"})
    public void findTest(String str) throws IOException, ServletException {
        when(req.getRequestURI()).thenReturn(str);
        when(res.getWriter()).thenReturn(pw);
        fruitServlet.doGet(req, res);
        verify(pw).write("{\"id\":1,\"name\":\"mango\",\"price\":10}\n\n");
    }

    @Test
    public void saveTest() throws IOException {
        FruitDto fruitDto = new FruitDto("carrot", 2);
        long id = 1;
        when(req.getRequestURI()).thenReturn("/myREST/fruit");
        when(jsn.fromJson(req.getReader(), FruitDto.class)).thenReturn(fruitDto);
        when(fruitService.save(fruitDto)).thenReturn(id);
        when(res.getWriter()).thenReturn(pw);
        try {
            fruitServlet.doPost(req, res);
            verify(pw).write("saved with id: 1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateTest() throws IOException, SQLException {
        FruitDto fruitDto = new FruitDto(1, 10);
        when(req.getRequestURI()).thenReturn("/myREST/fruit");
        when(jsn.fromJson(req.getReader(), FruitDto.class)).thenReturn(fruitDto);
        when(res.getWriter()).thenReturn(pw);
        try {
            fruitServlet.doPut(req, res);
            verify(pw).write("updated under id: 4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void deleteTest() throws IOException {
        FruitDto fruitDto = new FruitDto(1);
        when(req.getRequestURI()).thenReturn("/myREST/fruit");
        when(jsn.fromJson(req.getReader(), FruitDto.class)).thenReturn(fruitDto);
        when(res.getWriter()).thenReturn(pw);
        try {
            fruitServlet.doDelete(req, res);
            verify(pw).write("deleted under id: 4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
