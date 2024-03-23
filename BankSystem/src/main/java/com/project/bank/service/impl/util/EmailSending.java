package com.project.bank.service.impl.util;

import com.project.bank.util.ResourceManager;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * EmailSending has methods for sending email.
 */
public class EmailSending {

    public static final String EMAIL_USER = "mail.smtp.user";
    public static final String EMAIL_PASSWORD = "mail.smtp.passwordUniq";
    private static final String PATH_FILE_EMAIL_PROPERTY = "src/main/resources/email.yaml";

    /**
     * The method for sending email with attachment.
     * @param to  recipient
     * @param sub subject email
     * @param msg message email
     * @param pathFile  path to file for email attachment.
     */
    public static void send(String to, String sub, String msg, String pathFile) {

        try {
            ResourceManager resourceManager = ResourceManager.getInstance();
            resourceManager.loadPropertiesFromFile(PATH_FILE_EMAIL_PROPERTY);

            Session session = Session.getDefaultInstance(resourceManager.getData(),
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(resourceManager.getValue(EMAIL_USER),
                                        resourceManager.getValue(EMAIL_PASSWORD));
                        }
                    });
                MimeMessage message = new MimeMessage(session);
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject(sub);
                message.setText(msg);

                BodyPart messageBodyPart1 = new MimeBodyPart();
                messageBodyPart1.setText(msg);

                MimeBodyPart messageBodyPart2 = new MimeBodyPart();

                DataSource source = new FileDataSource(pathFile);
                messageBodyPart2.setDataHandler(new DataHandler(source));
                messageBodyPart2.setFileName(pathFile);

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart1);
                multipart.addBodyPart(messageBodyPart2);

                message.setContent(multipart);

                Transport.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


