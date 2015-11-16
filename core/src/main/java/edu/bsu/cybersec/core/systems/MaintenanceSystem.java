/*
 * Copyright 2015 Paul Gestwicki
 *
 * This file is part of The Social Startup Game
 *
 * The Social Startup Game is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The Social Startup Game is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with The Social Startup Game.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.bsu.cybersec.core.systems;

import edu.bsu.cybersec.core.ClockUtils;
import edu.bsu.cybersec.core.GameWorld;
import edu.bsu.cybersec.core.SystemPriority;
import edu.bsu.cybersec.core.Task;
import playn.core.Clock;
import tripleplay.entity.Entity;

import static com.google.common.base.Preconditions.*;

public final class MaintenanceSystem extends tripleplay.entity.System {

    private final GameWorld world;

    public MaintenanceSystem(GameWorld world) {
        super(world, SystemPriority.MODEL_LEVEL.value);
        this.world = checkNotNull(world);
    }

    @Override
    protected boolean isInterested(Entity entity) {
        boolean interested = entity.has(world.tasked)
                && world.tasked.get(entity.id) == Task.MAINTENANCE;
        if (interested) {
            checkState(entity.has(world.maintenanceSkill));
        }
        return interested;
    }

    @Override
    protected void update(Clock clock, Entities entities) {
        super.update(clock, entities);
        final int elapsedSeconds = world.gameTime.get().delta();
        for (int i = 0, limit = entities.size(); i < limit; i++) {
            final int id = entities.get(i);
            final float currentExposure = world.exposure.get();
            final float percentChangePerHour = (int) world.maintenanceSkill.get(id) / 1000f; // 5 skill = 0.05%
            final float changePerHour = currentExposure * percentChangePerHour;
            final float change = changePerHour * elapsedSeconds / ClockUtils.SECONDS_PER_HOUR;
            world.exposure.update(currentExposure - change);
        }
    }
}