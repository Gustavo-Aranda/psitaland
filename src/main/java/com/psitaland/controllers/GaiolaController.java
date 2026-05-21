package com.psitaland.controllers;

import com.psitaland.services.GaiolaService;
import com.psitaland.services.PassaroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/gaiolas")
public class GaiolaController {

    @Autowired
    private GaiolaService gaiolaService;


}
