package com.codersee.reactivemongocrud.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("companies")
data class Company(
    @Id
    val id: String? = null,
    var name: String,
    @Field("company_address")
    var address: String
)