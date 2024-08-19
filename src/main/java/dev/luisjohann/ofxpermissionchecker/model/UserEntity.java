package dev.luisjohann.ofxpermissionchecker.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import dev.luisjohann.ofxpermissionchecker.enums.StatusUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "OFX_USER")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      Long id;
      @Column(nullable = false)
      String name;
      @Column(nullable = false)
      String email;
      @Column(name = "id_keycloak")
      String idKeyCloack;
      @Column(name = "created_at", nullable = false)
      @Temporal(TemporalType.TIMESTAMP)
      @CreatedDate
      LocalDateTime createdAt;
      @Column(name = "status", nullable = false)
      @Enumerated(EnumType.STRING)
      StatusUser status;
}