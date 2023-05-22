package com.java.crudApplicationMaven.service;

import com.java.crudApplicationMaven.model.Post;
import com.java.crudApplicationMaven.repo.PostRepo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepo postRepo;

    public PostService(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    public Page<Post> getPaginatedAndSortedData(int pageNo, int pageSize, String sortBy, Sort.Direction sortDirection) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortDirection, sortBy));
        return postRepo.findAll(pageable);
    }

    public List<Post> getSortedDataAll(String sortBy, Sort.Direction sortDirection) {
        return postRepo.findAll(Sort.by(sortDirection, sortBy));
    }
}
