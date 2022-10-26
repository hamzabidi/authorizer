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

package net.asad.authorizer.authorization.operand;

import net.asad.authorizer.authorization.operand.exception.OperandException;
import net.asad.authorizer.authorization.operand.factory.OperandFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringOperandTest {
    private static OperandFactory factory;

    @BeforeAll
    static void setUp() {
        factory = new OperandFactory();
    }

    @Test
    public void whenBlankStringOperandValueGiven_thenCreateOperand_test() {
        //GIVEN
        String expression = "";

        //THEN
        assertThrows(OperandException.class, () -> factory.createValueOperand(expression));
    }

    @Test
    public void whenStringOperandValueGiven_thenCreateOperand_test() {
        //GIVEN
        String expression = "test expression ";

        //WHEN
        Operand operand = assertDoesNotThrow(() -> factory.createValueOperand(expression));

        //THEN
        assertInstanceOf(StringOperand.class, operand);
        assertEquals(((StringOperand) operand).toString(), expression);
    }

    @Test
    public void whenDoubleOperandValueGiven_thenThrowException_test() {
        // GIVEN
        String expression = "2.1";

        //THEN
        assertThrows(OperandException.class, () -> factory.createValueOperand(expression));
    }

    @Test
    public void whenIntegerOperandValueGiven_thenThrowException_test() {
        // GIVEN
        String expression = "1";

        //WHEN
        Operand operand = assertDoesNotThrow(() -> factory.createValueOperand(expression));

        //THEN
        assertNotEquals(StringOperand.class, operand.getClass());
    }

    @Test
    public void whenListOperandValueGiven_thenThrowException_test() {
        // GIVEN
        String expression = "{1}";

        //THEN
        assertThrows(OperandException.class, () -> factory.createValueOperand(expression));
    }
}
