/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab6f16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author devyn
 */
public class Lab6F16b {

    /**
     * @param args the command line arguments
     */
     public static void main(String[] args)
    {   /* Cost of dinner for multiple people at a restaurant
        // Each diner could have a drink, an appetizer, a main dish, and a dessert
        // Data is read in from a file :  first line is number of diners, taxrate, tip percent
        // Every line afer first:  diner_number, cost, item_category, item name
        // Item categories are single words: "drink", "appetizer", "entree", "dessert"
        */
        System.out.println("This system calculates the cost of a group meal");
        // Create a constant for the maximum number of diners that the program can handle
        
        final int MAXDINERS = 50;
        /*  L6Q1d
            Rewrite the program to use a multi-dimensional array for the costs
            and a multi-dimensional array for the names for the items.  In the 
            costs array include a column for the diner number.  (This will be
            used later in the program)
        */        
        double[] drinks = new double[MAXDINERS];
        double[] appetizers = new double[MAXDINERS];
        double[] entrees = new double[MAXDINERS];        
        double[] desserts = new double[MAXDINERS];        
        double[] diners = new double[MAXDINERS];        
        
        // Save the meal item names from the file 
        String[] drinkNames = new String[MAXDINERS];
        String[] appetizerNames = new String[MAXDINERS];
        String[] entreeNames = new String[MAXDINERS];
        String[] dessertNames = new String[MAXDINERS];

        int drinkCount, appetizerCount, entreeCount, dessertCount;
        double drinkCost, appetizerCost, entreeCost, dessertCost;        
        boolean billAvailable = true;
        double pretaxBill = 0;
        double taxRate = 8.25;
        double tipPercent = 18;
        double totalBill = 0;
        int party = 1;
        int diner = 0;
        double cost = 0;
        String itemCat = "";
        String itemName = "";
        boolean inputValid = true;
        
        File bill = new File("Bill.txt");
        Scanner billFile;
        String bLine;
        //Scanner billFile;
        
        try
        {   billFile = new Scanner(bill);
        }
        catch (FileNotFoundException fnf)
        {   billFile = new Scanner(System.in);
            System.out.println("No input file found ");
            billAvailable = false;
        }
        System.out.println();   

        drinkCount = appetizerCount = entreeCount = dessertCount = 0;
        drinkCost = appetizerCost = entreeCost = dessertCost = 0.0; 
        

        if (billAvailable && billFile.hasNext())
        {   // read first line of file to get number of diners
            // Check for bad data from the file
          if (billFile.hasNextInt())
          {
              party = billFile.nextInt();
              if (billFile.hasNextDouble())
              {
                  taxRate = billFile.nextDouble();
                  if (billFile.hasNextDouble())
                  {                  
                      tipPercent = billFile.nextDouble();
                      billFile.nextLine();
                  }
                  else
                  {   inputValid = false;   }  
              }
              else
              {   inputValid = false;   }              
          }
          else
          {   inputValid = false;   }
          
        /*  L6Q1c
            Given the errors in the file Bill3.txt, tell which lines of the 
            code catch/deal with each error so that they don't cause the program
            to crash.  Explain how each error is handled.
        */
          while (billFile.hasNext() && inputValid)
          { //diner_number, cost, item_category, item name
            if (billFile.hasNextInt())
            {
              diner = billFile.nextInt();
              
              if (billFile.hasNextDouble())
              {  cost = billFile.nextDouble();  }
              else
              {   inputValid = false;   }
              
                /*  L6Q1b
                    What errors can occur if the Q1b - line 1 is removed and 
                    all the references to billLine are changed to billFile?  
                    Describe all the errors that are found
                */         
                /*  L6Q1f
                    Error check the category for a valid value and set the 
                    invalidInput flag if the category is not valid
                */                 
                
              //billLine = new Scanner(billFile.nextLine());  // Q1b - line 1
              if (billFile.hasNext() && inputValid)
              {  //System.out.println("Reading cat for diner "+diner+" "+billLine.hasNext());
                  itemCat = billFile.next();  }
              else
              {   inputValid = false;   }
              
              if (billFile.hasNext() && inputValid)
              {  //System.out.println("Reading Name "+billLine.hasNext());
                  itemName = removeBlanks(billFile.nextLine());  }
              else
              {   inputValid = false;   }            
            // Save the costs and names for the four dinner 
            // items for Diner X from the file into the 
            // cost variables and string variables 
            // for drink, dessert, entree, and appetizer
            
                if (inputValid)
                {
                    System.out.println("Diner "+diner+" enjoyed "+itemName
                            +" as their "+itemCat);
                    pretaxBill += cost;
                    // calculate total amounts and costs for each kind of item
                    if (itemCat.equalsIgnoreCase("drink"))
                    {   drinkCount++;   
                        drinkCost += cost;  
                        drinks[diner] = cost;
                        drinkNames[diner] = itemName;
                    }
                    else if (itemCat.equalsIgnoreCase("appetizer"))
                    {   appetizerCount++;    
                        appetizerCost += cost;   
                        appetizers[diner] = cost;
                        appetizerNames[diner] = itemName;            
                    }
                    else if (itemCat.equalsIgnoreCase("entree"))
                    {   entreeCount++;   
                        entreeCost += cost;   
                        entrees[diner] = cost;
                        entreeNames[diner] = itemName;            
                    }  
                    else if (itemCat.equalsIgnoreCase("dessert"))
                    {   dessertCount++;   
                        dessertCost += cost;    
                        desserts[diner] = cost;
                        dessertNames[diner] = itemName;            
                    }  
                    // Find total cost for each diner
                    diners[diner] += cost;
                }
            }
            else
            {   billFile.nextLine();   }
            
            inputValid = true;
          }
          
          System.out.println();          
          
          System.out.printf("The cost of the dinner before tax is $%8.2f\n",pretaxBill);
          System.out.print("The tax rate is "+taxRate+"%");  
          System.out.println(" and the tip percent is "+tipPercent+"%");
          
          totalBill = pretaxBill + calcPct(taxRate,pretaxBill)+calcPct(tipPercent,pretaxBill);
          System.out.println();
          System.out.printf("The cost of the dinner with tax and tip is $%8.2f\n",totalBill);
           
          System.out.println();
          printCat(drinkCount,"drink",drinkCost);
          printCat(appetizerCount,"appetizer",appetizerCost);
          printCat(entreeCount,"entree",entreeCost);
          printCat(dessertCount,"dessert",dessertCost);
        }
        System.out.println();
        System.out.printf("Total Bill : $%8.2f\n",totalBill);
        System.out.printf("Average amount per person for a party of %d is $%8.2f\n",
                party,totalBill/party);        
        System.out.println();
        System.out.println();  
        printMeal(diners, drinks, appetizers, entrees, desserts);

          for (int index=0;index <MAXDINERS;index++)
          {
              if (diners[index] > 0)
                  System.out.print("Diner "+index);
              if (drinkNames[index] != null)
                  System.out.print(" had a "+drinkNames[index]
                            +" costing $"+drinks[index]);
              if (appetizerNames[index] != null)              
                  System.out.print( ", an appetizer of "+ appetizerNames[index]
                          +" for $"+appetizers[index]);
              if (entreeNames[index] != null)              
                  System.out.print(",\n   an entrÃ©e of "+entreeNames[index]
                          +" of $" +entrees[index]);
              if (dessertNames[index] != null)              
                  System.out.print(", and dessert of "+dessertNames[index]
                          +" costing $"+desserts[index]);
              if (diners[index] > 0)
                  System.out.print("\n");
              if (diners[index] > 0)
              {
                  printDinerCost(index, calcPct((100+taxRate+tipPercent),diners[index]));
                  System.out.println();
              }
          }
        /*  L6Q1e
            Use a bubble sort algorithm to sort the cost and name array based 
            on the diner number and then on the item cost.
        */           
          
    }
    public static void printCat(int count, String cat, double cost) 
    {
        System.out.printf("%d people had %ss at a cost of $%8.2f\n",count,cat,cost);
    }
    public static void printDinerCost(int count, double cost) 
    {
        System.out.printf("Diner %d had a meal with the total cost of $%8.2f\n",count,cost);
    }

    public static double calcPct(double percentageRate, double amount)
    {   // return the percentageRate of the entered amount
        return percentageRate*.01*amount;   
    }
    
    public static String removeBlanks(String inString)
    {
        while (inString.charAt(0) == ' ')
        {
            inString = inString.substring(1);
            //System.out.println("Removing blank in name["+i+"]");
        }
        return inString;
    }
    
    public static void printMeal(double[] diners, double[] drinks, double[] appetizers,
            double[] entrees, double[] desserts)
    {
        System.out.printf("Diner %4s %5s %5s %5s", "Drink", "Appetizer", "Entree", "Dessert");
        System.out.println();
        
        for(int i = 0; i < diners.length; i++)
        {
            if(diners[i] > 0)
            {
                System.out.printf(i + "%9s %7s %7s %7s", drinks[i], appetizers[i], entrees[i], desserts[i]);
                System.out.println();
            }
        }
    }
    
    /*  L6Q1a
        Write a method that prints out the costs for each diners four items in a table.
        Each row should be one diner and the columns are drinks, appetizers, 
            entrees, dessert
        Put titles on each column and make sure everything lines up.
    */
    
}
