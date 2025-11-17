/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TheBusiness.ProductManagement;

/**
 *
 * @author kal bugrara
 */
//this class will extract summary data from the product
public class ProductSummary {

    Product subjectproduct;
    int numberofsalesabovetarget;
    int numberofsalesbelowtarget;
    int productpriceperformance; //total profit above target --could be negative too
    int acutalsalesvolume;
    int rank; // will be done later

    public ProductSummary(Product p) {
        
        subjectproduct = p; //keeps track of the product itself not as well;
        numberofsalesabovetarget = p.getNumberOfProductSalesAboveTarget();
        productpriceperformance = p.getOrderPricePerformance();
        acutalsalesvolume = p.getSalesVolume();
        numberofsalesbelowtarget = p.getNumberOfProductSalesBelowTarget();
    }

    public int getSalesRevenues() {
        return acutalsalesvolume;
    }

    public int getNumberAboveTarget() {
        return numberofsalesabovetarget;
    }

    public int getProductPricePerformance() {
        return productpriceperformance;
    }

    public int getNumberBelowTarget() {
        return numberofsalesbelowtarget;
    }

    public boolean isProductAlwaysAboveTarget() {
        return false; // to be implemented
    }

    public Product getSubjectproduct() {
        return subjectproduct;
    }

    public void setSubjectproduct(Product subjectproduct) {
        this.subjectproduct = subjectproduct;
    }

    public int getNumberofsalesabovetarget() {
        return numberofsalesabovetarget;
    }

    public void setNumberofsalesabovetarget(int numberofsalesabovetarget) {
        this.numberofsalesabovetarget = numberofsalesabovetarget;
    }

    public int getNumberofsalesbelowtarget() {
        return numberofsalesbelowtarget;
    }

    public void setNumberofsalesbelowtarget(int numberofsalesbelowtarget) {
        this.numberofsalesbelowtarget = numberofsalesbelowtarget;
    }

    public int getProductpriceperformance() {
        return productpriceperformance;
    }

    public void setProductpriceperformance(int productpriceperformance) {
        this.productpriceperformance = productpriceperformance;
    }

    public int getAcutalsalesvolume() {
        return acutalsalesvolume;
    }

    public void setAcutalsalesvolume(int acutalsalesvolume) {
        this.acutalsalesvolume = acutalsalesvolume;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    //Parker- we dont need this redudant method
    
       public void maxTargetPrice(ProductSummary summary){
           Product product = summary.getSubjectproduct();
           int actualprice = product.getTargetPrice();
           
           int newtargetprice = Math.min(product.getFloorPrice(),Math.max(actualprice, product.getCeilingPrice()));
           
           product.setTargetPrice(newtargetprice);
           
        
        
    }


    
     @Override
    public String toString() {
        return  subjectproduct.toString();
    }
}
