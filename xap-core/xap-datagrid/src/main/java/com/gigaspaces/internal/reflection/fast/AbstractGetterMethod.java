/*
 * Copyright (c) 2008-2016, GigaSpaces Technologies, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigaspaces.internal.reflection.fast;

import com.gigaspaces.internal.reflection.IGetterMethod;
import com.gigaspaces.internal.reflection.standard.StandardProcedure;

import org.objectweb.gs.asm.Type;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Provides a base class for the dynamic Getter implementation. Wraps any exception thrown with
 * InvocationTargetException.
 *
 * @author guy
 * @since 7.0
 */
public abstract class AbstractGetterMethod<T> extends StandardProcedure<T> implements IGetterMethod<T> {
    final public static String INTERNAL_NAME = Type.getInternalName(AbstractGetterMethod.class);

    public AbstractGetterMethod(Method method) {
        super(method);
    }

    public Object get(T obj) throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        try {
            return internalGet(obj);
        } catch (Throwable ex) {
            throw new InvocationTargetException(ex);
        }
    }

    abstract public Object internalGet(T obj);
}
