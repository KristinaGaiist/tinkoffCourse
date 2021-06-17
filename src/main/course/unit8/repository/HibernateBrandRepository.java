package unit8.repository;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import unit8.entity.Brand;

@Repository
@ComponentScan({"unit11.configs"})
public class HibernateBrandRepository implements BrandRepository {

    private final SessionFactory sessionFactory;

    public HibernateBrandRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addBrand(String brandName) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Brand brand = new Brand();
            brand.setName(brandName);

            session.save(brand);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Brand> findAllBrands() {
        try (Session session = this.sessionFactory.openSession()) {
            return session.createQuery("SELECT b FROM Brand b", Brand.class).getResultList();
        }
    }

    @Override
    public Optional<Brand> findByName(String brandName) {
        try (Session session = this.sessionFactory.openSession()) {
            return session.createQuery("SELECT b FROM Brand b WHERE b.name =: brandName")
                .setParameter("brandName", brandName)
                .list()
                .stream()
                .findFirst();
        }
    }

    @Override
    public Optional<Brand> findById(long id) {
        try (Session session = this.sessionFactory.openSession()) {
            return session.createQuery("SELECT b FROM Brand b WHERE b.id =: id")
                .setParameter("id", id)
                .list()
                .stream()
                .findFirst();
        }
    }

    @Override
    public boolean existsByName(String name) {
        try (Session session = this.sessionFactory.openSession()) {
            long count = (long) session.createQuery("SELECT COUNT(b.id) FROM Brand b WHERE b.name =: name")
                .setParameter("name", name)
                .uniqueResult();

            return count != 0;
        }
    }

    @Override
    public boolean existsByName(String name, long excludeId) {
        try (Session session = this.sessionFactory.openSession()) {
            long count = (long) session.createQuery("SELECT COUNT(b.id) FROM Brand b "
                                                        + "WHERE b.name =: name and b.id !=: excludeId")
                .setParameter("name", name)
                .setParameter("excludeId", excludeId)
                .uniqueResult();

            return count != 0;
        }
    }

    @Override
    public void updateBrand(long id, String newName) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createQuery("UPDATE Brand SET name =: newName WHERE id =: id")
                .setParameter("newName", newName)
                .setParameter("id", id)
                .executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public void deleteBrandByName(String brandName) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createQuery("DELETE FROM Brand WHERE name =: brandName")
                .setParameter("brandName", brandName)
                .executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
