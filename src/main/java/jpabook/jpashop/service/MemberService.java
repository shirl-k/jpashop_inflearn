package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // readOnly = true 옵션 주면 JPA가 조회 성능 최적화시킴 읽기 전용이라 db에게 리소스 많이 쓰지 않도록 험
@RequiredArgsConstructor //final 있는 것만 생성자 만들어줌
public class MemberService {

    private final MemberRepository memberRepository;

//    @Autowired 방법1 필드 주입 방식 : mock 직접 주입 까다로움의 단점
//    private MemberRepository memberRepository; //필드 인젝션 : @Autowired 쓰면 스프링이 스프링 빈에 등록된 memberRepository 주입시켜줌

//    @Autowired 방법 2 - setter인젝션 방법
//     : 테스트 코드 작성 시 mock 직접 주입할 수도 있는 등 더 유연한 방식
//    중간에 setRepository 바뀌는 경우

//    private MemberRepository memberRepository;

//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

     //@Autowired 방법3 생성자 인젝션 방식 : 2번 방식 보완한 방식으로 권장
//    private final MemberRepository MemberRepository; //
//
//    public MemberService(MemberRepository memberRepository) { //변경할 일 없기 떄문에 final 넣길 추천
//        this.memberRepository = memberRepository; // 꿀팁 - 최신 버전 스프링에서는 @autowired가 없어도 생성자가 하나만 있는 경우에는 알아서 인젝션 해줌
//    }
    //방법4 @AllArgsConstructor - private final MemberRepository MemberRepository; 필드에 있는 걸 가지고 생성자 만들어줌
    //방법5 @RequiredArgsConstructor = final 에 있는 필드만 가지고 생성자 만들어줌 (가장 추천) 디테일한 부분은 롬복 참고

    //회원 가입  - 핵심 기능1
    @Transactional //등록  (readOnly = false)
    public Long join(Member member) {  //join으로 member 객체 넘기기

        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId(); //id 반환
    }
    //exception 예외처리
    private void validateDuplicateMember(Member member) { 
        //MemberA 둘이 동시에 DB 인서트를 했을 경우 ... 
        // 중복 가능성 둘이 동시에 validateDuplicateMember 통과하고 로직을 둘 다 동시에 호출하게 되는 문제
        // 같은 이름으로 둘이 동시에 가입 가능성 -> 멀티스레드 동시성 문제로 DB에 member의 name 제약 조건을 유니크로 잡아줘서 막을 것을 권장

        List<Member> findmembers = memberRepository.findByName(member.getName()); //memberRepository에서 findByName으로 같은 이름이 있는지 찾아본 후 반환값: findmembers 로 값
        if (!findmembers.isEmpty()) { //만약 이름이 조회되면 가입된 회원이 있는거니 
            throw new IllegalStateException("이미 존재하는 회원입니다");  //throw new IllegalStateException() 처리
        }
    }

    //회원 전체 조회   - 핵심 기능2
    public List<Member> findMembers() {
        return memberRepository.findAll();
    } //회원 리스트 조회

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId); // 회원 정보 단건 조회

    }

}
