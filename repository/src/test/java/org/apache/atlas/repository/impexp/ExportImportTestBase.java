/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.atlas.repository.impexp;

import com.google.inject.Inject;
import org.apache.atlas.ApplicationProperties;
import org.apache.atlas.AtlasConstants;
import org.apache.atlas.AtlasException;
import org.apache.atlas.exception.AtlasBaseException;
import org.apache.atlas.model.discovery.AtlasSearchResult;
import org.apache.atlas.repository.store.graph.v1.DeleteHandlerV1;
import org.apache.atlas.repository.store.graph.v1.SoftDeleteHandlerV1;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class ExportImportTestBase {

    protected DeleteHandlerV1 deleteHandler = mock(SoftDeleteHandlerV1.class);

    protected void assertAuditEntry(ExportImportAuditService auditService) {
        AtlasSearchResult result = null;
        try {
            result = auditService.get("", "", getCurrentCluster(), "", "", "", 10, 0);
        } catch (AtlasBaseException e) {
            fail("auditService.get: failed!");
        } catch (AtlasException e) {
            fail("getCurrentCluster: failed!");
        }
        assertNotNull(result);
        assertNotNull(result.getEntities());
        assertTrue(result.getEntities().size() > 0);
    }

    private String getCurrentCluster() throws AtlasException {
        return ApplicationProperties.get().getString(AtlasConstants.CLUSTER_NAME_KEY, "default");
    }
}