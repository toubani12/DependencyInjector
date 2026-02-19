package com.badhacker.pres;

import com.badhacker.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PresSpringXML {
    public static void main(String[] args) {
        ApplicationContext springContext =
                new ClassPathXmlApplicationContext("config.xml");
        IMetier metier = (IMetier) springContext.getBean("metier");
        metier.calcul();

    }
}
