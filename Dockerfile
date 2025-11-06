# Multi-stage build: primeiro estágio para compilar
FROM eclipse-temurin:21-jdk AS build

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Dar permissão de execução ao mvnw
RUN chmod +x ./mvnw

# Fazer download das dependências (cache layer)
RUN ./mvnw dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Compilar a aplicação
RUN ./mvnw clean package -DskipTests

# Segundo estágio: imagem final apenas com o JAR
FROM eclipse-temurin:21-jdk

# Definir diretório de trabalho
WORKDIR /app

# Copiar o JAR compilado do estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Expor a porta 8080
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

