package unit8.unit10.city;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.cfg.Configuration;
import unit8.repository.HibernateCityRepository;
import unit8.service.CityService;

public class DeleteCityServlet extends HttpServlet {

    private final CityService service =
        new CityService(new HibernateCityRepository(new Configuration().configure().buildSessionFactory()));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("name", req.getParameter("name"));
        req.getRequestDispatcher("/city/deleteCity.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        service.deleteCity(req.getParameter("name"));
        resp.sendRedirect(req.getContextPath() + "/showCities");
    }
}
