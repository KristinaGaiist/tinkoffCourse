package unit8.unit10.city;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.cfg.Configuration;
import unit8.repository.HibernateCityRepository;
import unit8.service.CityService;

public class CreateCityServlet extends HttpServlet {

    private final CityService service =
        new CityService(new HibernateCityRepository(new Configuration().configure().buildSessionFactory()));

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        service.createCity(req.getParameter("name"));
        resp.sendRedirect(req.getContextPath() + "/showCities");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/city/createCity.jsp").forward(req, resp);
    }
}
