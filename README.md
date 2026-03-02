# 🚀 Forum Hub API

O **Forum Hub** é uma API REST desenvolvida como parte de um desafio de back-end. A plataforma simula o funcionamento de um fórum de dúvidas (estilo Alura), permitindo que alunos, instrutores e administradores interajam por meio de tópicos e respostas, garantindo a organização do conhecimento e a resolução de dúvidas de forma segura e eficiente.



## 🛠️ Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 3.5.11**
* **Spring Security** (Autenticação e Autorização via JWT)
* **Spring Data JPA** (Persistência e consultas ao banco)
* **MySQL** (Banco de dados relacional)
* **Flyway** (Gerenciamento de migrações de banco de dados)
* **Lombok** (Produtividade e redução de código boilerplate)
* **SpringDoc OpenAPI (Swagger)** (Documentação interativa da API)

---

## 🔐 Segurança e Controle de Acesso

A segurança é baseada em **JSON Web Tokens (JWT)**. A autenticação é *stateless*, exigindo o token no cabeçalho `Authorization` para todas as rotas protegidas.

### Níveis de Permissão (RBAC)

| Perfil | Descrição de Acesso                                                                            |
| :--- |:-----------------------------------------------------------------------------------------------|
| **ROLE_ADMIN** | Acesso total: gestão de usuários, cursos, tópicos e visualização de itens inativos/arquivados. |
| **ROLE_PROFESSOR** | Tem permissão para cadastrar novos usuários no sistema.                                        |
| **ROLE_MONITOR** | Tem permissão para cadastrar novos usuários no sistema.                                          |
---

## 📋 Regras de Negócio e Restrições

### 🛡️ Proteção de Conteúdo (Ownership)
* **Edição e Exclusão**: Um tópico só pode ser editado ou excluído pelo seu **autor original** ou por um usuário com perfil **ADMIN**.
* **Solução de Tópicos**: A marcação de uma resposta como "Solução" é restrita ao autor do tópico ou administradores.

### 📚 Consistência de Dados
* **Cursos Inativos**: O sistema impede a criação de novos tópicos ou respostas vinculadas a cursos que foram desativado no banco de dados.

---

## 🛣️ Endpoints da API

Abaixo estão listados os principais endpoints da aplicação. Para detalhes sobre os modelos de JSON (Request/Response), consulte o Swagger.

### 🔑 Autenticação
| Método | Endpoint | Descrição | Acesso |
| :--- | :--- | :--- | :--- |
| `POST` | `/login` | Efetua login e retorna o Token JWT | Público |

### 💬 Tópicos
| Método | Endpoint | Descrição                                            | Acesso |
| :--- | :--- |:-----------------------------------------------------| :--- |
| `GET` | `/topicos` | Lista todos os tópicos ativos (paginado)             | Autenticado |
| `GET` | `/topicos/{id}` | Detalha um tópico específico                         | Autenticado |
| `POST` | `/topicos` | Cria um novo tópico (Bloqueado para cursos inativos) | Autenticado |
| `PUT` | `/topicos/{id}` | Atualiza título/mensagem (Apenas Autor ou Admin)     | Autor/Admin |
| `DELETE` | `/topicos/{id}` | Exclusão lógica (Apenas Autor ou Admin)              | Autor/Admin |
| `GET` | `/topicos/arquivados` | Lista tópicos inativos/arquivados                    | Admin |

### ✍️ Respostas e Soluções
| Método | Endpoint | Descrição | Acesso |
| :--- | :--- | :--- | :--- |
| `POST` | `/topicos/{id}/respostas` | Registra uma resposta em um tópico | Autenticado |
| `PUT` | `/respostas/{id}/solucao` | Marca resposta como solução (Apenas Autor do Tópico ou Admin) | Autor/Admin |

### 📚 Cursos
| Método | Endpoint | Descrição               | Acesso |
| :--- | :--- |:------------------------| :--- |
| `GET` | `/cursos` | Lista todos os cursos   | Admin |
| `POST` | `/cursos` | Cadastra um novo curso  | Admin |
| `PUT` | `/cursos/{id}` | Atualiza dados do curso | Admin |
| `DELETE` | `/cursos/{id}` | Desativa um curso       | Admin |

### 👥 Usuários
| Método | Endpoint | Descrição | Acesso |
| :--- | :--- | :--- | :--- |
| `GET` | `/usuarios` | Lista todos os usuários ativos | Admin |
| `GET` | `/usuarios/{id}` | Detalha perfil (Usuário comum vê apenas o seu) | Próprio/Admin |
| `POST` | `/usuarios` | Cadastra novo usuário | Admin/Monitor/Prof |
| `DELETE` | `/usuarios/{id}` | Desativa um usuário | Admin |

---

## ⚠️ Tratamento de Erros

A API possui um tratador de erros global (`@RestControllerAdvice`) que padroniza as respostas de erro:

* **400 Bad Request**: Erros de validação (Bean Validation) ou regras de negócio (Ex: Curso Inativo).
* **403 Forbidden**: Tentativa de acessar recurso de outro usuário ou rota restrita a Admin.
* **404 Not Found**: Registro não encontrado no banco de dados.

---
## 📖 Documentação da API (Swagger)

A API conta com documentação interativa via Swagger UI.

* **URL de acesso**: `http://localhost:8080/swagger-ui.html`

> **Como testar**:
> 1. Realize o login no endpoint `/login`.
> 2. Copie o Token gerado.
> 3. No Swagger, clique no botão **Authorize** (cadeado) e cole o token.



---

## 🚀 Como Rodar a Aplicação
**Clonar o Repositório**:
   ```bash
   git clone https://github.com/seu-usuario/forum-hub.git