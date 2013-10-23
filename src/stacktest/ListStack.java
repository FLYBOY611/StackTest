/*******************************************************************************
* Module Name: ListStack										
*												
* Module Description: Defines how the movements work such as push, pop and so on
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
public class ListStack {
    StackItem head; 
 
    public ListStack(){
        
    }
     
    public void push(String element){
    //moves the element on top of the stack
        if(head == null){
            StackItem fire = new StackItem(element);
            head = fire;
        
        }else if(head != null){
            StackItem water = new StackItem(element);
            water.next = head;
            head = water;
      
        }
    }
    
    public String pop(){
    //Removes and return the top element from the stack
        
        String temp = head.x;         
        head = head.next;
        
        return temp;
        
    }
    
    
    public String peek(){
    //Returns the top element without removing it
        
        return head.x;
    }
    
       
    public boolean isEmpty(){
    //Returns true if the stack is empty
        if(head == null){
        return true;
        }else{
            return false;
            }
    }
    
     
    public int size(){
    //Returns the size of the list
        StackItem temp = head;
        int count = 0;
        
        while(temp != null){
            count++;
            temp = temp.next;
        }
        
        return count;
        
    }
    
    
    
    public void print(){
    //Prints out all contents of the stack
        System.out.println(toString());
    }
    
    public String toString(){
    //Prints out all contents of the stack   
        String output = ""; 
        StackItem temp = head;
        
         
        while(temp != null){
            output += temp.x;
            temp = temp.next;
        }
        
        return output;
    }
}