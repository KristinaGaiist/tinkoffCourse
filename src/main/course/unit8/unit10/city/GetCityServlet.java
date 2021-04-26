package unit8.unit10.city;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.cfg.Configuration;
import unit8.entity.City;
import unit8.repository.HibernateCityRepository;
import unit8.service.CityService;

public class GetCityServlet extends HttpServlet {

    private final CityService service =
        new CityService(new HibernateCityRepository(new Configuration().configure().buildSessionFactory()));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            List<City> cities = service.getAllCities();
            req.setAttribute("cities", cities);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/city/showCities.jsp");
            dispatcher.forward(req, resp);
    }
}
