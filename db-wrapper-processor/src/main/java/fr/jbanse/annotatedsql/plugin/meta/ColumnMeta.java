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

import com.annotatedsql.annotation.sql.Column;
import com.annotatedsql.util.TextUtils;

/**
 * Metadata for a column.
 */
public class ColumnMeta {

    /**
     * Camelcase name.
     */
    private final String mBaseName;

    /**
     *
     */
    private final String mName;
    /**
     * column SQL Type
     */
    private final Column.Type mType;

    private final String mExpr;

    public ColumnMeta(String name, String expr, Column.Type type) {
        mBaseName = TextUtils.var2class(TextUtils.capitalize(name.toLowerCase()));
        mName = name.toUpperCase();
        mExpr = expr;
        mType = type;
    }

    public String getBaseName() {
        return mBaseName;
    }

    public String getName() {
        return mName;
    }

    public String getExpr() {
        return mExpr;
    }

    public String getType() {
        return mType.name();
    }
}
