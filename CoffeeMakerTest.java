package edu.ncsu.csc326.coffeemaker;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;

/**
 * @author Sarah Heckman
 * Cleaned and Bug-Fixed Version
 */
public class CoffeeMaker {
    /** Array of recipes in coffee maker - Removed static for thread safety */
    private RecipeBook recipeBook;
    /** Inventory of the coffee maker - Removed static for thread safety */
    private Inventory inventory;
    
    public CoffeeMaker() {
        recipeBook = new RecipeBook();
        inventory = new Inventory();
    }
    
    public boolean addRecipe(Recipe r) {
        return recipeBook.addRecipe(r);
    }
    
    public String deleteRecipe(int recipeToDelete) {
        return recipeBook.deleteRecipe(recipeToDelete);
    }
    
    public String editRecipe(int recipeToEdit, Recipe r) {
        return recipeBook.editRecipe(recipeToEdit, r);
    }
    
    /**
     * Updated JavaDoc to match void return type.
     */
    public synchronized void addInventory(String amtCoffee, String amtMilk, String amtSugar, String amtChocolate) throws InventoryException {
        inventory.addCoffee(amtCoffee);
        inventory.addMilk(amtMilk);
        inventory.addSugar(amtSugar);
        inventory.addChocolate(amtChocolate);
    }
    
    public synchronized String checkInventory() {
        return inventory.toString();
    }
    
    public synchronized int makeCoffee(int recipeToPurchase, int amtPaid) {
        Recipe[] recipes = getRecipes();
        
        // Fix: Added bounds check to prevent ArrayIndexOutOfBoundsException
        if (recipeToPurchase < 0 || recipeToPurchase >= recipes.length || recipes[recipeToPurchase] == null) {
            return amtPaid;
        }
        
        Recipe selectedRecipe = recipes[recipeToPurchase];
        
        // Logic cleanup for better readability
        if (selectedRecipe.getPrice() <= amtPaid && inventory.useIngredients(selectedRecipe)) {
            return amtPaid - selectedRecipe.getPrice();
        }
        
        return amtPaid;
    }

    public synchronized Recipe[] getRecipes() {
        return recipeBook.getRecipes();
    }
}
