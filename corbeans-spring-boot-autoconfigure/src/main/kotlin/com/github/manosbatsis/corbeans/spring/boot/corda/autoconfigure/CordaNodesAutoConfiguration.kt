/*
 *     Corbeans: Corda integration for Spring Boot
 *     Copyright (C) 2018 Manos Batsis
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 3 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 *     USA
 */
package com.github.manosbatsis.corbeans.spring.boot.corda.autoconfigure


import com.github.manosbatsis.corbeans.spring.boot.corda.actuator.CordaInfoContributor
import com.github.manosbatsis.corbeans.spring.boot.corda.actuator.CordaInfoEndpoint
import com.github.manosbatsis.corbeans.spring.boot.corda.bind.SecureHashConverter
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration


/**
 * Auto-configures a CordaNodeService for each Corda Node
 */
@Configuration
@ComponentScan(basePackages = arrayOf("com.github.manosbatsis.corbeans"))
class CordaNodesAutoConfiguration {

    companion object {
        private val logger = LoggerFactory.getLogger(CordaNodesAutoConfiguration::class.java)

        @Bean
        @ConditionalOnMissingBean(NodeServiceBeanFactoryPostProcessor::class)
        @JvmStatic
        fun nodeServiceBeanFactoryPostProcessor(): NodeServiceBeanFactoryPostProcessor {
            logger.debug("Creating a NodeServiceBeanFactoryPostProcessor")
            return NodeServiceBeanFactoryPostProcessor()
        }
    }

    /** Add transparent SecureHash conversion */
    @Bean
    @ConditionalOnMissingBean(SecureHashConverter::class)
    fun secureHashConverter(): SecureHashConverter {
        return SecureHashConverter()
    }

    /** Add custom actuator endpoint based on known nodes */
    @Bean
    fun cordaInfoEndpoint(): CordaInfoEndpoint {
        return CordaInfoEndpoint()
    }

    /** Extend actuator's info endpoint based on known nodes */
    @Bean
    @ConditionalOnProperty(
            prefix = "corbeans", name = arrayOf("actuator.info.disable"),
            havingValue="false", matchIfMissing = true)
    fun cordaInfoContributor(): CordaInfoContributor {
        return CordaInfoContributor()
    }

}
