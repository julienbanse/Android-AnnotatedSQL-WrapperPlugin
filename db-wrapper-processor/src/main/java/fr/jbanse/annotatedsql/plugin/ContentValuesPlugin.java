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

package fr.jbanse.annotatedsql.plugin;

import com.annotatedsql.ftl.SchemaMeta;
import com.annotatedsql.ftl.TableColumns;
import com.annotatedsql.ftl.ViewMeta;
import com.annotatedsql.processor.ProcessorLogger;
import com.annotatedsql.processor.logger.TagLogger;
import com.annotatedsql.processor.sql.ISchemaPlugin;
import com.annotatedsql.processor.sql.TableResult;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import fr.jbanse.annotatedsql.plugin.meta.ColumnMeta;
import fr.jbanse.annotatedsql.plugin.meta.ContentValuesMeta;
import fr.jbanse.annotatedsql.plugin.meta.SchemaMetaContentValues;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Created by julien on 25/02/2015.
 */
public class ContentValuesPlugin implements ISchemaPlugin {

    private TagLogger logger;
    private ProcessingEnvironment processingEnv;
    private Configuration cfg = new Configuration();
    private SchemaMetaContentValues schemaProjections;


    @Override
    public void init(ProcessingEnvironment processingEnv, ProcessorLogger logger) {
        this.processingEnv = processingEnv;
        this.logger = new TagLogger("ProjectionPlugin", logger);
        cfg.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/res"));
        schemaProjections = new SchemaMetaContentValues();
    }

    @Override
    public void processTable(TypeElement element, TableResult tableInfo) {
        this.logger.i("[ContentValues] start processTable" + tableInfo.getTableName());
        ContentValues contentValuesAnnotation = element.getAnnotation(ContentValues.class);
        if (contentValuesAnnotation != null) {
            TableColumns tableColumns = tableInfo.getTableColumns();
            String className = element.getSimpleName().toString();
            ContentValuesMeta contentValuesMeta = new ContentValuesMeta(className,
                    contentValuesAnnotation.useInt(),
                    contentValuesAnnotation.useLong(),
                    contentValuesAnnotation.useFloat(),
                    contentValuesAnnotation.useDouble());
            for (Map.Entry<String, String> e : tableColumns.getColumn2Variable().entrySet()) {
                String column = e.getValue();
                ColumnMeta columnMeta = new ColumnMeta(column, className + "." + column, tableColumns.getSqlType(e.getKey()));
                this.logger.i("[ContentValues] addColumns2Projection: " + className + "." + column + ": type = " + columnMeta.getType());
                contentValuesMeta.addColumn(columnMeta);
            }
            if (!contentValuesMeta.isEmpty()) {
                schemaProjections.addContentValues(contentValuesMeta);
            }
        }
        this.logger.i("[ContentValues] end processTable" + tableInfo.getTableName());

    }

    @Override
    public void processView(TypeElement element, ViewMeta meta) {

    }

    @Override
    public void processRawQuery(TypeElement element, ViewMeta meta) {

    }

    @Override
    public void processSchema(TypeElement element, SchemaMeta model) {
        this.logger.i("[ContentValues] start processSchema " + model.getClassName());
        schemaProjections.setPkgName(model.getPkgName());
        schemaProjections.setStoreClassName(model.getStoreClassName());
        schemaProjections.setSchemaClassName(model.getClassName());
        generateSchemaContentValues();
        this.logger.i("[ContentValues] end processSchema " + model.getClassName());
    }

    private void generateSchemaContentValues() {
        generateAbstractContentValues(schemaProjections);
        for (ContentValuesMeta meta : schemaProjections.getContentValuesMeta()) {
            meta.setPkgName(schemaProjections.getPkgName());
            meta.setStoreName(schemaProjections.getStoreClassName());
            generateContentValues(meta);
        }
    }

    private void generateContentValues(ContentValuesMeta contentValuesMeta) {
        this.logger.i("[ContentValues] generateContentValues");
        String className = contentValuesMeta.getPkgName() + "." + contentValuesMeta.getName();
        try {
            logger.i("[ContentValues] generateContentValues: try creating class:  " + className);
            JavaFileObject file = processingEnv.getFiler().createSourceFile(className);
            logger.i("[ContentValues] generateContentValues: Creating file:  " + className + " in " + file.getName());
            Writer out = file.openWriter();
            Template t = cfg.getTemplate("content_values_wrapper.ftl");
            t.process(contentValuesMeta, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.e("[ContentValues] EntityProcessor IOException: ", e);
        } catch (TemplateException e) {
            logger.e("[ContentValues] EntityProcessor TemplateException: ", e);
        }
        this.logger.i("[ContentValues] generateContentValues end");
    }

    private void generateAbstractContentValues(SchemaMetaContentValues model) {
        this.logger.i("[AbstractContentValues] generateAbstractContentValues");
        try {
            ContentValuesMeta contentValuesMeta = new ContentValuesMeta("Abstract", false, false, false, false);
            contentValuesMeta.setPkgName(model.getPkgName());
            String className = contentValuesMeta.getPkgName() + "." + contentValuesMeta.getName();
            JavaFileObject file = processingEnv.getFiler().createSourceFile(className);
            logger.i("[AbstractContentValues] generateAbstractContentValues: Creating file:  " + className + " in " + file.getName());
            Writer out = file.openWriter();
            Template t = cfg.getTemplate("abstract_contentvalues_wrapper.ftl");
            t.process(contentValuesMeta, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.e("[AbstractContentValues] EntityProcessor IOException: ", e);
        } catch (TemplateException e) {
            logger.e("[AbstractContentValues] EntityProcessor TemplateException: ", e);
        }
        this.logger.i("[AbstractContentValues] generateAbstractContentValues end");
    }
}
