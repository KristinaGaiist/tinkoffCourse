package unit8.unit10.customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import unit8.repository.HibernateCarRepository;
import unit8.repository.HibernateCustomerRepository;
import unit8.service.CustomerService;
import unit8.utils.ValidationUtil;

public class DeleteCustomerServlet extends HttpServlet {

    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private CustomerService service = new CustomerService(new HibernateCustomerRepository(sessionFactory),
                                                          new HibernateCarRepository(sessionFactory));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("id", req.getParameter("id"));
        req.getRequestDispatcher("/customer/deleteCustomer.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int id = ValidationUtil.parseInt(req.getParameter("id"));
        service.deleteById(id);
        resp.sendRedirect(req.getContextPath() + "/showCustomers");
    }
}
