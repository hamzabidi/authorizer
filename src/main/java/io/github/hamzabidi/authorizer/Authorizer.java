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

package io.github.hamzabidi.authorizer;

import io.github.hamzabidi.authorizer.authorization.Authorization;
import io.github.hamzabidi.authorizer.exception.AuthorizerException;

import java.util.ArrayList;
import java.util.List;

public class Authorizer {
    private final List<Authorization> authorizations;

    public Authorizer() {
        this.authorizations = new ArrayList<>();
    }

    public Authorizer(List<Authorization> authorizations) {
        this.authorizations = authorizations;
    }

    public boolean hasAuthorization(String actionExpression, Object resource) throws AuthorizerException {
        for (Authorization authorization : authorizations) {
            try {
                if (authorization.matches(actionExpression, resource)) {
                    return true;
                }
            } catch (Exception e) {
                throw new AuthorizerException(e);
            }
        }
        return false;
    }
}
