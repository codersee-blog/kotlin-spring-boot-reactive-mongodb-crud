package com.codersee.reactivemongocrud.controller

import com.codersee.reactivemongocrud.request.CompanyRequest
import com.codersee.reactivemongocrud.response.CompanyResponse
import com.codersee.reactivemongocrud.service.CompanyService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@RestController
@RequestMapping("/api/company")
class CompanyController(
    private val companyService: CompanyService
) {

    @PostMapping
    fun createCompany(@RequestBody request: CompanyRequest): Mono<CompanyResponse> {
        return companyService.createCompany(request)
            .map { CompanyResponse.fromEntity(it) }
    }

    @GetMapping
    fun findAllCompanies(): Flux<CompanyResponse> {
        return companyService.findAll()
            .map { CompanyResponse.fromEntity(it) }
            .delayElements(Duration.ofSeconds(2))
    }

    @GetMapping("/{id}")
    fun findCompanyById(@PathVariable id: String): Mono<CompanyResponse> {
        return companyService.findById(id)
            .map { CompanyResponse.fromEntity(it) }
    }

    @PutMapping("/{id}")
    fun updateCompany(
        @PathVariable id: String,
        @RequestBody request: CompanyRequest
    ): Mono<CompanyResponse> {
        return companyService.updateCompany(id, request)
            .map { CompanyResponse.fromEntity(it) }
    }

    @DeleteMapping("/{id}")
    fun deleteCompany(@PathVariable id: String): Mono<Void> {
        return companyService.deleteById(id)
    }
}