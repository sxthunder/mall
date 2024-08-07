package com.macro.mall.portal.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.macro.mall.model.OmsCartItem;
import com.macro.mall.portal.domain.CartProduct;
import com.macro.mall.portal.domain.CartPromotionItem;
import com.macro.mall.portal.service.CartService;
import com.macro.mall.portal.service.OmsCartItemService;
import com.macro.mall.portal.service.OmsPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 购物车管理Service实现类
 * Created by macro on 2018/8/2.
 */
@Service
public class OmsCartItemServiceImpl implements OmsCartItemService {
    @Autowired
    private CartService cartService;
    @Autowired
    private OmsPromotionService promotionService;

    @Override
    public int add(OmsCartItem cartItem) {
        return cartService.addCartItem(cartItem);
    }

    @Override
    public List<OmsCartItem> list(Long memberId) {
        return cartService.listCartItems(memberId);
    }

    @Override
    public List<CartPromotionItem> listPromotion(Long memberId, List<Long> cartIds) {
        List<OmsCartItem> cartItemList = list(memberId);
        if (CollUtil.isNotEmpty(cartIds)) {
            cartItemList = cartItemList.stream().filter(item -> cartIds.contains(item.getId())).collect(Collectors.toList());
        }
        return promotionService.calcCartPromotion(cartItemList);
    }

    @Override
    public int updateQuantity(Long id, Long memberId, Integer quantity) {
        return cartService.updateCartItemQuantity(id, memberId, quantity);
    }

    @Override
    public int delete(Long memberId, List<Long> ids) {
        return cartService.deleteCartItems(memberId, ids);
    }

    @Override
    public CartProduct getCartProduct(Long productId) {
        return cartService.getCartProduct(productId);
    }

    @Override
    public int updateAttr(OmsCartItem cartItem) {
        return cartService.updateCartItemAttr(cartItem);
    }

    @Override
    public int clear(Long memberId) {
        return cartService.clearCart(memberId);
    }
}
