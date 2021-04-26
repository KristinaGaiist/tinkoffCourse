package unit8.unit10.customer;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import unit8.entity.Customer;
import unit8.repository.HibernateCarRepository;
import unit8.repository.HibernateCustomerRepository;
import unit8.service.CustomerService;

public class GetCustomerServlet extends HttpServlet {

    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private CustomerService service = new CustomerService(new HibernateCustomerRepository(sessionFactory),
                                                          new HibernateCarRepository(sessionFactory));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Customer> customers = service.getAllCustomers();
        req.setAttribute("customers", customers);
        req.getRequestDispatcher("/customer/showCustomers.jsp").forward(req, resp);
    }
}
