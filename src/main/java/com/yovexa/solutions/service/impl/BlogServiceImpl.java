package com.yovexa.solutions.service.impl;

import com.yovexa.solutions.dto.blog.BlogRequest;
import com.yovexa.solutions.dto.blog.BlogResponse;
import com.yovexa.solutions.dto.common.PagedResponse;
import com.yovexa.solutions.exception.DuplicateResourceException;
import com.yovexa.solutions.exception.ResourceNotFoundException;
import com.yovexa.solutions.mapper.EntityMapper;
import com.yovexa.solutions.model.Blog;
import com.yovexa.solutions.repository.BlogRepository;
import com.yovexa.solutions.service.BlogService;
import com.yovexa.solutions.util.SlugUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final EntityMapper mapper;

    @Override
    public List<BlogResponse> getPublicBlogs(String search, String category) {
        List<Blog> list;
        if (category != null && !category.trim().isEmpty() && !category.equalsIgnoreCase("ALL")) {
            list = blogRepository.findByStatusAndCategoryOrderByPublishedAtDesc("PUBLISHED", category);
        } else {
            list = blogRepository.findByStatusOrderByPublishedAtDesc("PUBLISHED");
        }

        if (search != null && !search.trim().isEmpty()) {
            String q = search.trim().toLowerCase();
            list = list.stream()
                    .filter(b -> (b.getTitle() != null && b.getTitle().toLowerCase().contains(q)) ||
                                 (b.getExcerpt() != null && b.getExcerpt().toLowerCase().contains(q)) ||
                                 (b.getContent() != null && b.getContent().toLowerCase().contains(q)))
                    .toList();
        }

        return list.stream().map(mapper::toBlogResponse).toList();
    }

    @Override
    public PagedResponse<BlogResponse> getPublicBlogsPaged(String search, String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("publishedAt").descending());
        Page<Blog> pageResult = blogRepository.searchPublic(
                (search != null && !search.trim().isEmpty()) ? search.trim() : null,
                category,
                pageable
        );
        return PagedResponse.of(pageResult.map(mapper::toBlogResponse));
    }

    @Override
    public BlogResponse getBlogBySlug(String slug) {
        Blog blog = blogRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "slug", slug));
        return mapper.toBlogResponse(blog);
    }

    @Override
    public PagedResponse<BlogResponse> getAdminBlogs(String search, String category, String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Blog> pageResult = blogRepository.searchAndFilter(
                (search != null && !search.trim().isEmpty()) ? search.trim() : null,
                category,
                status,
                pageable
        );
        return PagedResponse.of(pageResult.map(mapper::toBlogResponse));
    }

    @Override
    public BlogResponse getBlogById(String id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", id));
        return mapper.toBlogResponse(blog);
    }

    @Override
    public BlogResponse createBlog(BlogRequest request) {
        Blog blog = mapper.toBlog(request);

        if (blogRepository.existsBySlug(blog.getSlug())) {
            blog.setSlug(blog.getSlug() + "-" + System.currentTimeMillis());
        }

        Blog saved = blogRepository.save(blog);
        return mapper.toBlogResponse(saved);
    }

    @Override
    public BlogResponse updateBlog(String id, BlogRequest request) {
        Blog existing = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", id));

        existing.setTitle(request.getTitle());

        String targetSlug = (request.getSlug() != null && !request.getSlug().trim().isEmpty())
                ? SlugUtils.toSlug(request.getSlug())
                : existing.getSlug();

        if (!existing.getSlug().equals(targetSlug) && blogRepository.existsBySlugAndIdNot(targetSlug, id)) {
            throw new DuplicateResourceException("Blog", "slug", targetSlug);
        }
        existing.setSlug(targetSlug);

        existing.setExcerpt(request.getExcerpt());
        existing.setContent(request.getContent());
        existing.setFeaturedImage(request.getFeaturedImage());
        existing.setCategory(request.getCategory());
        existing.setAuthor(request.getAuthor());
        if (request.getTags() != null) {
            existing.setTags(request.getTags());
        }

        if (request.getStatus() != null) {
            String newStatus = request.getStatus().toUpperCase();
            if ("PUBLISHED".equals(newStatus) && existing.getPublishedAt() == null) {
                existing.setPublishedAt(Instant.now());
            }
            existing.setStatus(newStatus);
        }
        if (request.getPublishedAt() != null) {
            existing.setPublishedAt(request.getPublishedAt());
        }
        existing.setSeoTitle(request.getSeoTitle());
        existing.setSeoDescription(request.getSeoDescription());

        Blog saved = blogRepository.save(existing);
        return mapper.toBlogResponse(saved);
    }

    @Override
    public void deleteBlog(String id) {
        if (!blogRepository.existsById(id)) {
            throw new ResourceNotFoundException("Blog", "id", id);
        }
        blogRepository.deleteById(id);
    }
}
