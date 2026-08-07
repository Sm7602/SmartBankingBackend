package com.sbb.api.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbb.api.dao.ScheduledPaymentRepository;
import com.sbb.api.dao.CustomerRepository;
import com.sbb.api.entity.ScheduledPayment;
import com.sbb.api.entity.Customer;

@Service
public class ScheduledPaymentService {

    @Autowired
    private ScheduledPaymentRepository scheduledPaymentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public ScheduledPayment createScheduledPayment(Long userId,ScheduledPayment scheduledPayment) {
        System.out.println("ScheduledPaymentService.createScheduledPayment()");
        Customer customer = customerRepository.findById(userId).orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        scheduledPayment.setCustomer(customer);
        scheduledPayment.setPaymentReference("SP" + System.currentTimeMillis());
        scheduledPayment.setCreatedAt(LocalDateTime.now());
        scheduledPayment.setUpdatedAt(LocalDateTime.now());

        return scheduledPaymentRepository.save(scheduledPayment);
    }

    public ScheduledPayment getScheduledPaymentById(Long id) {
        System.out.println("ScheduledPaymentService.getScheduledPaymentById()");
        return scheduledPaymentRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Scheduled Payment not found"));
    }

    public List<ScheduledPayment> getScheduledPaymentsByUserId(Long userId) {
        System.out.println("ScheduledPaymentService.getScheduledPaymentsByUserId()");
        return scheduledPaymentRepository.findByUserId(userId);
    }

    public ScheduledPayment updateScheduledPayment(Long id,ScheduledPayment scheduledPayment) {
        System.out.println("ScheduledPaymentService.updateScheduledPayment()");
        ScheduledPayment existing =getScheduledPaymentById(id);

        existing.setBeneficiaryAccountNumber(scheduledPayment.getBeneficiaryAccountNumber());
        existing.setAmount(scheduledPayment.getAmount());
        existing.setFrequency(scheduledPayment.getFrequency());
        existing.setNextPaymentDate(scheduledPayment.getNextPaymentDate());
        existing.setActive(scheduledPayment.getActive());
        existing.setUpdatedAt(LocalDateTime.now());

        return scheduledPaymentRepository.save(existing);
    }

    public ScheduledPayment activateScheduledPayment(Long id) {
        System.out.println("ScheduledPaymentService.activateScheduledPayment()");
        ScheduledPayment payment = getScheduledPaymentById(id);

        payment.setActive(true);
        payment.setUpdatedAt(LocalDateTime.now());
        return scheduledPaymentRepository.save(payment);
    }

    public ScheduledPayment deactivateScheduledPayment(Long id) {
        System.out.println("ScheduledPaymentService.deactivateScheduledPayment()");
        ScheduledPayment payment =getScheduledPaymentById(id);

        payment.setActive(false);
        payment.setUpdatedAt(LocalDateTime.now());
        return scheduledPaymentRepository.save(payment);
    }

    public void deleteScheduledPayment(Long id) {
        System.out.println("ScheduledPaymentService.deleteScheduledPayment()");
        ScheduledPayment payment =getScheduledPaymentById(id);
        scheduledPaymentRepository.delete(payment);
    }
}