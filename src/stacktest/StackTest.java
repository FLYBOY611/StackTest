/*******************************************************************************
* Module Name: StackTest										
*												
* Module Description: Takes an infix expression and converts it to a postfix
* expression while also outputting the evaluation. Also includes error checking
*												
* @Param:											
*												
* @Return:                      									
*												
*													
*******************************************************************************/
                                                                                                                                         
                                                                     
package stacktest;
import java.util.Scanner;
/**
 *
 * @author Richard
 */
 

public class StackTest {
      
    
    public static void main(String[] args) {
        String entry = "0";

        
        //Start the scanner and enter the first line
        System.out.println("Welcome to the stack program!");
        
        //Keep running so long as an empty expression isn't given
        while(!entry.equals("")){
            System.out.println("\n" + "Please enter in characters, "
                    + "enter an empty expression to end");
            Scanner input = new Scanner(System.in);
            entry = input.nextLine();
            boolean ErrorFree = true;

            //Check for errors in the statement
            ErrorFree = ErrorCheck(entry);
            if(ErrorFree == true){
    
             PostfixAndAnswer GrandFinale = InfixToPost(entry);

             System.out.println("The original infix is: " + entry);
             System.out.println("The postfix is:" + GrandFinale.postfix);
             System.out.print("The evaluation is " + GrandFinale.answer + "\n");
           }
        }
        

}      
      
   
   
public static PostfixAndAnswer InfixToPost(String InfixExpression){
            
        ListStack Listo = new ListStack();
        String WorkingEntry = "";
        String postfix = "";
   
        
        //Replace all blank space in the String entry 
        WorkingEntry = InfixExpression.replaceAll("\\s+",""); 

      
        //Scroll through the String entry left to right
        for(int i = 0; i<WorkingEntry.length(); i++){
            
            String currentItem = "";
            //If its a Digit move it to the postfix
            if(Character.isDigit(WorkingEntry.charAt(i)) == true)  {                                
                int k = i;
                
                //Check to see how many digits the number is 
                while(k<WorkingEntry.length() 
                        && Character.isDigit(WorkingEntry.charAt(k)) == true){
                   
                    currentItem += Character.toString(WorkingEntry.charAt(k));
                    i=k;
                    k++;
                    }
          
                postfix = postfix + " " + currentItem;               
                
            }else{
                currentItem = Character.toString(WorkingEntry.charAt(i));
            }
            
           //If its a left brace push it to the stack
           if(WorkingEntry.charAt(i) == '('){
                Listo.push(Character.toString(WorkingEntry.charAt(i)));
                
                }
           
           //If its an Operator
            if(WorkingEntry.charAt(i) == '+' || WorkingEntry.charAt(i) == '-' ||
                WorkingEntry.charAt(i) == '*' || WorkingEntry.charAt(i) == '/'){
                //Push to the stack if the stack is empty
                //OR if the stack item is a left paren                
                if(Listo.isEmpty() == true || Listo.peek().equals("(")){
                    Listo.push(Character.toString(WorkingEntry.charAt(i)));
                }
                 //Otherwise we compare the precedence of the operators
                    else if(Listo.isEmpty() == false){
                      boolean stackwins = true;  
                      
                      while(Listo.isEmpty() == false && stackwins == true){
                        
                        //If the stack has greater priority we pop                            
                        int opPrecedence = comparePrecedence(currentItem, Listo.peek());
                        
                        if(opPrecedence == OP_ONE_TAKES_PRECEDENCE){
                           stackwins = false;                          
                        }else if(opPrecedence == OP_TWO_TAKES_PRECEDENCE || 
                                 opPrecedence == SAME_PRECEDENCE){
                           postfix = postfix + " " + Listo.pop();
                            }                      
                      }
                      //At the end of it all we need to debated operand pushed
                      //onto the stack
                      Listo.push(Character.toString(WorkingEntry.charAt(i)));         
                }    
        }
        
                    
           //If its a right brace then
           if(WorkingEntry.charAt(i) == ')'){
               //Keep poping until a left brace
               while(Listo.isEmpty() == false &&  !Listo.peek().equals("(")){
                   postfix = postfix + " " + Listo.pop();
                    }
                    //Finally, pop out the leftbrace
                    if(Listo.peek().equals("(")){
                        Listo.pop();
                    }            
            }
    }
        //After the infix expression has been evaluated pop all remaining
        //Operators and add them to the stack
        while(Listo.isEmpty() == false){
            postfix = postfix + " " + Listo.pop();
        }
        
//EVALUATION CODE
        
        //Scroll throught the postfix, left to right
        for(int j = 0; j<postfix.length(); j++){
           String tempdigit = ""; 
           //If its a digit 
           if(Character.isDigit(postfix.charAt(j)) == true){
               //Check how many digits it is
               int f=j;
               while(f<postfix.length() 
                        && Character.isDigit(postfix.charAt(f)) == true){
                    tempdigit += Character.toString(postfix.charAt(f));
                    j=f;
                    f++;
                    }
                //Then push it onto the stack
                Listo.push(tempdigit);
                }
           //If it's an operator
           if(postfix.charAt(j) == '+' || postfix.charAt(j) == '-' ||
               postfix.charAt(j) == '*' || postfix.charAt(j) == '/'){
               String OP1 = "";
               String OP2 = "";
               int result;
               
               OP1 = Listo.peek();
               Listo.pop();
               OP2 = Listo.peek();
               
               if(postfix.charAt(j) == '*'){
                result = Integer.parseInt(OP1) * Integer.parseInt(OP2);
                Listo.pop();
                Listo.push(Integer.toString(result));
               }
               
               if(postfix.charAt(j) == '/'){
                result = Integer.parseInt(OP2) / Integer.parseInt(OP1);
                Listo.pop();
                Listo.push(Integer.toString(result));
               }
               
               if(postfix.charAt(j) == '-'){
                result = Integer.parseInt(OP2) - Integer.parseInt(OP1);
                Listo.pop();
                Listo.push(Integer.toString(result));
               }
               
               if(postfix.charAt(j) == '+'){
                result = Integer.parseInt(OP1) + Integer.parseInt(OP2);
                Listo.pop();
                Listo.push(Integer.toString(result));
               }
           }
               
        }
             
        PostfixAndAnswer output = new PostfixAndAnswer();
        output.postfix = postfix;
        output.answer = Listo.pop();
        return output;     
       
   }
   //End of Main
   
   
   
   
public static int getPrecedence(String Oper){
        if( Oper.equals("*") || Oper.equals("/") ){
            return 2;
        }else if(Oper.equals("+") || Oper.equals("-") ){
            return 1;
        }else{
            throw(new ClassCastException("Not an operator")); 
        }
    }
    
    public static int OP_ONE_TAKES_PRECEDENCE = -1;
    public static int OP_TWO_TAKES_PRECEDENCE = 1;
    public static int SAME_PRECEDENCE = 0;
    
public static int comparePrecedence (String Oper1, String Oper2){

        int operOnePrec = getPrecedence(Oper1);
        int operTwoPrec = getPrecedence(Oper2);

        //If the stack has greater priority we pop    
        if( operOnePrec > operTwoPrec ){
            return OP_ONE_TAKES_PRECEDENCE;
        } else if(operOnePrec < operTwoPrec){
            return OP_TWO_TAKES_PRECEDENCE;
        }else{
            return SAME_PRECEDENCE;
        }
    }
  
//ERROR CHECKING
public static boolean ErrorCheck(String ErrorEntry){
    String WorkingEntry = ErrorEntry;    
    String comp1 = "";
    String comp2 = "";
    int j = 0;
    int k = 0;
    int z = 0;
    int q = 0;
    int Lparen = 0;
    int Rparen = 0;
    boolean OpParen = false;
    boolean NotValid = false;
    
   
      
        //Error check loop
        for(int i = 0; i<WorkingEntry.length(); i++){
            comp1 = "";
            comp2 = "";
            
            //Check for invalid characters
            if(!(WorkingEntry.charAt(i) == '+' || WorkingEntry.charAt(i) == '-' ||
                 WorkingEntry.charAt(i) == '*' || WorkingEntry.charAt(i) == '/' ||
                 WorkingEntry.charAt(i) == '(' || WorkingEntry.charAt(i) == ')' ||
                 WorkingEntry.charAt(i) == ' ' ||
                (Character.isDigit(WorkingEntry.charAt(i)) == true))){
                        NotValid = true;
                        q = i;
                        
                    }
            
            //Count left parens
            if((i<WorkingEntry.length()) &&
                    (WorkingEntry.charAt(i) == '(')){
                        Lparen++;
                        z=i;                       
                    }
            
            //Count right parens
            if((i<WorkingEntry.length()) &&
                    (WorkingEntry.charAt(i) == ')')){
                //and make sure a number doesn't directly follow a right paren        
                if((i+1<WorkingEntry.length()) && (Character.isDigit(WorkingEntry.charAt(i+1)) == true)){
                            z=i;
                            OpParen = true;
                        }
                        Rparen++;
                        z=i;
                    }
            
            //Check numbers and digits 
            if((i<WorkingEntry.length()) &&
                    (Character.isDigit(WorkingEntry.charAt(i)) == true)){
                            //Make sure a left paren doesn't follow a digit
                            if((i+1<WorkingEntry.length()) && 
                                   (WorkingEntry.charAt(i+1) == '(')){
                                z=i;
                                OpParen = true;
                            }
                        comp1 = "Number";
                        k=i+1;
                    while(k<WorkingEntry.length() && 
                        Character.isDigit(WorkingEntry.charAt(k)) == true){
                        k++;

                    } 
                        j = k;
                    }
            
            //Check for Operators
            if((i<WorkingEntry.length()) &&
                    (WorkingEntry.charAt(i) == '+' || WorkingEntry.charAt(i) == '-' ||
                     WorkingEntry.charAt(i) == '*' || WorkingEntry.charAt(i) == '/')){
                        comp1 = "Operator";
                        j = i+1;
                    } 
                //Check if the next symbol is an operator  
                while(j<WorkingEntry.length()){                  
                    if((j<WorkingEntry.length()) &&
                    (WorkingEntry.charAt(j) == '+' || WorkingEntry.charAt(j) == '-' ||
                     WorkingEntry.charAt(j) == '*' || WorkingEntry.charAt(j) == '/')){
                        comp2 = "Operator";
                        break;
                        }
                    //Or if its a number
                    if((j<WorkingEntry.length()) &&
                    (Character.isDigit(WorkingEntry.charAt(j)) == true)){              
                        comp2 = "Number";
                        break;
                        }
                    
                        j++;
                    }
 
//Error conditions                
                    
                //Not a valid character
                if(NotValid == true){
                    System.out.println("\n" + "ERROR!! Not a valid character");
                    System.out.println("At this point");
                    System.out.println(WorkingEntry);
                    while(q>0){
                        System.out.print(" ");
                        q--;
                        }
                    System.out.print("^" + "\n");
                    return false;
                }
                
                //Operator next to parenthesis detected
                if(OpParen == true){
                    System.out.println("\n" + "ERROR!! Operator and parenthesis detected");
                    System.out.println("At this point");
                    System.out.println(WorkingEntry);
                    while(z>0){
                        System.out.print(" ");
                        z--;
                        }
                    System.out.print("^" + "\n");
                    return false;
                }    
                
                //Two operators in succesion
                if(comp1 == "Operator" && comp2 == "Operator"){
                    System.out.println("\n" + "ERROR!! Two Operators in a row detected!");
                    System.out.println("At this point");
                    System.out.println(WorkingEntry);
                    while(j>0){
                        System.out.print(" ");
                        j--;
                        }
                    System.out.print("^" + "\n");
                    return false;
                }
                
                 //Two operands in succession 
                 if(comp1 == "Number" && comp2 == "Number"){
                    System.out.println("\n" + "ERROR!! Two Numbers in a row detected!");
                    System.out.println("At this point");
                    System.out.println(WorkingEntry);
                    while(j>0){
                        System.out.print(" ");
                        j--;
                        }
                    System.out.print("^" + "\n");
                    return false;
                }
                 

                
            } //End of the For loop
        
        //Parentheses Mismatch
                if(!(Lparen == Rparen)){
                    System.out.println("\n" + "ERROR!! Parentheses mismatch!");
                    System.out.println("At this point");
                    System.out.println(WorkingEntry);                    
                    while(z>0){
                        System.out.print(" ");
                        z--;
                        }
                    System.out.print("^" + "\n");
                    return false;
                }
        return true;
        }
        
      }
        
 