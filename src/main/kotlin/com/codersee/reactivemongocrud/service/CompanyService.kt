package com.codersee.reactivemongocrud.service

import com.codersee.reactivemongocrud.exception.NotFoundException
import com.codersee.reactivemongocrud.model.Company
import com.codersee.reactivemongocrud.model.Employee
import com.codersee.reactivemongocrud.repository.CompanyRepository
import com.codersee.reactivemongocrud.repository.EmployeeRepository
import com.codersee.reactivemongocrud.request.CompanyRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class CompanyService(
    private val companyRepository: CompanyRepository,
    private val employeeRepository: EmployeeRepository
) {

    fun createCompany(request: CompanyRequest): Mono<Company> =
        companyRepository.save(
            Company(
                name = request.name,
                address = request.address
            )
        )

    fun findAll(): Flux<Company> =
        companyRepository.findAll()

    fun findById(id: String): Mono<Company> =
        companyRepository.findById(id)
            .switchIfEmpty {
                Mono.error(
                    NotFoundException("Company with id $id not found")
                )
            }

    fun updateCompany(id: String, request: CompanyRequest): Mono<Company> =
        findById(id)
            .flatMap {
                companyRepository.save(
                    it.apply {
                        name = request.name
                        address = request.address
                    }
                )
            }
            .doOnSuccess { updateCompanyEmployees(it).subscribe() }

    fun deleteById(id: String): Mono<Void> =
        findById(id)
            .flatMap(companyRepository::delete)

    private fun updateCompanyEmployees(updatedCompany: Company): Flux<Employee> =
        employeeRepository.saveAll(
            employeeRepository.findByCompanyId(updatedCompany.id!!)
                .map { it.apply { company = updatedCompany } }
        )
}