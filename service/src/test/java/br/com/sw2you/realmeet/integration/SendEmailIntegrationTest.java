package br.com.sw2you.realmeet.integration;

import static org.mockito.ArgumentMatchers.eq;

import br.com.sw2you.realmeet.core.BaseIntegrationTest;
import br.com.sw2you.realmeet.email.EmailSender;
import br.com.sw2you.realmeet.email.model.EmailInfo;
import br.com.sw2you.realmeet.utils.TestUtils;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SendEmailIntegrationTest extends BaseIntegrationTest {

    private static final String EMAIL_ADDRESS = "gabrielsilvamartins1500@gmail.com";
    private static final String SUBJECT = "subject";
    private static final String EMAIL_TEMPLATE = "template-test.html";

    @Autowired
    private EmailSender victim;

    @MockitoBean
    private JavaMailSender javaMailSender;

    @MockitoBean
    private MimeMessage mimeMessage;

    @Test
    void sendEmailTest() {
        Mockito.when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        var emailInfo = EmailInfo
                .newBuilder()
                .from(EMAIL_ADDRESS)
                .to(List.of(EMAIL_ADDRESS))
                .subject(SUBJECT)
                .template(EMAIL_TEMPLATE)
                .templateData(Map.of("param", "some text"))
                .build();
        victim.send(emailInfo);

        TestUtils.sleep(2000);
        Mockito.verify(javaMailSender).send(eq(mimeMessage));

    }
}
