package com.ecom.clothes.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ecom.clothes.service.ProductSkuService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductSkuController {

	private final ProductSkuService productSkuService;
}
