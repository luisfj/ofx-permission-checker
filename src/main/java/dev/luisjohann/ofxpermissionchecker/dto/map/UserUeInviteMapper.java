package dev.luisjohann.ofxpermissionchecker.dto.map;

import dev.luisjohann.ofxpermissionchecker.dto.UserUeInviteDTO;
import dev.luisjohann.ofxpermissionchecker.dto.UserUeInviteDetailDTO;
import dev.luisjohann.ofxpermissionchecker.dto.UserUeInviteDetailUeDTO;
import dev.luisjohann.ofxpermissionchecker.model.UserUeInviteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserUeInviteMapper {

    static UserUeInviteMapper INSTANCE = Mappers.getMapper(UserUeInviteMapper.class);

    UserUeInviteEntity dtoToEntity(UserUeInviteDTO dto);

    @Mapping(source = "createdBy.name", target = "createdName")
    @Mapping(source = "ue.name", target = "ueName")
    UserUeInviteDetailUeDTO entityToDTO(UserUeInviteEntity entity);

    List<UserUeInviteDetailDTO> entityToInviteListDTO(List<UserUeInviteEntity> invites);

    List<UserUeInviteDetailUeDTO> entityToInviteDetailUeListDTO(List<UserUeInviteEntity> invites);
}