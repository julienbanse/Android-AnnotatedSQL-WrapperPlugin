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
 * Meta class to describe contentvalues annotation associate to @Table column content.
 */
public class ContentValuesMeta {

    /**
     * filter to generate wrapper method with int or Integer parameter.
     */
    private final boolean mMustGenerateMethodsForInt;
    /**
     * filter to generate wrapper method with long or Long parameter.
     */
    private final boolean mMustGenerateMethodsForLong;

    /**
     * filter to generate wrapper method with float or Float parameter.
     */
    private final boolean mMustGenerateMethodsForFloat;

    /**
     * filter to generate wrapper method with double or Double parameter.
     */
    private final boolean mMustGenerateMethodsForDouble;

    /**
     * associated table name.
     */
    private final String mTableName;

    /**
     * conent values package name.
     */
    private String mPkgName;

    /**
     * contentvalues class name.
     */
    private final String mName;

    private String mStoreName;

    /**
     * table package
     */
    private String mTablePackage;

    /**
     * column meta of table.
     */
    private final List<ColumnMeta> mColumnMetas = new ArrayList<ColumnMeta>();

    /**
     * default constructor for ContentValues class description.
     *
     * @param name
     * @param mustGenerateMethodsForInt
     * @param mustGenerateMethodsForLong
     * @param mustGenerateMethodsForFloat
     * @param mustGenerateMethodsForDouble
     */
    public ContentValuesMeta(String name, boolean mustGenerateMethodsForInt,
                             boolean mustGenerateMethodsForLong,
                             boolean mustGenerateMethodsForFloat,
                             boolean mustGenerateMethodsForDouble) {
        mMustGenerateMethodsForInt = mustGenerateMethodsForInt;
        mMustGenerateMethodsForLong = mustGenerateMethodsForLong;
        mMustGenerateMethodsForFloat = mustGenerateMethodsForFloat;
        mMustGenerateMethodsForDouble = mustGenerateMethodsForDouble;
        mTableName = name;
        this.mName = name + "ContentValues";
    }

    public String getName() {
        return mName;
    }

    public List<ColumnMeta> getColumn() {
        return mColumnMetas;
    }

    public String getStoreName() {
        return mStoreName;
    }

    public String getTableName() {
        return mTableName;
    }

    public void setStoreName(String storeName) {
        mStoreName = storeName;
    }

    public String getPkgName() {
        return mPkgName;
    }

    public void setPkgName(String pkgName) {
        mTablePackage = pkgName;
        mPkgName = pkgName + ".contentvalues";
    }

    public String getTablePackage() {
        return mTablePackage;
    }

    public void addColumn(ColumnMeta c) {
        if (c == null) {
            return;
        }
        mColumnMetas.add(c);
    }

    public boolean mustGenerateMethodsForInt() {
        return mMustGenerateMethodsForInt;
    }

    public boolean mustGenerateMethodsForLong() {
        return mMustGenerateMethodsForLong;
    }

    public boolean mustGenerateMethodsForFloat() {
        return mMustGenerateMethodsForFloat;
    }

    public boolean mustGenerateMethodsForDouble() {
        return mMustGenerateMethodsForDouble;
    }

    public boolean isEmpty() {
        return mColumnMetas.isEmpty();
    }
}
