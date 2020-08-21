package com.example.cursomvc.services;

import com.example.cursomvc.domain.Categoria;
import com.example.cursomvc.domain.Produto;
import com.example.cursomvc.repositories.CategoriaRepository;
import com.example.cursomvc.repositories.ProdutoRepository;
import com.example.cursomvc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repo;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto find(Integer id) {
        Optional<Produto> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                        "Objeto não encontrado! ID: " + id + " , Tipo: " + Produto.class.getName()
                )
        );
    }

    public Page<Produto> search(
            String nome,
            List<Integer> ids,
            Integer page,
            Integer linesPerPage,
            String orderBy,
            String direction
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        List<Categoria> categorias = categoriaRepository.findAllById(ids);


        return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);

    }
}