package com.algaworks.algafood.infrastructure.service.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService {

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            String corpo = processarTemplate(mensagem);

            log.info("[Disparo de email] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar email", e);
        }
    }
}
