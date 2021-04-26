package unit8.unit10.car;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import unit8.entity.Model;
import unit8.repository.HibernateCarRepository;
import unit8.repository.HibernateModelRepository;
import unit8.service.CarService;
import unit8.service.ModelService;

public class CreateCarServlet extends HttpServlet {

    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private final CarService carService = new CarService(new HibernateCarRepository(sessionFactory));
    private final ModelService modelService = new ModelService(new HibernateModelRepository(sessionFactory));

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Model model = modelService.getModel(req.getParameter("model"));
        carService.createCar(req.getParameter("stateNumber"), model);
        resp.sendRedirect(req.getContextPath() + "/showCars");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/car/createCar.jsp").forward(req, resp);
    }
}
