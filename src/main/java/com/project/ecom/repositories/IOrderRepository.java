package com.project.ecom.repositories;

import com.project.ecom.enums.OrderStatus;
import com.project.ecom.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.customerId = :customerId and o.status not in (:excludedStatuses)")
    List<Order> getActiveOrders(@Param("customerId") Long customerId, @Param("excludedStatuses")List<OrderStatus> excludedStatuses);

    Page<Order> findByCustomerId(Long customerId, Pageable pageable);

    Optional<Order> findByIdAndCustomerId(Long orderId, Long customerId);
}
