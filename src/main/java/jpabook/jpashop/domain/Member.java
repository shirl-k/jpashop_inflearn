package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue

    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;// 내장 타입 포함됨

    @OneToMany 
    private List<Order> orders = new ArrayList<>(); //일대다 관계 -  하나의 회원이 여러개 상품 주문 order과 반대 개념
}
