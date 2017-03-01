package com.hengtiansoft.business.setting.dto;

import java.io.Serializable;


/**
 * @author jiekaihu
 *
 */
public class SBasicParaDto implements Serializable{
	
	private static final long serialVersionUID = -960967784859359168L;

	private Integer           paraId;

    private Integer           typeId;

    private String            paraName;

    private String            paraValue1;

    private String            paraValue2;

    private String            paraValue3;

    private String            paraValue4;

    private String            remark;

	public Integer getParaId() {
		return paraId;
	}

	public void setParaId(Integer paraId) {
		this.paraId = paraId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getParaName() {
		return paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}

	public String getParaValue1() {
		return paraValue1;
	}

	public void setParaValue1(String paraValue1) {
		this.paraValue1 = paraValue1;
	}

	public String getParaValue2() {
		return paraValue2;
	}

	public void setParaValue2(String paraValue2) {
		this.paraValue2 = paraValue2;
	}

	public String getParaValue3() {
		return paraValue3;
	}

	public void setParaValue3(String paraValue3) {
		this.paraValue3 = paraValue3;
	}

	public String getParaValue4() {
		return paraValue4;
	}

	public void setParaValue4(String paraValue4) {
		this.paraValue4 = paraValue4;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
}
