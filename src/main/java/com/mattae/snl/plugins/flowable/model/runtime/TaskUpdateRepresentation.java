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
package com.mattae.snl.plugins.flowable.model.runtime;

import org.flowable.ui.common.model.AbstractRepresentation;

import java.util.Date;

public class TaskUpdateRepresentation extends AbstractRepresentation {

    private String name;
    private String description;
    private Date dueDate;

    private String formKey;

    private boolean nameSet;
    private boolean descriptionSet;
    private boolean dueDateSet;
    private boolean formKeySet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.nameSet = true;
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.descriptionSet = true;
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDateSet = true;
        this.dueDate = dueDate;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
        formKeySet = true;
    }

    public boolean isNameSet() {
        return nameSet;
    }

    public boolean isDescriptionSet() {
        return descriptionSet;
    }

    public boolean isDueDateSet() {
        return dueDateSet;
    }

    public boolean isFormKeySet() {
        return formKeySet;
    }
}
