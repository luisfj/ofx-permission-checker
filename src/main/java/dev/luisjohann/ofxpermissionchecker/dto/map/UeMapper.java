package dev.luisjohann.ofxpermissionchecker.dto.map;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import dev.luisjohann.ofxpermissionchecker.dto.UeRegisterDTO;
import dev.luisjohann.ofxpermissionchecker.model.UeEntity;

@Mapper
// (componentModel = "spring")
public interface UeMapper {

   static UeMapper INSTANCE = Mappers.getMapper(UeMapper.class);
   
   UeEntity dtoToEntity(UeRegisterDTO dto);
}