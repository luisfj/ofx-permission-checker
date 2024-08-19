package dev.luisjohann.ofxpermissionchecker.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.luisjohann.ofxpermissionchecker.model.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>{
   
   Optional<UserEntity> findByEmail(String email);

   Optional<UserEntity> findByIdKeyCloack(String idKeyCloack);
}
