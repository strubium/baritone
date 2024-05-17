/*
 * This file is part of Baritone.
 *
 * Baritone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Baritone is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Baritone.  If not, see <https://www.gnu.org/licenses/>.
 */

package baritone.command.defaults;

import baritone.api.IBaritone;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.ItemById;
import baritone.api.command.exception.CommandException;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class represents a command to craft items in the game.
 * It extends the Command class provided by the Baritone API.
 */
public class CraftCommand extends Command {

    /**
     * Constructor for the CraftCommand class.
     *
     * @param baritone The instance of the Baritone API.
     */
    protected CraftCommand(IBaritone baritone) {
        super(baritone, "craft");
    }

    /**
     * Executes the craft command.
     *
     * @param label The label of the command.
     * @param args The arguments passed to the command.
     * @throws CommandException If there is an error executing the command.
     */
    @Override
    public void execute(String label, IArgConsumer args) throws CommandException {
        int amount = args.getAsOrDefault(Integer.class, 1); // Get the quantity of items to craft. Default is 1.
        Item item = args.getDatatypeForOrNull(ItemById.INSTANCE); // Get the item to craft.
        List<IRecipe> recipes;

        // If an item is specified, get the crafting recipes for that item.
        if (item!= null) {
            recipes = baritone.getCraftingProcess().getCraftingRecipes(item, false);
        } else {
            String itemName = args.rawRest(); // Get the item name to craft.
            recipes = new ArrayList<>();
            // Find the crafting recipes for the specified item name.
            for (IRecipe recipe : CraftingManager.REGISTRY) {
                if (recipe.getRecipeOutput().getDisplayName().equalsIgnoreCase(itemName)) {
                    recipes.add(recipe);
                }
            }
        }

        // If no recipes are found, log a message.
        if (recipes.isEmpty()) {
            logDirect("no crafting recipe found.");
        } else if (!baritone.getCraftingProcess().canCraft(recipes, amount)) { // Check if there are enough resources.
            logDirect("Insufficient Resources");
        } else {
            baritone.getCraftingProcess().craft(recipes, amount); // Craft the items.
        }
    }

    /**
     * Provides tab completion options for the craft command.
     *
     * @param label The label of the command.
     * @param args The arguments passed to the command.
     * @return A stream of tab completion options.
     * @throws CommandException If there is an error executing the command.
     */
    @Override
    public Stream<String> tabComplete(String label, IArgConsumer args) throws CommandException {
        // If there are more than 2 arguments, do not provide tab completion.
        while (args.has(2)) {
            if (args.peekDatatypeOrNull(ItemById.INSTANCE) == null) {
                return Stream.empty();
            }
            args.get();
        }
        // Provide tab completion for item names.
        return args.tabCompleteDatatype(ItemById.INSTANCE);
    }

    /**
     * Returns a short description of the craft command.
     *
     * @return The short description of the command.
     */
    @Override
    public String getShortDesc() {
        return "Craft a item.";
    }

    /**
     * Returns a list of detailed descriptions of the craft command.
     *
     * @return A list of detailed descriptions of the command.
     */
    @Override
    public List<String> getLongDesc() {
        return Arrays.asList(
                "Go to a crafting table and craft a item.",
                "",
                "Usage:",
                "> craft [quantity] <item>  - Go to a crafting table, and craft a item.",
                "Examples:",
                "> craft 17 planks -> will craft 20 planks out of any logs you have.",
                "> craft oak wood planks -> will craft 4 oak wood planks."
        );
    }
}