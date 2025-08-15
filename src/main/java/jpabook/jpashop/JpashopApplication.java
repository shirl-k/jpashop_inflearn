package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication  //이 어노테이션이 있으면 이 패키지와 패키지 하위에 있는 모든 거 스프링이 컴포넌트 스캔 후 자동 등록
public class JpashopApplication {

	public static void main(String[] args) {

//		Hello hello = new Hello(); //객체 생성
//		hello.setData("hello");  //조건 설정
//		String data = hello.getData();
//		System.out.println("data: " + data);

		SpringApplication.run(JpashopApplication.class, args);
	}

}
