package fr.jbanse.annotatedsql.plugin.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julien on 25/02/2015.
 */
public class ContentValuesMeta {

    private final String mTableName;
    private String mPkgName;

    private final String mName;

    private String mStoreName;

    private String mTablePackage;

    private final List<ColumnMeta> mColumnMetas = new ArrayList<ColumnMeta>();

    public ContentValuesMeta(String name) {
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

    public boolean isEmpty() {
        return mColumnMetas.isEmpty();
    }
}
