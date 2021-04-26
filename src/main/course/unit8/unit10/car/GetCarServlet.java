package unit8.unit10.car;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.cfg.Configuration;
import unit8.entity.Car;
import unit8.repository.HibernateCarRepository;
import unit8.service.CarService;

public class GetCarServlet extends HttpServlet {

    private final CarService service =
        new CarService(new HibernateCarRepository(new Configuration().configure().buildSessionFactory()));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            List<Car> cars = service.getAllCars();
            req.setAttribute("cars", cars);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/car/showCars.jsp");
            dispatcher.forward(req, resp);
    }
}
