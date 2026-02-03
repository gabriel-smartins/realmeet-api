package br.com.sw2you.realmeet.email;

import br.com.sw2you.realmeet.email.model.Attachment;
import br.com.sw2you.realmeet.email.model.EmailInfo;
import br.com.sw2you.realmeet.exception.EmailSendingException;
import br.com.sw2you.realmeet.util.StringUtils;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.activation.DataHandler;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);
    private static final String TEXT_HTML_CHARSET_UTF_8 = "text/html;charset=UTF-8";

    private final JavaMailSender javaMailSender;
    private final ITemplateEngine templateEngine;

    public EmailSender(JavaMailSender javaMailSender, ITemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void send(EmailInfo emailInfo) {
        LOGGER.info("Sending e-mail with subject: '{}' to '{}'", emailInfo.getSubject(), emailInfo.getTo());

        var mimeMessage = javaMailSender.createMimeMessage();
        var multipart = new MimeMultipart();

        addBasicDetails(emailInfo, mimeMessage);
        addHtmlBody(emailInfo.getTemplate(), emailInfo.getTemplateData(), multipart);
        addAttachments(emailInfo.getAttachments(), multipart);
        setContent(mimeMessage, multipart);

        javaMailSender.send(mimeMessage);
    }


    private void addBasicDetails(EmailInfo emailInfo, MimeMessage mimeMessage) {
        try {
            mimeMessage.setFrom(emailInfo.getFrom());
            mimeMessage.setSubject(emailInfo.getSubject());
            mimeMessage.addRecipients(Message.RecipientType.TO, StringUtils.join(emailInfo.getTo()));

            if (Objects.nonNull(emailInfo.getCc())) {
                mimeMessage.addRecipients(Message.RecipientType.CC, StringUtils.join(emailInfo.getCc()));
            }

            if (Objects.nonNull(emailInfo.getBcc())) {
                mimeMessage.addRecipients(Message.RecipientType.BCC, StringUtils.join(emailInfo.getBcc()));
            }
        } catch (MessagingException e) {
            throwEmailSendingException(e, "Error adding data to MIME message");
        }
    }

    private void addHtmlBody(String template, Map<String, Object> templateData, MimeMultipart multipart) {
        var messageHtmlPart = new MimeBodyPart();
        var context = new Context();

        if (Objects.nonNull(templateData)) {
            context.setVariables(templateData);
        }

        try {
            messageHtmlPart.setContent(templateEngine.process(template, context), TEXT_HTML_CHARSET_UTF_8);
            multipart.addBodyPart(messageHtmlPart);
        } catch (MessagingException e) {
            throwEmailSendingException(e, "Error adding HTML content to MIME message");
        }
    }

    private void addAttachments(List<Attachment> attachments, MimeMultipart multipart) {
        Optional.ofNullable(attachments).ifPresent(list -> {
            list.forEach(a -> {
                try {
                    var messageAttachmentPart = new MimeBodyPart();
                    messageAttachmentPart.setDataHandler(new DataHandler(new ByteArrayDataSource(a.getInputStream(), a.getContentType())));
                    messageAttachmentPart.setFileName(a.getFileName());
                    multipart.addBodyPart(messageAttachmentPart);
                } catch (MessagingException | IOException e) {
                    throwEmailSendingException(e, "Error adding attachments to MIME message");
                }
            });
        });
    }

    private void setContent(MimeMessage mimeMessage, MimeMultipart multipart) {
        try {
            mimeMessage.setContent(multipart);
        } catch (MessagingException e) {
            throwEmailSendingException(e, "Error setting content to MIME message");
        }
    }

    private void throwEmailSendingException(Exception e, String errorMessage) {
        var fullErrorMessage = String.format("%s: %s", e.getMessage(), errorMessage);
        LOGGER.error(fullErrorMessage);
        throw new EmailSendingException(fullErrorMessage, e);
    }
}
