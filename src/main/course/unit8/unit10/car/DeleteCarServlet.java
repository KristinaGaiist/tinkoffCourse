package unit8.unit10.car;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.cfg.Configuration;
import unit8.repository.HibernateCarRepository;
import unit8.service.CarService;

public class DeleteCarServlet extends HttpServlet {

    private final CarService service =
        new CarService(new HibernateCarRepository(new Configuration().configure().buildSessionFactory()));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        req.setAttribute("stateNumber", req.getParameter("stateNumber"));
        req.getRequestDispatcher("/car/deleteCar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        service.deleteByStateNumber(req.getParameter("stateNumber"));
        resp.sendRedirect(req.getContextPath() + "/showCars");
    }
}
