package br.com.ufcg.ccc.psoft.service;

import br.com.ufcg.ccc.psoft.exception.QuantidadeSaboresInvalidosException;
import br.com.ufcg.ccc.psoft.exception.SaborNotFoundException;
import br.com.ufcg.ccc.psoft.model.ItemDePedido;
import br.com.ufcg.ccc.psoft.model.Sabor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemDePedidoServiceImpl implements ItemDePedidoService{

    @Autowired
    private SaborService saborService;


    public Double checkItem(ItemDePedido itemDePedido) throws QuantidadeSaboresInvalidosException, SaborNotFoundException {
        if(itemDePedido.getTamanho().toUpperCase().equals("MEDIO") && itemDePedido.getSabores().size() > 1){
            throw new QuantidadeSaboresInvalidosException();
        } else if (itemDePedido.getTamanho().toUpperCase().equals("MEDIO") && itemDePedido.getSabores().size() == 1){
            if (!checkSaborExist(itemDePedido.getSabores().get(0))) {
                throw new SaborNotFoundException();
            }
            return itemDePedido.getSabores().get(0).getValorMedio();
        } else if(itemDePedido.getTamanho().toUpperCase().equals("GRANDE") && itemDePedido.getSabores().size() <= 2){
            double valor= 0;
            double quantSabores = 0;
            for (Sabor sabor : itemDePedido.getSabores()){
                if (!checkSaborExist(sabor)) {
                    throw new SaborNotFoundException();
                }
                quantSabores += 1;
                valor += sabor.getValorGrande();
            }
            return valor/quantSabores;
        } else {
            throw new QuantidadeSaboresInvalidosException();
        }
    }

    private boolean checkSaborExist(Sabor sabor) throws SaborNotFoundException {
        if(saborService.getSaborById(sabor.getId()) == null){
            return false;
        }
        return true;
    }
	
}
