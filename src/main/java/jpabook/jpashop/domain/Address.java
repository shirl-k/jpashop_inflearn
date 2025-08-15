package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.Valid;
import lombok.Getter;

@Embeddable //embedded랑 embeddable 두가지 씀
@Getter
public class Address {

    protected Address() {
    } //값 타입은 @Setter는 제거하고 생성자에서 값을 모두 초기화해서 변경 불가능한 클래스 만들자. 접근제한자는 public 보다는 protected 추천

    private String city;
    private String street;
    private String zipcode;

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
