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

package com.gigaspaces.query.extension.metadata.impl;

import com.gigaspaces.internal.io.IOUtils;
import com.gigaspaces.query.extension.QueryExtensionProvider;
import com.gigaspaces.query.extension.metadata.QueryExtensionPathInfo;
import com.gigaspaces.query.extension.metadata.TypeQueryExtension;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@com.gigaspaces.api.InternalApi
public class TypeQueryExtensionImpl implements TypeQueryExtension, Externalizable {
    // serialVersionUID should never be changed.
    private static final long serialVersionUID = 1L;

    private QueryExtensionProvider queryExtensionProvider;
    private final Map<String, QueryExtensionPathInfo> propertiesInfo = new HashMap<String, QueryExtensionPathInfo>();

    /**
     * Required for Externalizable
     */
    public TypeQueryExtensionImpl() {
    }

    public void add(String path, QueryExtensionPathInfo queryExtensionPathInfo) {
        this.propertiesInfo.put(path, queryExtensionPathInfo);
    }

    @Override
    public QueryExtensionPathInfo get(String path) {
        return this.propertiesInfo.get(path);
    }

    @Override
    public Set<String> getPaths() {
        return propertiesInfo.keySet();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(propertiesInfo.size());
        for (Map.Entry<String, QueryExtensionPathInfo> entry : propertiesInfo.entrySet()) {
            IOUtils.writeString(out, entry.getKey());
            IOUtils.writeObject(out, entry.getValue());
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            String key = IOUtils.readString(in);
            QueryExtensionPathInfo value = IOUtils.readObject(in);
            propertiesInfo.put(key, value);
        }
    }
}
