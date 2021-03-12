package com.powin.sunspectests;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


// CodeReview-QUAL-13 I noticed in the pm.xml that this is packaged as a war. If you are running 
// the tests by java command line, you might not need to have this packaged as a war and "spring-
// ified". I think we should make a decision to package as a jar and run it as command line or to
// package as a war, springify it, have it running on a tomcat and have rest interfaces to call 
// the test suite.  We might need to do the latter in order to make this part of a countinuous 
// deployment model someday.

@Controller
@RequestMapping("/main")
public class MainController {
    private String cViewName;

    public String getViewName() {
        return cViewName;
    }

    public void setViewName(String viewName) {
        cViewName = viewName;
    }

    @GetMapping
    public String get() {
        return getViewName();
    }
}
