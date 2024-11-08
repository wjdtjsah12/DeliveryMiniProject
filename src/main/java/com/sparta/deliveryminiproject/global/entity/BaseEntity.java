package com.sparta.deliveryminiproject.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

  @CreatedDate
  @Column(updatable = false, nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createdAt;

  @CreatedBy
  @Column(updatable = false, nullable = false, length = 100)
  private String createdBy;

  @LastModifiedDate
  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime updatedAt;

  @LastModifiedBy
  @Column(nullable = false, length = 100)
  private String updatedBy;

  @Column(nullable = false)
  private Boolean isDeleted = false;

}