package com.ecom.clothes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.clothes.service.ProductAttributeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/admin/attributes")
@RequiredArgsConstructor
@Slf4j
public class ProductAttributeController {

	private final ProductAttributeService productAttributeService;
}
