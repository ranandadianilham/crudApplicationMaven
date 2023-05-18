package com.java.crudApplicationMaven.auth;

import com.java.crudApplicationMaven.payload.response.BaseResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(@RequestBody RegisterRequest request) {
        try {
            return new ResponseEntity<>(new BaseResponse(200, "success", service.register(request)), HttpStatus.OK);
        }
        catch (ConstraintViolationException e) {
            String messageTemplate = "";
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            for (ConstraintViolation<?> violation : violations) {
                messageTemplate = violation.getMessageTemplate();
                System.out.println(messageTemplate);
            }
            return new ResponseEntity<>(new BaseResponse(404, messageTemplate, null), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(new BaseResponse(404, e.getLocalizedMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<BaseResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return new ResponseEntity<>(new BaseResponse(200, "success", service.authenticate(request)), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(new BaseResponse(404, e.getLocalizedMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

}
