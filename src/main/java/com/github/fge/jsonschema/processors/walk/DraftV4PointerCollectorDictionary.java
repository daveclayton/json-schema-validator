/*
 * Copyright (c) 2013, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.fge.jsonschema.processors.walk;

import com.github.fge.jsonschema.library.Dictionary;
import com.github.fge.jsonschema.library.DictionaryBuilder;
import com.github.fge.jsonschema.processors.walk.draftv4.AllOfPointerCollector;
import com.github.fge.jsonschema.processors.walk.draftv4.AnyOfPointerCollector;
import com.github.fge.jsonschema.processors.walk.draftv4.DefinitionsPointerCollector;
import com.github.fge.jsonschema.processors.walk.draftv4.NotPointerCollector;
import com.github.fge.jsonschema.processors.walk.draftv4.OneOfPointerCollector;

public final class DraftV4PointerCollectorDictionary
{
    private static final Dictionary<PointerCollector> DICTIONARY;

    private DraftV4PointerCollectorDictionary()
    {
    }

    static {
        final DictionaryBuilder<PointerCollector> builder
            = Dictionary.newBuilder();

        builder.addAll(CommonPointerCollectorDictionary.get());

        String keyword;
        PointerCollector collector;

        keyword = "allOf";
        collector = AllOfPointerCollector.getInstance();
        builder.addEntry(keyword, collector);

        keyword = "anyOf";
        collector = AnyOfPointerCollector.getInstance();
        builder.addEntry(keyword, collector);

        keyword = "definitions";
        collector = DefinitionsPointerCollector.getInstance();
        builder.addEntry(keyword, collector);

        keyword = "not";
        collector = NotPointerCollector.getInstance();
        builder.addEntry(keyword, collector);

        keyword = "oneOf";
        collector = OneOfPointerCollector.getInstance();
        builder.addEntry(keyword, collector);

        DICTIONARY = builder.freeze();
    }

    public static Dictionary<PointerCollector> get()
    {
        return DICTIONARY;
    }
}
