package com.example.myapplication.data.repository

import com.example.myapplication.Service
import com.example.myapplication.data.model.Services
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ServiceRepository {
    fun getServicesByType(serviceType: String): List<Service> = transaction {
        Services.select { Services.serviceType eq serviceType.lowercase() }
            .map { it.toService() }
    }

    fun getServiceById(id: Int): Service? = transaction {
        Services.select { Services.id eq id }.singleOrNull()?.toService()
    }

    private fun ResultRow.toService(): Service = Service(
        id = this[Services.id],
        name = this[Services.name],
        profession = this[Services.profession],
        rating = this[Services.rating],
        serviceType = this[Services.serviceType],
        pricePerHour = this[Services.pricePerHour],
        availability = this[Services.availability],
        location = this[Services.location]
    )
}