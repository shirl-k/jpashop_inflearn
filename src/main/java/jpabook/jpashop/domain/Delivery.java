package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY) //거울역할 (Order의 delivery)
    private Order order; //매핑 안하면 빨간줄 //OnetoOne: 일대일매핑

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)  //ORDINAL이 디폴트지만 숫자로 들어가기 때문에 중간에 추가되면 망하므로 반드시 STRING으로 쓸 것
    private DeliveryStatus status; //Ready, COMP
}
