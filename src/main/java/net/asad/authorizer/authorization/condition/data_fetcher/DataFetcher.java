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

package net.asad.authorizer.authorization.condition.data_fetcher;

import net.asad.authorizer.authorization.condition.data_fetcher.exception.DataFetcherException;
import net.asad.authorizer.authorization.operand.PropertyOperand;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;

@Component
public class DataFetcher {
    public Object fetch(Object resource, PropertyOperand property) throws DataFetcherException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return recurse(resource, property.asList().iterator());
    }

    private Object recurse(Object resource, Iterator<String> property) throws DataFetcherException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        if (!property.hasNext()) {
            return resource;
        }

        String subProperty = property.next();
        AccessibleObject accessibleObject;

        if (
                Arrays.stream(resource.getClass().getDeclaredMethods())
                        .anyMatch(method -> method.getName().equals(subProperty))
        ) {
            accessibleObject = resource.getClass().getDeclaredMethod(subProperty);
        } else if (
                Arrays.stream(resource.getClass().getDeclaredMethods())
                        .anyMatch(method -> method.getName().equals("get%s".formatted(StringUtils.capitalize(subProperty))))
        ) {
            accessibleObject = resource.getClass().getDeclaredMethod("get%s".formatted(StringUtils.capitalize(subProperty)));
        } else if (
                Arrays.stream(resource.getClass().getDeclaredMethods())
                        .anyMatch(method -> method.getName().equals("is%s".formatted(StringUtils.capitalize(subProperty))))
        ) {
            accessibleObject = resource.getClass().getDeclaredMethod("is%s".formatted(StringUtils.capitalize(subProperty)));
        } else if (
                Arrays.stream(resource.getClass().getDeclaredMethods())
                        .anyMatch(method -> method.getName().equals("has%s".formatted(StringUtils.capitalize(subProperty))))
        ) {
            accessibleObject = resource.getClass().getDeclaredMethod("has%s".formatted(StringUtils.capitalize(subProperty)));
        } else if (
                Arrays.stream(resource.getClass().getDeclaredFields())
                        .anyMatch(field -> field.getName().equals(subProperty))
        ) {
            accessibleObject = resource.getClass().getDeclaredField(subProperty);
        } else {
            throw new DataFetcherException("No accessor was found for the field '%s'".formatted(subProperty));
        }


        return accessibleObject instanceof Field ?
                recurse(((Field) accessibleObject).get(resource), property) :
                recurse(((Method) accessibleObject).invoke(resource), property);

    }
}
