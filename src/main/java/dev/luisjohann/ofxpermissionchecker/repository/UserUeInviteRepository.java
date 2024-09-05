package dev.luisjohann.ofxpermissionchecker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.luisjohann.ofxpermissionchecker.enums.StatusUserInvite;
import dev.luisjohann.ofxpermissionchecker.model.UserUeInviteEntity;

@Repository
public interface UserUeInviteRepository extends CrudRepository<UserUeInviteEntity, Long> {

   Optional<UserUeInviteEntity> findByIdAndEmailIgnoreCase(Long id, String email);

   @Query("""
            select uu from UserUeInviteEntity uu 
               join fetch uu.ue ue 
               join fetch uu.createdBy userCr 
            where 
               uu.email = :email 
               and uu.status = :status
            """)
   List<UserUeInviteEntity> findByEmailIgnoreCaseAndStatus(String email, StatusUserInvite status);

   List<UserUeInviteEntity> findByUeId(Long ueId);

   Optional<UserUeInviteEntity> findByIdAndUeId(Long inviteId, Long ueId);

   boolean existsByEmailAndUeIdAndStatus(String email, Long ueId, StatusUserInvite status);

   Optional<UserUeInviteEntity> findByIdAndUeIdAndStatus(Long id, Long ueId, StatusUserInvite status);
}
