package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(@NotNull Integer pageNumber, @NotNull Integer pageSize, @NotNull String sortBy, @NotNull String sortOrder) {

        Sort sortByOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByOrder);
        Page<Category> page = categoryRepository.findAll(pageDetails);
        List<Category> categoryList = page.getContent();
        if (categoryList.isEmpty()) {
            throw new APIException("No category created till now.");
        }
        List<CategoryDTO> contentList = categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        return new CategoryResponse(contentList, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
    }

    @Override
    public CategoryDTO createCategory(@NotNull CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category existingCategoryByName = categoryRepository.findByCategoryName(category.getCategoryName());
        Optional<Category> existingCategoryByID = categoryRepository.findById(category.getCategoryId());
        if (existingCategoryByName != null || existingCategoryByID.isPresent()) {
            throw existingCategoryByID.isPresent() ? new APIException("Category with " + category.getCategoryId() + " already exists.")
                    : new APIException("Category with " + category.getCategoryName() + " already exists.");
        }
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(@NotNull Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryId));
        categoryRepository.delete(category);
        CategoryDTO deletedCategoryDTo = modelMapper.map(category, CategoryDTO.class);
        return deletedCategoryDTo;
    }

    @Override
    public CategoryDTO updateCategory(@NotNull CategoryDTO categoryDTO,@NotNull Long categoryId) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryId));
        existingCategory.setCategoryName(category. getCategoryName());
        Category savedCategory = categoryRepository.save(existingCategory);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

}
