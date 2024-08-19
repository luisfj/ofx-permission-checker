package dev.luisjohann.ofxpermissionchecker.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import dev.luisjohann.ofxpermissionchecker.enums.StatusUser;
import dev.luisjohann.ofxpermissionchecker.enums.StatusUserInvite;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "USER_UE_INVITE")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUeInviteEntity {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      Long id;
      @Column(nullable = false)
      String email;
      @ManyToOne(fetch = FetchType.LAZY, optional = false)
      @JoinColumn(name = "id_ue", nullable = false)
      UeEntity ue;
      boolean administrator;
      @Column(name = "can_read", nullable = false)
      boolean permissionRead;
      @Column(name = "can_write", nullable = false)
      boolean permissionWrite;
      @Column(name = "can_import", nullable = false)
      boolean permissionImport;
      @Column(name = "status", nullable = false)
      @Enumerated(EnumType.STRING)
      StatusUserInvite status;
      @ManyToOne(fetch = FetchType.LAZY, optional = false)
      @JoinColumn(name = "created_by", nullable = false)
      UserEntity createdBy;
      @Column(name = "created_at", nullable = false)
      @Temporal(TemporalType.TIMESTAMP)
      @CreatedDate
      LocalDateTime createdAt;
}