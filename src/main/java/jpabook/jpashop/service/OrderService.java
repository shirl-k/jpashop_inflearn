package jpabook.jpashop.service;


import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId,int count) {

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성  - OrderItem 클래스에 만든 createOrderItem 생성메서드 사용
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //createOrderItem 생성메서드 말고 OrderItem 객체 생성해서 쓰면 유지보수가 어렵다...주의! 나중에 수정하기 어려움
        //ex) OrderItem orderItem1 = new OrderItem();  OrderItem 에 생성자 만들면 서비스에 빨간 줄
        //생성자 protected로 접근 제한되어있어서 여기에 new Order(); 스타일로 생성하면 빨간줄
        
        //항상 제약 걸면서 코드 짜는 것이 설계, 개발, 유지보수 측면에서 굿
        
        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order); //order넘김  //cascade 범위 라이프사이클

        return order.getId(); //order 식별자값 반환

    }

    //취소
    /**
     *주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }
    //검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }
}
