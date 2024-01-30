package com.vicayala.demotravel.infraestructure.helpers;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class EmailHelper {

    private final Path TEMPLATE_PATH = Paths.get("src/main/resources/email/email_template.html");
    private final JavaMailSender mailSender;

    public void sendMail(String to, String name, String product){
        MimeMessage message = mailSender.createMimeMessage();
        String htmlContent = this.readHtmlTemplate(name, product);

        try {
            message.setFrom(new InternetAddress("victor.ayalamarin@gmail.com"));
            message.setRecipients(MimeMessage.RecipientType.TO, to);
            message.setContent(htmlContent, MediaType.TEXT_HTML_VALUE);
            mailSender.send(message);
        }catch (MessagingException mexc){
            log.error("Error to Send Mail" + ExceptionUtils.getStackTrace(mexc));
        }
    }

    private String readHtmlTemplate(String name, String product){
        try (var lines = Files.lines(TEMPLATE_PATH)){
            var html = lines.collect(Collectors.joining());
            return html.replace("{name}", name).replace("{product}", product);

        }catch (IOException ioe){
            log.error("Cant read html template", ExceptionUtils.getStackTrace(ioe));
            throw new RuntimeException();
        }
    }
}
