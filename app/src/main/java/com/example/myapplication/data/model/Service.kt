package com.example.myapplication.data.model

@Serializable
data class Service(
    val id: Int,
    val name: String,
    val profession: String,
    val rating: Double,
    val serviceType: String, // "plumbing", "electric"
    val pricePerHour: String,
    val availability: String,
    val location: String
)

object Services : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val profession = varchar("profession", 100)
    val rating = double("rating")
    val serviceType = varchar("service_type", 20)
    val pricePerHour = varchar("price_per_hour", 20)
    val availability = varchar("availability", 100)
    val location = varchar("location", 200)

    override val primaryKey = PrimaryKey(id)
}