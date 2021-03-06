/*******************************************************************************
 * Copyright (c)2014 Prometheus Consulting
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package nz.co.senanque.base;

import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * Short description
 * 
 * @author Roger Parkinson
 * @version $Revision: 1.1 $
 */
public interface CustomerDAO
{

    //@Transactional
    Customer createCustomer();

    /* (non-Javadoc)
     * @see nz.co.senanque.madura.workflow.client.ContextDAO#createContext(java.util.Map)
     */
    @Transactional
    long save(Customer customer);

    Customer getCustomer(long id);

}
