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

package com.github.fge.jsonschema.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.main.JsonSchemaException;
import com.github.fge.jsonschema.ref.JsonRef;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Queues;

import java.util.Deque;

public abstract class JsonSchemaTree
    extends JsonTree
{
    protected final JsonRef loadingRef;

    protected final Deque<JsonRef> refStack = Queues.newArrayDeque();

    protected JsonRef currentRef;

    protected JsonSchemaTree(final JsonRef loadingRef, final JsonNode baseNode)
    {
        super(baseNode);
        this.loadingRef = currentRef = loadingRef;

        final JsonRef ref = idFromNode(baseNode);

        if (ref != null)
            currentRef = currentRef.resolve(ref);
    }

    protected JsonSchemaTree(final JsonNode baseNode)
    {
        this(JsonRef.emptyRef(), baseNode);
    }

    @VisibleForTesting
    final JsonRef getCurrentRef()
    {
        return currentRef;
    }

    private static JsonRef idFromNode(final JsonNode node)
    {
        if (!node.path("id").isTextual())
            return null;

        try {
            return JsonRef.fromString(node.get("id").textValue());
        } catch (JsonSchemaException ignored) {
            return null;
        }
    }
}