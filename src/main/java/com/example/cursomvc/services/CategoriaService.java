package com.example.cursomvc.services;

import com.example.cursomvc.domain.Categoria;
import com.example.cursomvc.repositories.CategoriaRepository;
import com.example.cursomvc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repo;

    public Categoria buscar(Integer id) {
        Optional<Categoria> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! ID: " + id + " , Tipo: " + Categoria.class.getName())
        );
    }
}
