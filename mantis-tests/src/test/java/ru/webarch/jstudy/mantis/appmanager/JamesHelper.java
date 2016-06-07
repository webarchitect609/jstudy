package ru.webarch.jstudy.mantis.appmanager;

import org.apache.commons.net.telnet.TelnetClient;
import ru.webarch.jstudy.mantis.model.MailMessage;
import ru.webarch.jstudy.mantis.model.MantisUser;

import javax.mail.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JamesHelper {

    private ApplicationManager app;

    private Session mailSession;
    private String mailserver;
    private Store store;

    private TelnetClient telnet;
    private InputStream in;
    private PrintStream out;

    public JamesHelper(ApplicationManager app) {
        this.app = app;
        telnet = new TelnetClient();
        mailSession = Session.getDefaultInstance(System.getProperties());
    }

    public void createUser(MantisUser user) {
        initTelnetSession();
        write("adduser " + user.getUsername() + " " + user.getPassword());
        String result = readUntil("User " + user.getUsername() + " added");
        closeTelnetSession();
    }

    public void deleteUser(MantisUser user) {
        initTelnetSession();
        write("deleteuser " + user.getUsername());
        String result = readUntil("User " + user.getUsername() + " deleted");
        closeTelnetSession();
    }

    private String readUntil(String pattern) {
        try {

            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();

            char ch = (char) in.read();
            while (true) {
                System.out.print(ch);
                sb.append(ch);
                if (ch == lastChar) {
                    if (sb.toString().endsWith(pattern)) {
                        return sb.toString();
                    }
                }
                ch = (char) in.read();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void write(String value) {
        try {
            out.println(value);
            out.flush();
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTelnetSession() {
        mailserver = app.getProperty("mailserver.host");
        int port = Integer.parseInt(app.getProperty("mailserver.port"));
        String login = app.getProperty("mailserver.login");
        String password = app.getProperty("mailserver.password");

        try {
            telnet.connect(mailserver, port);
            in = telnet.getInputStream();
            out = new PrintStream(telnet.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Первая попытка ( по неизвестной причине неуспешная)
        readUntil("Login id:");
        write("");
        readUntil("Password:");
        write("");

        //Вторая попытка
        readUntil("Login id:");
        write(login);
        readUntil("Password:");
        write(password);

        //Read welcome message
        readUntil("Welcome " + login + ". HELP for a list of commands");
    }

    private void closeTelnetSession() {
        write("quit");
    }


    public List<MailMessage> waitForMail(MantisUser user, long timeout) throws MessagingException {

        long now = System.currentTimeMillis();
        while (System.currentTimeMillis() < now + timeout) {
            List<MailMessage> allMail = getAllMail(user);
            if (allMail.size() > 0) {
                return allMail;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new Error("Нет почты");
    }

    private List<MailMessage> getAllMail(MantisUser user) throws MessagingException {
        Folder inbox = openInbox(user);
        List<MailMessage> messages = Arrays.asList(
                inbox.getMessages()).stream().map((m) -> toModelMail(m)).collect(Collectors.toList());
        closeFolder(inbox);
        return messages;
    }

    private void closeFolder(Folder folder) throws MessagingException {
        folder.close(true);
        store.close();
    }

    public static MailMessage toModelMail(Message message) {
        try {
            return new MailMessage(message.getAllRecipients()[0].toString(), (String) message.getContent());
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Folder openInbox(MantisUser user) throws MessagingException {
        store = mailSession.getStore("pop3");
        store.connect(mailserver, user.getUsername(), user.getPassword());
        Folder folder = store.getDefaultFolder().getFolder("INBOX");
        folder.open(Folder.READ_WRITE);//Можно открыть только на чтение
        return folder;
    }
}
