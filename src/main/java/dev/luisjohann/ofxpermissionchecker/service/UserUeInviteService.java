package dev.luisjohann.ofxpermissionchecker.service;

import dev.luisjohann.ofxpermissionchecker.dto.UserUeInviteDTO;
import dev.luisjohann.ofxpermissionchecker.dto.UserUeInviteDetailDTO;
import dev.luisjohann.ofxpermissionchecker.dto.UserUeInviteDetailUeDTO;
import dev.luisjohann.ofxpermissionchecker.dto.map.UserUeInviteMapper;
import dev.luisjohann.ofxpermissionchecker.enums.StatusUserInvite;
import dev.luisjohann.ofxpermissionchecker.exceptions.ImportOfxException;
import dev.luisjohann.ofxpermissionchecker.exceptions.UnauthorizedException;
import dev.luisjohann.ofxpermissionchecker.repository.UeRepository;
import dev.luisjohann.ofxpermissionchecker.repository.UserUeInviteRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserUeInviteService {

    final UserUeInviteRepository userUeInviteRepository;
    final UserUeService userUeService;
    final UeRepository ueRepository;
    final AuthService authService;

    public void inviteUser(UserUeInviteDTO inviteDTO, Long ueId) {
        if (userUeService.existsByUserEmailAndUeId(inviteDTO.email(), ueId) ||
                userUeInviteRepository.existsByEmailAndUeIdAndStatus(inviteDTO.email(), ueId, StatusUserInvite.INVITED))
            throw new ImportOfxException("Convite já existe ou usuário já possui acesso a UE");

        var ue = ueRepository.findById(ueId).orElseThrow(() -> new NotFoundException("UE informada não existe"));
        var invite = UserUeInviteMapper.INSTANCE.dtoToEntity(inviteDTO);
        invite.setStatus(StatusUserInvite.INVITED);
        invite.setUe(ue);
        invite.setCreatedAt(LocalDateTime.now());
        invite.setCreatedBy(authService.getLoggedUser());

        userUeInviteRepository.save(invite);
    }

    public void updateInviteUser(UserUeInviteDTO inviteDTO, Long ueId, Long inviteId) {
        var invite = userUeInviteRepository.findByIdAndUeIdAndStatus(inviteId, ueId, StatusUserInvite.INVITED)
                .orElseThrow(() -> new ImportOfxException("Não existe convite com os parâmetros informados"));

        invite.setStatus(StatusUserInvite.INVITED);
        invite.setEmail(inviteDTO.email());
        invite.setAdministrator(inviteDTO.administrator());
        invite.setPermissionImport(inviteDTO.permissionImport());
        invite.setPermissionRead(inviteDTO.permissionRead());
        invite.setPermissionWrite(inviteDTO.permissionWrite());

        userUeInviteRepository.save(invite);
    }

    @Transactional
    public void confirmInvite(Long inviteId) {
        var loggedUser = authService.getLoggedUser();

        var invite = userUeInviteRepository.findByIdAndEmailIgnoreCase(inviteId, loggedUser.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Acesso negado"));

        invite.setStatus(StatusUserInvite.CONFIRMED);

        userUeInviteRepository.save(invite);

        userUeService.addAccessFromConfimedInvite(invite, loggedUser);
    }

    public void rejectInvite(Long inviteId) {
        var loggedUser = authService.getLoggedUser();

        var invite = userUeInviteRepository.findByIdAndEmailIgnoreCase(inviteId, loggedUser.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Acesso negado"));

        invite.setStatus(StatusUserInvite.REJECTED);

        userUeInviteRepository.save(invite);
    }

    public void deleteInvite(Long ueId, Long inviteId) {
        var invite = userUeInviteRepository.findByIdAndUeId(inviteId, ueId)
                .orElseThrow(() -> new ImportOfxException("Invite não encontrado"));

        userUeInviteRepository.delete(invite);
    }

    public List<UserUeInviteDetailDTO> findUeInvites(Long ueId) {
        var invites = userUeInviteRepository.findByUeId(ueId);

        return UserUeInviteMapper.INSTANCE.entityToInviteListDTO(invites);
    }

    public List<UserUeInviteDetailUeDTO> findMyInvites() {
        var invites = userUeInviteRepository.findByEmailIgnoreCaseAndStatus(authService.getLoggedUserEmail(), StatusUserInvite.INVITED);

        return UserUeInviteMapper.INSTANCE.entityToInviteDetailUeListDTO(invites);
    }

}
