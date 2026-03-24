package com.mc.back.sigrecette.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Data
@Entity
@Table(name = "adm_fonc")
public class AdmFonc {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_parent")
    private Long idParent;

    @Size(max = 40)
    @NotNull
    @Column(name = "code", nullable = false, length = 40)
    private String code;

    @Size(max = 300)
    @Column(name = "icon", length = 300)
    private String icon;

    @Size(max = 300)
    @NotNull
    @Column(name = "des_fr", nullable = false, length = 300)
    private String desFr;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "is_active", nullable = false)
    private Long isActive;

    @Size(max = 1500)
    @Column(name = "router", length = 1500)
    private String router;

    @Size(max = 300)
    @Column(name = "des_en", length = 300)
    private String desEn;
    
    @Size(max = 300)
    @Column(name = "code_translate", length = 300)
    private String codeTranslate;

    @Transient
    private List<AdmFonc> listSousMenu;

    @Transient
    private Boolean checked = false;
    
    @Transient
    private Long isList;

    @Transient
    private Long isUpdate;

    @Transient
    private Long isSupp;

    @Transient
    private Long isDetails;

    @Transient
    private Long isExport;

    @Transient
    private Long isImprime;

    @Transient
    private Long isAdd;

}