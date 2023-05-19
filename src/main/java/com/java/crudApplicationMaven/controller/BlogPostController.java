package com.java.crudApplicationMaven.controller;

import com.java.crudApplicationMaven.model.Post;
import com.java.crudApplicationMaven.payload.request.PostGetAllRequest;
import com.java.crudApplicationMaven.payload.response.BaseResponse;
import com.java.crudApplicationMaven.repo.PostRepo;
import com.java.crudApplicationMaven.service.listPagination.PostService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/blog/post")
@SuppressWarnings("unchecked")
public class BlogPostController {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private PostService postService;

    @PostMapping("/list")
    public ResponseEntity<BaseResponse> getAllPosts(@RequestBody PostGetAllRequest postGetAllRequest) {
        try {
            List<Post> postList = new ArrayList<>();
            Sort.Direction sortDirection = Sort.Direction.fromString(postGetAllRequest.getSortType());
            Page<Post> paginatedData = postService.getPaginatedAndSortedData(
                    postGetAllRequest.getPageNo(), postGetAllRequest.getRowPerPage(),  postGetAllRequest.getSortBy(), sortDirection );
            if (postGetAllRequest.getPageNo() >= paginatedData.getTotalPages()) {
                return new ResponseEntity<>(new BaseResponse(404,  "Not Found", null),HttpStatus.NOT_FOUND);
            }
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("pageNo", postGetAllRequest.getPageNo());
            responseBody.put("rowPerPage", postGetAllRequest.getRowPerPage());
            responseBody.put("pageTotal", paginatedData.getTotalPages());
            responseBody.put("sortBy", postGetAllRequest.getSortBy());
            responseBody.put("sortType", sortDirection);
            responseBody.put("rowTotal", paginatedData.getTotalElements());
            responseBody.put("data", paginatedData.getContent());
            return new ResponseEntity<>(new BaseResponse(200, "SUCCESS", responseBody), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(404,  e.getMessage(), null),HttpStatus.NOT_FOUND);

        }
    }

    //get post by id
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getPostById(@PathVariable Long id) {
        try {
            Optional<Post> postData = postRepo.findById(id);

            if (postData.isPresent()) {
                return new ResponseEntity<>(new BaseResponse(200, "SUCCESS", postData.get()), HttpStatus.OK);
            }
            return new ResponseEntity<>(new BaseResponse(404, "NOT FOUND", null),HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(404,  e.getMessage(), null),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addPost(@RequestBody Post post, Errors errors) {

        try {
        Post postObj = postRepo.save(post);
            if (errors.hasErrors()) {
                return new ResponseEntity(new BaseResponse(404, "NOT FOUND", null), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new BaseResponse(200, "SUCCESS", postObj), HttpStatus.OK);
        }catch (ConstraintViolationException e) {
            String messageTemplate = "";
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            for (ConstraintViolation<?> violation : violations) {
                messageTemplate = violation.getMessageTemplate();
            }
            return new ResponseEntity<>(new BaseResponse(404, messageTemplate, null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(new BaseResponse(404,  e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<BaseResponse> updatePostById(@PathVariable Long id, @RequestBody Post newPostData) {

        Optional<Post> oldPostData = postRepo.findById(id);

        try {
            if (oldPostData.isPresent()) {
                Post updatedPostData = oldPostData.get();
                updatedPostData.setTitle(newPostData.getTitle());
                updatedPostData.setAuthor(newPostData.getAuthor());
                updatedPostData.setBody(newPostData.getBody());

                Post postObj = postRepo.save(updatedPostData);

                return new ResponseEntity<>(new BaseResponse(200, "SUCCESS", postObj), HttpStatus.OK);
            }
            return new ResponseEntity(new BaseResponse(404, "NOT FOUND", null), HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity(new BaseResponse(404, e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deletePostById(@PathVariable Long id) {
        try {
            boolean entityExist = postRepo.existsById(id);
            postRepo.deleteById(id);
            if(entityExist) {
                return new ResponseEntity<>(new BaseResponse(200, "SUCCESS", null), HttpStatus.OK);

            }else {
                return new ResponseEntity(new BaseResponse(404, "NOT FOUND", null), HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            return new ResponseEntity(new BaseResponse(404, e.getMessage(), null), HttpStatus.NOT_FOUND);

        }

    }
}
