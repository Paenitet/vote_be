package com.example.demo.service;

import com.example.demo.dao.CustomerDAO;
import com.example.demo.util.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

@Log4j2
public enum CustomerService {
    INSTANCE;

    private CustomerDAO dao;
    private ModelMapper modelMapper;

    CustomerService() {
        dao = new CustomerDAO();
        modelMapper = MapperUtil.INSTANCE.get();
    }
}
