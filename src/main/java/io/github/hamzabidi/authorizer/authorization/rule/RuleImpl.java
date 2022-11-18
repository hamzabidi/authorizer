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

package io.github.hamzabidi.authorizer.authorization.rule;

import io.github.hamzabidi.authorizer.authorization.condition.Condition;
import io.github.hamzabidi.authorizer.authorization.condition.data_fetcher.exception.DataFetcherException;
import io.github.hamzabidi.authorizer.authorization.condition.exception.ConditionException;
import io.github.hamzabidi.authorizer.authorization.condition.factory.ConditionFactory;
import io.github.hamzabidi.authorizer.authorization.operand.exception.OperandException;
import io.github.hamzabidi.authorizer.authorization.operator.exception.OperatorException;
import io.github.hamzabidi.authorizer.authorization.rule.exception.RuleException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class RuleImpl implements Rule {
    private final List<Condition> conditions;

    public RuleImpl() {
        this.conditions = new ArrayList<>();
    }

    public RuleImpl(String expression) throws OperatorException, ConditionException, OperandException, RuleException {
        String[] splitRule = expression.split(String.valueOf(SEPARATOR));

        if (expression.isEmpty() || splitRule.length == 0) {
            throw new RuleException(
                    "Rule must be composed of at 1 or more valid conditions separated by '%c'. Found 0 condition in '%s'".formatted(
                            SEPARATOR,
                            expression
                    )
            );
        }

        ConditionFactory conditionFactory = new ConditionFactory();
        conditions = new ArrayList<>();


        for (String condition : splitRule) {
            conditions.add(conditionFactory.create(condition));
        }
    }

    @Override
    public boolean matches(Object resource) throws OperatorException, OperandException, DataFetcherException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        for (Condition condition : conditions) {
            if (!condition.matches(resource)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean matches(Object resource, Object base) throws OperatorException, OperandException, DataFetcherException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        for (Condition condition : conditions) {
            if (!condition.matches(resource, base)) {
                return false;
            }
        }

        return true;
    }
}
