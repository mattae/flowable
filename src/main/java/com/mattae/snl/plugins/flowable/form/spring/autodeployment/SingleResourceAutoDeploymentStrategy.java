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

package com.mattae.snl.plugins.flowable.form.spring.autodeployment;

import org.flowable.common.spring.CommonAutoDeploymentProperties;
import org.flowable.form.api.FormDeploymentBuilder;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.form.engine.FormEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

/**
 * Implementation of {@link org.flowable.common.spring.AutoDeploymentStrategy AutoDeploymentStrategy}
 * that performs a separate deployment for each resource by name.
 *
 * @author Tiese Barrell
 * @author Joram Barrez
 */
public class SingleResourceAutoDeploymentStrategy extends AbstractFormAutoDeploymentStrategy {

    /**
     * The deployment mode this strategy handles.
     */
    public static final String DEPLOYMENT_MODE = "single-resource";
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleResourceAutoDeploymentStrategy.class);

    public SingleResourceAutoDeploymentStrategy() {
    }

    public SingleResourceAutoDeploymentStrategy(CommonAutoDeploymentProperties deploymentProperties) {
        super(deploymentProperties);
    }

    @Override
    protected String getDeploymentMode() {
        return DEPLOYMENT_MODE;
    }

    @Override
    protected void deployResourcesInternal(String deploymentNameHint, Resource[] resources, FormEngine engine) {
        FormRepositoryService repositoryService = engine.getFormRepositoryService();

        // Create a separate deployment for each resource using the resource name

        for (final Resource resource : resources) {

            final String resourceName = determineResourceName(resource);
            final FormDeploymentBuilder deploymentBuilder = repositoryService.createDeployment().enableDuplicateFiltering().name(resourceName);
            addResource(resource, resourceName, deploymentBuilder);
            try {

                deploymentBuilder.deploy();

            } catch (RuntimeException e) {
                if (isThrowExceptionOnDeploymentFailure()) {
                    throw e;
                } else {
                    LOGGER.warn("Exception while autodeploying form definitions for resource {}. "
                        + "This exception can be ignored if the root cause indicates a unique constraint violation, "
                        + "which is typically caused by two (or more) servers booting up at the exact same time and deploying the same definitions. ", resource, e);
                }

            }
        }
    }

}
