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
public class Lab6Part1e {

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
        double[][] costs = new double[MAXDINERS][5];
        String[][] names = new String[MAXDINERS][4];
        
        /*double[] drinks = new double[MAXDINERS];
        double[] appetizers = new double[MAXDINERS];
        double[] entrees = new double[MAXDINERS];        
        double[] desserts = new double[MAXDINERS];        
        double[] diners = new double[MAXDINERS];        
        String[] drinkNames = new String[MAXDINERS];
        String[] appetizerNames = new String[MAXDINERS];
        String[] entreeNames = new String[MAXDINERS];
        String[] dessertNames = new String[MAXDINERS];
        */
        
        // Save the meal item names from the file 
        
        

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
        Scanner billLine;
        
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
                
              billLine = new Scanner(billFile.nextLine());  // Q1b - line 1
              if (billLine.hasNext() && inputValid)
              {  //System.out.println("Reading cat for diner "+diner+" "+billLine.hasNext());
                  itemCat = billLine.next();  }
              else
              {   inputValid = false;   }
              
              if (billLine.hasNext() && inputValid)
              {  //System.out.println("Reading Name "+billLine.hasNext());
                  itemName = removeBlanks(billLine.nextLine());  }
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
                        costs[diner][1] = cost;
                        names[diner][0] = itemName;
                    }
                    else if (itemCat.equalsIgnoreCase("appetizer"))
                    {   appetizerCount++;    
                        appetizerCost += cost;   
                        costs[diner][2] = cost;
                        names[diner][1] = itemName;            
                    }
                    else if (itemCat.equalsIgnoreCase("entree"))
                    {   entreeCount++;   
                        entreeCost += cost;   
                        costs[diner][3] = cost;
                        names[diner][2] = itemName;
                    }  
                    else if (itemCat.equalsIgnoreCase("dessert"))
                    {   dessertCount++;   
                        dessertCost += cost;    
                        costs[diner][4] = cost;
                        names[diner][3] = itemName;            
                    }  
                    // Find total cost for each diner
                    costs[diner][0] += cost;
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
      /*  for(int i = 0; i < diners.length; i++)
        {
           costs[i][0] = diners[i];
           costs[i][1] = drinks[i];
           costs[i][2] = appetizers[i];
           costs[i][3] = entrees[i];
           costs[i][4] = desserts[i];
            
        }
          for(int i = 0; i < diners.length; i++)
        {
           names[i][0] = drinkNames[i];
           names[i][1] = appetizerNames[i];
           names[i][2] = entreeNames[i];
           names[i][3] = dessertNames[i];   
        }
        */  
     /*   for(int i = 0; i < costs.length; i++)
        {
            if(costs[i][0] > 0)
            {
                
            System.out.println(names[i][0]);
            System.out.println(names[i][1]);
            System.out.println(names[i][2]);
            System.out.println(names[i][3]);
           }
        }
       */ 
        //printMeal(costs);

        for(int index = 1; index < 7; index++)
        {
            
                for(int i = 1; i < 4; i++)
            {
                if(costs[index][i] > costs[index][i+1])
                {
                    double old = costs[index][i];
                    costs[index][i] = costs[index][i+1];
                    costs[index][i+1] = old;
                    if((costs[index][i] < costs[index][i-1]) && i != 1)
                    {
                        for(int m = i; m > 1; m--)
                        {
                            if(costs[index][m] < costs[index][m-1])
                            {
                                double d = costs[index][m];
                            costs[index][m] = costs[index][m-1];
                            costs[index][m-1] = d;
                            }
                        }
                    }
                }
                
            }
                for(int i = 1; i < 5; i++)
                {   
                    System.out.println(costs[index][i]);
                }
        }
        for(int i = 1; i < costs.length; i++)
        {
            
                System.out.print(i + "----");
                for(int j = 1; j < 5; j++){
                    System.out.println(costs[i][j]);
  
            }
        }
        
        /*  for (int index=0;index <MAXDINERS;index++)
          {
              if (costs[index][0] > 0)
                  System.out.print("Diner "+index);
              if (names[index][0] != null)
                  System.out.print(" had a "+names[index][0]
                            +" costing $"+costs[index][1]);
              if (names[index][1] != null)              
                  System.out.print( ", an appetizer of "+ names[index][1]
                          +" for $"+costs[index][2]);
              if (names[index][2] != null)              
                  System.out.print(",\n   an entrÃ©e of "+names[index][2]
                          +" of $" +costs[index][3]);
              if (names[index][3] != null)              
                  System.out.print(", and dessert of "+names[index][3]
                          +" costing $"+costs[index][4]);
              if (costs[index][0] > 0)
                  System.out.print("\n");
              if (costs[index][0] > 0)
              {
                  printDinerCost(index, calcPct((100+taxRate+tipPercent),costs[index][0]));
                  System.out.println();
              }
          }
          
          for(int i = 1; i < costs.length; i++)
          {
           if(costs[i][0] < costs[i-1][0] && costs[i][0] > 0)
           {
             double tempCost = costs[i-1][0];
             costs[i-1][0] = costs[i][0];
             costs[i][0] = tempCost;
           }
          }
          
          for(int i = 0; i < costs.length; i++)
          {
              if(costs[i][0] > 0)
              System.out.println(costs[i][0]);
          }
        */ 
        
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
    
    public static void printMeal(double[][] costs)
    {
        System.out.printf("Diner %4s %5s %5s %5s", "Drink", "Appetizer", "Entree", "Dessert");
        System.out.println();
        
        for(int i = 0; i < costs.length; i++)
        {
            if(costs[i][0] > 0)
            {
                System.out.printf(i + "%9s %7s %7s %7s", costs[i][1], costs[i][2], costs[i][3], costs[i][4]);
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
