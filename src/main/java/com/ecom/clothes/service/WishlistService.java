package com.ecom.clothes.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.clothes.dto.common.PageRequest;
import com.ecom.clothes.dto.request.CreateWishlistRequest;
import com.ecom.clothes.dto.response.WishlistPageResponse;
import com.ecom.clothes.dto.response.WishlistResponse;
import com.ecom.clothes.entity.ProductSku;
import com.ecom.clothes.entity.User;
import com.ecom.clothes.entity.Wishlist;
import com.ecom.clothes.repository.ProductSkuRepository;
import com.ecom.clothes.repository.UserRepository;
import com.ecom.clothes.repository.WishlistRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WishlistService {

	private final WishlistRepository wishlistRepository;
	private final UserRepository userRepository;
	private final ProductSkuRepository productSkuRepository;

	@Transactional(readOnly = true)
	public WishlistPageResponse getAllWishlistByUserId(Long userId, @Valid PageRequest pageRequest) {
		log.info("User with id: {} fetches all their wishlist", userId);

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("Not found user with id: {}" + userId));

		Page<Wishlist> wishlistPage = wishlistRepository.findAllByUserId(userId, pageRequest.toPageable());

		List<WishlistResponse> wishlistResponses = wishlistPage.getContent().stream().map(WishlistResponse::fromEntity)
				.toList();

		return new WishlistPageResponse(wishlistResponses, wishlistPage.getNumber(), wishlistPage.getSize(),
				pageRequest.sortBy(), pageRequest.direction(), pageRequest.search());
	}

	@Transactional
	public WishlistResponse addToWishlist(Long userId, @Valid CreateWishlistRequest request) {
		log.info("User with id: {} add the product sku with id: {} to their wishlist", userId, request.productSkuId());

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("Not found user with id: {}" + userId));

		ProductSku productSku = productSkuRepository.findById(request.productSkuId())
				.orElseThrow(() -> new RuntimeException("Not found product sku with id: {}" + request.productSkuId()));

		Wishlist wishlist = new Wishlist();
		wishlist.setUser(user);
		wishlist.setProductSku(productSku);

		Wishlist saveWishlist = wishlistRepository.save(wishlist);
		log.info("User added product sku to wishlist successfully");

		return WishlistResponse.fromEntity(saveWishlist);
	}
}
