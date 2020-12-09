package com.codersee.reactivemongocrud.repository

import com.codersee.reactivemongocrud.model.Company
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface CompanyRepository : ReactiveMongoRepository<Company, String>