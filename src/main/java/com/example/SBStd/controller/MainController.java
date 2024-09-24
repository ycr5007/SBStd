package com.example.SBStd.controller;

import com.example.SBStd.domain.Article;
import com.example.SBStd.domain.Person;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// @Controller : Controller 선언
@RestController
public class MainController {

    // 데이터 보존을 위한 전역변수
    private int reqCnt = 0;

    // DB 대용 List instance
    private List<Article> articles = new ArrayList<>(
            // Default DataSet
            Arrays.asList(
                    new Article("title1", "Hello World")
                    , new Article("title2", "GoodBye World")
                    , new Article("title3", "This is New World")
            )
    );

    private List<Person> persons = new ArrayList<>(
            // Default DataSet
            Arrays.asList(
                    new Person(23, "James")
                    , new Person(29, "Robert")
                    , new Person(21, "Ciro")
            )
    );


    @RequestMapping("/sbStd")
    public String index() {
        System.out.println("Link Test"); // Spring Boot Console 출력
        return "axios&SpringBoot Test";
    }

    @GetMapping("/test1")
    @ResponseBody
    public String showGet(@RequestParam(defaultValue = "DefaultPage") String pageName) {
        reqCnt++;
        // HTML 작성 Return 문법
        return """
                <h1>%d : Hello World_Get</h1>
                <p>PageName(url?pageName=***) : %s</p>
                <form method="POST" action="/test2">
                    <input type="text" name="word" placeholder="Hello World!" />
                    <input type="number" name="num" placeholder="Input Number!" />
                    <input type="text" name="name" placeholder="Dummy Test!" />
                    <input type="submit" value="Submit" />
                </form>
                """.formatted(reqCnt, pageName);
    }

    @PostMapping("/test2")
    @ResponseBody
    // @RequestParam 요청한 Parameter 값의 옵션 설정
    // String 의 경우 "" | null 으로 데이터가 넘어오지만, int 의 경우 Parsing 에서 에러 발생
    public String showPost(@RequestParam(defaultValue = "DUMMY") String name, String word, int num) {
        // Get - Post 간 데이터 통신은 Input 태그의 name 옵션을 기준으로 데이터를 불러온다.
        return """
                <h1>%d : Hello World_Post</h1>
                <span>word : %s</span> </br>
                <span>numb : %d</span> </br>
                <span>name : %s</span>
                """.formatted(reqCnt, word, num, name);
    }

    @GetMapping("/test3")
    @ResponseBody
    public String showXmulY(Integer x, Integer y) {
        // Integer 자체로 받을 때 에러발생 하지 않으나, 아래 함수 수행 시 자료형 조정을 위해 NULL 처리 추가
        // (RequstParam 에서 defaultValue 를 지정하지 않을 경우 객체형태로 데이터를 받아 NULL 처리를 통해 동일한 동작을 만들 수 있음)
        if (x == null) x = 9;
        if (y == null) y = 9;

        // 자료형에 대한 확정 要 (Integer <=> int 간의 차이 해소)
        // Integer 객체형태의 경우, 불변 조건(final) 적용
        final Integer FINAL_X = x;

        // rangeClosed(startNum, limit) : 반복문
        // mapToObj(arrow Func) : Stream 수행 데이터의 형태를 Parsing
        return IntStream.rangeClosed(1, y)
                .mapToObj(i -> "%d * %d = %d".formatted(FINAL_X, i, FINAL_X * i))
                .collect(Collectors.joining("<br>"));

        /*
         * Lamda : 람다 캡처링(capturing lambda)이란 간단히 말해 이처럼 파라미터로 넘겨받은 데이터가 아닌
         *           람다식 외부에서 정의된 변수를 참조하는 변수를 람다식 내부에 저장하고 사용하는 동작을 의미
         * */
    }

    // PathVariable 의 경우, 값이 없을 경우 404 Error 발생
    // URL 예시 : http://localhost:8080/formTest/A
    @GetMapping("/formTest/{name}")
    @ResponseBody
    public String showForm(@PathVariable String name) {
        // Switch 문법 확인 (최신문법 Java 버전 확인 要)
        return switch (name) {
            case "A" -> {
                String returnType = "Yield";
                yield "%s A".formatted(returnType); // Switch 내부 Return 과 외부 Return 구분을 위한 문법 Yield
            }
            case "B" -> "Return B";
            // 2 개 이상의 값이 있을 경우 ", " 구분
            case "C", "D" -> "Return C Or D";
            default -> "NOPE :)";
        };
    }

    // Session 단계 데이터 보존 방식
    @GetMapping("/inputSessionID")
    @ResponseBody
    public String inputSession() {
        int rand = (int) (Math.random() * 100) + 1;
        return """
                <h1>Input Session Data</h1>
                <form method="POST" action="/saveSessionID/%d">
                    <input type="text" name="id" placeholder="Input Your ID" />
                    <input type="submit" value="Login" />
                </form>
                """.formatted(rand);
    }

    @PostMapping("/saveSessionID/{value}")
    @ResponseBody
    public String SaveSession(HttpServletRequest req, String id, @PathVariable String value) {
        // Requset 객체에서 Session 을 가져옴
        HttpSession session = req.getSession();

        session.setAttribute("userID", id);
        session.setAttribute("value", value);

        if (id == null || id.isEmpty()) {
            id = "Who Are You?";
            session.invalidate();
        }

        String rtVal = "";
        try {
            rtVal = session.getAttribute("value").toString();
        } catch (IllegalStateException e) {
            rtVal = e.toString();
        }

        return """
                <h1>Save Session Data<h1>
                <span>userId : %s</span></br>
                <span>value : %s</span>
                """.formatted(id, rtVal);
    }

    @GetMapping("/getSessionID")
    @ResponseBody
    // Spring Boot 내장되어 있기 떄문에, Request 객체를 통하지 않고 직접 Session 을 가져올 수 있음
    public String getSession(HttpSession session) {
        // req => Cookie => JSESSIONID => Session 을 얻을 수 있음
        // DevTools [Application] 탭 > Cookies 내부에 JSESSIONID 로 저장 됨

        return """
                    <h1>Get Saved Session Data</h1>
                    <span>userId : %s</span></br>
                    <span>value : %s</span>
                """.formatted(session.getAttribute("userID"), session.getAttribute("value"));
    }

    @GetMapping("/addArticle")
    @ResponseBody
    public String addArticle(String title, String body) {
        Article article = new Article(title, body);
        articles.add(article);

        return """
                <span>create Successfully %d Article</span>
                """.formatted(article.getId());
    }

    @GetMapping("/showArticles")
    @ResponseBody
    public String showArticles() {
        StringBuilder rs = new StringBuilder();
        articles.forEach(a -> {
            rs.append("""
                    <p>
                        <a href="/showArticle/%d">%s </a>
                    </p>
                    """.formatted(a.getId(), a.getTitle()));
        });
        return rs.toString();
    }

    @GetMapping("/showArticle/{id}")
    @ResponseBody
    public Article showOneArticle(@PathVariable int id) {
        Article article = articles
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()// 특정 id 값을 기준으로 지정된 loop 까지 반복 수행 (for 문에서 if - return 방식)
                .get();
        return article;
    }

    @GetMapping("/modifyArticle/{id}")
    @ResponseBody
    public String modifyArticle(@PathVariable int id, String title, String body) {

        /* NoSUchElementException try-catch 처리
        Article article = null;
        try {
            article = articles
                    .stream()
                    .filter(a -> a.getId() == id)
                    .findFirst()// 특정 id 값을 기준으로 지정된 loop 까지 반복 수행 (for 문에서 if - return 방식)
                    .get();
        } catch(NoSuchElementException e) {
            return """
                    <p>%s</p>
                    <span>%d is not found Article</span>
                    """.formatted(e.toString(), id);
        }
        */

        // Optional 을 할용한 filter null 처리
        Article article = articles
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null); // id 를 찾지 목하면 null 반환

        if(article == null)
            return """
            <span>%d is not found Article</span>
            """.formatted(id);

        article.setTitle(title);
        article.setBody(body);

        return """
                <span>modify Successfully %d Article</span>
                """.formatted(id);
    }

    @RequestMapping("/deleteArticle/{id}")
    @ResponseBody
    public String deleteArticle(@PathVariable int id) {

        Article article = articles
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null); // id 를 찾지 목하면 null 반환

        if(article == null)
            return """
            <span>%d is not found Article</span>
            """.formatted(id);

        // List 데이터 삭제
        articles.remove(article);

        return """
                <span>delete Successfully %d Article</span>
                """.formatted(id);
    }

    // @RequestMapping("/addPerson")
    @RequestMapping("/addPerson/{id}") // @PathVariable 또한 자동으로 Mapping 된다. (단, 이 때는 id 값을 입력 받기에 매개변수의 Id 값이 저장된다.)
    // Spring 동작 우선순위 : QueryString > PathVariable (ex) localhost:8080/1?id=2&age=23&name=test => Result : id = 1 / age = 23 / name = test
    @ResponseBody
    public String addPerson(Person person) {
        persons.add(person);
        return """
                <span>create Successfully %d User</span>
                """.formatted(person.getId()); // 생성자를 거치지 않기 떄문에 Id 값은 항상 0 으로 매개변수의 값만 저장한다.
    }

    @RequestMapping("/showPersons")
    @ResponseBody
    public String showPersonList() {
        StringBuilder rs = new StringBuilder();
        persons.forEach(a -> {
            rs.append("""
                    <p>
                        <a href="/???/%d">%s </a>
                    </p>
                    """.formatted(a.getId(), a.getName()));
        });
        return rs.toString();
    }
}