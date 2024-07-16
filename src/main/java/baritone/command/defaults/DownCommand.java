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
import baritone.api.pathing.goals.GoalYLevel;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DownCommand extends Command {

    public DownCommand(IBaritone baritone) {
        super(baritone, "down");
    }

    @Override
    public void execute(String label, IArgConsumer args) throws CommandException {
        args.requireExactly(1);
        int blocksDown = args.getAs(Integer.class);
        int currentY = ctx.playerFeet().y;
        int targetY = currentY - blocksDown;
        GoalYLevel goal = new GoalYLevel(targetY);

        baritone.getCustomGoalProcess().setGoalAndPath(goal);
        logDirect(String.format("Goal set to %d blocks down (Y = %d)", blocksDown, targetY));
    }

    @Override
    public Stream<String> tabComplete(String label, IArgConsumer args) {
        return Stream.empty();
    }

    @Override
    public String getShortDesc() {
        return "Go down!";
    }
    @Override
    public List<String> getLongDesc() {
        return Arrays.asList(
                "Creates a GoalYLevel some amount of blocks down",
                "",
                "Usage:",
                "> up <distance> - makes a GoalYLevel goal distance blocks in below of you"
        );
    }
}