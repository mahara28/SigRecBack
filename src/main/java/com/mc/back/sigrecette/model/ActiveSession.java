package com.mc.back.sigrecette.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ACTIVE_SESSIONS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveSession {

    @Id
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "TOKEN", length = 500)
    private String token;

    @Column(name = "CREATED_AT")
    private Timestamp createdAt;
    
    
    @Column(name = "REFRESH_TOKEN", length = 500)
    private String refreshToken;

    @Column(name = "REFRESH_EXPIRES_AT")
    private Timestamp refreshExpiresAt;
    
    
 
}
