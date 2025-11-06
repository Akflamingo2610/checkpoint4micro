package com.checkpoint4.checkpoint4micro.controller;

import com.checkpoint4.checkpoint4micro.model.Produto;
import com.checkpoint4.checkpoint4micro.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProdutoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        produtoRepository.deleteAll();
    }

    @Test
    void testCreateProduto() throws Exception {
        // Arrange
        Produto produto = new Produto();
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição do produto teste");
        produto.setPreco(99.99);
        produto.setQuantidadeEstoque(10);

        // Act & Assert
        mockMvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Produto Teste"))
                .andExpect(jsonPath("$.descricao").value("Descrição do produto teste"))
                .andExpect(jsonPath("$.preco").value(99.99))
                .andExpect(jsonPath("$.quantidadeEstoque").value(10));
    }

    @Test
    void testGetAllProdutos() throws Exception {
        // Arrange
        Produto produto1 = new Produto("Produto 1", "Descrição 1", 50.0, 5);
        Produto produto2 = new Produto("Produto 2", "Descrição 2", 100.0, 10);
        produtoRepository.save(produto1);
        produtoRepository.save(produto2);

        // Act & Assert
        mockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").exists())
                .andExpect(jsonPath("$[1].nome").exists());
    }

    @Test
    void testGetProdutoById() throws Exception {
        // Arrange
        Produto produto = new Produto("Produto Teste", "Descrição", 75.50, 15);
        Produto produtoSalvo = produtoRepository.save(produto);
        Long id = produtoSalvo.getId();

        // Act & Assert
        mockMvc.perform(get("/api/produtos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value("Produto Teste"))
                .andExpect(jsonPath("$.preco").value(75.50))
                .andExpect(jsonPath("$.quantidadeEstoque").value(15));
    }

    @Test
    void testGetProdutoByIdNotFound() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/produtos/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateProduto() throws Exception {
        // Arrange
        Produto produto = new Produto("Produto Original", "Descrição Original", 50.0, 5);
        Produto produtoSalvo = produtoRepository.save(produto);
        Long id = produtoSalvo.getId();

        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setNome("Produto Atualizado");
        produtoAtualizado.setDescricao("Nova descrição");
        produtoAtualizado.setPreco(150.0);
        produtoAtualizado.setQuantidadeEstoque(20);

        // Act & Assert
        mockMvc.perform(put("/api/produtos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value("Produto Atualizado"))
                .andExpect(jsonPath("$.descricao").value("Nova descrição"))
                .andExpect(jsonPath("$.preco").value(150.0))
                .andExpect(jsonPath("$.quantidadeEstoque").value(20));
    }

    @Test
    void testUpdateProdutoNotFound() throws Exception {
        // Arrange
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setNome("Produto Atualizado");
        produtoAtualizado.setPreco(150.0);
        produtoAtualizado.setQuantidadeEstoque(20);

        // Act & Assert
        mockMvc.perform(put("/api/produtos/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoAtualizado)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteProduto() throws Exception {
        // Arrange
        Produto produto = new Produto("Produto para Deletar", "Descrição", 30.0, 3);
        Produto produtoSalvo = produtoRepository.save(produto);
        Long id = produtoSalvo.getId();

        // Act & Assert
        mockMvc.perform(delete("/api/produtos/{id}", id))
                .andExpect(status().isNoContent());

        // Verificar que o produto foi deletado
        mockMvc.perform(get("/api/produtos/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteProdutoNotFound() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/produtos/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCompleteCrudFlow() throws Exception {
        // CREATE - Criar um produto
        Produto produto = new Produto();
        produto.setNome("Produto CRUD");
        produto.setDescricao("Teste completo CRUD");
        produto.setPreco(200.0);
        produto.setQuantidadeEstoque(25);

        String response = mockMvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Produto produtoCriado = objectMapper.readValue(response, Produto.class);
        Long id = produtoCriado.getId();

        // READ - Buscar o produto criado
        mockMvc.perform(get("/api/produtos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto CRUD"));

        // UPDATE - Atualizar o produto
        produto.setNome("Produto CRUD Atualizado");
        produto.setPreco(250.0);

        mockMvc.perform(put("/api/produtos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto CRUD Atualizado"))
                .andExpect(jsonPath("$.preco").value(250.0));

        // DELETE - Deletar o produto
        mockMvc.perform(delete("/api/produtos/{id}", id))
                .andExpect(status().isNoContent());

        // Verificar que foi deletado
        mockMvc.perform(get("/api/produtos/{id}", id))
                .andExpect(status().isNotFound());
    }
}

