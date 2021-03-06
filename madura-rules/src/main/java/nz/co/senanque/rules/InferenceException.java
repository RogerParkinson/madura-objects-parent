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
package nz.co.senanque.rules;

import nz.co.senanque.validationengine.ValidationException;

/**
 * General exception thrown during inference
 * 
 * @author Roger Parkinson
 * @version $Revision: 1.2 $
 */
public class InferenceException extends ValidationException
{
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
	/**
	 * Constructor for InferenceException.
	 * @param arg0
	 */
    public InferenceException(final String arg0)
    {
        super(arg0);
    }
    public InferenceException(final String arg0, final Exception exception)
    {
        super(arg0);
    }
}
