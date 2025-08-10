package jpabook.jpashop.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne   //member랑 order랑 양방향 참조
    @JoinColumn(name = "member_id") //foreign key "member_id" 매핑
    private Member member; //order랑 member는 다대일 관계 -> many to one
}
