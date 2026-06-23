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
import com.sbb.api.entity.ScheduledPayment;
import com.sbb.api.service.ScheduledPaymentService;

@RestController
@RequestMapping("/api/scheduled-payments")
public class ScheduledPaymentController {

    @Autowired
    private ScheduledPaymentService scheduledPaymentService;

    @PostMapping("/userId/{userId}")
    public ScheduledPayment createScheduledPayment(@PathVariable Long userId, @RequestBody ScheduledPayment scheduledPayment) {
        System.out.println("ScheduledPaymentController.createScheduledPayment()");
        return scheduledPaymentService.createScheduledPayment(userId,scheduledPayment);
    }

    @GetMapping("/{id}")
    public ScheduledPayment getScheduledPaymentById(@PathVariable Long id) {
        System.out.println("ScheduledPaymentController.getScheduledPaymentById()");
        return scheduledPaymentService.getScheduledPaymentById(id);
    }

    @GetMapping("/user/{userId}")
    public List<ScheduledPayment> getScheduledPaymentsByUserId(@PathVariable Long userId) {
        System.out.println("ScheduledPaymentController.getScheduledPaymentsByUserId()");
        return scheduledPaymentService.getScheduledPaymentsByUserId(userId);
    }

    @PutMapping("/{id}")
    public ScheduledPayment updateScheduledPayment(@PathVariable Long id,@RequestBody ScheduledPayment scheduledPayment) {
        System.out.println("ScheduledPaymentController.updateScheduledPayment()");
        return scheduledPaymentService.updateScheduledPayment(id,scheduledPayment);
    }

    @PutMapping("/{id}/activate")
    public ScheduledPayment activateScheduledPayment(@PathVariable Long id) {
        System.out.println("ScheduledPaymentController.activateScheduledPayment()");
        return scheduledPaymentService.activateScheduledPayment(id);
    }

    @PutMapping("/{id}/deactivate")
    public ScheduledPayment deactivateScheduledPayment(@PathVariable Long id) {
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