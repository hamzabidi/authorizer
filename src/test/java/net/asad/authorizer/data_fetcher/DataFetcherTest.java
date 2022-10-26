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

package net.asad.authorizer.data_fetcher;

import net.asad.authorizer.authorization.condition.data_fetcher.DataFetcher;
import net.asad.authorizer.authorization.condition.data_fetcher.exception.DataFetcherException;
import net.asad.authorizer.authorization.operand.PropertyOperand;
import net.asad.authorizer.authorization.operand.PropertyOperandImpl;
import net.asad.authorizer.authorization.operand.exception.OperandException;
import net.asad.resource_example.Account;
import net.asad.resource_example.Age;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;

@SpringBootTest
class DataFetcherTest {
    @Autowired
    private DataFetcher dataFetcher;

    @Test
    void fetch() throws DataFetcherException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, OperandException {
        Account resource = new Account("test 1", "test 2", new Age(20));
        PropertyOperand propertyOperand = new PropertyOperandImpl("age");

        Object fetched = dataFetcher.fetch(resource, propertyOperand);
        System.out.println(fetched);
    }
}
