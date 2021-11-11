package com.atividadeSup.restapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class StartController {

    public double INSSDESCONTA(double total) {
        double desconto = 0.0;

        if (total < 429.00) {
            desconto = total * (0.0765);
        } else if (total >= 429.00 && total < 540.00) {
            desconto = total * (0.0865);
        } else if (total >= 540.00 && total < 715.00) {
            desconto = total * (0.0900);
        } else if (total >= 715.00 && total < 1430.00) {
            desconto = total * (0.1100);
        } else if (total >= 1430.00) {
            desconto = 157.00;
        }
        return desconto;
    }

    public double IRRFDESCONTA(double inss, double salarioBruto) {

        double desconto = 0.0;
        double totalINSS = salarioBruto - inss;

        if (totalINSS < 1058.00) {
            desconto = totalINSS * (0.000);
        } else if (totalINSS >= 1058.00 && salarioBruto < 2115.00) {
            desconto = totalINSS * (0.15);
        } else if (totalINSS >= 2115.00) {
            desconto = totalINSS * (0.275);
        }
        return desconto;
    }

    public double salarioLiquido(double total, double irrf, double inss) {

        double salarioLiquido = total - inss - irrf;

        return salarioLiquido;
    }

    private static final String template = "Salário Liquido: %s";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/start")
    public Start start (@RequestParam(value = "salario") Double salarioBruto){
        double inss = INSSDESCONTA(salarioBruto);
        double irrf = IRRFDESCONTA(inss, salarioBruto);
        double salarioLiquido = salarioLiquido(salarioBruto, irrf, inss);

        return new Start(String.format(template,salarioLiquido));
    }


}
