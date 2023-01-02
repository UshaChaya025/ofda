package com.cg.ofda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cg.ofda.dto.FoodCartDto;
import com.cg.ofda.dto.ItemDto;
import com.cg.ofda.exception.CartException;
import com.cg.ofda.exception.RestaurantException;
import com.cg.ofda.repository.ICartRepository;
import com.cg.ofda.repository.IItemRepository;

@Service
public class ICartServiceImpl implements ICartService{
	@Autowired
	ICartRepository cartRepository;
	@Autowired
	IItemRepository itemRepository;
	
	
	/* @author : Usha
	 * @return : Item 
	 * @description : This method adds Item to the repository and returns Item
	 */
	@Override
	public FoodCartDto addItemToCart(String cartId, ItemDto item) throws CartException{
		if(cartRepository.existsById(cartId)) {
		FoodCartDto cart = cartRepository.findById(cartId).get();
		List<ItemDto> items = cart.getItemList();
		items.add(item);
		return cartRepository.save(cart);
		}
		else
			throw new CartException("Cart Id not found");
	}
	
	
	/* @author : Usha
	 * @return : CartDto 
	 * @description : This method clears the cart
	 */
	

	@Override
	public FoodCartDto clearCart(String cartId) throws CartException{
		if(cartRepository.existsById(cartId)) {
			FoodCartDto cart = cartRepository.findById(cartId).get();
			cartRepository.delete(cart);
			return cart;
		}
		else throw new CartException("Cart Id not found");
	}
	
	/* @author : Usha
	 * @return : List<FoodCartDto>
	 * @description : This method views all items from  the repository 
	 */

	@Override
	public List<FoodCartDto> viewCart() {
		return cartRepository.findAll();
	}
	
	/* @author : Usha
	 * @return : FoodCartDto
	 * @description : This method increases the quantity of the given itemId in the cart
	 */

	@Override
	public FoodCartDto increaseQuantity(String cartId, String itemId, int quantity) throws CartException{
		ItemDto it = null;
		if(cartRepository.existsById(cartId)) {
		FoodCartDto cart = cartRepository.findById(cartId).get();
		List<ItemDto> items = cart.getItemList();
		for(ItemDto item : items) {
			if(item.getItemId().equals(itemId))
			 it = item;
		}
		int qty = it.getQuantity();
		it.setQuantity(quantity+qty);
		cartRepository.save(cart);
		return cart;	
		}
		else
			throw new CartException("Cart Id not found");
	}
	
	
	/* @author : Usha
	 * @return : FoodCartDto
	 * @description : This method increases the quantity of the given itemId in the cart
	 */
	
	
	@Override
	public FoodCartDto reduceQuantity(String cartId, String itemId, int quantity) throws CartException {
		ItemDto it = null;
		if(cartRepository.existsById(cartId)) {
		FoodCartDto cart = cartRepository.findById(cartId).get();
		List<ItemDto> items = cart.getItemList();
		for(ItemDto item : items) {
			if(item.getItemId().equals(itemId))
			 it = item;
		}
		int qty = it.getQuantity();
		it.setQuantity(qty - quantity);
		cartRepository.save(cart);
		return cart;
		}
		else
			throw new CartException("Cart Id not found");
	}
	
	/* @author : Usha
	 * @return : FoodCartDto
	 * @description : This method removes the item from the cart
	 */
	
	public FoodCartDto removeItem(String cartId, String itemId) throws CartException {
		if(cartRepository.existsById(cartId)) {
			FoodCartDto cart = cartRepository.findById(cartId).get();
			List<ItemDto> items = cart.getItemList();
			ItemDto it = null;
			for(ItemDto item : items) {
				if(item.getItemId().equals(itemId)) {
					it = item;
				}
			}
			itemRepository.delete(it);
			cartRepository.save(cart);
			return cart;
		}
		else throw new CartException("Cart Id not found");
		
	}
	
	
	/* @author : Usha
	 * @return : FoodCartDto
	 * @description : This method views the cart by cartId 
	 */


	@Override
	public FoodCartDto viewCartById(String cartId) {
		Optional<FoodCartDto> findById = cartRepository.findById(cartId);
		return findById.orElseThrow(() -> new RestaurantException("There are no restaurant having id:" + cartId));
	}
}



