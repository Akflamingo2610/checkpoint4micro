# Checkpoint4Micro - API de Gerenciamento de Produtos

## Descrição
API REST desenvolvida em Spring Boot para gerenciamento de produtos, criada para o Check Point 1 da disciplina SOA - Microservices and Web Engineering.

## Tecnologias Utilizadas
- Java 21
- Spring Boot 3.5.6
- Spring Data JPA
- MySQL 8.0
- Docker
- Swagger/OpenAPI 3

## Estrutura da Aplicação
- **Model**: Entidade `Produto` com validações
- **Repository**: Interface para acesso aos dados
- **Controller**: Endpoints REST para CRUD de produtos
- **Config**: Configuração do Swagger/OpenAPI

## Endpoints da API
- `GET /api/produtos` - Listar todos os produtos
- `GET /api/produtos/{id}` - Buscar produto por ID
- `POST /api/produtos` - Criar novo produto
- `PUT /api/produtos/{id}` - Atualizar produto
- `DELETE /api/produtos/{id}` - Excluir produto

## Execução da Aplicação

### Pré-requisitos
- Docker
- Docker Compose
- Java 21 (para desenvolvimento local)
- Maven (para desenvolvimento local)

### 1. Execução com Docker Hub (docker run)

#### Passo 1: Fazer o build da aplicação
```bash
# Na raiz do projeto
mvn clean package -DskipTests
```

#### Passo 2: Construir a imagem Docker
```bash
docker build -t seu-usuario/checkpoint4micro:latest .
```

#### Passo 3: Fazer push para o Docker Hub
```bash
docker push seu-usuario/checkpoint4micro:latest
```

#### Passo 4: Executar com docker run
```bash
# Primeiro, executar o banco de dados
docker run -d --name mysql-db \
  -e MYSQL_ROOT_PASSWORD=rootpassword \
  -e MYSQL_DATABASE=checkpoint4micro \
  -e MYSQL_USER=appuser \
  -e MYSQL_PASSWORD=apppassword \
  -p 3306:3306 \
  mysql:8.0

# Aguardar o banco inicializar (aproximadamente 30 segundos)
sleep 30

# Executar a aplicação
docker run -d --name checkpoint4micro-api \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/checkpoint4micro \
  -e SPRING_DATASOURCE_USERNAME=appuser \
  -e SPRING_DATASOURCE_PASSWORD=apppassword \
  --add-host=host.docker.internal:host-gateway \
  seu-usuario/checkpoint4micro:latest
```

#### Passo 5: Verificar se a aplicação está rodando
```bash
# Verificar logs da aplicação
docker logs checkpoint4micro-api

# Testar endpoint
curl http://localhost:8080/api/produtos
```

### 2. Execução com Docker Compose

#### Passo 1: Fazer o build da aplicação
```bash
# Na raiz do projeto
mvn clean package -DskipTests
```

#### Passo 2: Executar com docker-compose
```bash
docker-compose up --build
```

#### Passo 3: Verificar se os serviços estão rodando
```bash
# Verificar containers
docker-compose ps

# Verificar logs
docker-compose logs api
docker-compose logs database
```

#### Passo 4: Parar os serviços
```bash
docker-compose down
```

### 3. Acesso à Documentação Swagger

Após a aplicação estar rodando, acesse:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

### 4. Testando a API

#### Criar um produto
```bash
curl -X POST http://localhost:8080/api/produtos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Produto Teste",
    "descricao": "Descrição do produto teste",
    "preco": 29.99,
    "quantidadeEstoque": 100
  }'
```

#### Listar produtos
```bash
curl http://localhost:8080/api/produtos
```

#### Buscar produto por ID
```bash
curl http://localhost:8080/api/produtos/1
```

#### Atualizar produto
```bash
curl -X PUT http://localhost:8080/api/produtos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Produto Atualizado",
    "descricao": "Nova descrição",
    "preco": 39.99,
    "quantidadeEstoque": 50
  }'
```

#### Excluir produto
```bash
curl -X DELETE http://localhost:8080/api/produtos/1
```

## Estrutura do Projeto
```
checkpoint4micro/
├── src/main/java/com/checkpoint4/checkpoint4micro/
│   ├── Checkpoint4microApplication.java
│   ├── config/
│   │   └── SwaggerConfig.java
│   ├── controller/
│   │   └── ProdutoController.java
│   ├── model/
│   │   └── Produto.java
│   └── repository/
│       └── ProdutoRepository.java
├── src/main/resources/
│   └── application.properties
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## Informações do Docker Hub

- **Imagem**: `seu-usuario/checkpoint4micro:latest`
- **URL**: https://hub.docker.com/r/seu-usuario/checkpoint4micro

## Troubleshooting

### Problema: Aplicação não consegue conectar ao banco
**Solução**: Verifique se o container do MySQL está rodando e se as variáveis de ambiente estão corretas.

### Problema: Porta 8080 já está em uso
**Solução**: Pare outros serviços na porta 8080 ou altere a porta no docker-compose.yml.

### Problema: Erro de build da aplicação
**Solução**: Verifique se o Java 21 está instalado e se todas as dependências estão corretas no pom.xml.

## Desenvolvido por
[Inserir nomes dos membros do grupo]


