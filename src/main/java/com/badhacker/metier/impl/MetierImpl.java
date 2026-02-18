package com.badhacker.metier.impl;

import com.badhacker.dao.IDao;
import com.badhacker.metier.IMetier;

public class MetierImpl implements IMetier {
    IDao dao;
    public MetierImpl(IDao dao) {
        this.dao = dao;
    }
    @Override
    public double calcul() {
        double data = this.dao.getData();
        double temp = data + 273 ;
        return temp;

    }
}
