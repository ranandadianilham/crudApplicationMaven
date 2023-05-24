package com.java.crudApplicationMaven.controller;

import com.java.crudApplicationMaven.constant.enumeration.ResponseStatusCode;
import com.java.crudApplicationMaven.model.Post;
import com.java.crudApplicationMaven.payload.request.PostGetAllRequest;
import com.java.crudApplicationMaven.payload.response.BaseResponse;
import com.java.crudApplicationMaven.repo.PostRepo;
import com.java.crudApplicationMaven.service.PostService;
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
@SuppressWarnings("all")
public class BlogPostController {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private PostService postService;

    @PostMapping("/list/paginated")
    public ResponseEntity<BaseResponse> getAllPostsPaginated(@RequestBody PostGetAllRequest postGetAllRequest) {
        try {
            // get sort Direction
            Sort.Direction sortDirection = Sort.Direction.fromString(postGetAllRequest.getSortType());
            // just allow get all without page in body
            Map<String, Object> responseBody = new HashMap<>();
            // if page no empty, row per page empty get all
            if (postGetAllRequest.getPageNo() == 0 && postGetAllRequest.getRowPerPage() == 0) {
                responseBody = postService.getSortedDataAll(postGetAllRequest.getSortBy(),
                        sortDirection);
            } else {
                // Get Posts data with pagination and sorted
                // pagination start from page 0.
                responseBody = postService.getPaginatedAndSortedData(
                        postGetAllRequest.getPageNo(), postGetAllRequest.getRowPerPage(), postGetAllRequest.getSortBy(),
                        sortDirection);
            }

            // if data empty
            if (responseBody.size() == 0) {
                return new ResponseEntity<>(new BaseResponse(ResponseStatusCode.DATA_NOT_FOUND.code(),
                        ResponseStatusCode.DATA_NOT_FOUND.desc(), responseBody), HttpStatus.NOT_FOUND);
            }
            // return response body
            return new ResponseEntity<>(new BaseResponse(ResponseStatusCode.SUCCESS.code(),
                    ResponseStatusCode.SUCCESS.desc(), responseBody), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new BaseResponse(ResponseStatusCode.DATA_NOT_FOUND.code(), e.getMessage(), null),
                    HttpStatus.NOT_FOUND);

        }
    }

    /*
     * @PostMapping("/list/all")
     * public ResponseEntity<BaseResponse> getAllPosts(@RequestBody
     * PostGetAllRequest postGetAllRequest) {
     * try {
     * // get sort Direction
     * Sort.Direction sortDirection =
     * Sort.Direction.fromString(postGetAllRequest.getSortType());
     * // Get Posts data with pagination and sorted
     * List<Post> paginatedData =
     * postService.getSortedDataAll(postGetAllRequest.getSortBy(),
     * sortDirection);
     * // if data empty
     * if (paginatedData.size() == 0) {
     * return new ResponseEntity<>(new
     * BaseResponse(ResponseStatusCode.DATA_NOT_FOUND.code(),
     * "Empty List", null), HttpStatus.NOT_FOUND);
     * }
     * 
     * // mapped response body
     * Map<String, Object> responseBody = new HashMap<>();
     * responseBody.put("sortBy", postGetAllRequest.getSortBy());
     * responseBody.put("sortType", sortDirection);
     * responseBody.put("rowTotal", paginatedData.size());
     * responseBody.put("data", paginatedData);
     * 
     * // return response body
     * return new ResponseEntity<>(new
     * BaseResponse(ResponseStatusCode.SUCCESS.code(),
     * ResponseStatusCode.SUCCESS.desc(), responseBody), HttpStatus.OK);
     * } catch (Exception e) {
     * return new ResponseEntity<>(
     * new BaseResponse(ResponseStatusCode.DATA_NOT_FOUND.code(), e.getMessage(),
     * null),
     * HttpStatus.NOT_FOUND);
     * 
     * }
     * }
     */
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getPostById(@PathVariable Long id) {
        try {
            // get data by id
            Optional<Post> postData = postRepo.findById(id);
            // if exist
            if (postData.isPresent()) {
                return new ResponseEntity<>(new BaseResponse(ResponseStatusCode.SUCCESS.code(),
                        ResponseStatusCode.SUCCESS.desc(), postData.get()), HttpStatus.OK);
            }
            return new ResponseEntity<>(new BaseResponse(ResponseStatusCode.DATA_NOT_FOUND.code(),
                    ResponseStatusCode.DATA_NOT_FOUND.desc(), null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new BaseResponse(ResponseStatusCode.DATA_NOT_FOUND.code(), e.getMessage(), null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addPost(@RequestBody Post post, Errors errors) {

        try {
            Post postObj = postRepo.save(post);
            if (errors.hasErrors()) {
                return new ResponseEntity(new BaseResponse(ResponseStatusCode.DATA_NOT_FOUND.code(),
                        ResponseStatusCode.DATA_NOT_FOUND.desc(), null), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(
                    new BaseResponse(ResponseStatusCode.SUCCESS.code(), ResponseStatusCode.SUCCESS.desc(), postObj),
                    HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            String messageTemplate = "";
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            for (ConstraintViolation<?> violation : violations) {
                messageTemplate = violation.getMessageTemplate();
            }
            return new ResponseEntity<>(
                    new BaseResponse(ResponseStatusCode.DATA_NOT_FOUND.code(), messageTemplate, null),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(new BaseResponse(ResponseStatusCode.DATA_NOT_FOUND.code(), e.getMessage(), null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<BaseResponse> updatePostById(@PathVariable Long id, @RequestBody Post newPostData) {
        try {
            Optional<Post> oldPostData = postRepo.findById(id);
            if (oldPostData.isPresent()) {
                Post updatedPostData = oldPostData.get();
                updatedPostData.setTitle(newPostData.getTitle());
                updatedPostData.setAuthor(newPostData.getAuthor());
                updatedPostData.setBody(newPostData.getBody());

                Post postObj = postRepo.save(updatedPostData);

                return new ResponseEntity<>(
                        new BaseResponse(ResponseStatusCode.SUCCESS.code(), ResponseStatusCode.SUCCESS.desc(), postObj),
                        HttpStatus.OK);
            }
            return new ResponseEntity(new BaseResponse(ResponseStatusCode.DATA_NOT_FOUND.code(),
                    ResponseStatusCode.DATA_NOT_FOUND.desc(), null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity(new BaseResponse(ResponseStatusCode.DATA_NOT_FOUND.code(), e.getMessage(), null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> deletePostById(@PathVariable Long id) {
        try {
            boolean entityExist = postRepo.existsById(id);
            postRepo.deleteById(id);
            if (entityExist) {
                return new ResponseEntity<>(
                        new BaseResponse(ResponseStatusCode.SUCCESS.code(), ResponseStatusCode.SUCCESS.desc(), null),
                        HttpStatus.OK);

            } else {
                return new ResponseEntity(new BaseResponse(ResponseStatusCode.DATA_NOT_FOUND.code(),
                        ResponseStatusCode.DATA_NOT_FOUND.desc(), null), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity(new BaseResponse(ResponseStatusCode.DATA_NOT_FOUND.code(), e.getMessage(), null),
                    HttpStatus.NOT_FOUND);

        }

    }
}
