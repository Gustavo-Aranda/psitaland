# 📚 Documentação da API Psitaland

**Versão**: 1.0.0  
**Data**: Junho 2026  
**Linguagem**: Java 21  
**Framework**: Spring Boot 3.4.3

---

## 📋 Sumário Executivo

A **Psitaland API** é uma aplicação backend desenvolvida em **Spring Boot** para gerenciar um criadouro de aves (pássaros). A API fornece endpoints RESTful para criar, consultar, atualizar e deletar informações sobre:

- **Pássaros** (Passaro)
- **Espécies** de aves
- **Gaiolas** (locais físicos)
- **Mutações** (variações genéticas)
- **Status** (situação das aves)
- **Dashboard** (indicadores consolidados)

A aplicação segue a **arquitetura em camadas** (Controller → Service → Repository → Banco de Dados) e implementa tratamento centralizado de exceções com respostas padronizadas.

---

## 🏗️ Arquitetura do Projeto

### Estrutura de Camadas

```
┌─────────────────────────┐
│   Requisição HTTP       │
└────────────┬────────────┘
             │
┌────────────▼──────────────────────────────┐
│   Controllers (Porta de entrada)           │
│   - PassaroController                      │
│   - EspecieController                      │
│   - GaiolaController                       │
│   - MutacaoController                      │
│   - StatusController                       │
│   - DashboardController                    │
└────────────┬──────────────────────────────┘
             │
┌────────────▼──────────────────────────────┐
│   Services (Lógica de negócio)             │
│   - PassaroService                         │
│   - EspecieService                         │
│   - GaiolaService                          │
│   - MutacaoService                         │
│   - StatusService                          │
│   - DashboardService                       │
└────────────┬──────────────────────────────┘
             │
┌────────────▼──────────────────────────────┐
│   Repositories (Persistência)              │
│   - PassaroRepository                      │
│   - EspecieRepository                      │
│   - GaiolaRepository                       │
│   - MutacaoRepository                      │
│   - StatusRepository                       │
└────────────┬──────────────────────────────┘
             │
┌────────────▼──────────────────────────────┐
│   Banco de Dados (Supabase/PostgreSQL)     │
└────────────────────────────────────────────┘
```

### Princípios de Design

1. **Separação de Responsabilidades**: Cada camada tem uma função específica
2. **DTOs (Data Transfer Objects)**: Isolam as entidades JPA do cliente
3. **Tratamento Centralizado de Exceções**: GlobalExceptionHandler intercepta erros
4. **Validação no DTO**: Bean Validation (@Valid) valida dados antes de chegar ao Service
5. **Transações**: @Transactional garante consistência dos dados
6. **Logging**: SLF4J com Lombok @Slf4j para rastreamento

---

## 🔌 Endpoints da API

### Base URL
```
http://localhost:8080
```

### Documentação Interativa
```
http://localhost:8080/swagger-ui.html
```

---

## 🦜 Pássaros (`/passaros`)

Endpoint para gerenciar aves do plantel.

### Listar Todos os Pássaros
```http
GET /passaros
```

**Descrição**: Retorna lista de todos os pássaros cadastrados.

**Resposta (200 OK)**:
```json
[
  {
    "id": 1,
    "anilha": "A1234",
    "dataNascimento": "2023-06-15",
    "sexo": "M",
    "notaFiscal": "NF-001",
    "especie": {
      "id": 1,
      "nome": "Calopsita"
    },
    "mutacao": {
      "id": 1,
      "descricao": "Lutino"
    },
    "gaiola": {
      "id": 1,
      "numero": "G001"
    },
    "status": {
      "id": 1,
      "situacao": "Ativo"
    }
  }
]
```

---

### Buscar Pássaro por ID
```http
GET /passaros/{id}
```

**Parâmetros**:
- `id` (path, required): Identificador único do pássaro

**Resposta (200 OK)**:
```json
{
  "id": 1,
  "anilha": "A1234",
  "dataNascimento": "2023-06-15",
  "sexo": "M",
  "notaFiscal": "NF-001",
  "especie": { "id": 1, "nome": "Calopsita" },
  "mutacao": { "id": 1, "descricao": "Lutino" },
  "gaiola": { "id": 1, "numero": "G001" },
  "status": { "id": 1, "situacao": "Ativo" }
}
```

**Erros possíveis**:
- `404 Not Found`: Pássaro com ID especificado não existe

---

### Buscar Pássaro pela Anilha
```http
GET /passaros/anilha/{anilha}
```

**Parâmetros**:
- `anilha` (path, required): Código único da anilha (anel na pata)

**Resposta (200 OK)**: Mesmo formato do buscar por ID

**Erros possíveis**:
- `404 Not Found`: Nenhum pássaro encontrado com essa anilha

---

### Criar Novo Pássaro
```http
POST /passaros
Content-Type: application/json
```

**Corpo da Requisição**:
```json
{
  "anilha": "A1234",
  "dataNascimento": "2023-06-15",
  "sexo": "M",
  "notaFiscal": "NF-001",
  "especieId": 1,
  "mutacaoId": 1,
  "gaiolaId": 1,
  "statusId": 1
}
```

**Validações**:
- `anilha`: Obrigatório, máximo 20 caracteres
- `dataNascimento`: Obrigatório, deve ser data no passado
- `sexo`: Obrigatório, máximo 1 caractere ("M" ou "F")
- `notaFiscal`: Opcional, máximo 50 caracteres
- `especieId`: Obrigatório, deve existir no BD
- `mutacaoId`: Obrigatório, deve existir no BD
- `gaiolaId`: Obrigatório, deve existir no BD
- `statusId`: Obrigatório, deve existir no BD

**Resposta (201 Created)**:
```json
{
  "id": 1,
  "anilha": "A1234",
  "dataNascimento": "2023-06-15",
  "sexo": "M",
  "notaFiscal": "NF-001",
  "especie": { "id": 1, "nome": "Calopsita" },
  "mutacao": { "id": 1, "descricao": "Lutino" },
  "gaiola": { "id": 1, "numero": "G001" },
  "status": { "id": 1, "situacao": "Ativo" }
}
```

**Erros possíveis**:
- `400 Bad Request`: Validação falhou (campos obrigatórios ou formato inválido)
- `404 Not Found`: Espécie, mutação, gaiola ou status não existem
- `409 Conflict`: Regra de negócio violada (ex: anilha duplicada)

---

### Atualizar Pássaro
```http
PUT /passaros/{id}
Content-Type: application/json
```

**Parâmetros**:
- `id` (path, required): ID do pássaro a atualizar

**Corpo da Requisição**: Mesmo formato do criar

**Resposta (200 OK)**: Pássaro atualizado

**Erros possíveis**:
- `400 Bad Request`: Validação falhou
- `404 Not Found`: Pássaro, espécie, mutação, gaiola ou status não existem

---

### Excluir Pássaro
```http
DELETE /passaros/{id}
```

**Parâmetros**:
- `id` (path, required): ID do pássaro a deletar

**Resposta (204 No Content)**: Sem corpo

**Erros possíveis**:
- `404 Not Found`: Pássaro não existe

---

### Mover Pássaro para Outra Gaiola
```http
PUT /passaros/{id}/mover/{gaiolaId}
```

**Parâmetros**:
- `id` (path, required): ID do pássaro a mover
- `gaiolaId` (path, required): ID da gaiola de destino

**Resposta (200 OK)**: Pássaro com nova gaiola

**Validações de Negócio**:
- Pássaro não pode estar na mesma gaiola (BusinessRuleException)
- Ambos os IDs devem existir

**Erros possíveis**:
- `404 Not Found`: Pássaro ou gaiola não existem
- `409 Conflict`: Pássaro já está naquela gaiola

---

### Listar Pássaros por Espécie
```http
GET /passaros/especie/{especieId}
```

**Parâmetros**:
- `especieId` (path, required): ID da espécie

**Resposta (200 OK)**: Lista de pássaros daquela espécie

**Erros possíveis**:
- `404 Not Found`: Espécie não existe

---

## 🦅 Espécies (`/especies`)

Endpoint para gerenciar espécies de aves (ex: Calopsita, Ring Neck, Calafate).

### Listar Todas as Espécies
```http
GET /especies
```

**Resposta (200 OK)**:
```json
[
  {
    "id": 1,
    "nome": "Calopsita"
  },
  {
    "id": 2,
    "nome": "Ring Neck"
  }
]
```

---

### Buscar Espécie por ID
```http
GET /especies/{id}
```

**Resposta (200 OK)**:
```json
{
  "id": 1,
  "nome": "Calopsita"
}
```

---

### Criar Nova Espécie
```http
POST /especies
Content-Type: application/json
```

**Corpo da Requisição**:
```json
{
  "nome": "Calopsita"
}
```

**Validações**:
- `nome`: Obrigatório, máximo 100 caracteres

**Resposta (201 Created)**:
```json
{
  "id": 1,
  "nome": "Calopsita"
}
```

---

### Atualizar Espécie
```http
PUT /especies/{id}
Content-Type: application/json
```

**Corpo da Requisição**: Mesmo formato do criar

**Resposta (200 OK)**: Espécie atualizada

---

### Excluir Espécie
```http
DELETE /especies/{id}
```

**Resposta (204 No Content)**

---

## 🏠 Gaiolas (`/gaiolas`)

Endpoint para gerenciar gaiolas (localizações físicas do criadouro).

### Listar Todas as Gaiolas
```http
GET /gaiolas
```

**Resposta (200 OK)**:
```json
[
  {
    "id": 1,
    "numero": "G001"
  }
]
```

---

### Buscar Gaiola por ID
```http
GET /gaiolas/{id}
```

---

### Criar Nova Gaiola
```http
POST /gaiolas
Content-Type: application/json
```

**Corpo da Requisição**:
```json
{
  "numero": "G001"
}
```

**Validações**:
- `numero`: Obrigatório, máximo 10 caracteres

---

### Atualizar Gaiola
```http
PUT /gaiolas/{id}
```

---

### Excluir Gaiola
```http
DELETE /gaiolas/{id}
```

---

## 🎨 Mutações (`/mutacoes`)

Endpoint para gerenciar mutações (variações genéticas das aves).

Exemplos: Lutino, Albino, Canela, Pied, Opalino, etc.

### Listar Todas as Mutações
```http
GET /mutacoes
```

**Resposta (200 OK)**:
```json
[
  {
    "id": 1,
    "descricao": "Lutino"
  }
]
```

---

### Buscar Mutação por ID
```http
GET /mutacoes/{id}
```

---

### Criar Nova Mutação
```http
POST /mutacoes
Content-Type: application/json
```

**Corpo da Requisição**:
```json
{
  "descricao": "Lutino"
}
```

---

### Atualizar Mutação
```http
PUT /mutacoes/{id}
```

---

### Excluir Mutação
```http
DELETE /mutacoes/{id}
```

---

## 📊 Status (`/status`)

Endpoint para gerenciar status das aves (ex: Ativo, Venda, Reprodução, Aposentado).

### Listar Todos os Status
```http
GET /status
```

**Resposta (200 OK)**:
```json
[
  {
    "id": 1,
    "situacao": "Ativo"
  },
  {
    "id": 2,
    "situacao": "Venda"
  }
]
```

---

### Buscar Status por ID
```http
GET /status/{id}
```

---

### Criar Novo Status
```http
POST /status
Content-Type: application/json
```

**Corpo da Requisição**:
```json
{
  "situacao": "Ativo"
}
```

---

### Atualizar Status
```http
PUT /status/{id}
```

---

### Excluir Status
```http
DELETE /status/{id}
```

---

## 📈 Dashboard (`/dashboard`)

Endpoint consolidado que retorna indicadores e visão geral do plantel em uma única chamada.

### Obter Dashboard
```http
GET /dashboard
```

**Descrição**: Retorna todos os indicadores do plantel: total de aves, distribuição por espécie, gaiola, mutação e status.

**Resposta (200 OK)**:
```json
{
  "totalAves": 45,
  "totalEspecies": 3,
  "totalGaiolas": 5,
  "totalMutacoes": 8,
  "distribuicaoPorStatus": [
    {
      "status": "Ativo",
      "quantidade": 35
    },
    {
      "status": "Venda",
      "quantidade": 10
    }
  ],
  "distribuicaoPorEspecie": [
    {
      "especie": "Calopsita",
      "quantidade": 30
    },
    {
      "especie": "Ring Neck",
      "quantidade": 15
    }
  ],
  "distribuicaoPorGaiola": [
    {
      "gaiola": "G001",
      "quantidade": 9
    },
    {
      "gaiola": "G002",
      "quantidade": 9
    }
  ]
}
```

---

## 🗄️ Modelos de Dados

### Passaro (PASSAROS)

| Campo | Tipo | Nullable | Descrição |
|-------|------|----------|-----------|
| pas_id | INTEGER (PK) | NOT NULL | ID único (auto-increment) |
| pas_anilha | VARCHAR(100) | NOT NULL | Código da anilha (identificador físico único) |
| pas_data_nasc | DATE | NOT NULL | Data de nascimento |
| pas_sexo | VARCHAR(1) | NOT NULL | "M" ou "F" |
| pas_not_fis | VARCHAR(100) | NOT NULL | Número da nota fiscal |
| pas_esp_id | INTEGER (FK) | NOT NULL | Referência para ESPECIES |
| pas_mut_id | INTEGER (FK) | NOT NULL | Referência para MUTACOES |
| pas_gai_id | INTEGER (FK) | NOT NULL | Referência para GAIOLAS |
| pas_stt_id | INTEGER (FK) | NOT NULL | Referência para STATUS |

---

### Especie (ESPECIES)

| Campo | Tipo | Nullable | Descrição |
|-------|------|----------|-----------|
| esp_id | INTEGER (PK) | NOT NULL | ID único (auto-increment) |
| esp_nome | VARCHAR(100) | NOT NULL | Nome da espécie (ex: Calopsita) |

---

### Gaiola (GAIOLAS)

| Campo | Tipo | Nullable | Descrição |
|-------|------|----------|-----------|
| gai_id | INTEGER (PK) | NOT NULL | ID único (auto-increment) |
| gai_numero | VARCHAR(10) | NOT NULL | Número/identificação da gaiola |

---

### Mutacao (MUTACOES)

| Campo | Tipo | Nullable | Descrição |
|-------|------|----------|-----------|
| mut_id | INTEGER (PK) | NOT NULL | ID único (auto-increment) |
| mut_descricao | VARCHAR(100) | NOT NULL | Descrição da mutação (ex: Lutino) |

---

### Status (STATUS)

| Campo | Tipo | Nullable | Descrição |
|-------|------|----------|-----------|
| stt_id | INTEGER (PK) | NOT NULL | ID único (auto-increment) |
| stt_situacao | VARCHAR(100) | NOT NULL | Situação da ave (ex: Ativo, Venda) |

---

## 📦 Data Transfer Objects (DTOs)

### Padrão DTO

Os DTOs isolam as **entidades JPA** do cliente externo. O cliente nunca acessa diretamente a entidade `Passaro`, mas sim `PassaroResponseDTO`.

**Benefícios**:
1. Proteção: Cliente não vê campos internos
2. Independência: Mudanças na entidade não quebram a API
3. Validação: Entrada é validada no RequestDTO
4. Segurança: Senhas/dados sensíveis nunca saem da entidade

### PassaroRequestDTO

Representa os dados que o cliente **envia** para criar/atualizar uma ave.

```java
{
  "anilha": "A1234",
  "dataNascimento": "2023-06-15",
  "sexo": "M",
  "notaFiscal": "NF-001",
  "especieId": 1,      // Apenas ID, não o objeto inteiro
  "mutacaoId": 1,
  "gaiolaId": 1,
  "statusId": 1
}
```

**Validações**:
```
- anilha: @NotBlank, @Size(max=20)
- dataNascimento: @NotNull, @Past
- sexo: @NotBlank, @Size(max=1)
- notaFiscal: @Size(max=50)
- especieId: @NotNull
- mutacaoId: @NotNull
- gaiolaId: @NotNull
- statusId: @NotNull
```

### PassaroResponseDTO

Representa os dados que a API **devolve** após criar/atualizar/consultar uma ave.

```java
{
  "id": 1,
  "anilha": "A1234",
  "dataNascimento": "2023-06-15",
  "sexo": "M",
  "notaFiscal": "NF-001",
  "especie": { "id": 1, "nome": "Calopsita" },    // Objeto completo
  "mutacao": { "id": 1, "descricao": "Lutino" },
  "gaiola": { "id": 1, "numero": "G001" },
  "status": { "id": 1, "situacao": "Ativo" }
}
```

**Nota**: Os relacionamentos vêm como objetos completos (não apenas IDs), facilitando a leitura do cliente.

---

## ⚠️ Tratamento de Exceções

A API implementa **tratamento centralizado de exceções** através do `GlobalExceptionHandler`.

### Fluxo de Tratamento

```
Exceção lançada no Service
         ↓
GlobalExceptionHandler intercepta
         ↓
Transforma em ApiError (resposta padronizada)
         ↓
Retorna ResponseEntity com status HTTP apropriado
```

### Exceções Tratadas

#### 1️⃣ ResourceNotFoundException (404 Not Found)

**Quando ocorre**: Recurso solicitado não existe no banco de dados.

**Exemplos**:
- Buscar pássaro com ID que não existe
- Mover pássaro para gaiola que não existe
- Atualizar espécie com ID inexistente

**Resposta**:
```json
{
  "timestamp": "07/06/2026 14:30:00",
  "status": 404,
  "erro": "Recurso não encontrado",
  "mensagem": "Passaro com id 99 não encontrado.",
  "caminho": "/passaros/99"
}
```

**Construtores**:
```java
// Por ID
new ResourceNotFoundException("Passaro", 99)
// Resultado: "Passaro com id 99 não encontrado."

// Por campo customizado
new ResourceNotFoundException("Passaro", "anilha", "A1234")
// Resultado: "Passaro com anilha 'A1234' não encontrado."
```

---

#### 2️⃣ BusinessRuleException (409 Conflict)

**Quando ocorre**: Operação viola uma regra de negócio.

**Exemplos**:
- Tentar mover pássaro para a gaiola onde ele já está
- Tentar cadastrar anilha duplicada
- Tentar cadastrar gaiola com número duplicado
- Operação que viola integridade dos dados

**Resposta**:
```json
{
  "timestamp": "07/06/2026 14:30:00",
  "status": 409,
  "erro": "Conflito de regra de negócio",
  "mensagem": "O pássaro já está na gaiola G001.",
  "caminho": "/passaros/1/mover/1"
}
```

**Uso no código**:
```java
if (passaro.getGaiola().getId().equals(gaiolaId)) {
    throw new BusinessRuleException("O pássaro já está naquela gaiola.");
}
```

---

#### 3️⃣ MethodArgumentNotValidException (400 Bad Request)

**Quando ocorre**: Validação com @Valid falha (Bean Validation).

**Exemplos**:
- Campo obrigatório não foi enviado
- Data no futuro em campo que exige passado
- String maior que o limite de caracteres
- Tipo de dado inválido

**Resposta**:
```json
{
  "timestamp": "07/06/2026 14:30:00",
  "status": 400,
  "erro": "Erro de validação",
  "mensagem": "Um ou mais campos são inválidos.",
  "caminho": "/passaros",
  "errosCampos": [
    "A anilha é obrigatória.",
    "A data de nascimento deve ser uma data no passado."
  ]
}
```

**Nota**: Todos os erros de validação são listados simultaneamente, não apenas o primeiro.

---

#### 4️⃣ Exception genérica (500 Internal Server Error)

**Quando ocorre**: Erro inesperado não tratado pelas exceções específicas.

**Resposta**:
```json
{
  "timestamp": "07/06/2026 14:30:00",
  "status": 500,
  "erro": "Erro interno do servidor",
  "mensagem": "Ocorreu um erro inesperado. Contate o administrador.",
  "caminho": "/passaros"
}
```

---

### Estrutura ApiError

```java
{
  "timestamp": "07/06/2026 14:30:00",  // Formato: dd/MM/yyyy HH:mm:ss
  "status": 404,                        // Código HTTP
  "erro": "Recurso não encontrado",     // Título do erro
  "mensagem": "Passaro com id 99 não encontrado.",  // Detalhe
  "caminho": "/passaros/99",            // URI da requisição
  "errosCampos": [...]                  // Apenas para validação (opcional)
}
```

---

## 🗄️ Banco de Dados - Supabase

### Configuração

O projeto utiliza **Supabase** (PostgreSQL gerenciado) como banco de dados.

**Properties**:
```properties
spring.datasource.url=jdbc:postgresql://db.bqpmicshzrykvxabphsd.supabase.co:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=${SUPABASE_PWD}
spring.datasource.driver-class-name=org.postgresql.Driver
```

### Variáveis de Ambiente

- `SUPABASE_PWD`: Senha do usuário `postgres` no Supabase (NÃO commitar no git)

**Como definir (local)**:
```bash
export SUPABASE_PWD="sua_senha_aqui"
```

**Como definir (Windows PowerShell)**:
```powershell
$env:SUPABASE_PWD="sua_senha_aqui"
```

### Hibernate JPA Configuration

```properties
# DDL: validate = schema já existe, só valida
spring.jpa.hibernate.ddl-auto=validate

# SQL: mostrar no console
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Dialect: PostgreSQL
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### Diagrama ER (Entity-Relationship)

```
┌─────────────┐
│  ESPECIES   │
├─────────────┤
│ esp_id (PK) │
│ esp_nome    │
└─────────────┘
      ▲
      │ 1
      │
      │ N
      │
┌─────────────┐
│  PASSAROS   │
├─────────────┤
│ pas_id (PK) │
│ pas_anilha  │──────┐
│ pas_sexo    │      │
│ pas_esp_id  │      │
│ pas_mut_id  │      │
│ pas_gai_id  │      │
│ pas_stt_id  │      │ Pássaro é a entidade central
└─────────────┘      │ (referencia todas as outras)
      │              │
  ┌───┼───┬──────────┘
  │   │   │
  │   │   ├─────────────────┐
  │   │   │                 │
  ▼   ▼   ▼                 ▼
┌──────────────┐    ┌─────────────┐
│   MUTACOES   │    │   GAIOLAS   │
├──────────────┤    ├─────────────┤
│ mut_id (PK)  │    │ gai_id (PK) │
│ mut_desc...  │    │ gai_numero  │
└──────────────┘    └─────────────┘

┌──────────────┐
│    STATUS    │
├──────────────┤
│ stt_id (PK)  │
│ stt_situacao │
└──────────────┘
```

### Relacionamentos

| Tabela | Relacionamento | Cardinalidade | Descrição |
|--------|----------------|---------------|-----------|
| PASSAROS → ESPECIES | ManyToOne | N:1 | Vários pássaros, uma espécie |
| PASSAROS → MUTACOES | ManyToOne | N:1 | Vários pássaros, uma mutação |
| PASSAROS → GAIOLAS | ManyToOne | N:1 | Vários pássaros, uma gaiola |
| PASSAROS → STATUS | ManyToOne | N:1 | Vários pássaros, um status |

---

## 🚀 Como Executar a Aplicação

### Pré-requisitos

- **Java 21** instalado
- **Maven** instalado (ou usar `mvnw`)
- **Supabase** conta ativa com banco PostgreSQL
- **Variáveis de ambiente** configuradas

### Passo 1: Configurar Variáveis de Ambiente

**Windows (PowerShell)**:
```powershell
$env:SUPABASE_PWD="sua_senha_aqui"
```

**Linux/Mac**:
```bash
export SUPABASE_PWD="sua_senha_aqui"
```

### Passo 2: Compilar o Projeto

**Com Maven instalado**:
```bash
mvn clean install
```

**Ou com o wrapper do projeto**:
```bash
./mvnw clean install
```

### Passo 3: Executar a Aplicação

**Com Maven**:
```bash
mvn spring-boot:run
```

**Com o wrapper**:
```bash
./mvnw spring-boot:run
```

**Ou executar o JAR compilado**:
```bash
java -jar target/psitaland-0.0.1-SNAPSHOT.jar
```

### Passo 4: Verificar se Iniciou

```bash
curl http://localhost:8080/swagger-ui.html
```

Você deverá ver a interface do Swagger.

### Logs de Inicialização

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                  (v3.4.3)

2026-06-07T14:30:00.000Z  INFO 1234 --- [main] com.psitaland.PsitalandApplication : 
  Starting PsitalandApplication using Java 21.0.0 on localhost

...

2026-06-07T14:30:05.000Z  INFO 1234 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer :
  Tomcat started on port(s): 8080 (http)
```

---

## 📝 Exemplos de Uso

### Exemplo 1: Criar uma Nova Ave

**Requisição**:
```bash
curl -X POST http://localhost:8080/passaros \
  -H "Content-Type: application/json" \
  -d '{
    "anilha": "A1234",
    "dataNascimento": "2023-06-15",
    "sexo": "M",
    "notaFiscal": "NF-001",
    "especieId": 1,
    "mutacaoId": 1,
    "gaiolaId": 1,
    "statusId": 1
  }'
```

**Resposta (201 Created)**:
```json
{
  "id": 1,
  "anilha": "A1234",
  "dataNascimento": "2023-06-15",
  "sexo": "M",
  "notaFiscal": "NF-001",
  "especie": { "id": 1, "nome": "Calopsita" },
  "mutacao": { "id": 1, "descricao": "Lutino" },
  "gaiola": { "id": 1, "numero": "G001" },
  "status": { "id": 1, "situacao": "Ativo" }
}
```

---

### Exemplo 2: Mover Uma Ave de Gaiola

**Requisição**:
```bash
curl -X PUT http://localhost:8080/passaros/1/mover/2 \
  -H "Content-Type: application/json"
```

**Resposta (200 OK)**:
```json
{
  "id": 1,
  "anilha": "A1234",
  "dataNascimento": "2023-06-15",
  "sexo": "M",
  "notaFiscal": "NF-001",
  "especie": { "id": 1, "nome": "Calopsita" },
  "mutacao": { "id": 1, "descricao": "Lutino" },
  "gaiola": { "id": 2, "numero": "G002" },  // Gaiola atualizada
  "status": { "id": 1, "situacao": "Ativo" }
}
```

---

### Exemplo 3: Erro de Validação

**Requisição** (anilha obrigatória faltando):
```bash
curl -X POST http://localhost:8080/passaros \
  -H "Content-Type: application/json" \
  -d '{
    "dataNascimento": "2023-06-15",
    "sexo": "M",
    "especieId": 1,
    "mutacaoId": 1,
    "gaiolaId": 1,
    "statusId": 1
  }'
```

**Resposta (400 Bad Request)**:
```json
{
  "timestamp": "07/06/2026 14:30:00",
  "status": 400,
  "erro": "Erro de validação",
  "mensagem": "Um ou mais campos são inválidos.",
  "caminho": "/passaros",
  "errosCampos": [
    "A anilha é obrigatória."
  ]
}
```

---

### Exemplo 4: Recurso Não Encontrado

**Requisição**:
```bash
curl -X GET http://localhost:8080/passaros/999
```

**Resposta (404 Not Found)**:
```json
{
  "timestamp": "07/06/2026 14:30:00",
  "status": 404,
  "erro": "Recurso não encontrado",
  "mensagem": "Passaro com id 999 não encontrado.",
  "caminho": "/passaros/999"
}
```

---

### Exemplo 5: Obter Dashboard

**Requisição**:
```bash
curl -X GET http://localhost:8080/dashboard
```

**Resposta (200 OK)**:
```json
{
  "totalAves": 45,
  "totalEspecies": 3,
  "totalGaiolas": 5,
  "totalMutacoes": 8,
  "distribuicaoPorStatus": [
    { "status": "Ativo", "quantidade": 35 },
    { "status": "Venda", "quantidade": 10 }
  ],
  "distribuicaoPorEspecie": [
    { "especie": "Calopsita", "quantidade": 30 },
    { "especie": "Ring Neck", "quantidade": 15 }
  ],
  "distribuicaoPorGaiola": [
    { "gaiola": "G001", "quantidade": 9 },
    { "gaiola": "G002", "quantidade": 9 },
    { "gaiola": "G003", "quantidade": 9 },
    { "gaiola": "G004", "quantidade": 9 },
    { "gaiola": "G005", "quantidade": 9 }
  ]
}
```

---

## ⚙️ Configuração da Aplicação

### application.properties

```properties
# ============================================================
# Banco de Dados (Supabase / PostgreSQL)
# ============================================================
spring.application.name=psitaland
spring.datasource.url=jdbc:postgresql://db.bqpmicshzrykvxabphsd.supabase.co:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=${SUPABASE_PWD}
spring.datasource.driver-class-name=org.postgresql.Driver

# ============================================================
# JPA / Hibernate
# ============================================================
# validate = schema já existe no Supabase, só valida
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# ============================================================
# Servidor
# ============================================================
server.port=8080

# ============================================================
# Swagger / SpringDoc
# ============================================================
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/api-docs

# ============================================================
# Log
# ============================================================
logging.level.com.psitaland=INFO
logging.level.org.hibernate.SQL=DEBUG
```

### Dependências Principais

```xml
<!-- Spring Boot Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- JPA / Hibernate -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Bean Validation -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- PostgreSQL Driver -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<!-- SpringDoc OpenAPI (Swagger) -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>
```

---

## 📊 Estrutura de Pastas

```
psitaland/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── psitaland/
│   │   │           ├── PsitalandApplication.java      // Classe principal
│   │   │           ├── config/
│   │   │           │   └── OpenApiConfig.java          // Configuração Swagger
│   │   │           ├── controllers/                     // API endpoints
│   │   │           │   ├── PassaroController.java
│   │   │           │   ├── EspecieController.java
│   │   │           │   ├── GaiolaController.java
│   │   │           │   ├── MutacaoController.java
│   │   │           │   ├── StatusController.java
│   │   │           │   └── DashboardController.java
│   │   │           ├── dtos/                            // Data Transfer Objects
│   │   │           │   ├── passaro/
│   │   │           │   ├── especie/
│   │   │           │   ├── gaiola/
│   │   │           │   ├── mutacao/
│   │   │           │   ├── status/
│   │   │           │   └── dashboard/
│   │   │           ├── exception/                       // Tratamento de exceções
│   │   │           │   ├── GlobalExceptionHandler.java
│   │   │           │   ├── ResourceNotFoundException.java
│   │   │           │   ├── BusinessRuleException.java
│   │   │           │   └── ApiError.java
│   │   │           ├── models/                          // Entidades JPA
│   │   │           │   ├── Passaro.java
│   │   │           │   ├── Especie.java
│   │   │           │   ├── Gaiola.java
│   │   │           │   ├── Mutacao.java
│   │   │           │   └── Status.java
│   │   │           ├── repositories/                    // Persistência
│   │   │           │   ├── PassaroRepository.java
│   │   │           │   ├── EspecieRepository.java
│   │   │           │   ├── GaiolaRepository.java
│   │   │           │   ├── MutacaoRepository.java
│   │   │           │   └── StatusRepository.java
│   │   │           └── services/                        // Lógica de negócio
│   │   │               ├── PassaroService.java
│   │   │               ├── EspecieService.java
│   │   │               ├── GaiolaService.java
│   │   │               ├── MutacaoService.java
│   │   │               ├── StatusService.java
│   │   │               └── DashboardService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/
│           └── com/
│               └── psitaland/
│                   └── PsitalandApplicationTests.java
├── pom.xml                         // Configuração Maven
├── mvnw                            // Maven wrapper (Linux/Mac)
├── mvnw.cmd                        // Maven wrapper (Windows)
├── README.md
├── HELP.md
└── DOCUMENTATION.md                // Este arquivo
```

---

## 🔐 Segurança e Boas Práticas

### 1. Senhas e Credenciais

❌ **NUNCA commit no git**:
- Passwords do banco de dados
- API keys
- Tokens de autenticação

✅ **USE**:
- Variáveis de ambiente (`.env`, secrets do servidor)
- Gerenciadores de secrets (AWS Secrets Manager, Azure Key Vault)
- `.gitignore` para arquivos sensíveis

### 2. Validação de Entrada

✅ **A aplicação implementa**:
- Bean Validation com `@Valid` em todos os RequestDTOs
- Validações customizadas nos Services
- Tratamento centralizado de erros
- Mensagens de erro claras

### 3. DTOs Protegem Entidades

✅ **A API usa RequestDTO e ResponseDTO**:
- Cliente nunca acessa entidade JPA diretamente
- Campos sensíveis podem ser ocultados
- Mudanças no banco não quebram a API

### 4. Transações ACID

✅ **Services usam `@Transactional`**:
- Operações são atômicas
- Rollback em caso de erro
- Consistência garantida

### 5. Tratamento de Exceções

✅ **GlobalExceptionHandler centralizado**:
- Não expõe stack trace ao cliente
- Mensagens de erro consistentes
- Logging adequado para debug

---

## 📞 Suporte e Contato

**Projeto**: Psitaland API  
**Versão**: 1.0.0  
**Email**: contato@psitaland.com  
**Documentação Interativa**: http://localhost:8080/swagger-ui.html

---

## 📄 Licença

[Especificar licença do projeto]

---

**Última atualização**: Junho 2026  
**Mantido por**: Equipe Psitaland
