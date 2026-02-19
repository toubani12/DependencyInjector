package com.badhacker.metier.impl;

import com.badhacker.dao.IDao;
import com.badhacker.metier.IMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MetierImpl implements IMetier {

    @Autowired
//    @Qualifier("d")

    IDao dao;
    public MetierImpl(IDao dao) {
        this.dao = dao;
    }
    public MetierImpl (){}
    @Override
    public double calcul() {
        double data = this.dao.getData();
        double temp = data + 273 ;

        return temp;

    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
