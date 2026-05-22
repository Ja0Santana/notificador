package com.joaopaulo.notificador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaopaulo.notificador.business.EmailService;
import com.joaopaulo.notificador.business.dto.TarefaDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmailController.class)
@SuppressWarnings("null")
class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve retornar 200 ao disparar envio de email")
    void deveRetornar200AoEnviarEmail() throws Exception {
        TarefaDTO dto = TarefaDTO.builder()
                .emailUsuario("teste@email.com")
                .nomeTarefa("Tarefa Teste")
                .build();

        mockMvc.perform(post("/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(emailService).enviarEmail(any(TarefaDTO.class));
    }
}


