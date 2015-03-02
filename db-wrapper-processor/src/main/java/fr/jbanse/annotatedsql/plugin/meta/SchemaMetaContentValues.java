/*
 * Copyright 2015 Julien Banse
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package fr.jbanse.annotatedsql.plugin.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julien on 25/02/2015.
 */
public class SchemaMetaContentValues {
    private String mPkgName;

    private String mStoreClassName;

    private String mSchemaClassName;

    private String mProviderClass;

    private final List<ContentValuesMeta> mContentValuesMeta = new ArrayList<ContentValuesMeta>();

    public String getPkgName() {
        return mPkgName;
    }

    public void setPkgName(String pkgName) {
        this.mPkgName = pkgName;
    }

    public String getStoreClassName() {
        return mStoreClassName;
    }

    public void setStoreClassName(String storeClassName) {
        this.mStoreClassName = storeClassName;
    }

    public String getSchemaClassName() {
        return mSchemaClassName;
    }

    public void setSchemaClassName(String schemaClassName) {
        this.mSchemaClassName = schemaClassName;
    }

    public String getProviderClass() {
        return mProviderClass;
    }

    public void setProviderClass(String providerClass) {
        this.mProviderClass = providerClass;
    }

    public List<ContentValuesMeta> getContentValuesMeta() {
        return mContentValuesMeta;
    }

    public void addContentValues(ContentValuesMeta contentValuesMeta) {
        mContentValuesMeta.add(contentValuesMeta);
    }
}
