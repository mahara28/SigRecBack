package com.mc.back.sigrecette.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notification_user", schema = "public")
public class NotificationUser {
	
	@Id
    @Column(name = "id", nullable = false, precision = 22)
    private Long id;
	
	@Column(name = "id_notif", precision = 22)
    private Long idNotif;
	
	@Column(name = "id_user_rec", precision = 22)
    private Long idUserRec;
	
	@Column(name = "date_reception")
    private Instant dateReception;
	
	@Column(name = "lu")
    private Integer lu;

}
