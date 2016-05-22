package ru.webarch.jstudy.addressbook.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.webarch.jstudy.addressbook.model.ContactData;
import ru.webarch.jstudy.addressbook.model.GroupData;

import java.util.List;

public class HbConnectionTest {

    private SessionFactory sessionFactory;

    @BeforeClass
    protected void setUp() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }


    @Test
    public void testHbConnection() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        @SuppressWarnings("unchecked")
        List<ContactData> contactDataList = session.createQuery("from ContactData where deprecated = '0000-00-00'").list();

        //noinspection Convert2streamapi
        for (ContactData contact : contactDataList) {
            System.out.println(contact);
        }

        if (contactDataList.size() == 0) {
            System.out.println("contactDataList is empty");
        }

        session.getTransaction().commit();
        session.close();
    }

}
