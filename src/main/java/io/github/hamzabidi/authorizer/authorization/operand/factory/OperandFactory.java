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

package io.github.hamzabidi.authorizer.authorization.operand.factory;

import io.github.hamzabidi.authorizer.authorization.operand.*;
import io.github.hamzabidi.authorizer.authorization.operand.exception.OperandException;


public class OperandFactory {

    public ValueOperand createValueOperand(String expression) throws OperandException {
        if (IntegerOperand.STRICT_PATTERN.matcher(expression).matches()) {
            return new IntegerOperandImpl(Integer.parseInt(expression));
        } else if (StringOperandImpl.STRICT_PATTERN.matcher(expression).matches()) {
            return new StringOperandImpl(expression);
        }

        throw new OperandException(
                "String %s does not match any of regex patterns: \n %s \n %s".formatted(
                        expression,
                        IntegerOperand.STRICT_PATTERN,
                        StringOperandImpl.STRICT_PATTERN
                )
        );
    }


    public ComposedOperand createComposedOperand(String expression) throws OperandException {
        if (ListOperandImpl.STRICT_PATTERN.matcher(expression).matches()) {
            return new ListOperandImpl(expression);
        } else if (PropertyOperandImpl.STRICT_PATTERN.matcher(expression).matches()) {
            return new PropertyOperandImpl(expression);
        }

        throw new OperandException(
                "String %s does not match any of regex patterns: \n %s \n %s".formatted(
                        expression,
                        PropertyOperandImpl.STRICT_PATTERN,
                        ListOperandImpl.STRICT_PATTERN
                )
        );
    }
}
