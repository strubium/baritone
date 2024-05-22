import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack

import baritone.api.IBaritone;

/**
 * This class provides a helper method to retrieve the maximum level of a specific enchantment 
 * that the player has equipped in their inventory.
 */
public class BaritoneEnchantmentHelp {
    
    /**
    * This method checks if the player has a specific enchantment and returns its maximum level.
    *
    * @param baritone The instance of Baritone API.
    * @param enchantment The enchantment to check.
    * @return The maximum level of the enchantment the player has. If the player does not have the enchantment, it returns 0.
    */
    public static int getEnchantmentLevel(IBaritone baritone, Enchantment enchantment) {
        return EnchantmentHelper.getMaxEnchantmentLevel(enchantment, baritone.getPlayerContext().player());
    }
    
    /**
    * This method checks if the player has a specific enchantment on an ItemStack and returns its maximum level.
    *
    * @param itemStack The ItemStack to check for the enchantment.
    * @param enchantment The enchantment to check.
    * @return The maximum level of the enchantment the ItemStack has. If the ItemStack does not have the enchantment, it returns 0.
    */
    public static int getEnchantmentLevelItem(ItemStack itemStack, Enchantment enchantment) {
        return EnchantmentHelper.getEnchantmentLevel(enchantment, itemStack);
    }
}
