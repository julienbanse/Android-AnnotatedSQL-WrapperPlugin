package fr.jbanse.annotatedsql.plugin.wrapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
public @interface CursorType {
    Class value();
}
