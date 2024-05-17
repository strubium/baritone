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
import baritone.api.command.exception.CommandException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class represents a command to call System.gc() in Baritone.
 * It extends the Command class provided by Baritone's API.
 */
public class GcCommand extends Command {

    /**
     * Constructor for GcCommand.
     *
     * @param baritone The instance of IBaritone that this command is associated with.
     */
    public GcCommand(IBaritone baritone) {
        super(baritone, "gc");
    }

    /**
     * Executes the command.
     *
     * @param label The label of the command.
     * @param args The arguments passed to the command.
     * @throws CommandException If there are any errors executing the command.
     */
    @Override
    public void execute(String label, IArgConsumer args) throws CommandException {
        args.requireMax(0); // This command does not accept any arguments
        System.gc(); // Call System.gc()
        logDirect("ok called System.gc()"); // Log the success of calling System.gc()
    }

    /**
     * Provides tab completion options for the command.
     *
     * @param label The label of the command.
     * @param args The arguments passed to the command.
     * @return An empty stream, as this command does not support tab completion.
     */
    @Override
    public Stream<String> tabComplete(String label, IArgConsumer args) {
        return Stream.empty(); // This command does not support tab completion
    }

    /**
     * Returns a short description of the command.
     *
     * @return A string describing the command.
     */
    @Override
    public String getShortDesc() {
        return "Call System.gc()"; // Short description of the command
    }

    /**
     * Returns a long description of the command.
     *
     * @return A list of strings describing the command.
     */
    @Override
    public List<String> getLongDesc() {
        return Arrays.asList(
                "Calls System.gc().", // Long description of the command
                "",
                "Usage:",
                "> gc" // Usage example
        );
    }
}