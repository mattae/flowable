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
package com.mattae.snl.plugins.flowable.web.runtime.variable;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.mattae.snl.plugins.flowable.model.runtime.RestVariable;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

/**
 * @author Frederik Heremans
 */
@Component
public class DateRestVariableConverter implements RestVariableConverter {

    protected ISO8601DateFormat isoFormatter = new ISO8601DateFormat();

    @Override
    public String getRestTypeName() {
        return "date";
    }

    @Override
    public Class<?> getVariableType() {
        return Date.class;
    }

    @Override
    public Object getVariableValue(RestVariable result) {
        if (result.getValue() != null) {
            if (!(result.getValue() instanceof String)) {
                throw new FlowableIllegalArgumentException("Converter can only convert string to date");
            }
            try {
                return isoFormatter.parse((String) result.getValue());
            } catch (ParseException e) {
                throw new FlowableIllegalArgumentException("The given variable value is not a date: '" + result.getValue() + "'", e);
            }
        }
        return null;
    }

    @Override
    public void convertVariableValue(Object variableValue, RestVariable result) {
        if (variableValue != null) {
            if (!(variableValue instanceof Date)) {
                throw new FlowableIllegalArgumentException("Converter can only convert booleans");
            }
            result.setValue(isoFormatter.format(variableValue));
        } else {
            result.setValue(null);
        }
    }

}
