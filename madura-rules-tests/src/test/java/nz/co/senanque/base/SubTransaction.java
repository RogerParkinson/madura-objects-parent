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

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * Short description
 * 
 * @author Roger Parkinson
 * @version $Revision: 1.1 $
 */
@Component
public class SubTransaction
{
    private transient SessionFactory m_sessionFactory;

    @Transactional(readOnly=false)
    public void process(final Customer customer)
    {
        final Session session = getSessionFactory().getCurrentSession();
        Transaction ttransaction1 = session.getTransaction();
        ttransaction1.toString();
    }

    public SessionFactory getSessionFactory()
    {
        return m_sessionFactory;
    }

    public void setSessionFactory(final SessionFactory sessionFactory)
    {
        m_sessionFactory = sessionFactory;
    }

}
