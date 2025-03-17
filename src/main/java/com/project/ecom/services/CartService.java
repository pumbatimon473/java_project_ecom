package com.project.ecom.services;

import com.project.ecom.enums.CartStatus;
import com.project.ecom.enums.UserSessionStatus;
import com.project.ecom.exceptions.*;
import com.project.ecom.models.*;
import com.project.ecom.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {
    private final ICustomerRepository customerRepo;
    private final IProductRepository productRepo;
    private final IProductInventoryRepository productInventoryRepo;
    private final ICustomerSessionRepository customerSessionRepo;
    private final ICartRepository cartRepo;
    private final ICartItemRepository cartItemRepo;
    private final IAddressRepository addressRepo;
    private final IOrderService orderService;

    @Autowired
    public CartService(ICustomerRepository customerRepo, IProductRepository productRepo, IProductInventoryRepository productInventoryRepo, ICustomerSessionRepository customerSessionRepo, ICartRepository cartRepo, ICartItemRepository cartItemRepo, IAddressRepository addressRepo, IOrderService orderService) {
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
        this.productInventoryRepo = productInventoryRepo;
        this.customerSessionRepo = customerSessionRepo;
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
        this.addressRepo = addressRepo;
        this.orderService = orderService;
    }

    @Override
    public CartItem addProductToCart(Long customerId, Long productId, Integer quantity) {
        // check if the given product id is valid
        Product product = this.productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        // check if the customer is valid
        Customer customer = this.customerRepo.findById(customerId)
                .orElseThrow(() -> new UserNotFoundException(customerId));
        // check if the specified product is in the inventory
        ProductInventory productInventory = this.productInventoryRepo.findByProductId(productId)
                .orElseThrow(() -> new ProductNotInInventoryException(productId));
        if (productInventory.getQuantity() <= 0)
            throw new ProductNotInInventoryException(productId);
        if (productInventory.getQuantity() < quantity)
            throw new InsufficientInventoryException(productId);
        // find an active customer session
        Optional<CustomerSession> customerSessionOptional = this.customerSessionRepo.findByCustomerIdAndStatus(customerId, UserSessionStatus.ACTIVE);
        CustomerSession customerSession;
        Cart cart;
        if (customerSessionOptional.isEmpty()) {  // create a new active session
            customerSession = new CustomerSession();
            customerSession.setCustomer(customer);
            customerSession.setStatus(UserSessionStatus.ACTIVE);
            this.customerSessionRepo.save(customerSession);
            cart = new Cart();
            cart.setSession(customerSession);
            cart.setStatus(CartStatus.ACTIVE);
            this.cartRepo.save(cart);
        } else {
            customerSession = customerSessionOptional.get();
            // get active cart attached to the customer session
            Optional<Cart> cartOptional = this.cartRepo.findBySessionIdAndStatus(customerSession.getId(), CartStatus.ACTIVE);
            if (cartOptional.isEmpty()) {  // create a new cart
                cart = new Cart();
                cart.setSession(customerSession);
                cart.setStatus(CartStatus.ACTIVE);
                this.cartRepo.save(cart);
            } else {
                cart = cartOptional.get();
            }
        }
        // add product to the cart
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setCart(cart);
        return this.cartItemRepo.save(cartItem);
    }

    @Override
    public void removeCartItem(Long customerId, Long cartItemId) {
        CartItem cartItem = getCartItem(customerId, cartItemId);
        deleteCartItem(cartItem);
    }

    private void deleteCartItem(CartItem cartItem) {
        // remove the cart item
        // - if it is the only cart item then dispose the cart
        if (cartItem.getCart().getCartItems().size() == 1) {
            cartItem.getCart().setStatus(CartStatus.DISPOSED);
            this.cartRepo.save(cartItem.getCart());  // persist the change - letting the user session running even if the cart is empty
        } else {  // - else put the item into a disposed cart
            Cart disposedCart = new Cart();
            disposedCart.setSession(cartItem.getCart().getSession());
            disposedCart.setStatus(CartStatus.DISPOSED);
            this.cartRepo.save(disposedCart);
            cartItem.setCart(disposedCart);
            this.cartItemRepo.save(cartItem);
        }
    }

    private CartItem getCartItem(Long customerId, Long cartItemId) {
        this.customerRepo.findById(customerId)
                .orElseThrow(() -> new UserNotFoundException(customerId));
        CartItem cartItem = this.cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException(cartItemId));
        // check if the cart status is active and belongs to the same customer id
        if (cartItem.getCart().getStatus() != CartStatus.ACTIVE)
            throw new NoActiveCartLinkedException(cartItemId);
        if (!cartItem.getCart().getSession().getCustomer().getId().equals(customerId))
            throw new IllegalArgumentException("The given cart item is not associated with the customer");
        return cartItem;
    }

    @Override
    public CartItem updateCartItem(Long customerId, Long cartItemId, Integer incrementVal) {
        CartItem cartItem = getCartItem(customerId, cartItemId);
        // check if the sufficient inventory is available
        ProductInventory productInventory = this.productInventoryRepo.findByProductId(cartItem.getProduct().getId())
                .orElseThrow(() -> new ProductNotInInventoryException(cartItem.getProduct().getId()));
        if (productInventory.getQuantity() < cartItem.getQuantity() + incrementVal)
            throw new InsufficientInventoryException(cartItem.getProduct().getId());
        // update the cart item
        if (cartItem.getQuantity() + incrementVal <= 0) {  // remove the item from the cart
            this.deleteCartItem(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + incrementVal);
            this.cartItemRepo.save(cartItem);
        }
        return cartItem;
    }

    @Override
    public Cart getCart(Long customerId) {
        this.customerRepo.findById(customerId)
                .orElseThrow(() -> new UserNotFoundException(customerId));
        CustomerSession customerSession = this.customerSessionRepo.findByCustomerIdAndStatus(customerId, UserSessionStatus.ACTIVE)
                .orElseThrow(() -> new NoActiveUserSessionException(customerId));
        return this.cartRepo.findBySessionIdAndStatus(customerSession.getId(), CartStatus.ACTIVE)
                .orElseThrow(() -> new NoActiveCartException(customerId));
    }

    @Override
    public Order checkoutCart(Long customerId, Long deliveryAddressId) {
        Cart cart = this.getCart(customerId);
        this.addressRepo.findById(deliveryAddressId)
                .orElseThrow(() -> new AddressNotFoundException(deliveryAddressId));
        Long isDeliveryAddressLinked = this.customerRepo.isAddressAssociatedWithCustomer(deliveryAddressId, customerId);
        if (isDeliveryAddressLinked != 1)
            throw new AddressNotLinkedException(deliveryAddressId, customerId);
        // forward the request to the OrderService
        List<Long> cartItemIds = cart.getCartItems().stream().map(BaseModel::getId).toList();
        Order order = this.orderService.createOrder(customerId, cartItemIds, deliveryAddressId);
        // mark the cart as CHECKED_OUT and terminate the customer session
        cart.setStatus(CartStatus.CHECKED_OUT);
        cart.getSession().setStatus(UserSessionStatus.ENDED);
        this.customerSessionRepo.save(cart.getSession());
        this.cartRepo.save(cart);
        return order;
    }
}
