package dev.luisjohann.ofxpermissionchecker.dto.map;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import dev.luisjohann.ofxpermissionchecker.dto.UserUeDetailDTO;
import dev.luisjohann.ofxpermissionchecker.model.UserUeEntity;

@Mapper
public interface UserUeMapper {

   static UserUeMapper INSTANCE = Mappers.getMapper(UserUeMapper.class);

   @Mapping(source = "user.email", target = "email")
   @Mapping(source = "user.id", target = "userId")
   UserUeDetailDTO entityToTO(UserUeEntity userUeEntity);

   List<UserUeDetailDTO> entityToListDTO(List<UserUeEntity> userUeEntities);
}