package com.badhacker.pres;

import com.badhacker.dao.IDao;
import com.badhacker.metier.IMetier;
import com.badhacker.metier.impl.MetierImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Pres2 {
    //FileNotFoundException , ClassNotFoundException ,InstantiationException, IllegalAccessException
    public static void main (String[] args) throws Exception {
        Scanner sc = new Scanner(new File("config.txt"));
        String daoClassName = sc.nextLine();
        String metierClassName = sc.nextLine();
        Class cDao = Class.forName(daoClassName);
        Class cMetier = Class.forName(metierClassName);
        IDao dao = (IDao) cDao.newInstance();
        IMetier metier = (IMetier) cMetier.getConstructor(IDao.class).newInstance(dao);

        System.out.println("Res : " + metier.calcul() + "kelvin");


    }
}
