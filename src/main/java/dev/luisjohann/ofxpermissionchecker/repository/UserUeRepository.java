package dev.luisjohann.ofxpermissionchecker.repository;

import dev.luisjohann.ofxpermissionchecker.model.UserUeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserUeRepository extends CrudRepository<UserUeEntity, Long> {

    boolean existsByUserEmailAndUeId(String userEmail, Long ueId);

    boolean existsByUserIdNotAndUeIdAndAdministratorTrue(Long userId, Long ueId);

    @Query("""
            select uu from UserUeEntity uu 
               join fetch uu.ue ue 
               join fetch uu.createdBy userCr 
            where 
               uu.user.email = :email 
               and uu.status = 'ACTIVE'
            """)
    List<UserUeEntity> findByUserEmail(String email);

    Optional<UserUeEntity> findByUserIdAndUeId(Long userId, Long ueId);

    Optional<UserUeEntity> findByUserEmailAndUeId(String userEmail, Long ueId);

    @Query("select uu from UserUeEntity uu join fetch uu.user us where uu.ue.id = :ueId")
    List<UserUeEntity> findByUeId(@Param("ueId") Long ueId);
}
