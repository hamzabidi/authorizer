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

import io.github.hamzabidi.authorizer.authorization.condition.data_fetcher.exception.DataFetcherException;
import io.github.hamzabidi.authorizer.authorization.operand.ListOperand;
import io.github.hamzabidi.authorizer.authorization.operand.PropertyOperand;
import io.github.hamzabidi.authorizer.authorization.operand.StringOperand;
import io.github.hamzabidi.authorizer.authorization.operand.exception.OperandException;
import io.github.hamzabidi.authorizer.authorization.operator.BasicOperator;
import io.github.hamzabidi.authorizer.authorization.operator.ListOperator;
import io.github.hamzabidi.authorizer.authorization.operator.exception.OperatorException;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

public interface Condition {
    Pattern PATTERN = Pattern.compile("(?<leftOperand>(%s))(?<operator>(%s|%s))(?<rightOperand>(%s|%s|%s))".formatted(
            PropertyOperand.PATTERN,
            BasicOperator.PATTERN,
            ListOperator.PATTERN,
            StringOperand.PATTERN,
            PropertyOperand.PATTERN,
            ListOperand.PATTERN)
    );
    Pattern STRICT_PATTERN = Pattern.compile("^%s$".formatted(PATTERN));

    boolean matches(Object resource) throws OperatorException, DataFetcherException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, OperandException;

    boolean matches(Object resource, Object base) throws OperatorException, DataFetcherException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, OperandException;
}
