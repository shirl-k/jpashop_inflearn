package jpabook.jpashop.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)   //member랑 order랑 양방향 참조 //*(fetch = FetchType.EAGER) 사용은 Order 딱 한건만 조회할 때 적합.. 근데 그냥 쓰지말고 지연로딩으로 통일 (LAZY)
    @JoinColumn(name = "member_id") //foreign key "member_id" 매핑
    private Member member; //order랑 member는 다대일 관계 -> many to one

    //JPQL select o From order o; (JPA에서 제공하는 쿼리) -> SQL 로 그대로 번역됨 (select * from order)  n+1 개 날아감 order 날린 거 + 단건 쿼리 날아감 ...

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)  //빨간 줄 뜨는 건 아직 매핑이 안되어있는 경우
    private List<OrderItem> orderItems = new ArrayList<>();


//    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
//    persist(orderItemA)
//    persist(orderItemB)
//    persist(orderItemC)
//
//    persist(order)
//
//    엔티티 당 각각 persist 호출해야하는데
//    cascade = CascadeType.ALL 있으면 위 세 코드는 지우고 persist(order)만 써도 됨.


    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL) //모든 엔티티는 저장하고 싶으면 각각 persist 해줘야하는데 delivery 값만 세팅해주고 order에 cascade = CascadeType.ALL쓰면 한번에 가능
    @JoinColumn(name = "delivery_id") //연관관계 주인
    private Delivery delivery; //일대일 매핑 - 하나의 주문 정보는 하나의 배송정보만 가져야 일대일 관계 성립함
    //access를 많이 하는 쪽에 foreign key 놓는 것이 좋음. 조회 더 많이 하는 쪽에  (order, delivery 둘 다 fk 놔도 되지만 order쪽 선호)
    //연관관계 주인 정하기: Order 에도 delivery있고 Delivery에도 order 있다. fk에 가까운 곳에 있는 order에 있는 delivery에 연관관계의 주인으로 잡아주면 됨

    //스프링부트 신규 설정
    // 1. 카멜 케이스 -> 언더스코어 (memberPoint -> member_point)
    // 2. .(점) -> _(언더스코어)
    // 3. 대문자 -> 소문자
    private LocalDateTime orderDate; //주문시간
    // order_date 자바 캐멀 스타일을 언더스코어로 연결시키는게 스프링부트 신규 기본전략

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 [ORDER, CANCEL]

    //==연관관계 편의 메서드==// 양쪽 세팅할 때 한쪽만 ...!
//    public void setMember(Member member) {
//        this.member = member; // 반대로 양방향 넣어야함.
//    }
//    public static void main(String[] args)
}
