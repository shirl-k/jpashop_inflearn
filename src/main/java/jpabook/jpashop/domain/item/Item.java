package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {//구현체 쓸 거기 때문에 Item은 추상클래스로 만들기

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categoryies = new ArrayList<>();
    
    //==비즈니스 로직==// 데이터 가지고 있는 엔티티 쪽에 비즈니스 메서드 있는게 응집력이 높아서 객체지향적으로 관리하기 좋다
    //상품 주문 시 재고가 줄고 느는 로직

    /**
     * stock 증가
     */

    public void addStock(int orderQuantity) {
        this.stockQuantity += orderQuantity; //재고 수량 증가 로직
    }

    /**
     * stock 감소
     */

    public void removeStock(int orderQuantity) {
        int restStock = this.stockQuantity - orderQuantity;
        if (restStock < 0) { //재고 수량 <0
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
