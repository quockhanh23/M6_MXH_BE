package com.example.final_case_social_web.service.impl;

import com.example.final_case_social_web.model.Comment;
import com.example.final_case_social_web.model.Post2;
import com.example.final_case_social_web.repository.PostRepository;
import com.example.final_case_social_web.service.PostService;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Iterable<Post2> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post2> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Post2 save(Post2 post) {
        return postRepository.save(post);
    }

    @Override
    public void remove(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post2> findAllPostByUser(Long id) {
        return postRepository.findAllPostByUser(id);
    }

    @Override
    public List<Post2> allPost() {
        return postRepository.AllPost();
    }


    public List<Post2> getListLCReFile(String lcRef, String requestCode, Integer productType) {
        StringBuffer sql = new StringBuffer("Select * from tbl_ref_file where is_deleted = false ");
        if (lcRef != null && !"".equals(lcRef)) {
            sql.append(" and lc_ref =:lcRef ");
        }
        if (requestCode != null && !"".equals(requestCode)) {
            sql.append(" and request_code = :requestCode ");
        }
        if (productType != null) {
            sql.append(" and product_type = :productType ");
        }

        Query query = (Query) entityManager.createNativeQuery(sql.toString(), Comment.class);
        if (lcRef != null && !"".equals(lcRef)) {
            query.setParameter("lcRef", lcRef);
        }
        if (requestCode != null && !"".equals(requestCode)) {
            query.setParameter("requestCode", requestCode);
        }
        if (productType != null) {
            query.setParameter("productType", productType);
        }
        if (requestCode == null && productType == null) {
            query.setParameter("lcRef", lcRef);
        }
        if (lcRef == null && productType == null) {
            query.setParameter("requestCode", requestCode);
        }
        if (lcRef == null && requestCode == null) {
            query.setParameter("productType", productType);
        }
        return query.getResultList();
    }

}
