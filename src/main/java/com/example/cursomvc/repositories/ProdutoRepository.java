package com.example.cursomvc.repositories;

import com.example.cursomvc.domain.Categoria;
import com.example.cursomvc.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings(value = "unused")
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Transactional
    @Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
    Page<Produto> findDistinctByNomeContainingAndCategoriasIn(
            @Param("nome") String nome,
            @Param("categorias") List<Categoria> categorias,
            Pageable pageRequest
    );
}
