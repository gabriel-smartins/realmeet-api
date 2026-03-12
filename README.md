<div align="center">

# 📅 Realmeet API

**Sistema robusto para gestão e alocação de salas de reuniões corporativas**

[![Java](https://img.shields.io/badge/Java_17-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot_3-6DB33F?style=flat-square&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![JasperReports](https://img.shields.io/badge/JasperReports-EC1C24?style=flat-square&logo=adobeacrobatreader&logoColor=white)](https://community.jaspersoft.com/)
[![OpenAPI](https://img.shields.io/badge/OpenAPI_3-85EA2D?style=flat-square&logo=openapiinitiative&logoColor=black)](https://swagger.io/specification/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white)](https://www.docker.com/)

</div>

---

## Sobre o Projeto

O **Realmeet API** é uma solução Backend desenvolvida para otimizar o uso de espaços corporativos. O sistema resolve um problema clássico e frequente em empresas: conflitos de agenda e má gestão na reserva de salas de reunião. 

A aplicação permite que colaboradores verifiquem a disponibilidade e aloquem salas em tempo real, garantindo que não haja sobreposição de horários. Além do motor central de alocações, o projeto se destaca pela automação da comunicação (confirmações via e-mail) e pela entrega de valor gerencial através da geração de relatórios detalhados em PDF e XML.

🌟 **Diferencial: Abordagem API First**
Este projeto foi idealizado utilizando o conceito de **API First**. Antes de qualquer linha de código Java ser escrita, todo o contrato da API foi desenhado e documentado utilizando a especificação **OpenAPI 3.0**. Isso garante um acoplamento previsível, clareza nos *schemas* de entrada e saída, e facilita imensamente a integração por parte do Front-end.

---

## Índice

- [Funcionalidades](#funcionalidades)
- [Arquitetura](#arquitetura)
- [Tecnologias](#tecnologias)
- [Endpoints da API](#endpoints-da-api)
- [Como Executar](#como-executar)
- [Estrutura de Pastas](#estrutura-de-pastas)
- [Variáveis de Ambiente](#variáveis-de-ambiente)

---

## Funcionalidades

### Gestão de Espaços e Alocações

- **Cadastro de Salas:** Gerenciamento completo dos espaços físicos, incluindo nome e capacidade máxima (assentos).
- **Motor de Validação:** Verificação inteligente de conflitos de horários no banco de dados, garantindo a integridade e exclusividade de cada alocação (*Allocation*) em tempo real.
- **Listagem Avançada:** Busca de alocações com suporte a filtros por e-mail do funcionário, sala, intervalo de datas (`startAt` / `endAt`), além de paginação e ordenação (`limit`, `page`, `orderBy`).

### Comunicação e Relatórios Gerenciais

- **Sistema de Notificações:** Envio assíncrono de e-mails transacionais utilizando o Spring Mail.
- **Relatórios Gerenciais:** Geração dinâmica de relatórios de alocações filtrados por data, com suporte aos formatos **PDF** e **XML**, enviados diretamente para o e-mail do solicitante utilizando **JasperReports**.

---

## Arquitetura e Padrões

A aplicação foi desenhada com uma separação rigorosa de responsabilidades (*Separation of Concerns*), utilizando padrões de projeto estabelecidos no ecossistema Java/Spring:

- **Mappers:** Conversão isolada entre Entidades de Domínio e DTOs, evitando vazamento do modelo de banco de dados para a camada web.
- **Validators:** Regras de negócio e validações complexas (ex: checagem de conflitos de horários, validação de requisições de relatório) são separadas dos *Services* principais, mantendo as classes coesas.
- **Report Engine:** Um módulo interno robusto dedicado à geração de relatórios (`handler`, `resolver`, `model`), isolando a complexidade do JasperReports do restante da aplicação.

---

## Tecnologias

| Categoria       | Tecnologia                 |
| --------------- | -------------------------- |
| Linguagem       | Java 17                    |
| Framework       | Spring Boot 3              |
| Banco de Dados  | MySQL                      |
| API Contract    | OpenAPI 3.0 (Swagger)      |
| Relatórios      | JasperReports              |
| E-mails         | Spring Mail (JavaMail)     |
| ORM             | Spring Data JPA / Hibernate|
| Containerização | Docker                     |

---

## Endpoints da API

> **Segurança:** Todas as rotas exigem autenticação. Envie a chave da API no cabeçalho da requisição: `api-key: <sua-chave>`.

### Salas (Rooms)

| Método | Rota             | Descrição                      |
| ------ | ---------------- | ------------------------------ |
| POST   | `/rooms`         | Cria uma nova sala             |
| GET    | `/rooms/{id}`    | Retorna os detalhes de uma sala|
| PUT    | `/rooms/{id}`    | Atualiza os dados de uma sala  |
| DELETE | `/rooms/{id}`    | Deleta uma sala pelo ID        |

### Alocações (Allocations)

| Método | Rota               | Descrição                                                                      |
| ------ | ------------------ | ------------------------------------------------------------------------------ |
| POST   | `/allocations`     | Cria uma nova alocação de sala                                                 |
| GET    | `/allocations`     | Lista alocações (Suporta paginação e filtros via *Query Params*)               |
| PUT    | `/allocations/{id}`| Atualiza os horários ou assunto de uma alocação específica                     |
| DELETE | `/allocations/{id}`| Cancela/Deleta uma alocação pelo ID                                            |

### Relatórios (Reports)

| Método | Rota                   | Descrição                                                                 |
| ------ | ---------------------- | ------------------------------------------------------------------------- |
| POST   | `/reports/allocation`  | Gera o relatório de alocações (PDF ou XML) e envia para o e-mail informado|

---

## Como Executar

### Pré-requisitos

- [Java 17 JDK](https://adoptium.net/)
- [Docker](https://www.docker.com/) (para o banco de dados)
- Maven

### Passo a passo

**1. Clone o repositório**

~~~bash
git clone https://github.com/gabriel-smartins/realmeet-api.git
cd realmeet-api
~~~

**2. Configure as variáveis de ambiente**

Crie um arquivo `.env` na raiz do projeto contendo as credenciais de banco e e-mail. Veja a seção [Variáveis de Ambiente](#variáveis-de-ambiente) abaixo.

**3. Suba o banco de dados**

Acesse a pasta de infraestrutura Docker e inicie o container do MySQL:

~~~bash
cd .docker
docker-compose up -d
~~~

**4. Inicie a aplicação**

Navegue até a pasta do serviço principal e utilize o Maven para compilar e rodar o projeto.

~~~bash
cd ../service
./mvnw spring-boot:run
~~~

A API estará disponível localmente (geralmente em `http://localhost:8080`). Você pode utilizar a especificação `openapi.yaml` no [Swagger Editor](https://editor.swagger.io/) para explorar os *schemas* detalhados de cada rota.

---

## Estrutura de Pastas

A estrutura reflete a riqueza arquitetural do projeto:

~~~text
realmeet/
│
├── .docker/                   # Arquivos e configurações de infraestrutura Docker
├── client/                    # Especificação OpenAPI e possíveis clientes gerados
│
└── service/                   # Diretório principal da API Spring Boot
    └── src/main/
        ├── java/br/com/sw2you/realmeet/
        │   ├── config/        # Configurações globais do Spring e Beans
        │   ├── controller/    # Endpoints HTTP baseados na especificação OpenAPI
        │   ├── domain/        # Entidades do banco de dados (JPA)
        │   ├── email/         # Templates e lógica de disparo de e-mails
        │   ├── exception/     # Tratamento de erros (ex: mapeamento para ResponseError 422)
        │   ├── filter/        # Filtros de requisição HTTP (ex: validação de API Key)
        │   ├── mapper/        # Mapeamento e conversão de objetos (Entidade <-> DTO)
        │   ├── report/        # Lógica do JasperReports (handlers, resolvers, validators)
        │   ├── service/       # Lógica de negócio (Allocation, Notification, Report, Room)
        │   ├── util/          # Classes utilitárias compartilhadas
        │   └── validator/     # Classes específicas de validação de regras de negócio
        │
        └── resources/         # Application.yml, templates e arquivos .jrxml
~~~

---

## Variáveis de Ambiente

Para a aplicação se conectar ao banco e enviar os e-mails de notificação, as seguintes variáveis de ambiente precisam ser configuradas:

| Variável           | Descrição                                         |
| ------------------ | ------------------------------------------------- |
| `DB_ROOT_PASSWORD` | Senha root do banco de dados (MySQL)              |
| `DB_USER`          | Nome do usuário do banco de dados                 |
| `DB_PASSWORD`      | Senha do usuário do banco de dados                |
| `DB_NAME`          | Nome do banco de dados (`realmeet` ou similar)    |
| `MAIL_EMAIL`       | Endereço de e-mail remetente utilizado para envios|
| `MAIL_PASSWORD`    | Senha do e-mail (ou *App Password* gerado)        |

---

<div align="center">

Desenvolvido por Gabriel. Focado em arquitetura API First, Java robusto e automação corporativa.

</div>
