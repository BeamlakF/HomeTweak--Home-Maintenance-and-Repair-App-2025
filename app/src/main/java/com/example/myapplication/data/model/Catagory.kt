package com.example.myapplication.data.model

enum class ServiceType(val displayName: String) {
    PLUMBER("Plumbing"),
    PAINTER("Electrical"),
    CARPENTER("Carpenter"),
    ELECTRICIAN("Electrician"),
    CONTRACTOR("Car Repair"),
    GARDNER("Gardener");

    override fun toString(): String = displayName
}

data class Category(
    val id: Int,
    val name: String,
    val services: List<ServiceType>
)
