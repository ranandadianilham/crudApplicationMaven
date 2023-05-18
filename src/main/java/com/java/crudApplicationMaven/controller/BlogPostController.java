package com.java.crudApplicationMaven.controller;

import com.java.crudApplicationMaven.model.Post;
import com.java.crudApplicationMaven.payload.request.ListPaginationRequest;
import com.java.crudApplicationMaven.payload.response.BaseResponse;
import com.java.crudApplicationMaven.payload.response.PostResponse;
import com.java.crudApplicationMaven.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/blog/post")
public class BlogPostController {

    @Autowired
    private PostRepo postRepo;

    @PostMapping("/list")
    public ResponseEntity<BaseResponse> getAllPosts(@RequestBody ListPaginationRequest listPaginationRequest) {
        try {
            Sort.Direction sortDirection = Sort.Direction.fromString(listPaginationRequest.getSortType());
            List<Post> postList = new ArrayList<>();
            postRepo.findAll(Sort.by(sortDirection, listPaginationRequest.getSortBy())).forEach(postList::add);

            if (postList.isEmpty()) {
                return new ResponseEntity<>(new BaseResponse(204,  "No Content", null),HttpStatus.NO_CONTENT);
            }

            int pageTotalCount = postList.size() < listPaginationRequest.getRowPerPage() ? 1
                    : postList.size() / listPaginationRequest.getRowPerPage();

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("pageNo", listPaginationRequest.getPageNo());
            responseBody.put("rowPerPage", listPaginationRequest.getRowPerPage());
            responseBody.put("pageTotal", pageTotalCount);
            responseBody.put("sortBy", listPaginationRequest.getSortBy());
            responseBody.put("sortType", sortDirection);
            responseBody.put("rowTotal", postList.size());
            responseBody.put("data", postList);

            //return new ResponseEntity<>(responseBody, HttpStatus.OK);
            return new ResponseEntity<>(new BaseResponse(200, "SUCCESS", responseBody), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(404,  e.getMessage().toString(), null),HttpStatus.NOT_FOUND);

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
            return new ResponseEntity<>(new BaseResponse(404,  e.getMessage().toString(), null),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addPost(@RequestBody Post post, Errors errors) {
        Post postObj = postRepo.save(post);
        try {
            if (errors.hasErrors()) {
                return new ResponseEntity(new BaseResponse(404, "NOT FOUND", null), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new BaseResponse(200, "SUCCESS", postObj), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new BaseResponse(404,  e.getMessage().toString(), null), HttpStatus.NOT_FOUND);
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
            return new ResponseEntity(new BaseResponse(404, e.getMessage().toString(), null), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<PostResponse> deletePostById(@PathVariable Long id) {
        try {
            boolean entityExist = postRepo.existsById(id);
            postRepo.deleteById(id);
            if(entityExist) {
                return new ResponseEntity<>(new PostResponse(200, "success"), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(new PostResponse(404, "NOT FOUND"), HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(new PostResponse(404,  e.getMessage().toString()), HttpStatus.NOT_FOUND);
        }

    }
}
