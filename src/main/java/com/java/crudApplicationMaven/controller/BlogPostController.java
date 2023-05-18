package com.java.crudApplicationMaven.controller;

import com.java.crudApplicationMaven.model.Post;
import com.java.crudApplicationMaven.payload.request.ListPaginationRequest;
import com.java.crudApplicationMaven.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
public class BlogPostController {

    @Autowired
    private PostRepo postRepo;

    @PostMapping("/list")
    public ResponseEntity<Map<String, Object>> getAllPosts(@RequestBody ListPaginationRequest listPaginationRequest) {

        try {
            Sort.Direction sortDirection = Sort.Direction.fromString(listPaginationRequest.getSortType());
            List<Post> postList = new ArrayList<>();
            postRepo.findAll(Sort.by(sortDirection, listPaginationRequest.getSortBy())).forEach(postList::add);

            if (postList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get post by id
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> postData = postRepo.findById(id);

        if (postData.isPresent()) {
            return new ResponseEntity<>(postData.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<Post> addPost(@RequestBody Post post, Errors errors) {
        /*
         * The endpoint should accept a JSON payload containing the title, body, and
         * author of the blog post.
         * The endpoint should return the created blog post with a generated ID.
         */

        if (errors.hasErrors()) {
            return new ResponseEntity((errors), HttpStatus.BAD_REQUEST);
        }

        Post postObj = postRepo.save(post);

        return new ResponseEntity<>(postObj, HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Post> updatePostById(@PathVariable Long id, @RequestBody Post newPostData) {

        Optional<Post> oldPostData = postRepo.findById(id);

        if (oldPostData.isPresent()) {
            Post updatedPostData = oldPostData.get();
            updatedPostData.setTitle(newPostData.getTitle());
            updatedPostData.setAuthor(newPostData.getAuthor());
            updatedPostData.setBody(newPostData.getBody());

            Post postObj = postRepo.save(updatedPostData);

            return new ResponseEntity<>(postObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deletePostById(@PathVariable Long id) {
        postRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
