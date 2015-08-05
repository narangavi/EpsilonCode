package com.calix.impl;

/**
 * Created by vkesavan on 3/17/15.
 */

import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component(name = "com.calix.impl.EmailService",
        metatype = true, immediate = true, label = "Calix Email service",
        description = "Email service")
@Service(EmailService.class)
public class EmailService {
    public static ArrayList<InternetAddress> convertStringListToAddressList(List<String> emailList) {
        Logger log = LoggerFactory.getLogger(EmailService.class);
        ArrayList addressList = new ArrayList();

        for (String email : emailList) {
            try {
                InternetAddress address = new InternetAddress(email);
                addressList.add(address);
            } catch (AddressException e) {
                log.warn(new StringBuilder().append("convertStringListToAddressList(): Email could not be instantiated: <").append(email).append(">").toString());
            }
        }

        return addressList;
    }

    public static boolean sendEmail(MessageGatewayService messageGatewayService, ArrayList<InternetAddress> recipients, String sender, String subject, String messageBody) {
        return sendEmail(messageGatewayService, recipients, sender, subject, messageBody, null);
    }

    public static boolean sendEmail(MessageGatewayService messageGatewayService, ArrayList<InternetAddress> recipients, String sender, String subject, String messageBody, String htmlMessageBody) {
        Logger log = LoggerFactory.getLogger(EmailService.class);
        boolean success = false;

        HtmlEmail email = new HtmlEmail();
        try {
            email.setTo(recipients);
            email.setFrom(sender);
            email.setSubject(subject);
            email.setMsg(messageBody);
            if ((htmlMessageBody != null) && (!htmlMessageBody.equals(""))) {
                email.setHtmlMsg(htmlMessageBody);
            }
            MessageGateway messageGateway = messageGatewayService.getGateway(HtmlEmail.class);
            if (messageGateway == null) {
                log.error("sendEmail(): ERROR Mail Service does not appear to be configured properly.");
                return success;
            }
            messageGateway.send(email);
            log.info(new StringBuilder().append("sendEmail(): Sending email notification with subject '").append(subject).append("' ...").toString());
            success = true;
        } catch (EmailException e) {
            log.error(new StringBuilder().append("sendEmail(): Sending email failed. (").append(e.toString()).append(")").toString());
        }

        return success;
    }

    public static boolean isOkToSendEmail(String filename, int minBetweenEmails)
            throws IOException {
        boolean okToSend = false;
        String filePath = new StringBuilder().append(System.getProperty("java.io.tmpdir")).append(File.separator).append(filename).toString();

        File counterFile = new File(filePath);
        boolean counterFileExists = counterFile.exists();
        String currCtr = "";
        if (counterFileExists) {
            StringBuilder contents = new StringBuilder();
            BufferedReader input = new BufferedReader(new FileReader(filePath));
            try {
                String line = null;
                if ((line = input.readLine()) != null)
                    contents.append(line);
            } finally {
                input.close();
            }
            currCtr = contents.toString();
        }

        int ctr = counterFileExists ? Integer.valueOf(currCtr).intValue() : 0;
        ctr++;
        if ((ctr < 1) || (ctr >= minBetweenEmails)) {
            okToSend = true;
            currCtr = "0";
        } else {
            currCtr = String.valueOf(ctr);
        }

        Writer output = null;
        try {
            output = new BufferedWriter(new FileWriter(filePath));
            output.write(currCtr);
        } finally {
            if (output != null) {
                output.close();
            }
        }

        return okToSend;
    }
}