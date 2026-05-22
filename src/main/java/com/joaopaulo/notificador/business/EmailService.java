package com.joaopaulo.notificador.business;

import com.joaopaulo.notificador.business.dto.TarefaDTO;
import com.joaopaulo.notificador.business.infrastructure.exceptions.EmailException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${envio.email.remetente:paulo.jp806@gmail.com}")
    private String remetente;

    @Value("${envio.email.nomeRemetente:João Paulo Santana}")
    private String nomeRemetente;

    public void enviarEmail(TarefaDTO tarefaDTO) {
        try{
            MimeMessage mensagem = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMensagemHelper = new MimeMessageHelper(mensagem, true, StandardCharsets.UTF_8.name());

            mimeMensagemHelper.setFrom(new InternetAddress(remetente, nomeRemetente));
            mimeMensagemHelper.setTo(tarefaDTO.getEmailUsuario());
            
            String assunto = "Notificação de Tarefa";
            String templateName = "notificacao";

            if (tarefaDTO.getStatusNotificacao() != null && 
                tarefaDTO.getStatusNotificacao().equals(com.joaopaulo.notificador.business.enums.StatusNotificacao.VENCIDA)) {
                assunto = "Sua Tarefa Venceu - Plannit Pro";
                templateName = "vencimento";
            }

            mimeMensagemHelper.setSubject(assunto);

            Context context = new Context();
            context.setVariable("nomeTarefa", tarefaDTO.getNomeTarefa());
            context.setVariable("dataEvento", tarefaDTO.getDataEvento());
            context.setVariable("descricao", tarefaDTO.getDescricaoTarefa());
            String template = templateEngine.process(templateName, context);
            mimeMensagemHelper.setText(template, true);
            javaMailSender.send(mensagem);
        } catch (Exception e) {
            throw new EmailException("Erro ao enviar email de notificação", e);
        }
    }
}


