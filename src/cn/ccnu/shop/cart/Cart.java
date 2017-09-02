package cn.ccnu.shop.cart;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 购物车
 * 
 * @author William Python
 * 
 */
public class Cart {
	// 购物项集合
	private Map<Integer, CartItem> map = new HashMap<Integer, CartItem>();
	// 总计
	private Double total = 0d;

	// 获取
	public Collection<CartItem> getCartItems() {
		return map.values();
	}

	public Double getTotal() {
		return total;
	}

	// 添加到购物车
	public void addCartItem(CartItem item) {
		Integer pid = item.getProduct().getPid();
		CartItem _item = map.get(pid);
		if (_item == null) {
			map.put(pid, item);
		} else {
			_item.setCount(item.getCount() + _item.getCount());
		}
		total += item.getSubTotal();
	}

	// 清空购物车
	public void clearCart() {
		map.clear();
		total = 0d;
	}

	// 从购物车中移除某一购物项
	public void removeCartItem(Integer pid) {
		CartItem item = map.remove(pid);
		total -= item.getSubTotal();
	}
}
