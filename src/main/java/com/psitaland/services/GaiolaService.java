package com.psitaland.services;

import com.psitaland.repositories.GaiolaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GaiolaService {

    @Autowired
    private GaiolaRepository gaiolaRepository;

    @Transactional
    public void criarGaiola(){

    }
}
