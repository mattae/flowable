package com.mattae.snl.plugins.flowable.integration;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.dmn.feel.impl.FeelEngine;
import org.camunda.bpm.dmn.feel.impl.juel.FeelEngineFactoryImpl;
import org.camunda.bpm.dmn.feel.impl.juel.transform.FeelToJuelTransform;
import org.camunda.bpm.dmn.feel.impl.juel.transform.FeelToJuelTransformImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class FeelTest {
    public static FeelToJuelTransform feelToJuelTransform;

    @BeforeClass
    public static void initTransform() {
        feelToJuelTransform = new FeelToJuelTransformImpl();
    }

    @Test
    public void testNested() {
        assertTransform("x", "date(\"2021-06-04\") in [date(\"2021-05-01\")..date(\"2021-05-31\")]", "${not((x >= a) || (x == 13.37) || (x > .37 && x < .42) || (x < .37))}");
    }

    public void assertTransform(String input, String feelExpression, String expectedExpression) {
        String expression = feelToJuelTransform.transformSimpleUnaryTests(feelExpression, input);
        LOG.info("Expression: {}", expression);
        assertThat(expression).isEqualTo(expectedExpression);
    }
}
