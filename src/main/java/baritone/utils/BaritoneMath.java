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

package baritone.utils;

/**
 * A utility class for mathematical operations related to Baritone.
 *
 * @author Brady
 */
public final class BaritoneMath {

    private static final double FLOOR_DOUBLE_D = 1_073_741_824.0;
    private static final int FLOOR_DOUBLE_I = 1_073_741_824;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private BaritoneMath() {}
    
    /**
     * Fast floor operation for double values.
     * This method is optimized for performance and provides the same result as Math.floor(double).
     *
     * @param v The double value to be floored.
     * @return The largest integer less than or equal to the given double value.
     */
    public static int fastFloor(final double v) {
        return (int) (v + FLOOR_DOUBLE_D) - FLOOR_DOUBLE_I;
    }
    
    /**
     * Fast ceil operation for double values.
     * This method is optimized for performance and provides the same result as Math.ceil(double).
     *
     * @param v The double value to be ceiled.
     * @return The smallest integer greater than or equal to the given double value.
     */
    public static int fastCeil(final double v) {
        return FLOOR_DOUBLE_I - (int) (FLOOR_DOUBLE_D - v);
    }
}
