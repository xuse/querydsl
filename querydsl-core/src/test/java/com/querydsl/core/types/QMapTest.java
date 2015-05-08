/*
 * Copyright 2015, The Querydsl Team (http://www.querydsl.com/team)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.querydsl.core.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.Test;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;

public class QMapTest {

    StringPath str1 = Expressions.stringPath("str1");
    StringPath str2 = Expressions.stringPath("str2");
    StringPath str3 = Expressions.stringPath("str3");
    StringPath str4 = Expressions.stringPath("str4");
    Expression<?>[] exprs1 = new Expression[]{str1, str2};
    Expression<?>[] exprs2 = new Expression[]{str3, str4};

    Concatenation concat = new Concatenation(str1, str2);

    @Test
    public void TwoExpressions_getArgs() {
        assertEquals(Arrays.asList(str1, str2), new QMap(str1, str2).getArgs());
    }

    @Test
    public void OneArray_getArgs() {
        assertEquals(Arrays.asList(str1, str2), new QMap(exprs1).getArgs());
    }

    @Test
    public void TwoExpressionArrays_getArgs() {
        assertEquals(Arrays.asList(str1, str2, str3, str4), new QMap(exprs1, exprs2).getArgs());
    }

    @Test
    public void NestedProjection_getArgs() {
        assertEquals(Arrays.asList(str1, str2), FactoryExpressionUtils.wrap(new QMap(concat)).getArgs());
    }

    @Test
    public void NestedProjection_getArgs2() {
        assertEquals(Arrays.asList(str1, str2, str3), FactoryExpressionUtils.wrap(new QMap(concat, str3)).getArgs());
    }

    @Test
    public void NestedProjection_newInstance() {
        QMap expr = new QMap(concat);
        assertEquals("1234", FactoryExpressionUtils.wrap(expr).newInstance("12", "34").get(concat));
    }

    @Test
    public void NestedProjection_newInstance2() {
        QMap expr = new QMap(str1, str2, concat);
        assertEquals("1234", FactoryExpressionUtils.wrap(expr).newInstance("1", "2", "12", "34").get(concat));
    }

    @Test
    public void Tuple_Equals() {
        QMap expr = new QMap(str1, str2);
        assertEquals(expr.newInstance("str1", "str2"), expr.newInstance("str1", "str2"));
    }
    
    @Test
    public void Tuple_hashCode() {
        QMap expr = new QMap(str1, str2);
        assertEquals(expr.newInstance("str1", "str2").hashCode(), expr.newInstance("str1", "str2").hashCode());
    }

    @Test
    public void Null_Value() {
        QMap expr = new QMap(str1, str2);
        assertNotNull(expr.newInstance("str1", null));
    }

}
