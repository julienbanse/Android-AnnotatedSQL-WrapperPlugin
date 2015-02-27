package fr.jbanse.annotatedsql.plugin;

import com.annotatedsql.ftl.SchemaMeta;
import com.annotatedsql.ftl.TableColumns;
import com.annotatedsql.ftl.ViewMeta;
import com.annotatedsql.processor.ProcessorLogger;
import com.annotatedsql.processor.logger.TagLogger;
import com.annotatedsql.processor.sql.ISchemaPlugin;
import com.annotatedsql.processor.sql.TableResult;
import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.NewArrayTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
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
    private Trees trees;
    private SchemaMetaContentValues schemaProjections;


    @Override
    public void init(ProcessingEnvironment processingEnv, ProcessorLogger logger) {
        this.processingEnv = processingEnv;
        this.logger = new TagLogger("ProjectionPlugin", logger);
        this.trees = Trees.instance(processingEnv);
        cfg.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/res"));
        schemaProjections = new SchemaMetaContentValues();
    }

    @Override
    public void processTable(TypeElement element, TableResult tableInfo) {
        this.logger.i("[ContentValues] start processTable" + tableInfo.getTableName());
        ContentValues contentValuesAnnotation = element.getAnnotation(ContentValues.class);
        if (contentValuesAnnotation != null) {
            List<String> defColumns = new ArrayList<String>();
            TableColumns tableColumns = tableInfo.getTableColumns();
            for (Map.Entry<String, String> e : tableColumns.getColumn2Variable().entrySet()) {
                defColumns.add(e.getValue());
                this.logger.i("[ContentValues] processTable add column " + e.getKey() + " - " + e.getValue());
            }
            parseTableForContentValues(element, defColumns);
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
//        schemaProjections.setProviderClass(providerClass);
        generateSchemaContentValues();
        this.logger.i("[ContentValues] end processSchema " + model.getClassName());
    }

    private void parseTableForContentValues(TypeElement element, List<String> defaultColumns) {
        String className = element.getSimpleName().toString();
        ContentValuesMeta contentValuesMeta = new ContentValuesMeta(className);
        addColumns2Projection(className, contentValuesMeta, defaultColumns);

        if (!contentValuesMeta.isEmpty()) {
            schemaProjections.addContentValues(contentValuesMeta);
        }
    }

    private void addColumns2Projection(String className, ContentValuesMeta contentValuesMeta, List<String> columns) {
        for (String c : columns) {
            ColumnMeta columnMeta = new ColumnMeta(c, className + "." + c);
            this.logger.i("[ContentValues] addColumns2Projection: " + className + "." + c);
            contentValuesMeta.addColumn(columnMeta);
        }
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
            ContentValuesMeta contentValuesMeta = new ContentValuesMeta("Abstract");
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

    private static class CodeAnalyzerTreeScanner extends TreePathScanner<List<String>, Trees> {

        @Override
        public List<String> visitVariable(VariableTree variableTree, Trees trees) {
            List<String> columns = new ArrayList<String>();
            List<? extends AnnotationTree> annotationTrees = variableTree.getModifiers().getAnnotations();
            for (AnnotationTree a : annotationTrees) {
                for (ExpressionTree e : a.getArguments()) {
                    AssignmentTree assign = (AssignmentTree) e;
                    ExpressionTree value = assign.getExpression();
                    if (value instanceof NewArrayTree) {
                        NewArrayTree newArrayTree = (NewArrayTree) assign.getExpression();
                        for (ExpressionTree i : newArrayTree.getInitializers()) {
                            columns.add(i.toString());
                        }
                    } else {
                        columns.add(value.toString());
                    }
                }
            }
            return columns;
        }
    }
}
