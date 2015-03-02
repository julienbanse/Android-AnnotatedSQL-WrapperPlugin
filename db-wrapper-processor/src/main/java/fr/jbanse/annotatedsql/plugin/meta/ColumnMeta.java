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
 * Created by hamsterksu on 19.10.2014.
 */
public class ColumnMeta {

    private final String baseName;
    private final String name;
    private final Column.Type mType;

    private final String expr;

    public ColumnMeta(String name, String expr, Column.Type type) {
        baseName = TextUtils.var2class(TextUtils.capitalize(name.toLowerCase()));
        this.name = name.toUpperCase();
        this.expr = expr;
        mType = type;
    }

    public String getBaseName() {
        return baseName;
    }

    public String getName() {
        return name;
    }

    public String getExpr() {
        return expr;
    }

    public String getType() {
        return mType.name();
    }
}
