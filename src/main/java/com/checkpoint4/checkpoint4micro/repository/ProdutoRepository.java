package com.checkpoint4.checkpoint4micro.repository;

import com.checkpoint4.checkpoint4micro.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}


