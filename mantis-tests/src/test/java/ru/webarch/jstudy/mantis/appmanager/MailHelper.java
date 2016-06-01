package ru.webarch.jstudy.mantis.appmanager;

import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;
import ru.webarch.jstudy.mantis.model.MailMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MailHelper {
    private Wiser wiser;
    private ApplicationManager app;

    @SuppressWarnings("WeakerAccess")
    public MailHelper(ApplicationManager app) {
        this.app = app;
        wiser = new Wiser();
    }

    public List<MailMessage> waitForMail(int count, long timeout) {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() < start + timeout) {
            if (wiser.getMessages().size() >= count) {
                //noinspection Convert2MethodRef
                return wiser.getMessages().stream().map((w) -> toModelMail(w)).collect(Collectors.toList());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String msg = String.format("No mail. Wait for %s ms to receive %s messages.", timeout, count);
        app.log().error(msg);
        throw new Error(msg);
    }

    private static MailMessage toModelMail(WiserMessage wiserMessage) {
        try {
            MimeMessage message = wiserMessage.getMimeMessage();
            return new MailMessage(message.getAllRecipients()[0].toString(), (String) message.getContent());
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void start() {
        if (!wiser.getServer().isRunning()) {
            wiser.setPort(1026);
            wiser.start();
        }
    }

    public void stop() {
        wiser.stop();
    }
}
