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
package com.mattae.snl.plugins.flowable.web.runtime;

import com.mattae.snl.plugins.flowable.model.runtime.CommentRepresentation;
import com.mattae.snl.plugins.flowable.services.runtime.FlowableCommentService;
import org.flowable.ui.common.model.ResultListDataRepresentation;
import org.springframework.web.bind.annotation.*;

/**
 * REST resource related to comment collection on tasks and process instances.
 *
 * @author Frederik Heremans
 * @author Joram Barrez
 */
@RestController
@RequestMapping("/api")
public class CommentsResource {

    protected final FlowableCommentService commentService;

    public CommentsResource(FlowableCommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(value = "/rest/tasks/{taskId}/comments", produces = "application/json")
    public ResultListDataRepresentation getTaskComments(@PathVariable("taskId") String taskId) {
        return commentService.getTaskComments(taskId);
    }

    @PostMapping(value = "/rest/tasks/{taskId}/comments", produces = "application/json")
    public CommentRepresentation addTaskComment(@RequestBody CommentRepresentation commentRequest, @PathVariable("taskId") String taskId) {
        return commentService.addTaskComment(commentRequest, taskId);
    }

    @GetMapping(value = "/rest/process-instances/{processInstanceId}/comments", produces = "application/json")
    public ResultListDataRepresentation getProcessInstanceComments(@PathVariable("processInstanceId") String processInstanceId) {
        return commentService.getProcessInstanceComments(processInstanceId);
    }

    @PostMapping(value = "/rest/process-instances/{processInstanceId}/comments", produces = "application/json")
    public CommentRepresentation addProcessInstanceComment(@RequestBody CommentRepresentation commentRequest,
                                                           @PathVariable("processInstanceId") String processInstanceId) {
        return commentService.addProcessInstanceComment(commentRequest, processInstanceId);
    }

}
