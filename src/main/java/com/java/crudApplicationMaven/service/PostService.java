package com.java.crudApplicationMaven.service;

import com.java.crudApplicationMaven.model.Post;
import com.java.crudApplicationMaven.repo.PostRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> getPaginatedAndSortedData(int pageNo, int rowPerPage, String sortBy,
            Sort.Direction sortDirection) {
        Pageable pageable = PageRequest.of(pageNo, rowPerPage, Sort.by(sortDirection, sortBy));
        Map<String, Object> responseBody = new HashMap<>();
        Page<Post> paginatedData = postRepo.findAll(pageable);
        // mapped response body
        responseBody.put("pageNo", pageNo);
        responseBody.put("rowPerPage", rowPerPage);
        responseBody.put("pageTotal", paginatedData.getTotalPages());
        responseBody.put("sortBy", sortBy);
        responseBody.put("sortType", sortDirection);
        responseBody.put("rowTotal", paginatedData.getTotalElements());
        responseBody.put("data", paginatedData.getContent());
        return responseBody;
    }

    public Map<String, Object> getSortedDataAll(String sortBy, Sort.Direction sortDirection) {
        // mapped response body
        List<Post> paginatedData = postRepo.findAll(Sort.by(sortDirection, sortBy));
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("sortBy", sortBy);
        responseBody.put("sortType", sortDirection);
        responseBody.put("rowTotal", paginatedData.size());
        responseBody.put("data", paginatedData);
        return responseBody;
    }
}
