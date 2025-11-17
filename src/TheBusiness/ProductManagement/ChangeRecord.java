/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TheBusiness.ProductManagement;

/**
 *
 * @author parke
 */
public class ChangeRecord {
    
    int oldprice;

    public ChangeRecord(int oldprice) {
        this.oldprice = oldprice;
    }

    public int getOldprice() {
        return oldprice;
    }

    public void setOldprice(int oldprice) {
        this.oldprice = oldprice;
    }
    
    @Override 
    public String toString (){
        return String.valueOf(oldprice);
    }
    
}
