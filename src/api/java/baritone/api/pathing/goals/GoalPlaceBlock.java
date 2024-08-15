package baritone.api.pathing.goals;/*
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

import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.SettingsUtil;
import baritone.api.utils.interfaces.IGoalRenderPos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class GoalPlaceBlock implements Goal, IGoalRenderPos {

    public final int x;
    public final int y;
    public final int z;
    private final ItemStack blockStack;

    public GoalPlaceBlock(BlockPos pos, ItemStack blockStack) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.blockStack = blockStack;
    }

    @Override
    public BlockPos getGoalPos() {
        return new BlockPos(x, y, z);
    }

    @Override
    public boolean isInGoal(int x, int y, int z) {
        // Check if the player is adjacent to the goal block
        int xDiff = x - this.x;
        int yDiff = y - this.y;
        int zDiff = z - this.z;
        return Math.abs(xDiff) <= 1 && yDiff == 0 && Math.abs(zDiff) <= 1 && !(xDiff == 0 && zDiff == 0);
    }

    @Override
    public double heuristic(int x, int y, int z) {
        // Heuristic can be based on distance to the goal block
        int xDiff = x - this.x;
        int yDiff = y - this.y;
        int zDiff = z - this.z;
        return GoalBlock.calculate(xDiff, yDiff, zDiff);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GoalPlaceBlock goal = (GoalPlaceBlock) o;
        return x == goal.x && y == goal.y && z == goal.z;
    }

    @Override
    public int hashCode() {
        return (int) BetterBlockPos.longHash(x, y, z) * -49639096;
    }

    @Override
    public String toString() {
        return String.format(
                "GoalPlaceBlock{x=%s,y=%s,z=%s}",
                SettingsUtil.maybeCensor(x),
                SettingsUtil.maybeCensor(y),
                SettingsUtil.maybeCensor(z)
        );
    }

    /**
     * Determines the best direction to place the block from the player's perspective.
     *
     * @param playerPos The player's current position.
     * @return The direction to face for block placement.
     */
    public EnumFacing getPlacementDirection(BlockPos playerPos) {
        int xDiff = x - playerPos.getX();
        int yDiff = y - playerPos.getY();
        int zDiff = z - playerPos.getZ();

        if (Math.abs(xDiff) > Math.abs(zDiff)) {
            return xDiff > 0 ? EnumFacing.EAST : EnumFacing.WEST;
        } else {
            return zDiff > 0 ? EnumFacing.SOUTH : EnumFacing.NORTH;
        }
    }

    /**
     * Places the block at the goal position.
     */
    public void placeBlock(WorldClient world) {
        Minecraft minecraft = Minecraft.getMinecraft();
        BlockPos pos = getGoalPos();
        EnumFacing direction = getPlacementDirection(pos);
        Vec3d hitVec = new Vec3d(0.5, 0.5, 0.5); // Adjust this vector as needed for accurate placement

        // Ensure the player is holding the correct item
        minecraft.player.inventory.setInventorySlotContents(minecraft.player.inventory.currentItem, blockStack);

        // Place the block
        minecraft.playerController.processRightClickBlock(
                minecraft.player,
                world,
                pos,
                direction,
                hitVec,
                EnumHand.MAIN_HAND
        );
    }
}