/*
 * MIT License
 *
 * Copyright (c) 2022 hamzabidi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.hamzabidi.authorizer.authorization.resource;

import io.github.hamzabidi.authorizer.authorization.resource.exception.ResourceException;

public class ResourceImpl implements Resource {
    private final String expression;

    public ResourceImpl(String expression) throws ResourceException {
        if (!STRICT_PATTERN.matcher(expression).matches()) {
            throw new ResourceException(
                    "String %s does not match regex pattern %s".formatted(
                            expression,
                            STRICT_PATTERN
                    )
            );
        }

        this.expression = expression;
    }

    @Override
    public String toString() {
        return expression;
    }


    @Override
    public boolean matches(Object object) {
        return object.getClass().getName().equals(expression);
    }
}
