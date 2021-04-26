package unit8.unit10.car;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.cfg.Configuration;
import unit8.entity.Car;
import unit8.repository.HibernateCarRepository;
import unit8.service.CarService;

public class GetCarCustomersServlet extends HttpServlet {

    private final CarService service =
        new CarService(new HibernateCarRepository(new Configuration().configure().buildSessionFactory()));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Car car = service.getCar(req.getParameter("stateNumber"));
        req.setAttribute("car", car);
        req.setAttribute("customers", car.getCustomers());
        req.getRequestDispatcher("/customer/showCustomers.jsp").forward(req, resp);
    }
}
