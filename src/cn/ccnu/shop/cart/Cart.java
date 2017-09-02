package cn.ccnu.shop.cart;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * ���ﳵ
 * 
 * @author William Python
 * 
 */
public class Cart {
	// �������
	private Map<Integer, CartItem> map = new HashMap<Integer, CartItem>();
	// �ܼ�
	private Double total = 0d;

	// ��ȡ
	public Collection<CartItem> getCartItems() {
		return map.values();
	}

	public Double getTotal() {
		return total;
	}

	// ��ӵ����ﳵ
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

	// ��չ��ﳵ
	public void clearCart() {
		map.clear();
		total = 0d;
	}

	// �ӹ��ﳵ���Ƴ�ĳһ������
	public void removeCartItem(Integer pid) {
		CartItem item = map.remove(pid);
		total -= item.getSubTotal();
	}
}
