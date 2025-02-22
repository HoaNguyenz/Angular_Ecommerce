package com.hoa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hoa.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	@Query("SELECT p FROM Product p " +
		       "WHERE (:category IS NULL OR p.category.name = :category) " +
		       "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
		       "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
		       "ORDER BY " +
		       "CASE WHEN :sort = 'price_low' THEN p.price END ASC, " +
		       "CASE WHEN :sort = 'price_high' THEN p.price END DESC")
		public List<Product> filterProducts(@Param("category") String category,
		                                    @Param("minPrice") Integer minPrice,
		                                    @Param("maxPrice") Integer maxPrice,
		                                    @Param("sort") String sort);

	
}
