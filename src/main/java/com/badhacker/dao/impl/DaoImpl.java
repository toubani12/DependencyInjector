package com.badhacker.dao.impl;

import com.badhacker.dao.IDao;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("d")
public class DaoImpl implements IDao {
    @Override
    public double getData() {
        System.out.println("Version de base de donnees");
        double t = 29;
        return t;
    }
}
