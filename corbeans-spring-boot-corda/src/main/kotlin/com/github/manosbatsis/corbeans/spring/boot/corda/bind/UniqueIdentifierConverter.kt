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
package com.github.manosbatsis.corbeans.spring.boot.corda.bind

import net.corda.core.contracts.UniqueIdentifier
import org.springframework.core.convert.converter.Converter
import java.util.*

/**
 * Custom converter for transparent String<>UniqueIdentifier binding.
 * External ID conversions are supported for strings following an ${externalId}_${id} format
 */
class UniqueIdentifierConverter : Converter<String, UniqueIdentifier> {

    override fun convert(source: String): UniqueIdentifier {
        // Is an external ID included?
        val separatorIndex = source.lastIndexOf('_')
        return if (separatorIndex < 0) UniqueIdentifier.fromString(source)
        else UniqueIdentifier(
                source.substring(0, separatorIndex),
                UUID.fromString(source.substring(separatorIndex + 1)))
    }


}