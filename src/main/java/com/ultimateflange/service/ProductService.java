package com.ultimateflange.service;

import com.ultimateflange.dto.ProductDTO;
import com.ultimateflange.model.Product;
import com.ultimateflange.model.User;
import com.ultimateflange.repository.ProductRepository;
import com.ultimateflange.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // Get all products
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get product by ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Get product by key
    public Product getProductByKey(String key) {
        return productRepository.findByProductKey(key);
    }

    // Get products by supplier
    public List<Product> getProductsBySupplier(Long supplierId) {

        User supplier = userRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        return productRepository.findBySupplier(supplier);
    }

    // Create product
    public Product createProduct(ProductDTO productDTO, Long supplierId) {

        User supplier = userRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        if (!"Supplier".equals(supplier.getUserType())) {
            throw new RuntimeException("Only suppliers can create products");
        }

        Product product = new Product();

        product.setProductKey(productDTO.getKey());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setUnit(productDTO.getUnit());
        product.setCategory(productDTO.getCategory());
        product.setMaterial(productDTO.getMaterial());
        product.setSpecs(productDTO.getSpecs());
        product.setStandard(productDTO.getStandard());
        product.setDiameter(productDTO.getDiameter());
        product.setPressure(productDTO.getPressure());

        if (productDTO.getFeatures() != null) {
            product.setFeatures(String.join(",", productDTO.getFeatures()));
        }

        product.setSupplier(supplier);

        return productRepository.save(product);
    }

    // Update product
    public Product updateProduct(Long id, ProductDTO productDTO) {

        Product product = getProductById(id);

        product.setProductKey(productDTO.getKey());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setUnit(productDTO.getUnit());
        product.setCategory(productDTO.getCategory());
        product.setMaterial(productDTO.getMaterial());
        product.setSpecs(productDTO.getSpecs());
        product.setStandard(productDTO.getStandard());
        product.setDiameter(productDTO.getDiameter());
        product.setPressure(productDTO.getPressure());

        if (productDTO.getFeatures() != null) {
            product.setFeatures(String.join(",", productDTO.getFeatures()));
        }

        return productRepository.save(product);
    }

    // Delete product
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Search product
    public List<Product> searchProducts(String query) {
        return productRepository
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
    }

    // Convert entity to DTO
    public ProductDTO convertToDTO(Product product) {

        ProductDTO dto = new ProductDTO();

        dto.setId(product.getId());
        dto.setKey(product.getProductKey());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setUnit(product.getUnit());
        dto.setCategory(product.getCategory());
        dto.setMaterial(product.getMaterial());
        dto.setSpecs(product.getSpecs());
        dto.setStandard(product.getStandard());
        dto.setDiameter(product.getDiameter());
        dto.setPressure(product.getPressure());

        if (product.getFeatures() != null) {
            dto.setFeatures(Arrays.asList(product.getFeatures().split(",")));
        }

        if (product.getSupplier() != null) {
            dto.setSupplierId(product.getSupplier().getId());
            dto.setSupplierName(product.getSupplier().getCompany());
        }

        return dto;
    }
}
}
