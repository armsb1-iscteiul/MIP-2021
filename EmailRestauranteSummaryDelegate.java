package com.mycompany.myapp.delegate;

import com.mycompany.myapp.service.MailService;
import com.mycompany.myapp.service.dto.RestauranteDTO;
import com.mycompany.myapp.service.dto.RestauranteProcessDTO;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Locale;

@Component
public class EmailRestauranteSummaryDelegate implements JavaDelegate {

    @Autowired
    MailService mailService;

    @Autowired
    SpringTemplateEngine templateEngine;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        RestauranteProcessDTO restauranteProcess = (RestauranteProcessDTO) delegateExecution.getVariable("processInstance");
        RestauranteDTO restaurante = restauranteProcess.getRestaurante();
        String to = restaurante.getEmail();
        String subject = "[AgileKip] Summary of your travel " + restaurante.getNomeRestaurante();
        Context context = new Context(Locale.getDefault());
        context.setVariable("restaurante", restaurante);
        String content = templateEngine.process("restauranteProcess/emailRestauranteSummary", context);
        mailService.sendEmail(to, subject, content, false, true);
    }
}