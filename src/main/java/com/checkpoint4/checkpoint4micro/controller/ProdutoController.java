package com.checkpoint4.checkpoint4micro.controller;

import com.checkpoint4.checkpoint4micro.model.Produto;
import com.checkpoint4.checkpoint4micro.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "API para gerenciamento de produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @GetMapping
    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista de todos os produtos cadastrados")
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        return ResponseEntity.ok(produtos);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo seu ID")
    public ResponseEntity<Produto> buscarProduto(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    @Operation(summary = "Criar novo produto", description = "Cria um novo produto no sistema")
    public ResponseEntity<Produto> criarProduto(@Valid @RequestBody Produto produto) {
        Produto produtoSalvo = produtoRepository.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza um produto existente")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @Valid @RequestBody Produto produtoAtualizado) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);
        if (produtoExistente.isPresent()) {
            produtoAtualizado.setId(id);
            Produto produtoSalvo = produtoRepository.save(produtoAtualizado);
            return ResponseEntity.ok(produtoSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir produto", description = "Exclui um produto do sistema")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


