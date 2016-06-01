package ru.webarch.jstudy.mantis.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.webarch.jstudy.mantis.model.MantisUser;

import java.util.List;

public class DBHelper {

    private SessionFactory sessionFactory;
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private ApplicationManager app;
    private Session session;

    @SuppressWarnings("WeakerAccess")
    public DBHelper(ApplicationManager app) {
        this.app = app;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    /**
     * Возвращает список всех пользователей, за исключением администратора системы, чтобы не было риска его повредить
     *
     * @return Список пользователей
     */
    public List<MantisUser> allUsers() {
        @SuppressWarnings("unchecked")
        List<MantisUser> mantisUserList = openSession().createQuery("from MantisUser where username <> 'administrator'").list();
        closeSession();
        return mantisUserList;
    }


    @SuppressWarnings("WeakerAccess")
    public Session openSession() {
        if (session == null) {
            session = sessionFactory.openSession();
            session.beginTransaction();
        }
        return session;
    }

    @SuppressWarnings("WeakerAccess")
    public void closeSession() {
        if (session != null) {
            session.getTransaction().commit();
            session.close();
        }
        session = null;
    }

}
