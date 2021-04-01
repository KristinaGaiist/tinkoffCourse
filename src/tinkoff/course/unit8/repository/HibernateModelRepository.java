package unit8.repository;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import unit8.entity.Model;

public class HibernateModelRepository implements ModelRepository {

    private SessionFactory sessionFactory;

    public HibernateModelRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Model model) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.save(model);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<Model> findAll() {
        try (Session session = this.sessionFactory.openSession()) {
            return session.createQuery("SELECT m FROM Model m", Model.class).getResultList();
        }
    }

    @Override
    public Optional<Model> findByModel(String model) {
        try (Session session = this.sessionFactory.openSession()) {
            return session.createQuery("SELECT m FROM Model m WHERE m.model =: model")
                .setParameter("model", model)
                .list()
                .stream()
                .findFirst();
        }
    }

    @Override
    public boolean existsByModel(String model) {
        try (Session session = this.sessionFactory.openSession()) {
            long count = (long) session.createQuery("SELECT COUNT(m.id) FROM Model m WHERE m.model =: model")
                .setParameter("model", model)
                .uniqueResult();

            return count != 0;
        }
    }

    @Override
    public boolean existsByModel(String model, long excludeId) {
        try (Session session = this.sessionFactory.openSession()) {
            long count = (long) session.createQuery("SELECT COUNT(m.id) FROM Model m "
                                                        + "WHERE m.model =: model and m.id !=: excludeId")
                .setParameter("model", model)
                .setParameter("excludeId", excludeId)
                .uniqueResult();

            return count != 0;
        }
    }

    @Override
    public Optional<Model> findById(long id) {
        try (Session session = this.sessionFactory.openSession()) {
            return session.createQuery("SELECT m FROM Model m WHERE m.id =: id")
                .setParameter("id", id)
                .list()
                .stream()
                .findFirst();
        }
    }

    @Override
    public void updateModel(long id, String newModel) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createQuery("UPDATE Model SET model =: newModel WHERE id =: id")
                .setParameter("newModel", newModel)
                .setParameter("id", id)
                .executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void deleteByModel(String model) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createQuery("DELETE FROM Model WHERE model =: model")
                .setParameter("model", model)
                .executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
