package org.tbk.sbswdemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/helloworld")
    public String index() {
        return "hello world";
    }

}
