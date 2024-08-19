package dev.luisjohann.ofxpermissionchecker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.luisjohann.ofxpermissionchecker.model.UeEntity;

@Repository
public interface UeRepository extends CrudRepository<UeEntity, Long> {

}
