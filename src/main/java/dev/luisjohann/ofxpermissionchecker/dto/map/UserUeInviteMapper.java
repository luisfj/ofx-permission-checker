package dev.luisjohann.ofxpermissionchecker.dto.map;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import dev.luisjohann.ofxpermissionchecker.dto.UserUeInviteDTO;
import dev.luisjohann.ofxpermissionchecker.dto.UserUeInviteDetailDTO;
import dev.luisjohann.ofxpermissionchecker.model.UserUeInviteEntity;

@Mapper
public interface UserUeInviteMapper {

   static UserUeInviteMapper INSTANCE = Mappers.getMapper(UserUeInviteMapper.class);

   UserUeInviteEntity dtoToEntity(UserUeInviteDTO dto);

   List<UserUeInviteDetailDTO> entityToInviteListDTO(List<UserUeInviteEntity> invites);
}