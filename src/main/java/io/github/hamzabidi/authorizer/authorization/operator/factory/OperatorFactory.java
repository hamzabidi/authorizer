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

package io.github.hamzabidi.authorizer.authorization.operator.factory;

import io.github.hamzabidi.authorizer.authorization.operator.BasicOperatorImpl;
import io.github.hamzabidi.authorizer.authorization.operator.ListOperatorImpl;
import io.github.hamzabidi.authorizer.authorization.operator.Operator;
import io.github.hamzabidi.authorizer.authorization.operator.exception.OperatorException;


public class OperatorFactory {
    public Operator create(String expression) throws OperatorException {
        if (BasicOperatorImpl.STRICT_PATTERN.matcher(expression).matches()) {
            return new BasicOperatorImpl(expression);
        } else if (ListOperatorImpl.STRICT_PATTERN.matcher(expression).matches()) {
            return new ListOperatorImpl(expression, this);
        }

        throw new OperatorException(
                "String %s does not match any of regex patterns: \n %s \n %s".formatted(
                        expression,
                        BasicOperatorImpl.STRICT_PATTERN,
                        ListOperatorImpl.STRICT_PATTERN
                )
        );
    }
}
