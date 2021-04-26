package unit8.unit10.model;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.cfg.Configuration;
import unit8.entity.Model;
import unit8.repository.HibernateModelRepository;
import unit8.service.ModelService;
import unit8.utils.ValidationUtil;

public class UpdateModelServlet extends HttpServlet {

    private final ModelService service =
        new ModelService(new HibernateModelRepository(new Configuration().configure().buildSessionFactory()));

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int id = ValidationUtil.parseInt(req.getParameter("id"));
        String modelName = req.getParameter("model");

        Model model = service.getModelById(id);
        service.updateModel(model.getModel(), modelName);

        resp.sendRedirect(req.getContextPath() + "/showModels");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = ValidationUtil.parseInt(req.getParameter("id"));
        Model model = service.getModelById(id);

        req.setAttribute("model", model);
        req.getRequestDispatcher("/model/updateModel.jsp").forward(req, resp);
    }
}
