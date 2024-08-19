package dev.luisjohann.ofxpermissionchecker.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import dev.luisjohann.ofxpermissionchecker.enums.StatusActiveInactive;
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

@Table(name = "UNIDADE_ECONOMICA")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UeEntity {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      Long id;
      @Column(nullable = false)
      String name;
      @Column(name = "created_at", nullable = false)
      @Temporal(TemporalType.TIMESTAMP)
      @CreatedDate
      LocalDateTime createdAt;
      @Column(name = "status", nullable = false)
      @Enumerated(EnumType.STRING)
      StatusActiveInactive status;
}