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

import baritone.api.BaritoneAPI;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.SettingsUtil;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * Useful for long-range goals that don't have a specific Y level.
 * This class represents a goal to reach a specific XZ coordinate.
 *
 * @author leijurv
 */
public class GoalXZ implements Goal {

    private static final double SQRT_2 = Math.sqrt(2);

    /**
     * The X block position of this goal.
     */
    private final int x;

    /**
     * The Z block position of this goal.
     */
    private final int z;

    /**
     * Constructs a new GoalXZ with the specified X and Z positions.
     *
     * @param x the X position of the goal
     * @param z the Z position of the goal
     */
    public GoalXZ(int x, int z) {
        this.x = x;
        this.z = z;
    }

    /**
     * Constructs a new GoalXZ with the specified {@link BetterBlockPos} position.
     *
     * @param pos the block position containing X and Z coordinates
     */
    public GoalXZ(BetterBlockPos pos) {
        this.x = pos.x;
        this.z = pos.z;
    }

    /**
     * Determines if the specified coordinates are at the goal XZ position.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return true if the x and z coordinates match the goal's x and z coordinates, false otherwise
     */
    @Override
    public boolean isInGoal(int x, int y, int z) {
        return x == this.x && z == this.z;
    }

    /**
     * Calculates the heuristic cost to reach the target XZ position from the current coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return the heuristic cost
     */
    @Override
    public double heuristic(int x, int y, int z) {
        int xDiff = x - this.x;
        int zDiff = z - this.z;
        return calculate(xDiff, zDiff);
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

        GoalXZ goal = (GoalXZ) o;
        return x == goal.x && z == goal.z;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 1791873246;
        hash = hash * 222601791 + x;
        hash = hash * -1331679453 + z;
        return hash;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return String.format(
                "GoalXZ{x=%s,z=%s}",
                SettingsUtil.maybeCensor(x),
                SettingsUtil.maybeCensor(z)
        );
    }

    /**
     * Calculates the heuristic cost to reach the goal XZ position from the current XZ position differences.
     *
     * @param xDiff the difference in X coordinates
     * @param zDiff the difference in Z coordinates
     * @return the heuristic cost
     */
    public static double calculate(double xDiff, double zDiff) {
        // This is a combination of Pythagorean and Manhattan distance.
        // It takes into account the fact that pathing can either walk diagonally or forwards.
        double x = Math.abs(xDiff);
        double z = Math.abs(zDiff);
        double straight;
        double diagonal;
        if (x < z) {
            straight = z - x;
            diagonal = x;
        } else {
            straight = x - z;
            diagonal = z;
        }
        diagonal *= SQRT_2;
        return (diagonal + straight) * BaritoneAPI.getSettings().costHeuristic.value; // big TODO tune
    }

    /**
     * Creates a GoalXZ from a given direction, yaw, and distance.
     *
     * @param origin the starting position
     * @param yaw the yaw angle
     * @param distance the distance to the goal
     * @return a new GoalXZ at the specified direction and distance
     */
    public static GoalXZ fromDirection(Vec3d origin, float yaw, double distance) {
        float theta = (float) Math.toRadians(yaw);
        double x = origin.x - MathHelper.sin(theta) * distance;
        double z = origin.z + MathHelper.cos(theta) * distance;
        return new GoalXZ(MathHelper.floor(x), MathHelper.floor(z));
    }

    /**
     * Gets the X coordinate of the goal.
     *
     * @return the X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the Z coordinate of the goal.
     *
     * @return the Z coordinate
     */
    public int getZ() {
        return z;
    }
}
