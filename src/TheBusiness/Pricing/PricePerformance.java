/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TheBusiness.Pricing;

/**
 *
 * @author jingyangwang
 */
public class PricePerformance {
    //supplier and product 
    private String supplierName;
    private String productId;
    private String productName;
    //price
    private int floorPrice;
    private int ceilingPrice;
    private int targetPriceBefore;
    private int targetPriceAfter;
    //product sold
    private int unitsSold;
    private double actualAvgPrice;
    private int belowTargetCount;
    private int aboveTargetCount;

    //getter and setter 
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(int floorPrice) {
        this.floorPrice = floorPrice;
    }

    public int getCeilingPrice() {
        return ceilingPrice;
    }

    public void setCeilingPrice(int ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
    }

    public int getTargetPriceBefore() {
        return targetPriceBefore;
    }

    public void setTargetPriceBefore(int targetPriceBefore) {
        this.targetPriceBefore = targetPriceBefore;
    }

    public int getTargetPriceAfter() {
        return targetPriceAfter;
    }

    public void setTargetPriceAfter(int targetPriceAfter) {
        this.targetPriceAfter = targetPriceAfter;
    }

    public int getUnitsSold() {
        return unitsSold;
    }

    public void setUnitsSold(int unitsSold) {
        this.unitsSold = unitsSold;
    }

    public double getActualAvgPrice() {
        return actualAvgPrice;
    }

    public void setActualAvgPrice(double actualAvgPrice) {
        this.actualAvgPrice = actualAvgPrice;
    }

    public int getBelowTargetCount() {
        return belowTargetCount;
    }

    public void setBelowTargetCount(int belowTargetCount) {
        this.belowTargetCount = belowTargetCount;
    }

    public int getAboveTargetCount() {
        return aboveTargetCount;
    }

    public void setAboveTargetCount(int aboveTargetCount) {
        this.aboveTargetCount = aboveTargetCount;
    }
    //重写方法
    @Override public String toString() {
        return "PricePerformance{" +
                "supplier='" + supplierName + '\'' +
                ", productId='" + productId + '\'' +
                ", name='" + productName + '\'' +
                ", floor=" + floorPrice +
                ", ceiling=" + ceilingPrice +
                ", targetBefore=" + targetPriceBefore +
                ", targetAfter=" + targetPriceAfter +
                ", units=" + unitsSold +
                ", avg=" + actualAvgPrice +
                ", below=" + belowTargetCount +
                ", above=" + aboveTargetCount +
                '}';
    }
}
