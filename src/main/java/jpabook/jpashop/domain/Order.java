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

    //==연관관계 편의 메서드==// 양쪽 세팅할 때 한쪽만 ...! 확실하게 컨트롤하는 쪽에 넣기
    public void setMember(Member member) {   //member 세팅할 때
        this.member = member; // 반대로 양방향 넣어야함.
        member.getOrders().add(this);//+
    }
//    public static void main(String[] args) {
//        Member member = new Member();
//        Order order = new Order();

//        member.getOrders().add(order); 코드 줄어듦
//        order.setMember(member);
//    }
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==// 핵심 비즈니스 로직 개발
    public static Order createOrder(Member member, Delivery delivery, OrderItem...orderItem) {
        Order order = new Order();
        order.setMember(member); //파라미터로 member 세팅
        order.setDelivery(delivery); //delivery 세팅
        for (OrderItem item : orderItem) {
            order.addOrderItem(item);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }
    
    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {  //배송이 끝나서 취소가 불가능한 경우
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL); //validation 통과하면 그 외의 경우 나자신(OrderStatus)를 CANCEL 상태로 바꿈
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel(); //OrderItem도 주문 취소
        }
    }

    //==조회 로직==//

    /**
     *전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0; //totalPrice 0으로 초기화
        for (OrderItem orderItem : orderItems) {
            totalPrice +=orderItem.getTotalPrice(); //orderItems에 대한 루프돌면서 orderItem에 대한 TotalPrice 가져옴
        }
        return totalPrice;
    }
}
