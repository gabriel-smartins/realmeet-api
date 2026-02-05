package br.com.sw2you.realmeet.integration;

import static br.com.sw2you.realmeet.util.Constants.ALLOCATION;
import static br.com.sw2you.realmeet.utils.TestDataCreator.newAllocationBuilder;
import static br.com.sw2you.realmeet.utils.TestDataCreator.newRoomBuilder;

import br.com.sw2you.realmeet.config.properties.EmailConfigProperties;
import br.com.sw2you.realmeet.config.properties.TemplateConfigProperties;
import br.com.sw2you.realmeet.core.BaseIntegrationTest;
import br.com.sw2you.realmeet.email.EmailSender;
import br.com.sw2you.realmeet.email.TemplateType;
import br.com.sw2you.realmeet.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public class NotificationServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private NotificationService victim;

    @MockitoBean
    private EmailSender emailSender;

    @Autowired
    private TemplateConfigProperties templateConfigProperties;

    @Autowired
    private EmailConfigProperties emailConfigProperties;

    @Test
    void testNotifyAllocationCreated() {
        var room = newRoomBuilder().build();
        var allocation = newAllocationBuilder(room).build();
        victim.notifyAllocationCreated(allocation);

        Mockito.verify(emailSender).send(ArgumentMatchers.argThat(emailInfo ->
                        emailInfo.getSubject()
                                .equals(templateConfigProperties.getEmailTemplate(TemplateType.ALLOCATION_CREATED).getSubject()) &&
                                emailInfo.getTo().getFirst().equals(allocation.getEmployee().getEmail()) &&
                                emailInfo.getFrom().equals(emailConfigProperties.getFrom()) &&
                                emailInfo.getTemplate().equals(templateConfigProperties.getEmailTemplate(TemplateType.ALLOCATION_CREATED).getTemplateName()) &&
                                emailInfo.getTemplateData().get(ALLOCATION).equals(allocation)
                )
        );
    }

    @Test
    void testNotifyAllocationUpdated() {
        var room = newRoomBuilder().build();
        var allocation = newAllocationBuilder(room).build();
        victim.notifyAllocationUpdated(allocation);

        Mockito.verify(emailSender).send(ArgumentMatchers.argThat(emailInfo ->
                        emailInfo.getSubject()
                                .equals(templateConfigProperties.getEmailTemplate(TemplateType.ALLOCATION_UPDATED).getSubject()) &&
                                emailInfo.getTo().getFirst().equals(allocation.getEmployee().getEmail()) &&
                                emailInfo.getFrom().equals(emailConfigProperties.getFrom()) &&
                                emailInfo.getTemplate().equals(templateConfigProperties.getEmailTemplate(TemplateType.ALLOCATION_UPDATED).getTemplateName()) &&
                                emailInfo.getTemplateData().get(ALLOCATION).equals(allocation)
                )
        );
    }

    @Test
    void testNotifyAllocationDeleted() {
        var room = newRoomBuilder().build();
        var allocation = newAllocationBuilder(room).build();
        victim.notifyAllocationDeleted(allocation);

        Mockito.verify(emailSender).send(ArgumentMatchers.argThat(emailInfo ->
                        emailInfo.getSubject()
                                .equals(templateConfigProperties.getEmailTemplate(TemplateType.ALLOCATION_DELETED).getSubject()) &&
                                emailInfo.getTo().getFirst().equals(allocation.getEmployee().getEmail()) &&
                                emailInfo.getFrom().equals(emailConfigProperties.getFrom()) &&
                                emailInfo.getTemplate().equals(templateConfigProperties.getEmailTemplate(TemplateType.ALLOCATION_DELETED).getTemplateName()) &&
                                emailInfo.getTemplateData().get(ALLOCATION).equals(allocation)
                )
        );
    }
}
