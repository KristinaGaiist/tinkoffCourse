package unit8.unit10.brand;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.cfg.Configuration;
import unit8.entity.Brand;
import unit8.repository.HibernateBrandRepository;
import unit8.service.BrandService;

public class GetBrandServlet extends HttpServlet {

    private final BrandService service =
        new BrandService(new HibernateBrandRepository(new Configuration().configure().buildSessionFactory()));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            List<Brand> brands = service.getAllBrands();
            req.setAttribute("brands", brands);
            req.getRequestDispatcher("/brand/showBrands.jsp").forward(req, resp);
    }
}
