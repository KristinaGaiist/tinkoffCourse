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
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(model);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Model> findAll() {
        Session session = this.sessionFactory.openSession();
        List<Model> models = session.createQuery("SELECT M FROM Model M", Model.class).getResultList();
        session.close();

        return models;
    }

    @Override
    public Optional<Model> findByModel(String model) {
        Session session = this.sessionFactory.openSession();
        List<Model> models = session.createQuery("SELECT M FROM Model M WHERE M.model =: model")
            .setParameter("model", model)
            .list();
        session.close();

        return models.stream().findFirst();
    }

    @Override
    public Optional<Model> findById(long id) {
        Session session = this.sessionFactory.openSession();
        List<Model> models = session.createQuery("SELECT M FROM Model M WHERE M.id =: id")
            .setParameter("id", id)
            .list();
        session.close();

        return models.stream().findFirst();
    }

    @Override
    public void updateModel(long id, String newModel) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("UPDATE Model SET model =: newModel WHERE id =: id")
            .setParameter("newModel", newModel)
            .setParameter("id", id)
            .executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void deleteByModel(String model) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("DELETE FROM Model WHERE model =: model")
            .setParameter("model", model)
            .executeUpdate();

        transaction.commit();
        session.close();
    }
}
