package unit8.repository;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import unit8.entity.Car;
import unit8.entity.Customer;

public class HibernateCustomerRepository implements CustomerRepository {

    private SessionFactory sessionFactory;

    public HibernateCustomerRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Customer customer) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.save(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Customer> findAll() {
        try (Session session = this.sessionFactory.openSession()) {
            return session.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
        }
    }

    @Override
    public Optional<Customer> findByFullName(String firstName, String lastName, String middleName) {
        try (Session session = this.sessionFactory.openSession()) {
            return session.createQuery("SELECT c FROM Customer c WHERE c.lastName =: lastName "
                                           + "and c.firstName =: firstName and c.middleName =: "
                                           + "middleName")
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .setParameter("middleName", middleName)
                .list()
                .stream()
                .findFirst();
        }
    }

    @Override
    public Optional<Customer> findById(long id) {
        try (Session session = this.sessionFactory.openSession()) {
            return session.createQuery("SELECT c FROM Customer c WHERE c.id =: id")
                .setParameter("id", id)
                .list()
                .stream()
                .findFirst();
        }
    }

    @Override
    public List<Customer> findCustomersByCarBrand(String brandName) {
        try (Session session = this.sessionFactory.openSession()) {
            return session
                .createQuery("SELECT c FROM Customer c "
                                 + "JOIN c.cars car "
                                 + "WHERE car.model.brand.name =: brandName")
                .setParameter("brandName", brandName)
                .list();
        }
    }

    @Override
    public List<Customer> findCustomersByCarModel(String model) {
        try (Session session = this.sessionFactory.openSession()) {
            return session
                .createQuery("SELECT c FROM Customer c "
                                 + "JOIN c.cars car "
                                 + "WHERE car.model.model =: model")
                .setParameter("model", model)
                .list();
        }
    }

    @Override
    public boolean existsById(long id) {
        try (Session session = this.sessionFactory.openSession()) {
            long count = (long) session.createQuery("SELECT COUNT(c.id) FROM Customer c WHERE c.id =: id")
                .setParameter("id", id)
                .uniqueResult();
            return count != 0;
        }
    }

    @Override
    public void updateFirstName(long id, String newFirstName) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createQuery("UPDATE Customer SET firstName =: newFirstName WHERE id =: id")
                .setParameter("newFirstName", newFirstName)
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
    public void addCustomerCar(long customerId, long carId) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Car car = (Car) session.createQuery("SELECT c FROM Car c WHERE c.id =: carId")
                .setParameter("carId", carId)
                .list()
                .stream()
                .findFirst()
                .get();

            Customer customer = (Customer) session.createQuery("SELECT c FROM Customer c WHERE c.id =: customerId")
                .setParameter("customerId", customerId)
                .list()
                .stream()
                .findFirst()
                .get();
            customer.getCars().add(car);

            session.save(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public void delete(long id) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createQuery("DELETE FROM Customer WHERE id =: id")
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
    public void deleteCustomerCar(long customerId, Car car) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Customer customer = (Customer) session.createQuery("SELECT c FROM Customer c WHERE c.id =: customerId")
                .setParameter("customerId", customerId)
                .list()
                .stream()
                .findFirst()
                .get();
            customer.getCars().remove(car);

            session.save(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
