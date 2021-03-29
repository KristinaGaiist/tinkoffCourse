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
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        City city = new City();
        city.setName(cityName);

        session.save(city);
        transaction.commit();
        session.close();
    }

    @Override
    public List<City> findAllCities() {
        Session session = this.sessionFactory.openSession();
        List<City> cities = session.createQuery("SELECT C FROM City C", City.class).getResultList();
        session.close();

        return cities;
    }

    @Override
    public Optional<City> findByName(String cityName) {
        Session session = this.sessionFactory.openSession();
        List<City> cities = session.createQuery("SELECT C FROM City C WHERE C.name =: cityName")
            .setParameter("cityName", cityName)
            .list();
        session.close();

        return cities.stream().findFirst();
    }

    @Override
    public Optional<City> findById(long id) {
        Session session = this.sessionFactory.openSession();
        List<City> cities = session.createQuery("SELECT C FROM City C WHERE C.id =: id")
            .setParameter("id", id)
            .list();
        session.close();

        return cities.stream().findFirst();
    }

    @Override
    public void updateCity(long id, String newName) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("UPDATE City SET name =: newName WHERE id =: id")
            .setParameter("newName", newName)
            .setParameter("id", id)
            .executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void deleteCityByName(String cityName) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("DELETE FROM City WHERE name =: cityName")
            .setParameter("cityName", cityName)
            .executeUpdate();

        transaction.commit();
        session.close();
    }
}
