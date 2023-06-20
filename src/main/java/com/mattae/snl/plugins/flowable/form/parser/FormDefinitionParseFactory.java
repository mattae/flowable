package com.mattae.snl.plugins.flowable.form.parser;

/**
 * @author Matthew Edor
 */
public class FormDefinitionParseFactory extends org.flowable.form.engine.impl.parser.FormDefinitionParseFactory {

    public org.flowable.form.engine.impl.parser.FormDefinitionParse createParse() {
        return new FormDefinitionParse();
    }
}
