package com.mycompany.myapp.delegate;

import com.mycompany.myapp.service.dto.RestauranteProcessDTO;
import com.mycompany.myapp.domain.Restaurante;
import com.mycompany.myapp.repository.RestauranteRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Random;

@Component
public class CriarRefeicaoAlternativaDelegate implements JavaDelegate {

    @Autowired
    RestauranteRepository restauranteRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

	String[] comidaV = {"lasanha", "migas", "pizza"};
	String[] bebidaV = {"agua", "fanta", "coca-cola"};
	String[] sobremesaV = {"bolo", "gelatina", "mousse"};
	Random randomComida = new Random();
	int ic = randomComida.nextInt(3);
	Random randomBebida = new Random();
	int ib = randomBebida.nextInt(3);
	Random randomSobremesa = new Random();
	int is = randomSobremesa.nextInt(3);
	String nomeComida = comidaV[ic];
	String nomeBebida = bebidaV[ib];
	String nomeSobremesa = sobremesaV[is];

        RestauranteProcessDTO restauranteProcess = (RestauranteProcessDTO) delegateExecution.getVariable("processInstance");
	Restaurante restaurante = restauranteRepository.getOne(restauranteProcess.getRestaurante().getId());

	//delegateExecution.setVariable("possivelRealizar", possivelRealizar);
	restaurante.setNomeBebida(nomeBebida);
	restaurante.setNomePratoPrincipal(nomeComida);
	restaurante.setNomeSobremesa(nomeSobremesa);

	restauranteRepository.save(restaurante);
    }
}