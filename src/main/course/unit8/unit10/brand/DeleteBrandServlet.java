package unit8.unit10.brand;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.cfg.Configuration;
import unit8.repository.HibernateBrandRepository;
import unit8.service.BrandService;

public class DeleteBrandServlet extends HttpServlet {

    private BrandService service =
        new BrandService(new HibernateBrandRepository(new Configuration().configure().buildSessionFactory()));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        req.setAttribute("name", req.getParameter("name"));
        req.getRequestDispatcher("/brand/deleteBrand.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        service.deleteBrand(req.getParameter("name"));
        resp.sendRedirect(req.getContextPath() + "/showBrands");
    }
}
