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
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(customer);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Customer> findAll() {
        Session session = this.sessionFactory.openSession();
        List<Customer> customers = session.createQuery("SELECT C FROM Customer C", Customer.class).getResultList();
        session.close();

        return customers;
    }

    @Override
    public Optional<Customer> findByFullName(String firstName, String lastName, String middleName) {
        Session session = this.sessionFactory.openSession();
        List<Customer> customers = session.createQuery("SELECT C FROM Customer C WHERE C.lastName =: lastName "
                                                           + "and C.firstName =: firstName and C.middleName =: "
                                                           + "middleName")
            .setParameter("firstName", firstName)
            .setParameter("lastName", lastName)
            .setParameter("middleName", middleName)
            .list();
        session.close();

        return customers.stream().findFirst();
    }

    @Override
    public Optional<Customer> findById(long id) {
        Session session = this.sessionFactory.openSession();
        List<Customer> customers = session.createQuery("SELECT C FROM Customer C WHERE C.id =: id")
            .setParameter("id", id)
            .list();
        session.close();

        return customers.stream().findFirst();
    }

    @Override
    public List<Customer> findCustomersByCarBrand(String brandName) {
        Session session = this.sessionFactory.openSession();
        List<Customer> result = session
            .createQuery("SELECT C FROM Customer C "
                             + "JOIN C.cars car "
                             + "WHERE car.model.brand.name =: brandName")
            .setParameter("brandName", brandName)
            .list();
        session.close();

        return result;
    }

    @Override
    public List<Customer> findCustomersByCarModel(String model) {
        Session session = this.sessionFactory.openSession();
        List<Customer> result = session
            .createQuery("SELECT C FROM Customer C "
                             + "JOIN C.cars car "
                             + "WHERE car.model.model =: model")
            .setParameter("model", model)
            .list();
        session.close();

        return result;
    }

    @Override
    public void updateFirstName(long id, String newFirstName) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("UPDATE Customer SET firstName =: newFirstName WHERE id =: id")
            .setParameter("newFirstName", newFirstName)
            .setParameter("id", id)
            .executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void addCustomerCar(long customerId, long carId) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Car car = (Car) session.createQuery("SELECT C FROM Car C WHERE C.id =: carId")
            .setParameter("carId", carId)
            .list()
            .stream()
            .findFirst()
            .get();

        Customer customer = (Customer) session.createQuery("SELECT C FROM Customer C WHERE C.id =: customerId")
            .setParameter("customerId", customerId)
            .list()
            .stream()
            .findFirst()
            .get();
        customer.getCars().add(car);

        session.save(customer);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(long id) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("DELETE FROM Customer WHERE id =: id")
            .setParameter("id", id)
            .executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void deleteCustomerCar(long customerId, Car car) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = (Customer) session.createQuery("SELECT C FROM Customer C WHERE C.id =: customerId")
            .setParameter("customerId", customerId)
            .list()
            .stream()
            .findFirst()
            .get();
        customer.getCars().remove(car);

        session.save(customer);
        transaction.commit();
        session.close();
    }
}
