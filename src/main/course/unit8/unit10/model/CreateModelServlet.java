package unit8.unit10.model;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import unit8.entity.Brand;
import unit8.repository.HibernateBrandRepository;
import unit8.repository.HibernateModelRepository;
import unit8.service.BrandService;
import unit8.service.ModelService;

public class CreateModelServlet extends HttpServlet {

    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private final BrandService brandService = new BrandService(new HibernateBrandRepository(sessionFactory));
    private final ModelService modelService = new ModelService(new HibernateModelRepository(sessionFactory));

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Brand brand = brandService.getBrandByName(req.getParameter("brandName"));
        modelService.createModel(req.getParameter("model"), brand);
        resp.sendRedirect(req.getContextPath() + "/showModels");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/model/createModel.jsp").forward(req, resp);
    }
}
