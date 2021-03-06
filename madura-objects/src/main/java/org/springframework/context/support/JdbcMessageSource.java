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
package org.springframework.context.support;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Reading of messages using data in databases. It must be provided with a
 * datasource and sql code which must be passed 2 jdbc parameters which will be
 * respectively filled with the code and the string representation of the
 * locale. <BR>
 * TODO: improve the caching part, either switching to a real cache (but isn't
 * it too heavy to ask in the configuration to specify something like EHCache
 * etc..) or caching per entry (it is not much useful to cache depending on the
 * date of the last query whatever it is) <BR>
 * TODO: check that synchronization isn't needed on the cache part
 * 
 * @author Olivier Jolly
 */
public class JdbcMessageSource extends AbstractMessageSource implements
        InitializingBean {

    private static class CacheKey {
        @SuppressWarnings("unused")
		public String code;

        @SuppressWarnings("unused")
		public Locale locale;

        public CacheKey(String code, Locale locale) {
            this.code = code;
            this.locale = locale;
        }
    }

    private JdbcTemplate jdbcTemplate;

    private String sqlStatement;

    private Map<CacheKey,MessageFormat> cachedMessages = new HashMap<>();

    private long cachedMilliSecond = 0;

    private long lastQuery = 0;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.support.AbstractMessageSource#resolveCode(java.lang.String,
     *      java.util.Locale)
     */
    protected MessageFormat resolveCode(String code, Locale locale) {
        MessageFormat result = null;

        if (cachedMilliSecond == 0
                || ((result = cachedMessages.get(new CacheKey(
                        code, locale))) == null)
                || lastQuery + cachedMilliSecond < System.currentTimeMillis()) {
            result = resolveCodeInternal(code, locale);
            cachedMessages.put(new CacheKey(code, locale), result);
        }

        if (result != null)
            return result;

        return resolveCodeInternal(code, locale);
    }

    /**
     * Check in base the message associated with the given code and locale
     * 
     * @param code
     *            the code of the message to solve
     * @param locale
     *            the locale to check against
     * @return a MessageFormat if one were found, either for the given locale or
     *         for the default on, or null if nothing could be found
     */
    protected MessageFormat resolveCodeInternal(String code, Locale locale) {
        String result;

        lastQuery = System.currentTimeMillis();

        try {
            result = (String) jdbcTemplate.queryForObject(sqlStatement,
                    new Object[] { code, locale.toString() }, String.class);
        } catch (IncorrectResultSizeDataAccessException e) {
            if (locale != null) {
                // Retry without a locale if we checked with one before
                try {
                    result = (String) jdbcTemplate.queryForObject(sqlStatement,
                            new Object[] { code, null }, String.class);
                } catch (IncorrectResultSizeDataAccessException ex) {
                    return null;
                }
            } else {
                return null;
            }
        }

        return new MessageFormat(result, locale);
    }

    /**
     * @param jdbcTemplate
     *            The jdbcTemplate to set.
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @param sqlStatement
     *            The sqlStatement to set.
     */
    public void setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        if (sqlStatement == null) {
            throw new BeanInitializationException(
                    "Sql statement should be filled");
        }

        if (jdbcTemplate == null) {
            throw new BeanInitializationException(
                    "Jdbc template should be filled");
        }

    }

    /**
     * Empty the cache so that all future message resolving request ends in
     * database queries
     */
    public void clearCache() {
        cachedMessages.clear();
    }

}
