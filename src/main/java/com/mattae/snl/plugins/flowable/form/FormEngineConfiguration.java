/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mattae.snl.plugins.flowable.form;

import com.mattae.snl.plugins.flowable.form.impl.parser.FormDefinitionParseFactory;
import org.flowable.editor.form.converter.FormJsonConverter;
import org.flowable.form.api.FormManagementService;
import org.flowable.form.api.FormRepositoryService;

public class FormEngineConfiguration extends org.flowable.form.engine.FormEngineConfiguration {

    public FormEngineConfiguration() {
        formJsonConverter = new com.mattae.snl.plugins.flowable.form.editor.FormJsonConverter();
        formService = new FormServiceImpl(this);
    }

    public FormEngineConfiguration setFormManagementService(FormManagementService formManagementService) {
        this.formManagementService = formManagementService;
        return this;
    }

    protected void initDeployers() {
        if (formParseFactory == null) {
            formParseFactory = new FormDefinitionParseFactory();
        }
        super.initDeployers();
    }

    @Override
    public FormRepositoryService getFormRepositoryService() {
        return formRepositoryService;
    }

    public FormEngineConfiguration setFormRepositoryService(FormRepositoryService formRepositoryService) {
        this.formRepositoryService = formRepositoryService;
        return this;
    }

    public FormJsonConverter getFormJsonConverter() {
        return formJsonConverter;
    }

    public FormEngineConfiguration setFormJsonConverter(FormJsonConverter formJsonConverter) {
        this.formJsonConverter = formJsonConverter;
        return this;
    }
}
