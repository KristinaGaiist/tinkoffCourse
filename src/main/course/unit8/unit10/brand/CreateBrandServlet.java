package unit8.unit10.brand;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.cfg.Configuration;
import unit8.repository.HibernateBrandRepository;
import unit8.service.BrandService;

public class CreateBrandServlet extends HttpServlet {

    private BrandService service =
        new BrandService(new HibernateBrandRepository(new Configuration().configure().buildSessionFactory()));

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        service.createBrand(req.getParameter("name"));
        resp.sendRedirect(req.getContextPath() + "/showBrands");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/brand/createBrand.jsp").forward(req, resp);
    }
}
