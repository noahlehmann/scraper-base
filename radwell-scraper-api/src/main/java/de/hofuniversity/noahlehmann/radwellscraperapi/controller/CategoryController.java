package de.hofuniversity.noahlehmann.radwellscraperapi.controller;

import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.productcategory.ProductCategory;
import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.productcategory.ProductCategoryRepository;
import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.producttimestamp.ProductTimestamp;
import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.producttimestamp.ProductTimestampRepository;
import de.hofuniversity.noahlehmann.radwellscraperapi.persistence.sequence.SequenceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class CategoryController {

    private final ProductCategoryRepository categoryRepository;
    private final ProductTimestampRepository productTimestampRepository;
    private final SequenceDao sequenceDao;

    @Autowired
    public CategoryController(ProductCategoryRepository categoryRepository,
                              ProductTimestampRepository productTimestampRepository, SequenceDao sequenceDao) {
        this.categoryRepository = categoryRepository;
        this.productTimestampRepository = productTimestampRepository;
        this.sequenceDao = sequenceDao;
    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductCategory>> getAll() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<ProductCategory> getById(@PathVariable Long id) {
        Optional<ProductCategory> optional = categoryRepository.findById(id);
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/category")
    public ResponseEntity<ProductCategory> createNewCategory(@RequestBody ProductCategory productCategory) {
        productTimestampRepository.saveAll(
                productCategory.getProductTimestamps().stream()
                        .peek(t -> t.setId(sequenceDao.getNextSequenceId(ProductTimestamp.SEQUENCE_KEY)))
                        .collect(Collectors.toList())
        );
        productCategory.setId(sequenceDao.getNextSequenceId(ProductCategory.SEQUENCE_KEY));
        return ResponseEntity.created(URI.create("")).body(categoryRepository.save(productCategory));
    }

    @GetMapping("/category/{id}/timestamp")
    public ResponseEntity<Set<ProductTimestamp>> getTimestampsForCategory(@PathVariable Long id) {
        Optional<ProductCategory> optional = categoryRepository.findById(id);
        return optional.map(category -> ResponseEntity.ok(category.getProductTimestamps())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Set<ProductTimestamp>> getTimestampsForProductId(@PathVariable String productId) {
        return ResponseEntity.ok(productTimestampRepository.findAllByProductId(productId));
    }
}
