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

import com.mattae.snl.plugins.flowable.services.model.ExtendedUserRepresentation;
import org.flowable.engine.task.Comment;
import org.flowable.ui.common.model.AbstractRepresentation;

import java.util.Date;

public class CommentRepresentation extends AbstractRepresentation {

    private String id;
    private String message;
    private Date created;
    private String createdBy;
    private ExtendedUserRepresentation user;

    public CommentRepresentation() {

    }

    public CommentRepresentation(Comment comment, ExtendedUserRepresentation user) {
        this.id = comment.getId();
        this.message = comment.getFullMessage();
        this.created = comment.getTime();
        this.createdBy = comment.getUserId();
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ExtendedUserRepresentation getUser() {
        return user;
    }

    public void setUser(ExtendedUserRepresentation user) {
        this.user = user;
    }
}
