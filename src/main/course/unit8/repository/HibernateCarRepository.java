package unit8.repository;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import unit8.entity.Car;

@Repository
public class HibernateCarRepository implements CarRepository {

    private SessionFactory sessionFactory;

    public HibernateCarRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Car car) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.save(car);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Car> findAll() {
        try (Session session = this.sessionFactory.openSession()) {
            return session.createQuery("SELECT c FROM Car c", Car.class).getResultList();
        }
    }

    @Override
    public Optional<Car> findByStateNumber(String stateNumber) {
        try (Session session = this.sessionFactory.openSession()) {
            return session.createQuery("SELECT c FROM Car c WHERE c.stateNumber =: stateNumber")
                .setParameter("stateNumber", stateNumber)
                .list()
                .stream()
                .findFirst();
        }
    }

    @Override
    public Optional<Car> findById(long id) {
        try (Session session = this.sessionFactory.openSession()) {
            return session.createQuery("SELECT c FROM Car c WHERE c.id =: id")
                .setParameter("id", id)
                .list()
                .stream()
                .findFirst();
        }
    }

    @Override
    public boolean existsByStateNumber(String stateNumber) {
        try (Session session = this.sessionFactory.openSession()) {
            long count = (long) session.createQuery("SELECT COUNT(c.id) FROM Car c WHERE c.stateNumber =: stateNumber")
                .setParameter("stateNumber", stateNumber)
                .uniqueResult();

            return count != 0;
        }
    }

    @Override
    public boolean existsByStateNumber(String stateNumber, long excludeId) {
        try (Session session = this.sessionFactory.openSession()) {
            long count = (long) session.createQuery("SELECT COUNT(c.id) FROM Car c "
                                                        + "WHERE c.stateNumber =: stateNumber and c.id !=: excludeId")
                .setParameter("stateNumber", stateNumber)
                .setParameter("excludeId", excludeId)
                .uniqueResult();

            return count != 0;
        }
    }

    @Override
    public void updateStateNumber(long id, String newStateNumber) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createQuery("UPDATE Car SET stateNumber =: newStateNumber WHERE id =: id")
                .setParameter("newStateNumber", newStateNumber)
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
    public void deleteByStateNumber(String stateNumber) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createQuery("DELETE FROM Car WHERE stateNumber =: stateNumber")
                .setParameter("stateNumber", stateNumber)
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
