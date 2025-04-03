package cc.rcbb.mini.spring.test.controller;

import cc.rcbb.mini.spring.test.req.TestReq;
import cc.rcbb.mini.spring.web.RequestMapping;

/**
 * <p>
 * HelloWorldController
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/26
 */
public class HelloWorldController {

    /*@Autowired
    private TestUserService testUserService;*/

    @RequestMapping("/test1")
    public String doTest1() {
        return "test 1, hello world!";
    }

    @RequestMapping("/test2")
    public String doTest2(TestReq req) {
        return "test 2, hello world! " + req.toString();
    }

    /*@RequestMapping("/testUser")
    public String testUser() {
        return testUserService.getTestUser().toString();
    }*/

}
