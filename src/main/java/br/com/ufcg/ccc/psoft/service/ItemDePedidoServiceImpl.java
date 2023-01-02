package br.com.ufcg.ccc.psoft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ufcg.ccc.psoft.dto.requests.ItemDePedidoRequestDTO;
import br.com.ufcg.ccc.psoft.exception.QuantidadeSaboresInvalidosException;
import br.com.ufcg.ccc.psoft.exception.SaborNotFoundException;
import br.com.ufcg.ccc.psoft.model.ItemDePedido;
import br.com.ufcg.ccc.psoft.model.Sabor;
import br.com.ufcg.ccc.psoft.repository.ItemDePedidoRepository;

@Service
public class ItemDePedidoServiceImpl implements ItemDePedidoService{

    @Autowired
    private SaborService saborService;

    @Autowired
    private ItemDePedidoRepository itemDePedidoRepository;

   /*
    * Este método checa se o item é válido. Para ser válido, é preciso que:
    * 1)O(s) sabor(es) existam;
    * 2)Se for médio, tenha apenas 1 sabpr;
    * 3)Se for grande pode ter até 2 sabores;
    * 
    * Caso o item do pedido(pizza) seja válido, retorna o valor dele;
    */
    public Double checkItem(ItemDePedidoRequestDTO itemDePedido, List<Sabor> saboresDoPedido) throws QuantidadeSaboresInvalidosException, SaborNotFoundException {
       
    	if (itemDePedido.getTamanho().toUpperCase().equals("MEDIO") && saboresDoPedido.size() == 1){
            return saboresDoPedido.get(0).getValorMedio();
            
        } else if(itemDePedido.getTamanho().toUpperCase().equals("GRANDE") && saboresDoPedido.size() <= 2){
            double valor= 0;
            double quantSabores = 0;
            
            for (Sabor sabor : saboresDoPedido){
                quantSabores += 1;
                valor += sabor.getValorGrande();
            }
            return valor/quantSabores;
        } else {
            throw new QuantidadeSaboresInvalidosException();
        }
    }

    public ItemDePedido criarItemDePedido(List<Sabor> saboresDoPedido, String tamanho, double valor) {
    	
    	ItemDePedido itemEscolhido = new ItemDePedido(saboresDoPedido, tamanho, valor);
    	
    	itemDePedidoRepository.save(itemEscolhido);
    	
    	return itemEscolhido;
    }
	
}
