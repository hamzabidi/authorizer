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

class PropertyOperandTest {
    private static OperandFactory factory;

    @BeforeAll
    static void setUp() {
        factory = new OperandFactory();
    }

    @Test
    public void whenBlankPropertyOperandValueGiven_thenThrowException_test() {
        //GIVEN
        String expression = "";

        //THEN
        assertThrows(OperandException.class, () -> factory.createComposedOperand(expression));
    }

    @Test
    public void whenNotEmptyPropertyOperandValueGiven_thenCreateOperand_test() {
        //GIVEN
        String expression = "attr1.attr2";

        //WHEN
        Operand operand = assertDoesNotThrow(() -> factory.createComposedOperand(expression));

        //THEN
        assertInstanceOf(PropertyOperand.class, operand);
        assertEquals(((PropertyOperand) operand).toString(), expression);
    }

    @Test
    public void whenDoubleOperandValueGiven_thenThrowException_test() {
        // GIVEN
        String expression = "2.1";

        //THEN
        assertThrows(OperandException.class, () -> factory.createComposedOperand(expression));
    }

    @Test
    public void whenIntegerOperandValueGiven_thenThrowException_test() {
        // GIVEN
        String expression = "1";

        //THEN
        assertThrows(OperandException.class, () -> factory.createComposedOperand(expression));
    }

    @Test
    public void whenStringOperandValueGiven_thenThrowException_test() {
        // GIVEN
        String expression = "string test";

        //THEN
        assertThrows(OperandException.class, () -> factory.createComposedOperand(expression));
    }

    @Test
    public void whenMalformedOperand1ValueGiven_thenThrowException_test() {
        // GIVEN
        String expression = "1attr.attr2";

        //THEN
        assertThrows(OperandException.class, () -> factory.createComposedOperand(expression));
    }

    @Test
    public void whenMalformedOperand2ValueGiven_thenThrowException_test() {
        // GIVEN
        String expression = "1attr.";

        //THEN
        assertThrows(OperandException.class, () -> factory.createComposedOperand(expression));
    }

    @Test
    public void whenMalformedOperand3ValueGiven_thenThrowException_test() {
        // GIVEN
        String expression = ".attr1.attr2";

        //THEN
        assertThrows(OperandException.class, () -> factory.createComposedOperand(expression));
    }
}
