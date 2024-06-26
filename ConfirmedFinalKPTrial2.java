//Olivia Hill
//11/09/23
//Knapsack Simulator
import java.util.Scanner;
import java.util.ArrayList;
//import org.apache.commons.lang3.time.StopWatch;
import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileWriter;   // Import the FileWriter class
import java.awt.Font;
import javax.swing.*;    


class Main {
	public static void main(String[] args) {
	
	//arraylists for knapsack and objects
	ArrayList<Object> knapsack = new ArrayList<Object>();
	ArrayList<Object> toAdd = new ArrayList<Object>();

    //scanner
    Scanner input = new Scanner(System.in);

    //objects: value, weight, name
    Object x1 = new Object(22, 11, "Object 1");
    Object x2 = new Object(21, 22, "Object 2");
    Object x3 = new Object(10, 20, "Object 3");
    Object x4 = new Object(1, 23, "Object 4");
    Object x5 = new Object(18, 25, "Object 5");
    Object x6 = new Object(13, 1, "Object 6");
    Object x7 = new Object(14, 12, "Object 7");
    Object x8 = new Object(5, 1, "Object 8");
    Object x9 = new Object(19, 16, "Object 9");

    int maxWeight = 45;

    //initialize arraylist
    toAdd.add(x1);
    toAdd.add(x2);
    toAdd.add(x3);
    toAdd.add(x4);
    toAdd.add(x5);
    toAdd.add(x6);
    toAdd.add(x7);
    toAdd.add(x8);
    toAdd.add(x9);

    //needs to be recorded
    int numberOfComputations = 0;

    //loop
    boolean continueLoop = true;
    while(continueLoop) {
      numberOfComputations ++;
      printAll(knapsack, toAdd);

      if(!checkWeight(knapsack, toAdd, maxWeight, numberOfComputations)) { 
				//if not submitting
        mutateKnapsack(knapsack, toAdd, checkChoice(knapsack.size(), toAdd.size(), maxWeight, knapsack));
      }
      else {
				continueLoop = false;
      }
    }
  }

  public static boolean checkWeight(int maxWeight, ArrayList<Object> knapsack) {
    int currentWeight = 0;
    
    for(int i = 0; i < knapsack.size(); i++) {
      currentWeight += knapsack.get(i).getWeight();
    }
    if(currentWeight >= maxWeight) {
      return true;
    }

    return false; 
  }

  //finding out whether to add or remove
  public static boolean checkChoice (int knapsackSize, int toAddSize, int maxWeight, ArrayList<Object> knapsack) {
    Scanner input = new Scanner(System.in);
    String choice;
    boolean found = false;
    boolean full = checkWeight(maxWeight, knapsack);

    while(!found) {
      if(knapsackSize == 0) {
        return true;
      }
      else if(toAddSize == 0) {
        return false;
      }
      else if(full) {
        return false;
      }
      else {
        System.out.println("Would you like to add or remove from the Knapsack?");
        choice = input.nextLine().toLowerCase();
        if(choice.equals("add")){
          return true;
        }
        else if(choice.equals("remove")) {
          return false;
        }
        else
          System.out.println("Option choice not found.");
      }
    }

    return true;

  }

  //for submitting the values, still editing
  public static boolean submittingValues (ArrayList<Object> knapsack, ArrayList<Object> toAdd, int numberOfComputations) {
    Scanner input = new Scanner(System.in);
    boolean submitting = false;
    String choice;
    boolean found = false;

    if(knapsack.size() != 0) {
      System.out.println("Would you like to submit (yes or no)?"); 
      //add a line here about the time, the amount of space left in the knapsack, the current value, etc.
      while(!found) {
        choice = input.nextLine().toLowerCase();
        if(choice.equals("yes")) {
          submitting = true;
          found = true;
        }
        else if(choice.equals("no")) {
          submitting = false;
          found = true;
        }
        else {
          System.out.print("That option was not found, please retype your response here (yes or no): ");
        }
      }
    }
    else {
      submitting = false; 
    }

    if(submitting) {
      System.out.println("Submitting...");

      ArrayList<String> knapsackNames = new ArrayList<String>();
      ArrayList<Integer> knapsackWeights = new ArrayList<Integer>();
      ArrayList<Integer> knapsackValues = new ArrayList<Integer>();
      for(int i = 0; i < knapsack.size(); i++) {
        knapsackNames.add(knapsack.get(i).getName());
        knapsackValues.add(knapsack.get(i).getValue());
        knapsackWeights.add(knapsack.get(i).getWeight());
      }

      ArrayList<String> toAddNames = new ArrayList<String>();
      ArrayList<Integer> toAddWeights = new ArrayList<Integer>();
      ArrayList<Integer> toAddValues = new ArrayList<Integer>();
      for(int i = 0; i < toAdd.size(); i ++) {
        toAddNames.add(toAdd.get(i).getName());
        toAddValues.add(toAdd.get(i).getValue());
        toAddWeights.add(toAdd.get(i).getWeight());
      }      
      
      
      try {
        FileWriter myWriter = new FileWriter("datacollection.txt", true);
        myWriter.write("Knapsack contents:\n");
        for(int i = 0; i < knapsack.size(); i++) {
          myWriter.write(knapsackNames.get(i));
          myWriter.write(knapsackWeights.get(i));
          myWriter.write(knapsackValues.get(i));
        }
        myWriter.write("\nOutside of the Knapsack:\n");
        for(int i = 0; i < toAdd.size(); i++) {
          myWriter.write(toAddNames.get(i));
          myWriter.write(toAddWeights.get(i));
          myWriter.write(toAddValues.get(i));
        }
        myWriter.write("\nNumber of computations: " + numberOfComputations);
        myWriter.close();
      } catch (IOException e) {
        return false;
      }
      System.out.println("Submitted!");
      return true;
    }
    return false;
  }

  //for checking if the max weight is in tact
  public static boolean checkWeight (ArrayList<Object> knapsack, ArrayList<Object> toAdd, int maxWeight, int numberOfComputations) {
    int currentWeight = 0;
    
    for(int i = 0; i < knapsack.size(); i++) {
      currentWeight += knapsack.get(i).getWeight();
    }

    if(currentWeight > maxWeight) {
			toAdd.add(knapsack.get(knapsack.size() - 1));
      knapsack.remove(knapsack.size() - 1);
      System.out.println("The weight is " + currentWeight + ". It's weight is over the limit by " + (currentWeight - maxWeight) + "! We removed an object from the knapsack.\n");
			printAll(knapsack, toAdd);
			checkWeight(knapsack, toAdd, maxWeight, numberOfComputations);
			printValue(knapsack);
      return false; 
    }
    else if(currentWeight == maxWeight) {
      System.out.println("You are at the weight capacity: " + currentWeight + "/" + maxWeight + ".");
      printValue(knapsack);
      return submittingValues(knapsack, toAdd, numberOfComputations);
    }
    else if (currentWeight == 0) {
      return false;
    }
    else {
      System.out.println("The knapsack's weight is " + currentWeight + "/" + maxWeight + ".");
      printValue(knapsack);
      return submittingValues(knapsack, toAdd, numberOfComputations);
    }
  }

  //for printing the current value 
  public static void printValue(ArrayList<Object> knapsack) {
    int currentValue = 0;
    for(int i = 0; i < knapsack.size(); i ++) {
      currentValue += knapsack.get(i).getValue();
    }
    System.out.println("The knapsack's current value is " + currentValue + ".");
  }
  
  //for editing the knapsack and toAdd
  public static void mutateKnapsack (ArrayList<Object> knapsack, ArrayList<Object> toAdd, boolean add) {
    int index;
    Scanner input = new Scanner(System.in);
    boolean found = false;

    if(add) {
      System.out.println("What would you like to add to the Knapsack (please type the index number)? ");
    }
    else {
      System.out.println("What would you like to remove from the Knapsack (please type the index number)? ");
    }
    
    index = input.nextInt();

    while(!found) { //while it was not found
      if(add && index >= 0 && index < toAdd.size()) { //if within
        found = true;
      }
			else if(!add && index >= 0 && index < knapsack.size()){
				found = true;
			}
      else { // if not found
        System.out.println("Object was not found.");
        System.out.print("Please retype the index number: ");
        index = input.nextInt();
      }
    }

    if(add) {
      knapsack.add(toAdd.get(index));
      toAdd.remove(toAdd.get(index));
    }
    else {
      toAdd.add(knapsack.get(index));
      knapsack.remove(knapsack.get(index));
    }
  }

	public static void printAll(ArrayList<Object> knapsack, ArrayList<Object> toAdd) {
		System.out.println("\nCurrently in the Knapsack:");
		printSack(knapsack);
		System.out.println("Currently outside of the Knapsack:");
		printSack(toAdd);
	}

  //for printing the knapsack
  public static String printSack (ArrayList<Object> list) {
    if(list.size() == 0) { //if there is nothing in the list
      return "Nothing";
    }
    for(int i = 0; i < list.size(); i++) { //print inside
      System.out.println("Name: " + list.get(i).getName());
      System.out.println("Value: " + list.get(i).getValue());
      System.out.println("Weight: " + list.get(i).getWeight());
      System.out.println("Index: " + i + "\n");
    }
    return "\n";
  }
}
