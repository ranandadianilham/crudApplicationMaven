package com.java.crudApplicationMaven.auth;

import com.java.crudApplicationMaven.constant.enumeration.ResponseStatusCode;
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

    /* need get user */

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(@RequestBody RegisterRequest request) {
        try {
            return new ResponseEntity<>(new BaseResponse(ResponseStatusCode.SUCCESS.code(),
                    ResponseStatusCode.SUCCESS.desc(), service.register(request)), HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            String messageTemplate = "";
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            for (ConstraintViolation<?> violation : violations) {
                messageTemplate = violation.getMessageTemplate();
                System.out.println(messageTemplate);
            }
            return new ResponseEntity<>(
                    new BaseResponse(ResponseStatusCode.DATA_NOT_FOUND.code(), messageTemplate, null),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(
                    new BaseResponse(ResponseStatusCode.DATA_NOT_FOUND.code(), e.getLocalizedMessage(), null),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<BaseResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return new ResponseEntity<>(new BaseResponse(ResponseStatusCode.SUCCESS.code(),
                    ResponseStatusCode.SUCCESS.desc(), service.authenticate(request)), HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            String messageTemplate = "";
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            for (ConstraintViolation<?> violation : violations) {
                messageTemplate = violation.getMessageTemplate();
                System.out.println(messageTemplate);
            }
            return new ResponseEntity<>(
                    new BaseResponse(ResponseStatusCode.DATA_NOT_FOUND.code(), messageTemplate, null),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(
                    new BaseResponse(ResponseStatusCode.DATA_NOT_FOUND.code(), e.getLocalizedMessage(), null),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
