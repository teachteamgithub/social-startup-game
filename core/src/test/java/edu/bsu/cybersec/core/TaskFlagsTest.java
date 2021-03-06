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

package edu.bsu.cybersec.core;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class TaskFlagsTest {

    @Test
    public void testIsSet_notSet() {
        assertFalse(TaskFlags.REASSIGNABLE.isSet(0));
    }

    @Test
    public void testIsSet_set() {
        assertTrue(TaskFlags.REASSIGNABLE.isSet(TaskFlags.REASSIGNABLE.set(0)));
    }

    @Test
    public void testFlags() {
        int flags = TaskFlags.flags(TaskFlags.DEVELOPMENT);
        assertTrue(TaskFlags.DEVELOPMENT.isSet(flags));
    }

    @Test
    public void testFlags_notIncludedAreNotSet() {
        int flags = TaskFlags.flags(TaskFlags.DEVELOPMENT);
        assertFalse(TaskFlags.MAINTENANCE.isSet(flags));
    }

    @Test
    public void testAny() {
        int flags = TaskFlags.flags(TaskFlags.DEVELOPMENT);
        assertTrue(TaskFlags.any(TaskFlags.DEVELOPMENT, TaskFlags.MAINTENANCE).in(flags));
    }

    @Test
    public void testAny_none() {
        int flags = TaskFlags.flags();
        assertFalse(TaskFlags.any(TaskFlags.DEVELOPMENT, TaskFlags.MAINTENANCE).in(flags));
    }

    @Test
    public void testAny_noMatch() {
        int flags = TaskFlags.flags(TaskFlags.NOT_AT_WORK);
        assertFalse(TaskFlags.any(TaskFlags.DEVELOPMENT, TaskFlags.MAINTENANCE).in(flags));
    }
}
