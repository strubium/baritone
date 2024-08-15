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

import baritone.api.IBaritone;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.SettingsUtil;
import baritone.api.utils.input.Input;
import baritone.api.utils.interfaces.IGoalRenderPos;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class GoalPlaceBlock implements Goal, IGoalRenderPos {

    public final int x;
    public final int y;
    public final int z;
    private final Block blockStack;
    private final IBaritone baritone;

    public GoalPlaceBlock(BlockPos pos, Block blockStack, IBaritone baritone) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.blockStack = blockStack;
        this.baritone = baritone;
    }

    @Override
    public BlockPos getGoalPos() {
        return new BlockPos(x, y, z);
    }

    @Override
    public boolean isInGoal(int x, int y, int z) {
        // Log the coordinates being checked
        System.out.println("Checking if player is adjacent to goal at: (" + x + ", " + y + ", " + z + ")");

        // Calculate the distance to the goal
        BlockPos goalPos = new BlockPos(this.x, this.y, this.z);
        double distance = goalPos.distanceSq(x, y, z); // Use squared distance for efficiency

        // Log the distance for debugging
        System.out.println("Distance to goal: " + distance);

        // Check if the player is adjacent to the goal block
        if (x <= this.x & y <= this.y & z <=this.z) {
            placeBlock(baritone);
            return true;
        } else {
            return false;
        }
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
     * Places the block at the goal position.
     */
    public void placeBlock(IBaritone baritone) {
        baritone.getInputOverrideHandler().setInputForceState(Input.CLICK_RIGHT, true);
    }
}