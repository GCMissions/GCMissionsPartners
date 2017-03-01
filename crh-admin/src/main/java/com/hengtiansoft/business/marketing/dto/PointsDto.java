package com.hengtiansoft.business.marketing.dto;

import java.io.Serializable;

public class PointsDto implements Serializable {

    private static final long serialVersionUID = 5930427920421838578L;

    private String            registerPoints;

    private String            buyAmount;

    private String            buyPoints;

    public String getRegisterPoints() {
        return registerPoints;
    }

    public void setRegisterPoints(String registerPoints) {
        this.registerPoints = registerPoints;
    }

    public String getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(String buyAmount) {
        this.buyAmount = buyAmount;

    }

    public String getBuyPoints() {
        return buyPoints;
    }

    public void setBuyPoints(String buyPoints) {
        this.buyPoints = buyPoints;
    }

}
