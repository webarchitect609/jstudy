package ru.webarch.jstudy.addressbook.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.webarch.jstudy.addressbook.model.ContactData;
import ru.webarch.jstudy.addressbook.model.ContactSet;
import ru.webarch.jstudy.addressbook.model.GroupData;
import ru.webarch.jstudy.addressbook.model.GroupSet;

import java.util.List;

public class DbHelper {

    private SessionFactory sessionFactory;

    private Session session;

    @SuppressWarnings("WeakerAccess")
    public DbHelper() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public GroupSet groups() {
        @SuppressWarnings("unchecked")
        List<GroupData> groupDataList = openSession().createQuery("from GroupData").list();
        closeSession();
        return new GroupSet(groupDataList);
    }

    public ContactSet contacts() {
        @SuppressWarnings("unchecked")
        List<ContactData> contactDataList = openSession()
                .createQuery("from ContactData where deprecated = '0000-00-00'")
                .list();
        closeSession();
        return new ContactSet(contactDataList);
    }

    public ContactData getContact(int id) {
        @SuppressWarnings("unchecked")
        List<ContactData> contactDataList = openSession()
                .createQuery("from ContactData where id = " + id + " and deprecated = '0000-00-00'")
                .list();
        closeSession();
        return contactDataList.iterator().next();
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
