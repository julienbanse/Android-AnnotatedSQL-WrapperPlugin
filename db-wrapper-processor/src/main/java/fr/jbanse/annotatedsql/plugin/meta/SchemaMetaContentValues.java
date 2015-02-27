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
