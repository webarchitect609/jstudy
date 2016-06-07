package ru.webarch.jstudy.mantis.appmanager;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FtpHelper {
    private FTPClient ftp;
    private ApplicationManager app;

    @SuppressWarnings("WeakerAccess")
    public FtpHelper(ApplicationManager app) {
        this.app = app;
        ftp = new FTPClient();
    }

    public void upload(File file, String target, String backup) throws IOException {
        app.log().debug("Работа с FTP отключена, т.к. мешает отладке");
//        ftpConnect();
//        ftp.deleteFile(backup);
//        ftp.rename(target, backup);
//        ftp.enterLocalPassiveMode();
//        ftp.storeFile(target, new FileInputStream(file));
//        ftp.disconnect();
    }

    public void restore(String backup, String target) throws IOException {
        app.log().debug("Работа с FTP отключена, т.к. мешает отладке");
//        ftpConnect();
//        ftp.deleteFile(target);
//        ftp.rename(backup, target);
//        ftp.disconnect();
    }

    private void ftpConnect() throws IOException {
        ftp.connect(app.getProperty("ftp.host"));
        ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
        ftp.changeWorkingDirectory(app.getProperty("ftp.workdir"));
    }

}
