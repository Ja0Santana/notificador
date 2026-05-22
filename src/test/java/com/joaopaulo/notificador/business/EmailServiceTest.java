package com.joaopaulo.notificador.business;

import com.joaopaulo.notificador.business.dto.TarefaDTO;
import com.joaopaulo.notificador.business.enums.StatusNotificacao;
import com.joaopaulo.notificador.business.infrastructure.exceptions.EmailException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(emailService, "remetente", "teste@email.com");
        ReflectionTestUtils.setField(emailService, "nomeRemetente", "Nome Teste");
    }

    @Test
    @DisplayName("Deve enviar email comum usando template de notificacao")
    void deveEnviarEmailComum() {
        TarefaDTO request = TarefaDTO.builder()
                .emailUsuario("destinatario@email.com")
                .nomeTarefa("Consertar PC")
                .statusNotificacao(StatusNotificacao.PENDENTE)
                .build();

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(eq("notificacao"), any(Context.class))).thenReturn("<html>Conteudo</html>");

        emailService.enviarEmail(request);

        verify(templateEngine).process(eq("notificacao"), any(Context.class));
        verify(javaMailSender).send(any(MimeMessage.class));
    }

    @Test
    @DisplayName("Deve enviar email de vencimento quando status for VENCIDA")
    void deveEnviarEmailVencimento() {
        TarefaDTO request = TarefaDTO.builder()
                .emailUsuario("destinatario@email.com")
                .statusNotificacao(StatusNotificacao.VENCIDA)
                .build();

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(eq("vencimento"), any(Context.class))).thenReturn("<html>Venceu</html>");

        emailService.enviarEmail(request);

        verify(templateEngine).process(eq("vencimento"), any(Context.class));
        verify(javaMailSender).send(any(MimeMessage.class));
    }

    @Test
    @DisplayName("Deve lançar EmailException em caso de erro técnico no envio")
    void deveLancarEmailExceptionEmCasoDeErro() {
        TarefaDTO request = TarefaDTO.builder().emailUsuario("erro@email.com").build();
        
        when(javaMailSender.createMimeMessage()).thenThrow(new RuntimeException("Falha no servidor"));

        assertThatThrownBy(() -> emailService.enviarEmail(request))
                .isInstanceOf(EmailException.class)
                .hasMessageContaining("Erro ao enviar email");
    }
}


