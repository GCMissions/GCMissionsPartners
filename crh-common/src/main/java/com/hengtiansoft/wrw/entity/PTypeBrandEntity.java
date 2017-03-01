package com.hengtiansoft.wrw.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name ="P_TYPE_BRAND")
public class PTypeBrandEntity implements  Serializable{

	private static final long serialVersionUID = -5837492182613391684L;
	
	@EmbeddedId
	private PTypeBrandPK id;

	public PTypeBrandPK getId() {
		return id;
	}

	public void setId(PTypeBrandPK id) {
		this.id = id;
	}
	
	
	
}
