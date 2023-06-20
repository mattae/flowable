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
package com.mattae.snl.plugins.flowable.form.parser;

import com.mattae.snl.plugins.flowable.form.model.FormIOFormModel;
import org.apache.commons.io.IOUtils;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.form.engine.FormEngineConfiguration;
import org.flowable.form.engine.impl.persistence.entity.FormDefinitionEntity;
import org.flowable.form.engine.impl.util.CommandContextUtil;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Specific parsing of one form json file.
 *
 * @author Matthew Edor
 */
public class FormDefinitionParse extends org.flowable.form.engine.impl.parser.FormDefinitionParse {
    public FormDefinitionParse execute(FormEngineConfiguration formEngineConfig) {
        String encoding = formEngineConfig.getXmlEncoding();
        final FormEngineConfiguration formEngineConfiguration = CommandContextUtil.getFormEngineConfiguration();

        try (InputStreamReader in = newInputStreamReaderForSource(encoding)) {
            String formJson = IOUtils.toString(in);
            formModel = formEngineConfiguration.getFormJsonConverter().convertToFormModel(formJson);;
            if (formModel != null && (formModel.getFields() != null || !((FormIOFormModel) formModel).getComponents().isEmpty())) {
                FormDefinitionEntity formDefinitionEntity = CommandContextUtil.getFormEngineConfiguration().getFormDefinitionEntityManager().create();
                formDefinitionEntity.setKey(formModel.getKey());
                formDefinitionEntity.setName(formModel.getName());
                formDefinitionEntity.setResourceName(name);
                formDefinitionEntity.setDeploymentId(deployment.getId());
                formDefinitionEntity.setDescription(formModel.getDescription());
                formDefinitions.add(formDefinitionEntity);
            }
        } catch (Exception e) {
            throw new FlowableException("Error parsing form definition JSON", e);
        }
        return this;
    }

    private InputStreamReader newInputStreamReaderForSource(String encoding) throws UnsupportedEncodingException {
        if (encoding != null) {
            return new InputStreamReader(streamSource.getInputStream(), encoding);
        } else {
            return new InputStreamReader(streamSource.getInputStream());
        }
    }
}
