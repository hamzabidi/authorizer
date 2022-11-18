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

package io.github.hamzabidi.authorizer;

import io.github.hamzabidi.authorizer.authorization.Authorization;
import io.github.hamzabidi.authorizer.authorization.AuthorizationImpl;
import io.github.hamzabidi.authorizer.authorization.condition.data_fetcher.exception.DataFetcherException;
import io.github.hamzabidi.authorizer.authorization.condition.exception.ConditionException;
import io.github.hamzabidi.authorizer.authorization.operand.exception.OperandException;
import io.github.hamzabidi.authorizer.authorization.operator.exception.OperatorException;
import io.github.hamzabidi.authorizer.authorization.rule.exception.RuleException;
import io.github.hamzabidi.resource_example.Account;
import io.github.hamzabidi.resource_example.Age;
import io.github.hamzabidi.resource_example.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorizerApplicationTests {

    @Test
    void AuthorizerPass0Test() throws OperatorException, OperandException, DataFetcherException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        //GIVEN
        String authorizationExpression = "get-io.github.hamzabidi.resource_example.Account-age.myAge<=20&firstname{}{test}";
        Authorization authorization = assertDoesNotThrow(() -> new AuthorizationImpl(authorizationExpression));
        Account resource = new Account("test", "test 2", new Age(20));

        //THEN
        assertTrue(authorization.matches("get", resource));
    }

    @Test
    void AuthorizerPass1Test() throws OperatorException, OperandException, DataFetcherException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        //GIVEN
        String authorizationExpression = "get-io.github.hamzabidi.resource_example.Account-age.myAge<=19&firstname{}{test}";
        Authorization authorization = assertDoesNotThrow(() -> new AuthorizationImpl(authorizationExpression));
        Account resource = new Account("test", "test 2", new Age(20));

        //THEN
        assertFalse(authorization.matches("get", resource));
    }

    @Test
    void AuthorizerPass2Test() throws OperatorException, OperandException, DataFetcherException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        //GIVEN
        String authorizationExpression = "get-io.github.hamzabidi.resource_example.Account-age.myAge=userAge.myAge&firstname{}{test}";
        Authorization authorization = assertDoesNotThrow(() -> new AuthorizationImpl(authorizationExpression));
        Account resource = new Account("test", "test 2", new Age(20));
        User user = new User(0, new Age(20));

        //THEN
        assertTrue(authorization.matches("get", resource, user));
    }

    @Test
    void AuthorizerPass3Test() {
        //GIVEN

        // 1 - create authorizations
        String authorizationExpression1 = "get-io.github.hamzabidi.resource_example.Account";
        Authorization authorization1 = assertDoesNotThrow(() -> new AuthorizationImpl(authorizationExpression1));

        String authorizationExpression2 = "get-io.github.hamzabidi.resource_example.Card";
        Authorization authorization2 = assertDoesNotThrow(() -> new AuthorizationImpl(authorizationExpression2));

        List<Authorization> authorizations = new ArrayList<>();
        authorizations.add(authorization2);
        authorizations.add(authorization1);

        // 2 - create an authorizer
        Authorizer authorizer = new Authorizer(authorizations);

        // 3 - create who will ask for some actions in the application
        User user = new User(0, new Age(20), authorizer);

        // 4 - user would like do an action on an account resource
        Account resource = new Account("test", "test 2", new Age(20));

        //WHEN
        boolean matches = assertDoesNotThrow(() -> user.getAuthorizer().hasAuthorization("get", resource));

        //THEN
        assertTrue(matches);
    }

    @Test
    void AuthorizerDoesNotPass0Test() {
        //GIVEN
        String authorizationExpression = "get-io.github.hamzabidi.resource_example.Account-";

        //THEN
        assertThrows(RuleException.class, () -> new AuthorizationImpl(authorizationExpression));
    }

    @Test
    void AuthorizerDoesNotPass1Test() {
        //GIVEN
        String authorizationExpression = "get-io.github.hamzabidi.resource_example.Account-&";

        //THEN
        assertThrows(RuleException.class, () -> new AuthorizationImpl(authorizationExpression));
    }

    @Test
    void AuthorizerDoesNotPass2Test() {
        //GIVEN
        String authorizationExpression = "get-io.github.hamzabidi.resource_example.Account-&firstname{}{test}";

        //THEN
        assertThrows(ConditionException.class, () -> new AuthorizationImpl(authorizationExpression));
    }
}
