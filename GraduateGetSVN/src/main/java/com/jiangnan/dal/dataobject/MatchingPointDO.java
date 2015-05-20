package com.jiangnan.dal.dataobject;

/**
 * Created by gaoshuai.gs on 2015/5/13.
 */
public class MatchingPointDO {
    private Double universityPoint;
    private Double addressPoint;
    private Double MoneyPoint;
    private Double companySizePoint;
    private Double postTimePoint;
    private Double deliveryThreePoint;

    public Double getUniversityPoint() {
        return universityPoint;
    }

    public void setUniversityPoint(Double universityPoint) {
        this.universityPoint = universityPoint;
    }

    public Double getAddressPoint() {
        return addressPoint;
    }

    public void setAddressPoint(Double addressPoint) {
        this.addressPoint = addressPoint;
    }

    public Double getMoneyPoint() {
        return MoneyPoint;
    }

    public void setMoneyPoint(Double moneyPoint) {
        MoneyPoint = moneyPoint;
    }

    public Double getCompanySizePoint() {
        return companySizePoint;
    }

    public void setCompanySizePoint(Double companySizePoint) {
        this.companySizePoint = companySizePoint;
    }

    public Double getPostTimePoint() {
        return postTimePoint;
    }

    public void setPostTimePoint(Double postTimePoint) {
        this.postTimePoint = postTimePoint;
    }

    public Double getDeliveryThreePoint() {
        return deliveryThreePoint;
    }

    public void setDeliveryThreePoint(Double deliveryThreePoint) {
        this.deliveryThreePoint = deliveryThreePoint;
    }

    @Override
    public String toString() {
        return "MatchingPointDO{" +
                "universityPoint=" + universityPoint +
                ", addressPoint=" + addressPoint +
                ", MoneyPoint=" + MoneyPoint +
                ", companySizePoint=" + companySizePoint +
                ", postTimePoint=" + postTimePoint +
                ", deliveryThreePoint=" + deliveryThreePoint +
                '}';
    }
}
