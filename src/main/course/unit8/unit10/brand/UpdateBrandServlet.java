package unit8.unit10.brand;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.cfg.Configuration;
import unit8.entity.Brand;
import unit8.repository.HibernateBrandRepository;
import unit8.service.BrandService;
import unit8.utils.ValidationUtil;

public class UpdateBrandServlet extends HttpServlet {

    private BrandService service =
        new BrandService(new HibernateBrandRepository(new Configuration().configure().buildSessionFactory()));

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int id = ValidationUtil.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        Brand brand = service.getBrandById(id);

        service.renameBrand(brand.getName(), name);
        resp.sendRedirect(req.getContextPath() + "/showBrands");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = ValidationUtil.parseInt(req.getParameter("id"));
        Brand brand = service.getBrandById(id);

        req.setAttribute("brand", brand);
        req.getRequestDispatcher("/brand/updateBrand.jsp").forward(req, resp);
    }
}
