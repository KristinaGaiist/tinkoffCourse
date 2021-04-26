package unit8.unit10.model;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.cfg.Configuration;
import unit8.entity.Model;
import unit8.repository.HibernateModelRepository;
import unit8.service.ModelService;

public class GetModelServlet extends HttpServlet {

    private final ModelService service =
        new ModelService(new HibernateModelRepository(new Configuration().configure().buildSessionFactory()));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            List<Model> models = service.getAllModels();
            req.setAttribute("models", models);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/model/showModels.jsp");
            dispatcher.forward(req, resp);
    }
}
