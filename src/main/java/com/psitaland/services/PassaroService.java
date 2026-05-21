package com.psitaland.services;

import com.psitaland.models.Gaiola;
import com.psitaland.models.Passaro;
import com.psitaland.repositories.GaiolaRepository;
import com.psitaland.repositories.PassaroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassaroService {

    @Autowired
    private PassaroRepository passaroRepository;
    @Autowired
    private GaiolaRepository gaiolaRepository;

    @Transactional // Garante que se algo der errado, nada mude no banco
    public void moverParaGaiola(Integer passaroId, Integer gaiolaId) {
        Passaro passaro = passaroRepository.findById(passaroId)
                .orElseThrow(() -> new RuntimeException("Pássaro não encontrado!"));

        Gaiola novaGaiola = gaiolaRepository.findById(gaiolaId)
                .orElseThrow(() -> new RuntimeException("Gaiola destino não existe!"));


        if (passaro.getGaiola().getId().equals(gaiolaId)) {
            throw new RuntimeException("O pássaro já está nesta gaiola.");
        }

        passaro.setGaiola(novaGaiola);

        passaroRepository.save(passaro);
    }
}