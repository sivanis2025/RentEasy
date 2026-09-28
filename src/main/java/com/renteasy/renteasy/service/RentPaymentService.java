package com.renteasy.renteasy.service;

import com.renteasy.renteasy.entity.RentPayment;
import com.renteasy.renteasy.entity.Tenant;
import com.renteasy.renteasy.repository.RentPaymentRepository;
import com.renteasy.renteasy.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class RentPaymentService {

    private final RentPaymentRepository rentPaymentRepository;
    private final TenantRepository tenantRepository;

    public RentPaymentService(
            RentPaymentRepository rentPaymentRepository,
            TenantRepository tenantRepository) {

        this.rentPaymentRepository = rentPaymentRepository;
        this.tenantRepository = tenantRepository;
    }

    public RentPayment recordPayment(
            Long tenantId,
            LocalDate paymentMonth,
            BigDecimal amount) {

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Tenant not found with ID: " + tenantId));

        RentPayment payment = new RentPayment();

        payment.setTenant(tenant);
        payment.setPaymentMonth(paymentMonth);
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDate.now());

        return rentPaymentRepository.save(payment);
    }

    public int getPendingMonths(Long tenantId) {

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Tenant not found with ID: " + tenantId));

        YearMonth moveInMonth =
                YearMonth.from(tenant.getMoveInDate());

        YearMonth currentMonth =
                YearMonth.now();

        int monthsSinceMoveIn =
                (int) ChronoUnit.MONTHS.between(
                        moveInMonth,
                        currentMonth) + 1;

        List<RentPayment> payments =
                rentPaymentRepository.findByTenantTenantId(tenantId);

        int monthsPaid = payments.size();

        return Math.max(
                0,
                monthsSinceMoveIn - monthsPaid
        );
    }

    public List<Tenant> getTenantsWithCurrentMonthDue() {

        List<Tenant> allTenants = tenantRepository.findAll();

        List<Tenant> unpaidTenants = new ArrayList<>();

        YearMonth currentMonth = YearMonth.now();

        for (Tenant tenant : allTenants) {

            if (!tenant.isActive()) {
                continue;
            }

            List<RentPayment> payments =
                    rentPaymentRepository.findByTenantTenantId(
                            tenant.getTenantId());

            boolean paid = false;

            for (RentPayment payment : payments) {

                YearMonth paymentMonth =
                        YearMonth.from(payment.getPaymentMonth());

                if (paymentMonth.equals(currentMonth)) {
                    paid = true;
                    break;
                }
            }

            if (!paid) {
                unpaidTenants.add(tenant);
            }
        }

        return unpaidTenants;
    }
    public List<RentPayment> getAllPayments() {
            return rentPaymentRepository.findAll();
    }
}