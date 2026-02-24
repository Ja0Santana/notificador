<div align="center">
  <h1>Notification Service - Agendador de Tarefas</h1>
  <p><i>Microsserviço responsável pelo processamento e disparo de alertas de tarefas via E-mail.</i></p>
</div>

<hr>

<h2>📝 Descrição do Projeto</h2>
<p>Este serviço atua na camada de saída do ecossistema, processando as notificações geradas pelo sistema de agendamento. Atualmente, o serviço está integrado ao <b>Mailtrap</b> para a captura e teste de e-mails em ambiente de desenvolvimento.</p>

<p><b>Este serviço é parte integrante de uma arquitetura de microsserviços.</b> Para visualizar a orquestração completa, acesse:</p>
<p>🔗 <b>BFF Orquestrador:</b> <a href="https://github.com/Ja0Santana/BFF-Agendador">github.com/Ja0Santana/BFF-Agendador</a></p>

<hr>

<h2>🐋 Docker Hub - Imagem Oficial</h2>
<p>A imagem isolada deste serviço pode ser obtida via:</p>
<pre><code>docker pull joaopaul0/api-notificador:latest</code></pre>

<hr>

<h2>📧 Configuração de Notificações (Mailtrap)</h2>
<p>Por padrão, as notificações por e-mail são enviadas utilizando o <b>Mailtrap</b> como servidor SMTP de teste. Para que os disparos ocorram corretamente, é necessário configurar as seguintes credenciais no seu arquivo <code>application.yaml</code>:</p>

<pre><code>
spring:
  mail:
    host: sandbox.smtp.mailtrap.io
    port: 2525
    username: ${MAILTRAP_USER}
    password: ${MAILTRAP_PASSWORD}
</code></pre>

<hr>

<h2>🛠️ Tecnologias e Ferramentas</h2>
<ul>
  <li><b>Java 17+ & Spring Boot 3</b></li>
  <li><b>Spring Mail</b> (Integração SMTP)</li>
  <li><b>Mailtrap</b> (Servidor de e-mail fake para testes)</li>
  <li><b>Docker</b> (Conteinerização completa do serviço)</li>
  <li><b>SonarQube</b> (Monitoramento de qualidade e cobertura)</li>
</ul>

<hr>

<h2>🛡️ Qualidade e Engenharia</h2>
<ul>
  <li><b>SOLID & Clean Code:</b> Arquitetura preparada para fácil substituição do provider de e-mail (ex: AWS SES ou SendGrid).</li>
  <li><b>Environment Variables:</b> Suporte a variáveis de ambiente para proteção de credenciais sensíveis.</li>
  <li><b>Análise Estática:</b> Validação rigorosa via SonarQube para garantir zero vulnerabilidades críticas.</li>
</ul>

<hr>

<h2>🚦 Como Rodar Localmente</h2>
<ol>
  <li>Clone o repositório: <code>git clone https://github.com/Ja0Santana/notificador.git</code></li>
  <li>Insira suas credenciais do Mailtrap no <code>application.yaml</code>.</li>
  <li>Execute: <code>./gradlew bootRun</code></li>
</ol>

<hr>
