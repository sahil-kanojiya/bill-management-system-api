package com.billing.system.repository;


import com.billing.system.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public  interface BillRepository extends JpaRepository<Bill,Integer> {

    List<Bill> findByBillDate(LocalDate today);

}

