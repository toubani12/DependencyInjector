package com.badhacker.ext;

import com.badhacker.dao.IDao;
import org.springframework.stereotype.Component;

@Component("d2")
public class DaoImplV2 implements IDao {
    @Override
    public double getData() {
        System.out.println("version web service");
        double data = -158;
        return data;
    }
}
