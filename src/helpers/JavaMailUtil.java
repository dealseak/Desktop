package helpers;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class JavaMailUtil {

    public static void sendMail(String recepient,String subject,String content) throws Exception {
        System.out.println("Preparing to send Mail ...");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        String myAccountEmail="XXDEVEMAIL@Gmail.com";
        String password="4CDP25fun";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(myAccountEmail,password);
            }
        });
        Message message = prepareMessage(session,myAccountEmail,recepient,subject,content);
        Transport.send(message);
        System.out.println("Mail Sent Successfully!");
    }

    private static Message prepareMessage(Session session,String myAccountEmail,String recepient,String subject,String content){

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recepient));
            message.setSubject(subject);
            message.setText(content);
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }



}