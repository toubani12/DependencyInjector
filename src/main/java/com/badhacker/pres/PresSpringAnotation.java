package com.badhacker.pres;

import com.badhacker.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PresSpringAnotation {
    public static void main(String[] args){
        ApplicationContext context =
                new AnnotationConfigApplicationContext("com.badhacker.metier","com.badhacker.ext");
        IMetier metier = context.getBean(IMetier.class);
        System.out.println("RES = "+metier.calcul()+" kelvin");

    }
}
