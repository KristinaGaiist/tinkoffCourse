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
import unit8.utils.ValidationUtil;

public class UpdateCarServlet extends HttpServlet {

    private final CarService service =
        new CarService(new HibernateCarRepository(new Configuration().configure().buildSessionFactory()));

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int id = ValidationUtil.parseInt(req.getParameter("id"));
        String stateNumber = req.getParameter("stateNumber");
        Car car = service.getCarById(id);

        service.updateStateNumber(car.getStateNumber(), stateNumber);
        resp.sendRedirect(req.getContextPath() + "/showCars");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = ValidationUtil.parseInt(req.getParameter("id"));
        Car car = service.getCarById(id);

        req.setAttribute("car", car);
        req.getRequestDispatcher("/car/updateCar.jsp").forward(req, resp);
    }
}
