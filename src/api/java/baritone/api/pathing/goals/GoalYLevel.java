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

package baritone.api.pathing.goals;

import baritone.api.pathing.movement.ActionCosts;
import baritone.api.utils.SettingsUtil;

/**
 * Useful for mining (getting to diamond / iron level).
 * This class represents a goal to reach a specific Y level
 *
 * @author leijurv
 */
public class GoalYLevel implements Goal, ActionCosts {

    /**
     * The target Y level.
     */
    public final int level;

    /**
     * Constructs a new GoalYLevel to the specified target Y level.
     *
     * @param level the target Y level
     */
    public GoalYLevel(int level) {
        this.level = level;
    }

    /**
     * Determines if the specified coordinates are at the goal Y level.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return true if the y-coordinate is at the target Y level, false otherwise
     */
    @Override
    public boolean isInGoal(int x, int y, int z) {
        return y == level;
    }

    /**
     * Calculates the heuristic cost to reach the target Y level from the current coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return the heuristic cost
     */
    @Override
    public double heuristic(int x, int y, int z) {
        return calculate(level, y);
    }

    /**
     * Calculates the heuristic cost to reach the goal Y level from the current Y level.
     *
     * @param goalY the target Y level
     * @param currentY the current Y level
     * @return the heuristic cost
     */
    public static double calculate(int goalY, int currentY) {
        if (currentY > goalY) {
            // need to descend
            return FALL_N_BLOCKS_COST[2] / 2 * (currentY - goalY);
        }
        if (currentY < goalY) {
            // need to ascend
            return (goalY - currentY) * JUMP_ONE_BLOCK_COST;
        }
        return 0;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GoalYLevel goal = (GoalYLevel) o;
        return level == goal.level;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return level * 1271009915;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return String.format(
                "GoalYLevel{y=%s}",
                SettingsUtil.maybeCensor(level)
        );
    }
}
