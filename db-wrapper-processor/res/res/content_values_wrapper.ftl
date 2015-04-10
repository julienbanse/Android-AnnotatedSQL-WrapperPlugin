/*
 * AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the AnnotatedSQL Wrapper Plugin library.
 */
package ${pkgName};

import ${tablePackage}.${storeName}.${tableName};

import android.support.v4.util.Pools;

import android.content.ContentValues;

public class ${name} extends AbstractContentValues {

    private static volatile Pools.SynchronizedPool<${name}> sPool;

    public ${name}() {
        this(${column?size});
    }

    public ${name}(int size) {
        super(size);
    }

    public ${name}(ContentValues contentValues) {
        super(contentValues);
    }

    private static Pools.SynchronizedPool<${name}> getPool() {
       if (sPool == null) {
            synchronized (${name}.class) {
                if (sPool == null) {
                    sPool = new Pools.SynchronizedPool<>(10);
                }
            }
       }
       return sPool;
    }

    /**
     * method to get a ${name} instance using a pool to limit instance creation.
     * if you use this method, do not forget to call method recycle from the obtained instance.
     * @return ${name} instance
     */
    public static ${name} obtain() {
        ${name} instance = getPool().acquire();
        return (instance != null) ? instance : new ${name}();
    }

    /**
     * this method put this instance in the stack pool.
     * You must use this method only if you got the instance this method #obtain.
     * and clear content.
     */
    public void recycle() {
        clear();
        getPool().release(this);
    }

    <#list column as col>
        <#switch col.type>
        <#case 'INTEGER'>
            <#if mustGenerateMethodsForInt()>
    public ${name} put${col.baseName}(int value) {
        return put${col.baseName}(Integer.valueOf(value));
    }

    public ${name} put${col.baseName}(Integer value) {
        mContentValues.put(${col.expr}, value);
        return this;
    }
            </#if>
            <#if mustGenerateMethodsForLong()>
    public ${name} put${col.baseName}(long value) {
        return put${col.baseName}(Long.valueOf(value));
    }

    public ${name} put${col.baseName}(Long value) {
        mContentValues.put(${col.expr}, value);
        return this;
    }
            </#if>
        <#break>
        <#case 'REAL'>
        <#if mustGenerateMethodsForFloat()>
    public ${name} put${col.baseName}(float value) {
        return put${col.baseName}(Float.valueOf(value));
    }

    public ${name} put${col.baseName}(Float value) {
        mContentValues.put(${col.expr}, value);
        return this;
    }
        </#if>
        <#if mustGenerateMethodsForDouble()>
    public ${name} put${col.baseName}(double value) {
        return put${col.baseName}(Double.valueOf(value));
    }

    public ${name} put${col.baseName}(Double value) {
        mContentValues.put(${col.expr}, value);
        return this;
    }
        </#if>
        <#break>
        <#case 'BLOB'>
    public ${name} put${col.baseName}(byte[] value) {
        return put${col.baseName}(value);
    }
        <#break>
        <#default>
    public ${name} put${col.baseName}(String value) {
        mContentValues.put(${col.expr}, value);
        return this;
    }
        </#switch>
    </#list>
}