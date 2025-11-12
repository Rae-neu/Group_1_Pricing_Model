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
        for (Order o : mol.getOrders()) {        
            for (OrderItem oi : o.getOrderItems()) { 
                Product selected = oi.getSelectedProduct(); 
                if (selected == p) {
                    int q = oi.getQuantity();
                    double price = oi.getActualPrice();     // 可能叫 getSalesPrice()/getSellingPrice()
                    units += q;
                    revenue += price * q;
                    if (price < target) below++;
                    else if (price > target) above++;
                }
            }
        }

        PricePerformance perf = new PricePerformance();
        perf.setSupplierName(supplier.getName());
        perf.setProductId(p.getProductId());     // 若没有 id，可用 p.getName()
        perf.setProductName(p.getName());
        perf.setFloorPrice(floor);
        perf.setCeilingPrice(ceil);
        perf.setTargetPriceBefore(target);
        perf.setTargetPriceAfter(target);        // 先等于 before
        perf.setUnitsSold(units);
        perf.setActualAvgPrice(units == 0 ? 0.0 : revenue / units);
        perf.setBelowTargetCount(below);
        perf.setAboveTargetCount(above);
        return perf;
    }

    /** 根据表现决定是否升/降价，并设置 targetPriceAfter */
    public PricePerformance adjustTargetPrice(PricePerformance perf) {
        int before = perf.getTargetPriceBefore();
        int floor  = perf.getFloorPrice();
        int ceil   = perf.getCeilingPrice();

        int total = perf.getBelowTargetCount() + perf.getAboveTargetCount();
        double belowRatio = (total == 0) ? 0.0 : (perf.getBelowTargetCount() * 1.0 / total);
        double aboveRatio = (total == 0) ? 0.0 : (perf.getAboveTargetCount() * 1.0 / total);

        boolean lower = belowRatio >= (1.0 - overTargetRatioThreshold)
                     || perf.getActualAvgPrice() <= 0.90 * before;
        boolean raise = aboveRatio >= overTargetRatioThreshold
                     || perf.getActualAvgPrice() >= 1.05 * before;

        int after = before;
        if (lower && !raise) {
            after = Math.max((int)Math.round(before * (1.0 - priceDecreaseStep)), floor);
        } else if (raise && !lower) {
            after = Math.min((int)Math.round(before * (1.0 + priceIncreaseStep)), ceil);
        } else if (raise && lower) {
            int towardsAvg = (int)Math.round(perf.getActualAvgPrice());
            after = Math.max(floor, Math.min(ceil, towardsAvg));
        }
        perf.setTargetPriceAfter(after);
        return perf;
    }

    /** 批量遍历所有供应商/产品；writeBack=true 时把新 target 写回 Product */
    public List<PricePerformance> analyzeAllAndApply(boolean writeBack) {
        List<PricePerformance> out = new ArrayList<>();
        for (Supplier s : business.getSupplierDirectory().getSupplierList()) {
            for (Product p : s.getProductCatalog().getProductList()) {
                PricePerformance perf = analyzeProduct(s, p);
                adjustTargetPrice(perf);
                if (writeBack) {
                    p.setTargetPrice(perf.getTargetPriceAfter());
                }
                out.add(perf);
            }
        }
        return out;
    }
}

