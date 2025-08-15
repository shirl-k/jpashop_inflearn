package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jpabook.jpashop.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //@Repository 어노테이션 있으면 컴포넌트 스캔으로 자동으로 스프링이 빈 등록, 관리해줌 (back으로 컴포넌트 스캔 대상 확인)
@AllArgsConstructor //spring data JPA 있어서 가능
public class MemberRepository {

    private final EntityManager em; //코드 줄일 수 있음 - @AllArgsConstructor

    //    @PersistenceContext  //jpa의 엔티티 매니저 주입받음 엔티티 매니저 팩토리에서 직접 꺼내 쓸 필요 없이 스프링이 다 해결해줌
//    private EntityManager em;
// -> 스프링 최신버전에서 @PersistenceContext를 @Autowired로 변경할 수 있음 -> @RequiredArgsConstructor로 변경 가능
  
//    @PersistenceUnit
//    private EntityManagerFactory emf; 만약 엔티티 매니저 팩토리 직접 주입받고 싶다면 @PersistenceUnit 사용

    public void save(Member member) { em.persist(member); }
    //em.persist(member): member 집어넣으면 jpa가 영속성 컨텍스트에 member 객체 저장하는 로직
    //나중에 트랜잭션 커밋되는 시점에 db에 반영됨 db에 insert 쿼리 날아감

    public Member findOne(Long id) {
        return em.find(Member.class, id);  //단건 조회 em.find(타입, pk)
    }
    public List<Member> findAll() {     //리스트 조회 (회원 목록)
        return em.createQuery("select m from Member m", Member.class) //em.createQuery(JPQL, 반환타입) // + 변수 추출
                .getResultList();     //jpql sql과 문법이 거의 같은데 from의 대상이 테이블이 아닌 엔티티라는 점이 다름
    }
    public List<Member> findByName(String name){   //이름에 의해 조회
        return em.createQuery("select m from Member m where m.name = :name", Member.class)  //:name 으로 파라미터 바인딩
                .setParameter("name", name)  //setParameter로 name 바인딩 
                .getResultList(); 

    }
}
