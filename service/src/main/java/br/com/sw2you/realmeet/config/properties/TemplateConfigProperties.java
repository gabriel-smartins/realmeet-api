package br.com.sw2you.realmeet.config.properties;


import br.com.sw2you.realmeet.config.properties.model.EmailTemplate;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "realmeet.email.template")
public class TemplateConfigProperties {

    private final Map<String, EmailTemplate> emailTemplateMap;

    @ConstructorBinding
    public TemplateConfigProperties(Map<String, EmailTemplate> emailTemplateMap) {
        this.emailTemplateMap = emailTemplateMap;
    }

    public EmailTemplate getEmailTemplate(String property) {
        return emailTemplateMap.get(property);
    }
}
