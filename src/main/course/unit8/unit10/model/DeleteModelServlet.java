package unit8.unit10.model;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.cfg.Configuration;
import unit8.repository.HibernateModelRepository;
import unit8.service.ModelService;

public class DeleteModelServlet extends HttpServlet {

    private final ModelService service =
        new ModelService(new HibernateModelRepository(new Configuration().configure().buildSessionFactory()));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("model", req.getParameter("model"));
        req.getRequestDispatcher("/model/deleteModel.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        service.deleteModel(req.getParameter("model"));
        resp.sendRedirect(req.getContextPath() + "/showModels");
    }
}
