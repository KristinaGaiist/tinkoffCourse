package unit8.unit10.customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import unit8.entity.Car;
import unit8.entity.Customer;
import unit8.repository.HibernateCarRepository;
import unit8.repository.HibernateCustomerRepository;
import unit8.service.CarService;
import unit8.service.CustomerService;
import unit8.utils.ValidationUtil;

public class AddCustomerCarServlet extends HttpServlet {

    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private final HibernateCarRepository carRepository = new HibernateCarRepository(sessionFactory);
    private final CarService carService = new CarService(carRepository);
    private CustomerService service = new CustomerService(new HibernateCustomerRepository(sessionFactory),
                                                          carRepository);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = ValidationUtil.parseInt(req.getParameter("id"));
        String stateNumber = req.getParameter("stateNumber");
        Car car = carService.getCar(stateNumber);

        service.addCustomerCar(id, car.getId());

        Customer customer = service.getCustomerById(id);
        req.setAttribute("customer", customer);
        req.setAttribute("cars", customer.getCars());
        req.getRequestDispatcher("/car/showCars.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = ValidationUtil.parseInt(req.getParameter("id"));

        req.setAttribute("id", id);
        req.getRequestDispatcher("/customer/addCustomerCar.jsp").forward(req, resp);
    }
}
