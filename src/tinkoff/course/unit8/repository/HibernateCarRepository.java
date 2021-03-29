package unit8.repository;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import unit8.entity.Car;

public class HibernateCarRepository implements CarRepository {

    private SessionFactory sessionFactory;

    public HibernateCarRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Car car) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(car);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Car> findAll() {
        Session session = this.sessionFactory.openSession();
        List<Car> cars = session.createQuery("SELECT C FROM Car C", Car.class).getResultList();
        session.close();

        return cars;
    }

    @Override
    public Optional<Car> findByStateNumber(String stateNumber) {
        Session session = this.sessionFactory.openSession();
        List<Car> cars = session.createQuery("SELECT C FROM Car C WHERE C.stateNumber =: stateNumber")
            .setParameter("stateNumber", stateNumber)
            .list();
        session.close();

        return cars.stream().findFirst();
    }

    @Override
    public Optional<Car> findById(long id) {
        Session session = this.sessionFactory.openSession();
        List<Car> cars = session.createQuery("SELECT C FROM Car C WHERE C.id =: id")
            .setParameter("id", id)
            .list();
        session.close();

        return cars.stream().findFirst();
    }

    @Override
    public void updateStateNumber(long id, String newStateNumber) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("UPDATE Car SET stateNumber =: newStateNumber WHERE id =: id")
            .setParameter("newStateNumber", newStateNumber)
            .setParameter("id", id)
            .executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void deleteByStateNumber(String stateNumber) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("DELETE FROM Car WHERE stateNumber =: stateNumber")
            .setParameter("stateNumber", stateNumber)
            .executeUpdate();

        transaction.commit();
        session.close();
    }
}
