package com.powin.sunspectests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/status")
public class StatusController {
    private final static Logger LOG = LogManager.getLogger();

    @GetMapping
    @ResponseBody
    public String getStatus() {
        LOG.info("Received status check request.");

        return "OK";
    }
}
