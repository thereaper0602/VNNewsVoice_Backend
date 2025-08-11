package com.pmq.vnnewsvoice.controller.restController;


import com.pmq.vnnewsvoice.dto.CategoryDto;
import com.pmq.vnnewsvoice.dto.GeneratorDto;
import com.pmq.vnnewsvoice.mapper.GeneratorMapper;
import com.pmq.vnnewsvoice.pojo.Generator;
import com.pmq.vnnewsvoice.service.GeneratorService;
import com.pmq.vnnewsvoice.service.impl.GeneratorServiceImpl;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiGeneratorController {

    @Autowired
    private GeneratorMapper generatorMapper;

    @Autowired
    private GeneratorService generatorService;

    @GetMapping("/generators")
    public ResponseEntity<List<GeneratorDto>> getCategoryList(
            @RequestParam Map<String, String> params
            ) {
        List<Generator> generators = generatorService.getGenerators(params);
        List<GeneratorDto> generatorDtos = generators.stream().map(generatorMapper::toDto).toList();
        return new ResponseEntity<>(generatorDtos, HttpStatus.OK);
    }
}
