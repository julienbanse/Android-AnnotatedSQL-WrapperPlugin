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

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by julien on 25/02/2015.
 */
@Target(ElementType.TYPE)
public @interface ContentValues {

    boolean useInt() default true;

    boolean useLong() default true;

    boolean useFloat() default true;

    boolean useDouble() default true;
}
