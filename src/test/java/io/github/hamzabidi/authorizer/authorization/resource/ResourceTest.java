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

package io.github.hamzabidi.authorizer.authorization.resource;

import io.github.hamzabidi.authorizer.authorization.resource.exception.ResourceException;
import io.github.hamzabidi.authorizer.authorization.resource.factory.ResourceFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTest {
    private static ResourceFactory factory;

    @BeforeAll
    static void setUp() {
        factory = new ResourceFactory();
    }

    @Test
    public void whenBlankResourceValueGiven_thenThrowException_test() {
        //GIVEN
        String expression = "";

        //THEN
        assertThrows(ResourceException.class, () -> factory.create(expression));
    }

    @Test
    public void whenNotEmptyResourceValueGiven_thenCreateOperand_test() {
        //GIVEN
        String expression = "io.github.hamzabidi.authorizer.resource_example.Account";

        //WHEN
        Resource resource = assertDoesNotThrow(() -> factory.create(expression));

        //THEN
        assertEquals(resource.toString(), expression);
    }

    @Test
    public void whenMalformedResource0ValueGiven_thenThrowException_test() {
        //GIVEN
        String expression = "attr1.attr2";

        //THEN
        assertThrows(ResourceException.class, () -> factory.create(expression));
    }

    @Test
    public void whenMalformedResource1ValueGiven_thenThrowException_test() {
        // GIVEN
        String expression = "2.1";

        //THEN
        assertThrows(ResourceException.class, () -> factory.create(expression));
    }

    @Test
    public void whenMalformedResource2ValueGiven_thenThrowException_test() {
        // GIVEN
        String expression = "1";

        //THEN
        assertThrows(ResourceException.class, () -> factory.create(expression));
    }

    @Test
    public void whenMalformedResource3ValueGiven_thenThrowException_test() {
        // GIVEN
        String expression = "string test";

        //THEN
        assertThrows(ResourceException.class, () -> factory.create(expression));
    }

    @Test
    public void whenMalformedResource4ValueGiven_thenThrowException_test() {
        // GIVEN
        String expression = "1attr.attr2";

        //THEN
        assertThrows(ResourceException.class, () -> factory.create(expression));
    }

    @Test
    public void whenMalformedResource5ValueGiven_thenThrowException_test() {
        // GIVEN
        String expression = "1attr.";

        //THEN
        assertThrows(ResourceException.class, () -> factory.create(expression));
    }

    @Test
    public void whenMalformedResource6ValueGiven_thenThrowException_test() {
        // GIVEN
        String expression = ".attr1.attr2";

        //THEN
        assertThrows(ResourceException.class, () -> factory.create(expression));
    }
}
