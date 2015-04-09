Android-AnnotatedSQL-WrapperPlugin
==================================

Plugin for annotatedsql [Android-AnnotatedSQL][1].

Last version: 1.1.0

*Release note: add Pool methods (obtain and recycle). **This version must be used with appcompat-v4.***

Previous version: 1.0

*The purpose*
----------------

Generate ContentValues wrapper classes associated to your table's columns.
Each generated class can use a pool to limit instance creation. Use the method ``.obtain()`` and ``.recycle()``` to use the pool.

*How can you do it ?*
----------------
that's easy, you simply have to add annotation @ContentValues with your table definition (ie where you put your annotation @Table).

*Let's see an example :*
----------------------

```
@Provider(name = "MyProvider",
        schemaClass = "MyDbSchema",
        authority = MyStore.AUTHORITY,
        openHelperClass = "MySQLiteOpenHelper")
@Schema(className = "MyDbSchema",
        dbName = "mystore.db",
        dbVersion = 1)
public class MyStore {
    public static final String AUTHORITY = "mystore.provider";

    @ContentValues
    @Table(UserTable.TABLE_NAME)
    public static interface UserTable {
        String TABLE_NAME = "user";

        @URI
        String CONTENT_URI = TABLE_NAME;

        @Column(type = Column.Type.TEXT)
        String FULL_NAME = "fullname";

        @Unique
        @Column(type = Column.Type.TEXT)
        String EMAIL = "email";

        @Column(type = Column.Type.TEXT)
        String LAST_NAME = "last_name";

        @Column(type = Column.Type.TEXT)
        String FIRST_NAME = "first_name";
    }
}

```

*The result*
------------

ContentValues wrappers will be generated in a package named *${yourstore.package}*.contentvalues.  
All methods will be generated with Android-AnnotatedSQL types :

- **Text** : String
- **Integer** : int, Integer, long, Long => 4 methods for one column
- **Real** : float, Float, double, Double => 4 methods for one column
- **Blob** : byte[]

*N.B :* if you don't want to generate all methods for some types, you can filter them by settings annotation :

```
@ContentValues(useInt = true, useLong = false, useFloat = false, useDouble = true)
```

*How to add to your project ?*
----------------
Like other libs, add plugin to your dependencies:

`dependencies {
	compile "com.github.julienbanse:db-wrapper-api:1.1.0"
}`

Very easy way - just use [aptlibs][2] 

```
	aptlibs {

		annotatedSql {
			version = "${asVersion}"
			logLevel = 'INFO'
			plugins {
				contentValuesPlugin {
                	version = "1.1.0"
                	dependencies = ["com.github.julienbanse:db-wrapper-api:${version}",
                                "com.github.julienbanse:db-wrapper-processor:${version}"]
                	plugin = "fr.jbanse.annotatedsql.plugin.ContentValuesPlugin"
            	}
            }
		}
	}
```
This plugin can be used with the [Projection-Plugin][3]

[1]: https://github.com/hamsterksu/Android-AnnotatedSQL
[2]: https://github.com/hamsterksu/android-aptlibs-gradle-plugin
[3]: https://github.com/hamsterksu/annotatedsql-projection-plugin
