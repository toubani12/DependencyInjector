package com.badhacker.pres;

import com.badhacker.dao.impl.DaoImpl;
import com.badhacker.ext.DaoImplV2;
import com.badhacker.metier.impl.MetierImpl;

public class Pres1 {
    public static void main (String[] args) {
        DaoImplV2 dao = new DaoImplV2();
        MetierImpl metier = new MetierImpl(dao);
        System.out.println("RES: "+ metier.calcul()+" kelvin");
    }
}
