package stu.kms.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stu.kms.domain.SampleVO;
import stu.kms.domain.Ticket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/sample")
@Log4j
public class SampleController {

    @GetMapping(value = "/getText", produces = "text/plain; charset=utf-8")
    public String getText() {
        log.info("MIME TYPE: " + MediaType.TEXT_PLAIN_VALUE);
        return "안녕하세요.";
    }

    //sample.json은 Spring 5.2.4부터 deprecated.
    //xml이 json보다 우선 처리되기 때문에 명시적으로 json을 호출해야 함.
//    @GetMapping(value = "/getSample", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @GetMapping(value = "/getSample", produces = MediaType.APPLICATION_JSON_VALUE)
    public SampleVO getSample() {
        return new SampleVO(112, "스타", "로드");
    }

    //    xml은 produces 속성 생략 가능.
    @GetMapping(value = "/getSample2")
    public SampleVO getSample2() {
        return new SampleVO(113, "로켓", "라쿤");
    }

    @GetMapping(value = "/getList", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SampleVO> getList() {
        return IntStream.range(1, 10).mapToObj(i -> new SampleVO(i, i + "First", i + "Last"))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/getMap", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, SampleVO> getMap() {
        Map<String, SampleVO> map = new HashMap<>();
        map.put("First", new SampleVO(111, "그루트", "주니어"));
        return map;
    }

    @GetMapping(value = "/check", params = {"height", "weight"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SampleVO> check(Double height, Double weight) {
        SampleVO vo = new SampleVO(0, "" + height, "" + weight);

        ResponseEntity<SampleVO> result;
        if (height < 150) {
            result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
        } else {
            result = ResponseEntity.status(HttpStatus.OK).body(vo);
        }
        return result;
    }

    @GetMapping("/product/{cat}/{pid}")
    public String[] getPath(@PathVariable("cat") String cat, @PathVariable("pid") Integer pid) {
        return new String[] {"category : " + cat, "productId : " + pid};
    }

    @PostMapping("/ticket")
    public Ticket convert(@RequestBody Ticket ticket) {
        log.info("convert ticket..." + ticket);
        return ticket;
    }
}