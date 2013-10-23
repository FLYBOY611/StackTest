/*******************************************************************************
* Module Name: StackItem										
*												
* Module Description: Creates the format of the items that go into our list
*												
* @Param:											
*												
* @Return:                      									
*												
*													
*******************************************************************************/
package stacktest;

/**
 *
 * @author Richard
 */
public class StackItem {
    public String x;
    public StackItem next;
    
    public StackItem(String init_x){
        x = init_x;
    }
    public StackItem(String init_x, StackItem init_next){
        x = init_x;
        next = init_next;
    }
}
