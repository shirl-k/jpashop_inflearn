package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "order_item")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem { //주문서

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;  //Orderitem 입장에서 item은 ManyToOne


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 당시 가격 (상품 가격 변동 고려)
    private int count; //주문 수량

//    protected OrderItem() {
//    } 롬복으로도 해결가능 @NoArgsConstructor(access = AccessLevel.PROTECTED)

    //==생성 메서드==// 주문 항목을 만드는 공장 역할로, 어떤 아이템을 어떤 주문으로, 얼마에 샀는지, 몇개를 샀는지를 입력받아서 주문 항목객체 OrderItem 을 만들어 반환한다.
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel() {
        getItem().addStock(count);
    }
    //==조회 로직==//

    /**
     *주문 상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount(); //주문가격과 주문 수량 곱해서 TotalPrice 구해서 비즈니스 로직으로 가져옴
    }

}
