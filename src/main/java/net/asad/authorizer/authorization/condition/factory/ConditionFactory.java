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

package net.asad.authorizer.authorization.condition.factory;

import net.asad.authorizer.authorization.condition.Condition;
import net.asad.authorizer.authorization.condition.ConditionImpl;
import net.asad.authorizer.authorization.condition.exception.ConditionException;
import net.asad.authorizer.authorization.operand.PropertyOperand;
import net.asad.authorizer.authorization.operand.exception.OperandException;
import net.asad.authorizer.authorization.operand.factory.OperandFactory;
import net.asad.authorizer.authorization.operator.exception.OperatorException;
import net.asad.authorizer.authorization.operator.factory.OperatorFactory;

public class ConditionFactory {
    public Condition create(String expression) throws OperatorException, OperandException, ConditionException {
        return new ConditionImpl(expression);
    }

    public Condition create(String operator, String leftOperand, String rightOperand) throws OperatorException, OperandException {
        OperatorFactory operatorFactory = new OperatorFactory();
        OperandFactory operandFactory = new OperandFactory();

        try {
            return new ConditionImpl(
                    operatorFactory.create(operator),
                    (PropertyOperand) operandFactory.createComposedOperand(leftOperand),
                    operandFactory.createValueOperand(rightOperand)
            );
        } catch (OperandException e) {
            return new ConditionImpl(
                    operatorFactory.create(operator),
                    (PropertyOperand) operandFactory.createComposedOperand(leftOperand),
                    operandFactory.createComposedOperand(rightOperand)
            );
        }
    }
}
