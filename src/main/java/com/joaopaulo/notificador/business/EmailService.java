package com.joaopaulo.notificador.business;

import com.joaopaulo.notificador.business.dto.TarefaDTO;
import com.joaopaulo.notificador.business.infrastructure.exceptions.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${envio.email.remetente}")
    private String remetente;

    @Value("${envio.email.nomeRemetente}")
    private String nomeRemetente;

    public void enviarEmail(TarefaDTO tarefaDTO) {
        try{
            MimeMessage mensagem = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMensagemHelper = new MimeMessageHelper(mensagem, true, StandardCharsets.UTF_8.name());

            mimeMensagemHelper.setFrom(new InternetAddress(remetente, nomeRemetente));
            mimeMensagemHelper.setTo(InternetAddress.parse(tarefaDTO.getEmailUsuario()));
            mimeMensagemHelper.setSubject("Notificação de Tarefa");

            Context context = new Context();
            context.setVariable("nomeTarefa", tarefaDTO.getNomeTarefa());
            context.setVariable("dataEvento", tarefaDTO.getDataEvento());
            context.setVariable("descricao", tarefaDTO.getDescricaoTarefa());
            String template = templateEngine.process("notificacao", context);
            mimeMensagemHelper.setText(template, true);
            javaMailSender.send(mensagem);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new EmailException("Erro ao enviar email de notificação", e);
        }
    }
}
