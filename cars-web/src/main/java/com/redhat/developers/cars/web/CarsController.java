package com.redhat.developers.cars.web;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.common.util.KeycloakUriBuilder;
import org.keycloak.constants.ServiceUrlConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class CarsController {

    @Value("${welcome.message}")
    private String message = "Hello!! Welcome to my world of Cars";

    @Value("${cars.api.url}")
    private String carsApiUrl;

    private RestTemplate restTemplate;

    private KeycloakRestTemplate keycloakRestTemplate;

    @Autowired
    HttpServletRequest request;

    @Autowired
    public CarsController(RestTemplate restTemplate, KeycloakRestTemplate keycloakRestTemplate) {
        this.restTemplate = restTemplate;
        this.keycloakRestTemplate = keycloakRestTemplate;
    }

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        model.put("message", this.message);
        return "home";
    }

    @RequestMapping("/logout")
    public String logout(Map<String, Object> model) throws ServletException {
        request.logout();
        model.put("message", this.message);
        return "home";
    }

    @RequestMapping("/list")
    public String listAllCars(Principal principal, Model model,
                              @Value("${keycloak.auth-server-url}") String keycloakServerUri) {
        ResponseEntity<List> response = keycloakRestTemplate.getForEntity(carsApiUrl + "/list", List.class);
        log.info(response.getBody().toString());
        model.addAttribute("principal", principal);
        model.addAttribute("message", this.message);
        model.addAttribute("cars", response.getBody());
        String logoutURI = KeycloakUriBuilder.fromUri(keycloakServerUri)
            .path(ServiceUrlConstants.TOKEN_SERVICE_LOGOUT_PATH)
            .queryParam("redirect_uri", request.getContextPath())
            .build("istio").toString();
        model.addAttribute("logout", logoutURI);
        return "home";
    }
}
