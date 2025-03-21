package com.rbc.util;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import net.minidev.json.JSONObject;
import org.slf4j.LoggerFactory;

import jakarta.mail.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import static com.rbc.util.EmailInlineText.emailInlineHTMLText;
import static com.rbc.util.SmtpConfig.*;

public class SendEmail {

    private String filePath;
    private String env;
    private JSONObject jsObj;
    private String tags;
    private String runStatus = "SUCCESS";
    private int maxAttachmentSizeInMb = 15;
    private String browserType;

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(SendEmail.class);

    public SendEmail(String filePath, JSONObject jsonObject, String env, String tags, String browserType) {
        this.filePath = filePath;
        this.env = env;
        this.jsObj = jsonObject;
        this.tags = tags;
        this.browserType = browserType;
    }

    public void sendEmail() {
        SmtpConfig smtpConfig = new SmtpConfig();
        try {
            String emailBody = emailInlineHTMLText().toString();
            //Extracting execution details from results-json under cucumber-html-reports
            Integer featuresPassed = (Integer) jsObj.get("featuresPassed");
            Integer featuresFailed = (Integer) jsObj.get("featuresFailed");
            Integer featuresSkipped = (Integer) jsObj.get("featuresSkipped");
            Integer featuresTotal = featuresPassed + featuresFailed + featuresSkipped;

            Integer scenarioPassed = (Integer) jsObj.get("scenariosPassed");
            Integer scenarioFailed = (Integer) jsObj.get("scenariosFailed");

            Integer scenariosTotal = scenarioPassed + scenarioFailed;

            Boolean isFailed = false;
            if (scenarioFailed > 0) {
                this.runStatus = "FAIL";
                emailBody = emailBody.replace("<runStatusColor>", "red");
            } else if (scenariosTotal.intValue() == 0) {
                this.runStatus = "No Run";
                emailBody = emailBody.replace("<runStatusColor>", "goldenrod");
            } else {
                emailBody = emailBody.replace("<runStatusColor>", "green");
            }

            Map<String, String> propValues = smtpConfig.getPropValues();
            LOGGER.info("properties values: " + propValues);
            Properties props = System.getProperties();
            props.put("mail.smtp.host", propValues.get(HOST));
            Session session = Session.getInstance(props, null);
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(propValues.get(FROM), ""));
            msg.setReplyTo(InternetAddress.parse(propValues.get(TO), false));
            msg.setSubject("Automated Test Resutls - " + propValues.get(SUBJECT) + " _ " + env + " _ " + this.runStatus, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(propValues.get(TO), false));

            //Replacing the html text with actual Values
            emailBody = emailBody.replace("<environment>", env)
                    .replace("<tags>", tags)
                    .replace("<runStatus>", runStatus)
                    .replace("<projectName>", propValues.get(SUBJECT))
                    .replace("<featuresPassed>", featuresPassed.toString())
                    .replace("<featuresFailed>", featuresFailed.toString())
                    .replace("<featuresSkipped>", featuresSkipped.toString())
                    .replace("<featuresTotal>", featuresTotal.toString())
                    .replace("<scenariosPassed>", scenarioPassed.toString())
                    .replace("<scenariosFailed>", scenarioFailed.toString())
                    .replace("<scenariosTotal>", scenariosTotal.toString())
                    .replace("<browserType>", this.browserType.toUpperCase())
                    .replace("<repoURL>", propValues.get(REPOURL));

            //Adding email body
            Multipart multipart = new MimeMultipart();

            //Do not attach reports if its larger than the allowed limit
            if (!propValues.get(MAXATTACHMENTSIZE).trim().isEmpty()) {
                maxAttachmentSizeInMb = Integer.parseInt(propValues.get(MAXATTACHMENTSIZE).trim());
            }

            String reportAttachmentFile = filePath;
            if (Files.size(Paths.get(reportAttachmentFile)) <= maxAttachmentSizeInMb * 1024 * 1024) {
                BodyPart messageBodyPart1 = new MimeBodyPart();
                FileDataSource source = new FileDataSource(reportAttachmentFile);
                messageBodyPart1.setDataHandler(new DataHandler(source));
                messageBodyPart1.setFileName("results.zip");
                multipart.addBodyPart(messageBodyPart1);
                emailBody = emailBody.replace("<attachmentNote>", "Please refer the attached report for execution results.");
            } else {
                emailBody = emailBody.replace("<attachmentNote>", "Note - Execution resutls were not attached since it exceeds the max allowed size <b> " + maxAttachmentSizeInMb);
            }

            BodyPart messageBodyPart2 = new MimeBodyPart();
            messageBodyPart2.setContent(emailBody, "text/HTML");
            multipart.addBodyPart(messageBodyPart2);

            msg.setContent(multipart, "text/HTML");

            LOGGER.info("Message ready");
            Transport.send(msg);

            LOGGER.info("Email sent successfully!!");

        } catch (IOException | MessagingException e) {
            System.out.println("Exception Occurred : " + e);

        }

    }
}
