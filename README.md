# Checkpoint 4 - Microserviços com GitHub Actions

Este projeto implementa um microserviço Spring Boot com integração contínua e entrega contínua usando GitHub Actions.

## 🚀 Funcionalidades

- **API REST** para gerenciamento de produtos
- **Swagger UI** para documentação interativa da API
- **MySQL** como banco de dados
- **Docker** para containerização
- **GitHub Actions** para CI/CD

## 📋 GitHub Actions Implementadas

### 1. Continuous Integration (CI)
- **Arquivo**: `.github/workflows/ci.yml`
- **Trigger**: Push nas branches `develop`, `feature`, `hotfix`
- **Funcionalidades**:
  - Execução de testes unitários
  - Empacotamento da aplicação com Maven
  - Upload de artefatos

### 2. Continuous Delivery (CD)
- **Arquivo**: `.github/workflows/cd.yml`
- **Trigger**: Pull Request na branch `main`
- **Funcionalidades**:
  - Build da imagem Docker
  - Upload da imagem para Docker Hub
  - Cache de layers Docker

### 3. Release e Documentação
- **Arquivo**: `.github/workflows/release.yml`
- **Trigger**: Push na branch `main`
- **Funcionalidades**:
  - Geração de documentação (Javadoc)
  - Criação de Release com tags
  - Upload de artefatos de release

## 🔧 Configuração Necessária

### Secrets do GitHub
Configure os seguintes secrets no seu repositório GitHub:

1. `DOCKER_USERNAME` - Seu usuário do Docker Hub
2. `DOCKER_PASSWORD` - Sua senha/token do Docker Hub

### Como configurar os secrets:
1. Vá para Settings > Secrets and variables > Actions
2. Clique em "New repository secret"
3. Adicione `DOCKER_USERNAME` e `DOCKER_PASSWORD`

## 🐳 Docker

### Build local
```bash
docker build -t checkpoint4micro .
```

### Executar com Docker Compose
```bash
docker-compose up -d
```

## 📚 API Endpoints

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Base**: http://localhost:8080/api/produtos

### Endpoints disponíveis:
- `GET /api/produtos` - Lista todos os produtos
- `POST /api/produtos` - Cria um novo produto
- `GET /api/produtos/{id}` - Busca produto por ID
- `PUT /api/produtos/{id}` - Atualiza produto por ID
- `DELETE /api/produtos/{id}` - Remove produto por ID

## 🏗️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Data JPA**
- **MySQL 8.0**
- **Docker**
- **GitHub Actions**
- **Maven**
- **Swagger/OpenAPI**

## 📝 Informações de Entrega

- **GitHub**: https://github.com/Akflamingo2610/checkpoint4micro
- **Docker Hub**: https://hub.docker.com/r/akflamingo2610/checkpoint4micro

### Membros do Grupo:
- Lucas Thomazette Benvenuto - RM 98048
- Pedro Loterio dos Santos - RM 550909
