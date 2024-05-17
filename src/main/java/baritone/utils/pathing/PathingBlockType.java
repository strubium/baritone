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

package baritone.utils.pathing;

/**
 * Represents different types of blocks that can be encountered during pathing.
 *
 * @author Brady
 * @since 8/4/2018
 */
public enum PathingBlockType {

    AIR(0b00),
    WATER(0b01),
    AVOID(0b10),
    SOLID(0b11);

    private final boolean[] bits;
    
    /**
     * Constructs a PathingBlockType from the given bit representation.
     *
     * @param bits The bit representation of the PathingBlockType.
     */
    PathingBlockType(int bits) {
        this.bits = new boolean[]{
                (bits & 0b10) != 0,
                (bits & 0b01) != 0
        };
    }
    
    /**
     * Returns the bit representation of this PathingBlockType.
     *
     * @return The bit representation of this PathingBlockType.
     */
    public final boolean[] getBits() {
        return this.bits;
    }
    
    /**
     * Constructs a PathingBlockType from the given boolean values.
     *
     * @param b1 The first boolean value.
     * @param b2 The second boolean value.
     * @return The PathingBlockType corresponding to the given boolean values.
     */
    public static PathingBlockType fromBits(boolean b1, boolean b2) {
        return b1 ? b2 ? SOLID : AVOID : b2 ? WATER : AIR;
    }
}
