# Microsserviço Notificador

Microsserviço responsável pelo envio de notificações por email para os usuários.

## 📝 Descrição do Projeto

Este serviço processa e envia alertas de lembretes de tarefas e confirmações de cadastro utilizando o Mailtrap (para desenvolvimento) ou SMTP (para produção).

Consulte o [BFF-Agendador](https://github.com/Ja0Santana/BFF-Agendador) para visualizar o sistema completo.

## 🐋 Docker Hub - Imagem Oficial

```bash
docker pull joaopaul0/api-notificador:latest
```

## 🛠️ Tecnologias e Ferramentas

- Java 17+ & Spring Boot 3
- Spring Mail (Integração SMTP)
- Mailtrap (Testes de email)
- Docker
- SonarQube

## 🔐 Segurança

- **Environment Variables**: Todas as credenciais de SMTP e Mailtrap estão protegidas via variáveis de ambiente.
- **Isolação**: O serviço funciona de forma desacoplada, focado exclusivamente no processamento de mensagens.

## ⚙️ Variáveis de Ambiente

Copie o arquivo `.env.example` para `.env` e preencha com seus valores:

```bash
cp .env.example .env
```

| Variável | Descrição | Exemplo |
|---|---|---|
| `MAIL_HOST` | Host do servidor SMTP | `smtp.mailtrap.io` |
| `MAIL_PORT` | Porta do servidor SMTP | `2525` |
| `MAIL_USER` | Usuário do SMTP | `usuario` |
| `MAIL_PASS` | Senha do SMTP | `senha` |

## 🚀 Como Funciona

O microsserviço recebe solicitações de envio de email e as processa utilizando as configurações de SMTP fornecidas. É ideal para integração com filas ou chamadas diretas via Feign Client.

## 🚦 Como Rodar

### Via Docker (Recomendado)

```bash
docker-compose up --build
```

### Localmente

1. Clone o repositório:
```bash
git clone https://github.com/Ja0Santana/notificador.git
```

2. Configure o `.env` e rode:
```bash
./gradlew bootRun
```

## 🛡️ Qualidade

- SOLID
- Clean Code
- CI/CD ready via GitHub Actions + SonarQube
