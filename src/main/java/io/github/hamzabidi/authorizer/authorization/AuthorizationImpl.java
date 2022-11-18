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

package io.github.hamzabidi.authorizer.authorization;

import io.github.hamzabidi.authorizer.authorization.action.Action;
import io.github.hamzabidi.authorizer.authorization.action.ActionImpl;
import io.github.hamzabidi.authorizer.authorization.action.exception.ActionException;
import io.github.hamzabidi.authorizer.authorization.condition.data_fetcher.exception.DataFetcherException;
import io.github.hamzabidi.authorizer.authorization.condition.exception.ConditionException;
import io.github.hamzabidi.authorizer.authorization.exception.AuthorizationException;
import io.github.hamzabidi.authorizer.authorization.operand.exception.OperandException;
import io.github.hamzabidi.authorizer.authorization.operator.exception.OperatorException;
import io.github.hamzabidi.authorizer.authorization.resource.Resource;
import io.github.hamzabidi.authorizer.authorization.resource.ResourceImpl;
import io.github.hamzabidi.authorizer.authorization.resource.exception.ResourceException;
import io.github.hamzabidi.authorizer.authorization.rule.Rule;
import io.github.hamzabidi.authorizer.authorization.rule.RuleImpl;
import io.github.hamzabidi.authorizer.authorization.rule.exception.RuleException;

import java.lang.reflect.InvocationTargetException;

public class AuthorizationImpl implements Authorization {
    private final Action action;
    private final Resource resource;
    private final Rule rule;

    public AuthorizationImpl(String expression) throws AuthorizationException, ConditionException, OperandException, RuleException, OperatorException, ActionException, ResourceException {
        String[] splitAuthorization = expression.split(String.valueOf(SEPARATOR), 3);

        if (splitAuthorization.length != 2 && splitAuthorization.length != 3) {
            throw new AuthorizationException(
                    "Authorization must be composed of 2 or 3 expressions separated by '%c'. Found %d expression(s) in '%s'".formatted(
                            SEPARATOR,
                            splitAuthorization.length,
                            expression
                    )
            );
        }

        action = new ActionImpl(splitAuthorization[0]);
        resource = new ResourceImpl(splitAuthorization[1]);
        rule = splitAuthorization.length == 2 ? // authorization with empty rule (0 conditions)
                new RuleImpl() :
                new RuleImpl(splitAuthorization[2]);
    }

    @Override
    public boolean matches(String action, Object resource) throws OperatorException, OperandException, DataFetcherException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return this.action.matches(action) &&
                this.resource.matches(resource) &&
                this.rule.matches(resource);
    }

    @Override
    public boolean matches(String action, Object resource, Object base) throws OperatorException, OperandException, DataFetcherException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return this.action.matches(action) &&
                this.resource.matches(resource) &&
                this.rule.matches(resource, base);
    }
}
