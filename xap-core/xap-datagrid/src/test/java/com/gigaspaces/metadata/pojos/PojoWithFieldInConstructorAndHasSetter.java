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
package com.gigaspaces.metadata.pojos;

import com.gigaspaces.annotation.pojo.SpaceClassConstructor;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceProperty;

/**
 * @author: yaeln
 * @since: 6/30/16.
 */
public class PojoWithFieldInConstructorAndHasSetter {

    private String id;
    private String name;

    @SpaceClassConstructor
    public PojoWithFieldInConstructorAndHasSetter(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @SpaceId
    public String getId() {
        return id;
    }

    @SpaceProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
