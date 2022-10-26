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

package net.asad.authorizer.authorization.operator;

import net.asad.authorizer.authorization.operand.IntegerOperand;
import net.asad.authorizer.authorization.operand.StringOperand;
import net.asad.authorizer.authorization.operand.ValueOperand;
import net.asad.authorizer.authorization.operator.exception.OperatorException;

public class BasicOperatorImpl implements BasicOperator {
    private final String expression;

    public BasicOperatorImpl(String expression) throws OperatorException {
        if (!STRICT_PATTERN.matcher(expression).matches()) {
            throw new OperatorException(
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
    public boolean compare(ValueOperand left, ValueOperand right) throws OperatorException {
        if (!left.getClass().getName().equals(right.getClass().getName())) {
            throw new OperatorException(
                    "Try to compare two incompatible operands. Given operands types %s and %s"
                            .formatted(
                                    left.getClass().getName(),
                                    right.getClass().getName()
                            )
            );
        }

        if (left instanceof IntegerOperand) {
            return compare((IntegerOperand) left, (IntegerOperand) right);
        }

        if (left instanceof StringOperand) {
            return compare((StringOperand) left, (StringOperand) right);
        }

        throw new OperatorException("The two past operands are of a type that is not yet supported");
    }

    @Override
    public boolean compare(IntegerOperand left, IntegerOperand right) throws OperatorException {
        return switch (expression) {
            case "=" -> left.getInt() == right.getInt();
            case "!=" -> left.getInt() != right.getInt();
            case "<" -> left.getInt() < right.getInt();
            case ">" -> left.getInt() > right.getInt();
            case "<=" -> left.getInt() <= right.getInt();
            case ">=" -> left.getInt() >= right.getInt();
            default -> throw new OperatorException(
                    "Try to apply '%s' operator on operands of type %s."
                            .formatted(
                                    expression,
                                    IntegerOperand.class
                            )
            );
        };
    }

    @Override
    public boolean compare(StringOperand left, StringOperand right) throws OperatorException {
        final boolean equals = left.toString().equals(right.toString());

        return switch (expression) {
            case "=" -> equals;
            case "!=" -> !equals;
            default -> throw new OperatorException(
                    "Try to apply '%s' operator on operands of type %s."
                            .formatted(
                                    expression,
                                    StringOperand.class
                            )
            );
        };
    }
}
