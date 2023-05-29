package org.example.domain.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.domain.OrderStatus;

import java.sql.Timestamp;
import java.util.List;

/**
 * Order entity
 */
@Entity
@Table(name = "order")
@Data
@Accessors(chain = true)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "special_requests")
    private String specialRequests;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderDish> orderDishes;
}
