package org.tbk.sbswdemo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/helloworld")
    public String index() {
        return "hello world";
    }

    /*@RequestMapping("/helloworld/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin() {
        return "hello admin";
    }


    @RequestMapping("/helloworld/demo")
    @PreAuthorize("hasRole('ROLE_DEMO')")
    public String demo() {
        return "hello demo";
    }*/

}
