package unit8.unit10.city;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.cfg.Configuration;
import unit8.entity.City;
import unit8.repository.HibernateCityRepository;
import unit8.service.CityService;
import unit8.utils.ValidationUtil;

public class UpdateCityServlet extends HttpServlet {

    private final CityService service =
        new CityService(new HibernateCityRepository(new Configuration().configure().buildSessionFactory()));

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int id = ValidationUtil.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        City city = service.getCityById(id);

        service.renameCity(city.getName(), name);
        resp.sendRedirect(req.getContextPath() + "/showCities");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = ValidationUtil.parseInt(req.getParameter("id"));
        City city = service.getCityById(id);

        req.setAttribute("city", city);
        req.getRequestDispatcher("/city/updateCity.jsp").forward(req, resp);
    }
}
