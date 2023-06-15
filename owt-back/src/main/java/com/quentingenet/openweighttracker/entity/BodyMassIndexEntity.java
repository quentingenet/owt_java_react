package com.quentingenet.openweighttracker.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "body_mass_index")
public class BodyMassIndexEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_bmi")
  private Long idBmi;

  @Column(name = "body_mass_index")
  private Double bodyMassIndex;

  /*Getters and setters*/
  public Long getIdBmi() {
    return idBmi;
  }

  public void setIdBmi(Long idBmi) {
    this.idBmi = idBmi;
  }

  public Double getBodyMassIndex() {
    return bodyMassIndex;
  }

  public void setBodyMassIndex(Double bodyMassIndex) {
    this.bodyMassIndex = bodyMassIndex;
  }

}
