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

package io.github.hamzabidi.authorizer.authorization.operator;

import io.github.hamzabidi.authorizer.authorization.operand.ListOperand;
import io.github.hamzabidi.authorizer.authorization.operand.ValueOperand;
import io.github.hamzabidi.authorizer.authorization.operator.exception.OperatorException;
import io.github.hamzabidi.authorizer.authorization.operator.factory.OperatorFactory;

public class ListOperatorImpl implements ListOperator {
    private final String expression;
    private final BasicOperator equalOperator;

    public ListOperatorImpl(String expression, OperatorFactory factory) throws OperatorException {
        if (!STRICT_PATTERN.matcher(expression).matches()) {
            throw new OperatorException(
                    "String %s does not match regex pattern %s".formatted(
                            expression,
                            STRICT_PATTERN
                    )
            );
        }

        this.expression = expression;
        this.equalOperator = (BasicOperator) factory.create("=");
    }

    @Override
    public String toString() {
        return expression;
    }

    @Override
    public boolean contains(ListOperand listOperand, ValueOperand valueOperand) throws OperatorException {
        return switch (expression) {
            case "{}" -> {
                for (ValueOperand r : listOperand.getOperands()) {
                    if (equalOperator.compare(valueOperand, r)) {
                        yield true;
                    }
                }
                yield false;
            }
            case "!{}" -> {
                for (ValueOperand r : listOperand.getOperands()) {
                    if (equalOperator.compare(valueOperand, r)) {
                        yield false;
                    }
                }
                yield true;
            }
            default -> throw new OperatorException(
                    "Try to apply '%s' operator on a right operand of type %s.".formatted(
                            expression,
                            ListOperand.class
                    )
            );
        };
    }
}
