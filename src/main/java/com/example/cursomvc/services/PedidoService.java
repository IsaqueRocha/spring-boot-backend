package com.example.cursomvc.services;

import com.example.cursomvc.domain.Pedido;
import com.example.cursomvc.repositories.PedidoRepository;
import com.example.cursomvc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository repo;

    public Pedido find(Integer id) {
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                        "Objeto não encontrado! ID: " + id + " , Tipo: " + Pedido.class.getName()
                )
        );
    }
}
