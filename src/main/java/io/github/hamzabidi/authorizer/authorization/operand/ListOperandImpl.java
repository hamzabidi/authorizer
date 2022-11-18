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

package io.github.hamzabidi.authorizer.authorization.operand;

import io.github.hamzabidi.authorizer.authorization.operand.exception.OperandException;
import io.github.hamzabidi.authorizer.authorization.operand.factory.OperandFactory;

import java.util.ArrayList;
import java.util.List;

public class ListOperandImpl implements ListOperand {
    private final String expression;
    private final List<ValueOperand> operands;

    public ListOperandImpl(String expression) throws OperandException {
        if (!STRICT_PATTERN.matcher(expression).matches()) {
            throw new OperandException(
                    "String %s does not match regex pattern %s".formatted(
                            expression,
                            STRICT_PATTERN
                    )
            );
        }

        this.expression = expression;
        this.operands = new ArrayList<>();

        OperandFactory factory = new OperandFactory();

        for (String subValue : List.of(expression.substring(1, expression.length() - 1).split(String.valueOf(SEPARATOR)))) {
            operands.add(factory.createValueOperand(subValue));
        }
    }

    @Override
    public List<ValueOperand> getOperands() {
        return operands;
    }

    @Override
    public String toString() {
        return expression;
    }
}
