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

import edu.bsu.cybersec.core.GameWorld;
import edu.bsu.cybersec.core.SystemPriority;
import playn.core.Clock;
import tripleplay.entity.Entity;

import static com.google.common.base.Preconditions.*;

public final class EventTriggerSystem extends tripleplay.entity.System {
    private final GameWorld gameWorld;

    public EventTriggerSystem(GameWorld gameWorld) {
        super(gameWorld, SystemPriority.MODEL_LEVEL.value);
        this.gameWorld = checkNotNull(gameWorld);
    }

    @Override
    protected boolean isInterested(Entity entity) {
        boolean interested = entity.has(gameWorld.timeTrigger);
        if (interested) {
            checkState(entity.has(gameWorld.event), "I expect every time triggerable thing to have an event.");
        }
        return interested;
    }

    @Override
    protected void update(Clock clock, Entities entities) {
        super.update(clock, entities);
        for (int i = 0, limit = entities.size(); i < limit; i++) {
            final int id = entities.get(i);
            final int time = gameWorld.timeTrigger.get(id);
            if (gameWorld.gameTime.get().contains(time)) {
                runIfPossible(id);
            }
        }
    }

    private void runIfPossible(int id) {
        Runnable runnable = gameWorld.event.get(id);
        if (runnable != null) {
            gameWorld.event.get(id).run();
            gameWorld.event.set(id, null);
        }
    }
}
