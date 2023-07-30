package com.algaworks.algafood.core.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.FakeEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.SandboxEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.algaworks.algafood.core.email.EmailProperties.TipoImplEnvioEmail;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {
        if (emailProperties.getImpl().equals(TipoImplEnvioEmail.SMTP)) {
            return new SmtpEnvioEmailService();
        }

        if (emailProperties.getImpl().equals(TipoImplEnvioEmail.SANDBOX)) {
            return new SandboxEnvioEmailService();
        }

        return new FakeEnvioEmailService();
    }
}
