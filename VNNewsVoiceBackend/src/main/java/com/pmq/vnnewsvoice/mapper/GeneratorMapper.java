package com.pmq.vnnewsvoice.mapper;

import com.pmq.vnnewsvoice.dto.GeneratorDto;
import com.pmq.vnnewsvoice.pojo.Generator;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class GeneratorMapper implements Serializable {
    public GeneratorDto toDto(Generator generator){
        if(generator == null){
            return null;
        }
        GeneratorDto generatorDto = new GeneratorDto();
        generatorDto.setId(generator.getId());
        generatorDto.setName(generator.getName());
        generatorDto.setLogoUrl(generator.getLogoUrl());
        generatorDto.setUrl(generator.getUrl());
        generatorDto.setIsActive(generator.getIsActive());
        return generatorDto;
    }
}
