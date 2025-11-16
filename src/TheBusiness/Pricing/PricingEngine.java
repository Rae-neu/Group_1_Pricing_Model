/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TheBusiness.Pricing;

import TheBusiness.Business.Business;
import TheBusiness.OrderManagement.MasterOrderList;
import TheBusiness.OrderManagement.Order;
import TheBusiness.OrderManagement.OrderItem;
import TheBusiness.ProductManagement.Product;
import TheBusiness.Supplier.Supplier;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jingyangwang
 */
public class PricingEngine {
    //final class no inheritance
    private final Business business;

    //set threshold
    private double underTargetRatioThreshold = 0.60; 
    private double overTargetRatioThreshold  = 0.80; 
    private double priceDecreaseStep         = 0.05; 
    private double priceIncreaseStep         = 0.05; 
    
    //constructor
    public PricingEngine(Business business) { 
        this.business = business; 
    }
    //setter 
    public void setUnderTargetRatioThreshold(double v){
        this.underTargetRatioThreshold = v; 
    }
    public void setOverTargetRatioThreshold(double v){ 
        this.overTargetRatioThreshold = v; 
    }
    public void setPriceDecreaseStep(double v){
        this.priceDecreaseStep = v; 
    }
    public void setPriceIncreaseStep(double v){ 
        this.priceIncreaseStep = v; 
    }

    //analyze product method
    public PricePerformance analyzeProduct(Supplier supplier, Product p) {
        MasterOrderList mol = business.getMasterOrderList();

        int units = 0;
        int below = 0;
        int above = 0;
        double revenue = 0.0;
        
        int target = p.getTargetPrice();         
        int floor  = p.getFloorPrice();
        int ceil   = p.getCeilingPrice();

        //loop for orders
        //MasterOrderList 里加 getOrders
        for (Order o : mol.getOrders()) {     
            //Order 里加 getOrderItems
            //loop for order items in each order
            for (OrderItem oi : o.getOrderItems()) { 
                //get selected product
                Product selectedProduct = oi.getSelectedProduct(); 
                if (selectedProduct == p) {
                    //get quantity and actual price from order items
                    int quantity = oi.getQuantity();
                    double price = oi.getActualPrice();  
                    //sum the units sold and revenue
                    units = units + quantity;
                    revenue = revenue +  price * quantity;
                    
                    if (price < target) below++;
                    else if (price > target) above++;
                }
            }
            
        }
    //instantiate Price Performance
    PricePerformance perf = new PricePerformance();
        perf.setSupplierName(supplier.getName());
        perf.setProductId(p.toString());     
        perf.setProductName(p.toString());
        perf.setFloorPrice(floor);
        perf.setCeilingPrice(ceil);
        perf.setTargetPriceBefore(target);
        perf.setTargetPriceAfter(target);        
        perf.setUnitsSold(units);
        perf.setActualAvgPrice(units == 0 ? 0.0 : revenue / units);
        perf.setBelowTargetCount(below);
        perf.setAboveTargetCount(above);
        return perf;
    }

    //decide if increase or decrease the sales price based on performance
    //then set targetPriceAfter 
    public PricePerformance adjustTargetPrice(PricePerformance perf) {
        //get the previous targer price
        int before = perf.getTargetPriceBefore();
        int floor  = perf.getFloorPrice();
        int ceil   = perf.getCeilingPrice();
        
        //get the total number of sales = sales above average + sales below average
        int total = perf.getBelowTargetCount() + perf.getAboveTargetCount();
        //calculate the above/below ratio
        double belowRatio = (total == 0) ? 0.0 : (perf.getBelowTargetCount() * 1.0 / total);
        double aboveRatio = (total == 0) ? 0.0 : (perf.getAboveTargetCount() * 1.0 / total);
        //decide if to increase or decrease the price in comparison with the predefined threshold
        //if below ratio is higher than expected threshold, then the target price should decrease by 10%
        boolean lower = belowRatio >= (1.0 - overTargetRatioThreshold) //1-0.8
                     || perf.getActualAvgPrice() <= 0.90 * before;
        //if the above ratio is higher than expected threshold, then the target price should increase by 5%
        boolean raise = aboveRatio >= overTargetRatioThreshold
                     || perf.getActualAvgPrice() >= 1.05 * before;
        //define the new target price
        int after = before;
        if (lower && !raise) {
            after = Math.max((int)Math.round(before * (1.0 - priceDecreaseStep)), floor);
        } else if (raise && !lower) {
            after = Math.min((int)Math.round(before * (1.0 + priceIncreaseStep)), ceil);
        } else if (raise && lower) {
            int towardsAvg = (int)Math.round(perf.getActualAvgPrice());
            after = Math.max(floor, Math.min(ceil, towardsAvg));
        }
        //set the new target price to the price performance
        perf.setTargetPriceAfter(after);
        return perf;
    }

    
    public List<PricePerformance> analyzeAllAndApply(boolean writeBack) {
        List<PricePerformance> out = new ArrayList<>();
        //get the supplier list and loop for each supplier
        for (Supplier s : business.getSupplierDirectory().getSupplierList()) {
            //get each supplier's product list and loop for each product
            for (Product p : s.getProductCatalog().getProductList()) {
                //adjust target price for each product
                PricePerformance perf = analyzeProduct(s, p);
                adjustTargetPrice(perf);
                //if write back is true then update the target sales price
                if (writeBack) {
                    p.setTargetPrice(perf.getTargetPriceAfter());
                }
                out.add(perf);
            }
        }
        return out;
    }    
        
}