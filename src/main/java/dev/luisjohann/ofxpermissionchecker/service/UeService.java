package dev.luisjohann.ofxpermissionchecker.service;

import dev.luisjohann.ofxpermissionchecker.dto.SseUeChangeMessageDTO;
import dev.luisjohann.ofxpermissionchecker.dto.UeRegisterDTO;
import dev.luisjohann.ofxpermissionchecker.dto.map.UeMapper;
import dev.luisjohann.ofxpermissionchecker.enums.StatusActiveInactive;
import dev.luisjohann.ofxpermissionchecker.exceptions.ImportOfxException;
import dev.luisjohann.ofxpermissionchecker.queue.QueueSenderUeChange;
import dev.luisjohann.ofxpermissionchecker.repository.UeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UeService {

    final UeRepository ueRepository;
    final UserUeService userUeService;
    final AuthService authService;
    final QueueSenderUeChange queueSenderUeChange;

    @Transactional
    public void createUe(UeRegisterDTO dto) {
        var newUe = UeMapper.INSTANCE.dtoToEntity(dto);

        newUe.setCreatedAt(LocalDateTime.now());
        newUe.setStatus(StatusActiveInactive.ACTIVE);

        var saved = ueRepository.save(newUe);
        userUeService.inviteUserAsAdminToUe(authService.getLoggedUser(), saved);
        queueSenderUeChange.send(new SseUeChangeMessageDTO(saved.getId(), saved.getName(), saved.getStatus()));
    }

    @Transactional
    public void updateUe(Long ueId, UeRegisterDTO dto) {
        var ue = ueRepository.findById(ueId)
                .orElseThrow(() -> new ImportOfxException("Ue não encontrada"));

        ue.setName(dto.name());
        ue.setColor(dto.color());

        var saved = ueRepository.save(ue);
        queueSenderUeChange.send(new SseUeChangeMessageDTO(saved.getId(), saved.getName(), saved.getStatus()));
    }

}
