/*******************************************************************************
 * Copyright (c)2015 Prometheus Consulting
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
package nz.co.senanque.configuration;

import nz.co.senanque.resourceloader.ResourceBundleMessageSourceExt;
import nz.co.senanque.validationengine.ValidationEngine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;

/**
 * @author Roger Parkinson
 *
 */

@Configuration
@ComponentScan(basePackages = {
		"nz.co.senanque.validationengine",
		"nz.co.senanque.validationengine.annotations",
		"nz.co.senanque.rules",
		"nz.co.senanque.pizzaorder.generated",
		"nz.co.senanque.pizzaorder.factories"})
@PropertySource("classpath:config.properties")
public class SpringConfiguration {
	
	@Autowired
    Environment env;

	@Autowired ValidationEngine m_engine;
	public SpringConfiguration() {
		"".toString();
	}
	public ValidationEngine getEngine() {
		return m_engine;
	}
	public void setEngine(ValidationEngine engine) {
		m_engine = engine;
	}
	@Bean
    public MessageSource messageSource() { 
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSourceExt();
        messageSource.setBasenames("Messages");
        return messageSource;
    }
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
