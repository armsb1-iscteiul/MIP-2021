package com.mycompany.myapp.delegate;

import com.mycompany.myapp.service.dto.RestauranteProcessDTO;
import com.mycompany.myapp.domain.Restaurante;
import com.mycompany.myapp.repository.RestauranteRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ConfirmarRefeicaoDelegate implements JavaDelegate {

    @Autowired
    RestauranteRepository restauranteRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        RestauranteProcessDTO restauranteProcess = (RestauranteProcessDTO) delegateExecution.getVariable("processInstance");
	Restaurante restaurante = restauranteRepository.getOne(restauranteProcess.getRestaurante().getId());

	Boolean existePratoPrincipal = restauranteProcess.getRestaurante().getExistePratoPrincipal();
	Boolean existeBebida = restauranteProcess.getRestaurante().getExisteBebida();
	Boolean existeSobremesa = restauranteProcess.getRestaurante().getExisteSobremesa();
	Boolean possivelRealizar = false;

	if(existePratoPrincipal && existeBebida && existeSobremesa){
		possivelRealizar = true;
	}

	delegateExecution.setVariable("possivelRealizar", possivelRealizar);
	restaurante.setPossivelRealizar(possivelRealizar);

	restauranteRepository.save(restaurante);
    }
}