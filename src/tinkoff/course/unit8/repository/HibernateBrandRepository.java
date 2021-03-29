package unit8.repository;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import unit8.entity.Brand;

public class HibernateBrandRepository implements BrandRepository {

    private SessionFactory sessionFactory;

    public HibernateBrandRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addBrand(String brandName) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Brand brand = new Brand();
        brand.setName(brandName);

        session.save(brand);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Brand> findAllBrands() {
        Session session = this.sessionFactory.openSession();
        List<Brand> brands = session.createQuery("SELECT B FROM Brand B", Brand.class).getResultList();
        session.close();

        return brands;
    }

    @Override
    public Optional<Brand> findByName(String brandName) {
        Session session = this.sessionFactory.openSession();
        List<Brand> brands = session.createQuery("SELECT B FROM Brand B WHERE B.name =: brandName")
            .setParameter("brandName", brandName)
            .list();
        session.close();

        return brands.stream().findFirst();
    }

    @Override
    public Optional<Brand> findById(long id) {
        Session session = this.sessionFactory.openSession();
        List<Brand> brands = session.createQuery("SELECT B FROM Brand B WHERE B.id =: id")
            .setParameter("id", id)
            .list();
        session.close();

        return brands.stream().findFirst();
    }

    @Override
    public void updateBrand(long id, String newName) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("UPDATE Brand SET name =: newName WHERE id =: id")
            .setParameter("newName", newName)
            .setParameter("id", id)
            .executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void deleteBrandByName(String brandName) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("DELETE FROM Brand WHERE name =: brandName")
            .setParameter("brandName", brandName)
            .executeUpdate();

        transaction.commit();
        session.close();
    }
}
