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

package baritone.api.utils;

/**
 * This functional interface represents an operation on two boolean operands and produces a boolean result.
 * It is a specialization of {@link java.util.function.BiFunction} for boolean values.
 *
 * <p>This is a functional interface whose functional method is {@link #applyAsBoolean(boolean, boolean)}.
 *
 * @author Brady
 */
@FunctionalInterface
public interface BooleanBinaryOperator {

    /**
     * Applies this operator to the given arguments.
     *
     * @param a the first operand
     * @param b the second operand
     * @return the result of applying this operator to {@code a} and {@code b}
     */
    boolean applyAsBoolean(boolean a, boolean b);
}
