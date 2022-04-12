package com.nyt.nytnews.utils

interface EntityMapper<Entity, Domain> {
    fun mapFromEntity(entity: Entity): Domain
    fun mapToEntity(domain: Domain): Entity
}