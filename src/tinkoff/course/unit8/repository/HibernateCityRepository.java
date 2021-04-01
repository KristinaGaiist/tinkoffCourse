package unit8.repository;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import unit8.entity.City;

public class HibernateCityRepository implements CityRepository {

    private SessionFactory sessionFactory;

    public HibernateCityRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addCity(String cityName) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            City city = new City();
            city.setName(cityName);

            session.save(city);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<City> findAllCities() {
        try (Session session = this.sessionFactory.openSession()) {
            return session.createQuery("SELECT c FROM City c", City.class).getResultList();
        }
    }

    @Override
    public Optional<City> findByName(String cityName) {
        try (Session session = this.sessionFactory.openSession()) {
            return session.createQuery("SELECT c FROM City c WHERE c.name =: cityName")
                .setParameter("cityName", cityName)
                .list()
                .stream()
                .findFirst();
        }
    }

    @Override
    public Optional<City> findById(long id) {
        try (Session session = this.sessionFactory.openSession()) {
            return session.createQuery("SELECT c FROM City c WHERE c.id =: id")
                .setParameter("id", id)
                .list()
                .stream()
                .findFirst();
        }
    }

    @Override
    public boolean existByName(String name) {
        try (Session session = this.sessionFactory.openSession()) {
            long count = (long) session.createQuery("SELECT COUNT(c.id) FROM City c WHERE c.name =: name")
                .setParameter("name", name)
                .uniqueResult();

            return count != 0;
        }
    }

    @Override
    public boolean existsByName(String name, long excludeId) {
        try (Session session = this.sessionFactory.openSession()) {
            long count = (long) session.createQuery("SELECT COUNT(c.id) FROM City c "
                                                        + "WHERE c.name =: name and c.id !=: excludeId")
                .setParameter("name", name)
                .setParameter("excludeId", excludeId)
                .uniqueResult();

            return count != 0;
        }
    }

    @Override
    public void updateCity(long id, String newName) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createQuery("UPDATE City SET name =: newName WHERE id =: id")
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
    public void deleteCityByName(String cityName) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createQuery("DELETE FROM City WHERE name =: cityName")
                .setParameter("cityName", cityName)
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
