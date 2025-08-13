package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Category {
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item", //JoinTable로 중간 테이블 매핑해줘야함. 객체는 양쪽 다 컬렉션으로 다대다 관계 가능하지만, 관계형 DB는 컬렉션 관계를 양쪽에 가질 수 없기 때문에 일대 다, 다대일로 해서 중간테이블 매핑해줘야함
            joinColumns = @JoinColumn(name = "category_id"), //중간 테이블 category_item 의 category_id
            inverseJoinColumns = @JoinColumn(name = "item_id") //category_item 테이블의 아이템쪽으로 들어감
    ) //실무에서는 ManyToMany 대신 중간엔티티(Category item)을 추가해서 @ManyToOne @OneToMany 로 매핑하자 (다대다 X 일대다 다대일 매핑으로 풀어 쓰기)
    private List<Item> items = new ArrayList<>(); //다대다 관계 (ManyToMany) 카테고리도 리스트로 아이템을 가지고 아이템도 리스트로 카테고리를 가짐

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
}
