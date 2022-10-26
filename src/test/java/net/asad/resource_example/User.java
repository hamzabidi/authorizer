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

package net.asad.resource_example;

import net.asad.authorizer.Authorizer;

public class User {
    private final int id;
    private final Age userAge;
    private final Authorizer authorizer;

    public User(int id, Age userAge) {
        this.id = id;
        this.userAge = userAge;
        this.authorizer = new Authorizer();
    }

    public User(int id, Age userAge, Authorizer authorizer) {
        this.id = id;
        this.userAge = userAge;
        this.authorizer = authorizer;
    }

    public int getId() {
        return id;
    }

    public Age getUserAge() {
        return userAge;
    }

    public Authorizer getAuthorizer() {
        return authorizer;
    }
}
