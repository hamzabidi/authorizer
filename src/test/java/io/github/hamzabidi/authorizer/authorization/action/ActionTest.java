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

package io.github.hamzabidi.authorizer.authorization.action;

import io.github.hamzabidi.authorizer.authorization.action.exception.ActionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {

    @Test
    public void whenWrongActionGiven_thenTrowException_test() {
        //GIVEN
        String expression = ".";

        //THEN
        assertThrows(ActionException.class, () -> new ActionImpl(expression));
    }

    @Test
    public void createGetActionTest() {
        //GIVEN
        String expression = "get";

        //WHEN
        Action action = assertDoesNotThrow(() -> new ActionImpl(expression));

        //THEN
        assertEquals(action.toString(), expression);
    }

    @Test
    public void createPostActionTest() {
        //GIVEN
        String expression = "post";

        //WHEN
        Action action = assertDoesNotThrow(() -> new ActionImpl(expression));

        //THEN
        assertEquals(action.toString(), expression);
    }

    @Test
    public void createPutActionTest() {
        //GIVEN
        String expression = "put";

        //WHEN
        Action action = assertDoesNotThrow(() -> new ActionImpl(expression));

        //THEN
        assertEquals(action.toString(), expression);
    }

    @Test
    public void createPatchActionTest() {
        //GIVEN
        String expression = "patch";

        //WHEN
        Action action = assertDoesNotThrow(() -> new ActionImpl(expression));

        //THEN
        assertEquals(action.toString(), expression);
    }

    @Test
    public void createDeleteActionTest() {
        //GIVEN
        String expression = "delete";

        //WHEN
        Action action = assertDoesNotThrow(() -> new ActionImpl(expression));

        //THEN
        assertEquals(action.toString(), expression);
    }

    @Test
    public void createAllActionTest() {
        //GIVEN
        String expression = "*";

        //WHEN
        Action action = assertDoesNotThrow(() -> new ActionImpl(expression));

        //THEN
        assertEquals(action.toString(), expression);
    }

    @Test
    public void matchesTest() {
        //GIVEN
        String expression = "get";
        Action action = assertDoesNotThrow(() -> new ActionImpl(expression));

        //THEN
        assertTrue(action.matches(expression));
    }

    @Test
    public void doesNotMatchesTest() {
        //GIVEN
        String expression = "post";
        Action action = assertDoesNotThrow(() -> new ActionImpl("get"));

        //THEN
        assertFalse(action.matches(expression));
    }
}
