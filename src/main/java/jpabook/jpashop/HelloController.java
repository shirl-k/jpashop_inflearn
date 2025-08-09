package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("hello") //hello url 요청이 오면 controller 호출
    public String hello(Model model) { //콘트롤러의 메서드는 Model타입의 객체를 파라미터로 받아서 쓸 수 있음
        //개발자가 직접 model 객체 생성하지 않아도 파라미터로 선언만 해주면 된다는 장점!
        model.addAttribute("data", "hello!!!"); //data라는 키에 값 "hello!!!"넣어서 뷰로 넘김
        //모델에 데이터 등록할 때 사용. 변수값을 "변수명"이라는 이름으로 추가
        //model.addAttribute("변수명", "변수값") //변수에 데이터 실어서 뷰로 넘겨줄 수 있음
        return "helloview";
        // resources>templates>helloview.html
        //ctrl + 클릭 -> 해당 파일로 이동

    }
}
