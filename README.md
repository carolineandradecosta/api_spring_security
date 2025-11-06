# 🔒 Spring Security | API de Autenticação e Autorização JWT

![Badge de Estudo](https://img.shields.io/badge/Tipo-Projeto%20de%20Estudo-blueviolet)
![Badge de Linguagem](https://img.shields.io/badge/Linguagem-Java%2017+-orange)
![Badge de Framework](https://img.shields.io/badge/Framework-Spring%20Boot%203.x-brightgreen)
![Badge de Criptografia](https://img.shields.io/badge/Criptografia-BCrypt-red)
![Badge de Licença](https://img.shields.io/badge/Licen%C3%A7a-MIT-blue)

## 🌟 Visão Geral e Destaques Técnicos

Este projeto é uma **API RESTful** desenvolvida com o ecossistema Spring, que implementa uma solução completa de **Autenticação e Autorização Stateless**. O projeto foca em fornecer um modelo de segurança robusto e escalável, utilizando as seguintes práticas e tecnologias:

* **Autenticação Stateless (JWT):** Utiliza **JSON Web Tokens (JWT)** para gerenciamento de sessões sem estado. O token é assinado e validado em cada requisição, garantindo a escalabilidade da API.
* **Segurança de Credenciais:** As senhas dos usuários são protegidas usando o algoritmo **BCrypt** (`BCryptPasswordEncoder`), uma prática essencial para armazenamento seguro de credenciais.
* **Controle de Acesso Fino (RBAC/ACL):**
    * Implementa **Autorização Baseada em Papéis (RBAC)**, com os papéis `ADMIN` e `USER`.
    * Utiliza a anotação **`@PreAuthorize`** com **`@EnableMethodSecurity`** para aplicar regras de segurança diretamente nos métodos dos *Controllers*, permitindo um controle de acesso mais granular.
* **Arquitetura Extensível:** A segurança é gerenciada por um **Filtro JWT customizado** (`JwtAuthenticationFilter`), que se integra à cadeia de filtros do Spring Security.

---

## 💻 Stack Tecnológica e Padrões Utilizados

| Categoria | Tecnologia | Padrões/Técnicas Utilizadas |
| :--- | :--- | :--- |
| **Backend** | **Java 17+** | Uso de **Records** para DTOs (melhor legibilidade e imutabilidade). |
| **Framework** | **Spring Boot 3.x** | Injeção de Dependência, Autoconfiguração e Configuração de Beans. |
| **Segurança** | **Spring Security** | `SecurityFilterChain`, `AuthenticationManager` e **`SessionCreationPolicy.STATELESS`**. |
| **Criptografia** | **BCryptPasswordEncoder** | Hashing seguro de senhas. |
| **Autorização** | **`@PreAuthorize`** | **Autorização no Nível de Método** (RBAC/ACL). |
| **Comunicação** | **CORS** | Configuração segura do CORS para integração com *frontends* de origens específicas. |
| **Documentação** | **OpenAPI/Swagger** | Endpoints de documentação liberados para consumo e teste da API. |

---

## 🔑 Arquitetura de Segurança e Fluxo de Autenticação

O projeto segue o padrão de segurança baseado em JWT/Bearer Token, com foco na separação de responsabilidades.

### 1. Rotas Públicas (Configuração no `SecurityConfig`)
As rotas de autenticação e documentação são liberadas antes da cadeia de segurança principal:
* `POST /auth/login`
* `POST /auth/register`
* Rotas do Swagger (`/v3/api-docs/**`, `/swagger-ui/**`)

### 2. Fluxo de Autenticação
* O **`AuthService`** utiliza o **`BCryptPasswordEncoder`** para criptografar senhas antes de salvar.
* No login, o **`AuthenticationManager`** valida as credenciais.
* Em caso de sucesso, o **`JwtService`** gera um token JWT com as **Roles** do usuário.

### 3. Fluxo de Autorização
* O **`JwtAuthenticationFilter`** (Filtro Customizado) intercepta todas as requisições protegidas.
* O Filtro valida o Token e insere o `UserDetails` (com as Roles) no **`SecurityContextHolder`**.
* O uso de **`@PreAuthorize`** nos *Controllers* utiliza as informações do `SecurityContextHolder` para permitir ou negar o acesso aos métodos.

## 🗺️ Endpoints e Matriz de Acesso (RBAC)

| Rota (Controller) | Método | Acesso Requerido | Observações |
| :--- | :--- | :--- | :--- |
| `/auth/register` (AuthController) | `POST` | **Público** | Cria novo usuário. |
| `/auth/login` (AuthController) | `POST` | **Público** | Gera e retorna o Token JWT. |
| `/auth/me` (AuthController) | `GET` | **Autenticado** | Retorna dados do usuário logado. |
| `/clientes/publico` (ClienteController) | `GET` | **Público** | Rota de teste **desprotegida**. |
| `/clientes/{id}` (ClienteController) | `GET` | **`USER` OU `ADMIN`** | Proteção via `@PreAuthorize`. |
| `/clientes/user` (ClienteController) | `GET` | **`USER`** | Acesso restrito apenas ao papel `USER`. |
| `/clientes/admin` (ClienteController) | `GET` | **`ADMIN`** | Acesso restrito apenas ao papel `ADMIN`. |

---

## 🚀 Como Testar a API (via Swagger)

Para inspecionar e testar os endpoints da API de forma conveniente, utilize a documentação interativa gerada automaticamente.

1.  **Pré-requisitos:** O ambiente de execução do projeto deve estar em funcionamento (iniciado).
2.  **Acesso:** Abra o link do **Swagger UI** no seu navegador (assumindo a porta padrão):
    ```
    http://localhost:8080/swagger-ui.html
    ```
3.  **Fluxo de Teste:**
    * **Registro/Login:** Utilize os endpoints `/auth/register` e `/auth/login` para criar um usuário e obter o **Token JWT**.
    * **Autorização:** Cole o Token JWT obtido no botão **Authorize** do Swagger (formato: `Bearer <Token>`).
    * **Teste de Acesso:** Teste as rotas protegidas (`/clientes/admin`, `/clientes/user`) para verificar se as regras de **RBAC** estão sendo aplicadas.
