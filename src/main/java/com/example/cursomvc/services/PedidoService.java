package com.example.cursomvc.services;

import com.example.cursomvc.domain.ItemPedido;
import com.example.cursomvc.domain.PagamentoComBoleto;
import com.example.cursomvc.domain.Pedido;
import com.example.cursomvc.domain.enums.EstadoPagamento;
import com.example.cursomvc.repositories.ItemPedidoRepository;
import com.example.cursomvc.repositories.PagamentoRepository;
import com.example.cursomvc.repositories.PedidoRepository;
import com.example.cursomvc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private EmailService emailService;


    public Pedido find(Integer id) {
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                        "Objeto não encontrado! ID: " + id + " , Tipo: " + Pedido.class.getName()
                )
        );
    }

    @Transactional
    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.setCliente(clienteService.find(obj.getCliente().getId()));
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);

        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }

        obj = repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());

        for (ItemPedido itemPedido : obj.getItens()) {
            itemPedido.setDesconto(0.0);
            itemPedido.setProduto(produtoService.find(itemPedido.getProduto().getId()));
            itemPedido.setPreco(itemPedido.getProduto().getPreco());
            itemPedido.setPedido(obj);
        }

        itemPedidoRepository.saveAll(obj.getItens());
        emailService.sendOrderConfirmationEmail(obj);
        return obj;
    }
}
