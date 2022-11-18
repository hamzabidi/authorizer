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

package io.github.hamzabidi.authorizer.authorization.condition;

import io.github.hamzabidi.authorizer.authorization.condition.data_fetcher.DataFetcher;
import io.github.hamzabidi.authorizer.authorization.condition.data_fetcher.exception.DataFetcherException;
import io.github.hamzabidi.authorizer.authorization.condition.exception.ConditionException;
import io.github.hamzabidi.authorizer.authorization.operand.ListOperand;
import io.github.hamzabidi.authorizer.authorization.operand.Operand;
import io.github.hamzabidi.authorizer.authorization.operand.PropertyOperand;
import io.github.hamzabidi.authorizer.authorization.operand.ValueOperand;
import io.github.hamzabidi.authorizer.authorization.operand.exception.OperandException;
import io.github.hamzabidi.authorizer.authorization.operand.factory.OperandFactory;
import io.github.hamzabidi.authorizer.authorization.operator.BasicOperator;
import io.github.hamzabidi.authorizer.authorization.operator.ListOperator;
import io.github.hamzabidi.authorizer.authorization.operator.Operator;
import io.github.hamzabidi.authorizer.authorization.operator.exception.OperatorException;
import io.github.hamzabidi.authorizer.authorization.operator.factory.OperatorFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;

public class ConditionImpl implements Condition {
    private final DataFetcher dataFetcher = new DataFetcher();
    private final Operator operator;
    private final PropertyOperand propertyOperand;
    private final Operand rightOperand;
    private OperandFactory operandFactory;

    public ConditionImpl(Operator operator, PropertyOperand propertyOperand, Operand rightOperand) {
        this.operator = operator;
        this.propertyOperand = propertyOperand;
        this.rightOperand = rightOperand;
    }

    public ConditionImpl(String expression) throws OperatorException, OperandException, ConditionException {
        Matcher matcher = STRICT_PATTERN.matcher(expression);

        if (!matcher.matches()) {
            throw new ConditionException(
                    "String %s does not match regex pattern %s".formatted(
                            expression,
                            STRICT_PATTERN
                    )
            );
        }

        OperatorFactory operatorFactory = new OperatorFactory();
        operator = operatorFactory.create(matcher.group("operator"));

        operandFactory = new OperandFactory();

        propertyOperand = (PropertyOperand) operandFactory.createComposedOperand(matcher.group("leftOperand"));

        Operand rightOperand1;

        try {
            rightOperand1 = operandFactory.createValueOperand(matcher.group("rightOperand"));
        } catch (OperandException e) {
            rightOperand1 = operandFactory.createComposedOperand(matcher.group("rightOperand"));
        }

        rightOperand = rightOperand1;
    }

    @Override
    public boolean matches(Object resource) throws OperatorException, DataFetcherException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, OperandException {
        ValueOperand leftOperand;

        leftOperand = operandFactory.createValueOperand(
                String.valueOf(dataFetcher.fetch(resource, propertyOperand))
        );


        if (rightOperand instanceof ValueOperand) {
            return ((BasicOperator) operator).compare(leftOperand, (ValueOperand) rightOperand);
        }

        if (rightOperand instanceof ListOperand) {
            return ((ListOperator) operator).contains((ListOperand) rightOperand, leftOperand);
        }

        return false;
    }

    @Override
    public boolean matches(Object resource, Object base) throws OperatorException, DataFetcherException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, OperandException {
        if (!(rightOperand instanceof PropertyOperand)) {
            return matches(resource);
        }

        ValueOperand left;
        ValueOperand right;

        left = operandFactory.createValueOperand(String.valueOf(dataFetcher.fetch(resource, propertyOperand)));
        right = operandFactory.createValueOperand(String.valueOf(dataFetcher.fetch(base, (PropertyOperand) rightOperand)));


        return ((BasicOperator) operator).compare(left, right);
    }
}
