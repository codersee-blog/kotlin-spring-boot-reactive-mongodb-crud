package com.codersee.reactivemongocrud.controller

import com.codersee.reactivemongocrud.request.EmployeeRequest
import com.codersee.reactivemongocrud.response.EmployeeResponse
import com.codersee.reactivemongocrud.service.EmployeeService
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/employee")
class EmployeeController(
    private val employeeService: EmployeeService
) {

    @PostMapping
    fun createEmployee(@RequestBody request: EmployeeRequest): Mono<EmployeeResponse> {
        return employeeService.createEmployee(request)
            .map { EmployeeResponse.fromEntity(it) }
    }

    @GetMapping
    fun findAllEmployees(): Flux<EmployeeResponse> {
        return employeeService.findAll()
            .map { EmployeeResponse.fromEntity(it) }
    }

    @GetMapping("/{id}")
    fun findEmployeeById(@PathVariable id: ObjectId): Mono<EmployeeResponse> {
        return employeeService.findById(id)
            .map { EmployeeResponse.fromEntity(it) }
    }

    @GetMapping("/company/{companyId}")
    fun findAllByCompanyId(@PathVariable companyId: String): Flux<EmployeeResponse> {
        return employeeService.findAllByCompanyId(companyId)
            .map { EmployeeResponse.fromEntity(it) }
    }

    @PutMapping("/{id}")
    fun updateUpdateEmployee(
        @PathVariable id: ObjectId,
        @RequestBody request: EmployeeRequest
    ): Mono<EmployeeResponse> {
        return employeeService.updateEmployee(id, request)
            .map { EmployeeResponse.fromEntity(it) }
    }

    @DeleteMapping("/{id}")
    fun deleteEmployee(@PathVariable id: ObjectId): Mono<Void> {
        return employeeService.deleteById(id)
    }
}