<h1 align="center">Realmeet API 📅</h1>

<p align="center">
  <em>Sistema robusto para gestão e reserva de salas de reuniões corporativas.</em>
</p>

<p align="center">
  <img src="https://img.shields.io/static/v1?message=Java&logo=java&label=&color=007396&logoColor=white&labelColor=&style=for-the-badge" alt="java badge" />
  <img src="https://img.shields.io/static/v1?message=Spring+Boot&logo=springboot&label=&color=6DB33F&logoColor=white&labelColor=&style=for-the-badge" alt="spring badge" />
  <img src="https://img.shields.io/static/v1?message=JasperReports&logo=adobeacrobatreader&label=&color=EC1C24&logoColor=white&labelColor=&style=for-the-badge" alt="jasper badge" />
  <img src="https://img.shields.io/static/v1?message=PostgreSQL&logo=postgresql&label=&color=4169E1&logoColor=white&labelColor=&style=for-the-badge" alt="postgres badge" />
</p>

<br>

## 💻 Sobre o Projeto

O **Realmeet API** é uma solução Backend desenvolvida para otimizar o uso de espaços corporativos. O sistema permite que colaboradores verifiquem a disponibilidade e reservem salas de reunião em tempo real, evitando conflitos de agenda.

Além do gerenciamento de reservas, o projeto se destaca por automatizar a comunicação via **e-mail** (confirmações) e oferecer ferramentas de gestão através de **relatórios detalhados em PDF**.

---

## ⚙️ Funcionalidades Principais

- [x] **Gestão de Salas**: Cadastro completo de salas (nome, capacidade, recursos).
- [x] **Motor de Reservas**:
  - Verificação inteligente de conflito de horários.
  - Validação de disponibilidade em tempo real.
- [x] **Sistema de Notificações**:
  - Envio assíncrono de e-mails transacionais.
  - Templates HTML personalizados para confirmação de reserva.
- [x] **Relatórios Gerenciais**:
  - Geração de relatórios em PDF utilizando **JasperReports**.
  - Exportação de dados de uso das salas e histórico de reservas.

---

## 🛠️ Tecnologias Utilizadas

Este projeto foi construído seguindo as melhores práticas de mercado e Clean Code:

**Core & Frameworks:**
* **Java 17**
* **Spring Boot 3** (Web, Validation, Data JPA)
* **Spring Mail** (Integração SMTP)
* **JasperReports** (Engine de relatórios)

**Banco de Dados:**
* **PostgreSQL** (Produção)
* **H2 Database** (Testes em memória)
* **Flyway** (Opcional - Versionamento de banco)

**Ferramentas:**
* **Docker** (Containerização do ambiente)

---

## 🚀 Pré-requisitos
* Java JDK 17+
* Maven
* Docker (para o banco de dados)
