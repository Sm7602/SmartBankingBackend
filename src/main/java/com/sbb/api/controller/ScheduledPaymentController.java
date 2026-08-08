package com.sbb.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbb.api.dto.scheduledpayment.ScheduledPaymentRequest;
import com.sbb.api.dto.scheduledpayment.ScheduledPaymentResponse;
import com.sbb.api.dto.scheduledpayment.ScheduledPaymentUpdateRequest;
import com.sbb.api.service.ScheduledPaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/scheduled-payments")
public class ScheduledPaymentController {

    @Autowired
    private ScheduledPaymentService scheduledPaymentService;

    @PostMapping
    public ScheduledPaymentResponse  createScheduledPayment(@Valid @RequestBody ScheduledPaymentRequest request){
        System.out.println("ScheduledPaymentController.createScheduledPayment()");
        return scheduledPaymentService.createScheduledPayment(request);
    }

    @GetMapping("/{id}")
    public ScheduledPaymentResponse getScheduledPaymentById(@PathVariable Long id) {
        System.out.println("ScheduledPaymentController.getScheduledPaymentById()");
        return scheduledPaymentService.getScheduledPaymentById(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduledPaymentResponse> getScheduledPaymentsByCustomerId(@PathVariable Long customerId) {
        System.out.println("ScheduledPaymentController.getScheduledPaymentsByCustomerId()");
        return scheduledPaymentService.getScheduledPaymentsByCustomerId(customerId);
    }

    @PutMapping("/{id}")
    public ScheduledPaymentResponse  updateScheduledPayment(Long id,@Valid @RequestBody ScheduledPaymentUpdateRequest request)  {
        System.out.println("ScheduledPaymentController.updateScheduledPayment()");
        return scheduledPaymentService.updateScheduledPayment(id,request);
    }

    @PutMapping("/{id}/activate")
    public ScheduledPaymentResponse activateScheduledPayment(@PathVariable Long id) {
        System.out.println("ScheduledPaymentController.activateScheduledPayment()");
        return scheduledPaymentService.activateScheduledPayment(id);
    }

    @PutMapping("/{id}/deactivate")
    public ScheduledPaymentResponse deactivateScheduledPayment(@PathVariable Long id) {
        System.out.println("ScheduledPaymentController.deactivateScheduledPayment()");
        return scheduledPaymentService.deactivateScheduledPayment(id);
    }

    @DeleteMapping("/{id}")
    public String deleteScheduledPayment(@PathVariable Long id) {
        System.out.println("ScheduledPaymentController.deleteScheduledPayment()");
        scheduledPaymentService.deleteScheduledPayment(id);
        return "Scheduled Payment Deleted Successfully";
    }
}