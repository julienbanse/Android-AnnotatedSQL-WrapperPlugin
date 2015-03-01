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
