package unit8.unit10.customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import unit8.entity.City;
import unit8.repository.HibernateCarRepository;
import unit8.repository.HibernateCityRepository;
import unit8.repository.HibernateCustomerRepository;
import unit8.service.CityService;
import unit8.service.CustomerService;

public class CreateCustomerServlet extends HttpServlet {

    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private CustomerService customerService = new CustomerService(new HibernateCustomerRepository(sessionFactory),
                                                                  new HibernateCarRepository(sessionFactory));
    private final CityService cityService = new CityService(new HibernateCityRepository(sessionFactory));

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        City city = cityService.getCityByName(req.getParameter("city"));
        customerService.createCustomer(req.getParameter("firstName"), req.getParameter("lastName"),
                                       req.getParameter("middleName"), city);
        resp.sendRedirect(req.getContextPath() + "/showCustomers");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/customer/createCustomer.jsp").forward(req, resp);
    }
}
