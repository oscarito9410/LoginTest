@file:OptIn(ExperimentalResourceApi::class)

package com.aboolean.logintest.presentation.manager

import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString as resourceString

fun interface ResourceManager {
    suspend fun getString(resource: StringResource): String
}

class ResourceManagerImpl : ResourceManager {
    override suspend fun getString(resource: StringResource) =
        resourceString(resource)
}