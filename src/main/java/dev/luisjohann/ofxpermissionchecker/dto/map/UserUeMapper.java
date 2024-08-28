package dev.luisjohann.ofxpermissionchecker.dto.map;

import dev.luisjohann.ofxpermissionchecker.dto.UeUserCanAccessDTO;
import dev.luisjohann.ofxpermissionchecker.dto.UserUeDetailDTO;
import dev.luisjohann.ofxpermissionchecker.model.UserUeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserUeMapper {

    static UserUeMapper INSTANCE = Mappers.getMapper(UserUeMapper.class);

    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.id", target = "userId")
    UserUeDetailDTO entityToDTO(UserUeEntity userUeEntity);

    @Mapping(source = "createdBy.email", target = "createdEmail")
    @Mapping(source = "createdBy.id", target = "createdUserId")
    @Mapping(source = "ue.name", target = "ueName")
    @Mapping(source = "ue.id", target = "ueId")
    @Mapping(source = "ue.color", target = "color")
    UeUserCanAccessDTO entityToUeUserCanAccessDTO(UserUeEntity userUeEntity);

    List<UserUeDetailDTO> entityToListDTO(List<UserUeEntity> userUeEntities);

    List<UeUserCanAccessDTO> entityToUeUserCanAccessListDTO(List<UserUeEntity> userUeEntities);
}