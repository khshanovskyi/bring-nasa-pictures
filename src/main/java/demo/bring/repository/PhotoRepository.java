package demo.bring.repository;

import com.bobocode.svydovets.annotation.Component;
import com.bobocode.svydovets.annotation.Inject;
import demo.bring.entity.Photo;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Objects;

@Component("photoRepository")
@NoArgsConstructor
public class PhotoRepository implements EntityRepository<Photo> {

    @Inject
    private SessionFactory sessionFactory;

    @Override
    public Photo save(Photo photo) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(photo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return photo;
    }

    @Override
    public Photo delete(Photo photo) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(photo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return photo;
    }

    @Override
    public Photo deleteId(Long id) {
        var photo = Objects.requireNonNull(getById(id),
                String.format("Photo with id '%s' is not exists in DB and Cannot be deleted", id));
        return delete(photo);
    }

    @Override
    public Photo getById(Long id) {
        return sessionFactory.openSession().get(Photo.class, id);
    }

    @Override
    public List<Photo> getAll() {
        return sessionFactory.openSession().createQuery("FROM Photo", Photo.class).list();
    }
}
