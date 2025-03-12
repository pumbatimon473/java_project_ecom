> NOTE:
- Followed convention of UML diagram in order to indicate the access specifiers for the fields:
    - private : -
    - protected: #
    - public: +
    - default: ~
- Used lombok annotations for getters and setters for the fields (@Getter, @Setter)
- Used JPA annotations to indicate the relationship between entities.
---

## ECommerce: Class Diagram - v1
`initial design - 12th Mar' 2025`


### Entities
1. Product
2. ProductCategory
3. Cart
4. Order
5. Payment
6. Invoice
7. ProductImage
8. Customer
9. CartItem
10. OrderItem
11. Seller
12. CustomerSession
13. ProductInventory


### Embeddables
1. PhoneNumber


### Enums
1. CartStatus
2. OrderStatus
3. PaymentStatus
4. UserType
5. UserSessionStatus
6. Country
7. BharatState


### Related Interfaces
1. State

---
### Enums
```
enum CartStatus {
    ACTIVE,
    DISPOSED,
    CHECKED_OUT
}

enum OrderStatus {
    PLACED,
    SHIPPED,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}

enum PaymentStatus {
    SUCCESS,
    FAILURE
}

enum UserType {
    CUSTOMER,
    SELLER,
    ADMIN
}

enum UserSessionStatus {
    ACTIVE,
    ENDED
}

enum Country {
    BHARAT(BharatState.class);
}


interface State {
    behaviors:
        + getStateName(): String
}

enum BharatState implements State {
    JHARKHAND("Jharkhand"),
    UTTAR_PRADESH("Uttar Pradesh"),
    ASSAM("Assam"),
    NAGALAND("Nagaland");
    
    attributes:
        - stateName: String
}
```
---

### Classes:
```
@Getter
@Setter
abstract class BaseModel {
    attributes:
        - id: Long
}

@Getter
@Setter
class Product extends BaseModel {
    attributes:
        - name: String

        @ManyToOne
        @JoinColumn(name = "product_category_id")
        - category: ProductCategory
        
        - description: String
        - price: BigDecimal  // Not using Double due to inherent precision issues with floating-point arithmetic
        
        @OneToOne
        @JoinColumn(name = "product_image_id")
        - image: ProductImage

        @OneToMany(mappedBy = "product")
        - cartItems: List<CartItem>

        @OneToMany(mappedBy = "product")
        - orderItems: List<OrderItem>
        
        @OneToOne(mappedBy = "product")
        - inventory: ProductInventory

        @ManyToOne
        @JoinColumn(name = "seller_id")
        - seller: Seller
}


@Getter
@Setter
class ProductCategory extends BaseModel {
    attributes:
        - name: String
        - description: String

        @OneToMany(mappedBy = "productCategory")
        - product: Product
}


@Getter
@Setter
class ProductImage extends BaseModel {
    attributes:
        @OneToOne(mappedBy = "productImage")
        - product: Product

        @ElementCollection
        @CollectionTable(name = "product_images", joinColumn = @JoinColumn(name = "product_image_id"))
        @Column(name = "image_url")
        - imageUrls: List<String>
}


@Getter
@Setter
class Cart extends BaseModel {
    attributes:
        @OneToOne
        @JoinColumn(name = "customer_session_id")
        - session: CustomerSession

        @OneToMany(mappedBy = "cart")
        - cartItems: List<CartItem>
        
        @Enumerated(EnumType.STRING)
        - status: CartStatus
        
        @ManyToOne
        @JoinColumn(name = "customer_id")
        - customer: Customer
}


@Getter
@Setter
class CustomerSession extends BaseModel {
    attributes:
        @ManyToOne
        @JoinColumn(name = "customer_id")
        - customer: Customer

        @Enumerated(EnumType.STRING)
        - status: UserSessionStatus
        
        @OneToMany(mappedBy = "session")
        - carts: List<Cart>  // A customer session can have many carts but only one of them will be active
    
    behaviors:
        + isActive(): Boolean
}

@Getter
@Setter
class CartItem extends BaseModel {
    attributes:
        @ManyToOne
        @JoinColumn(name = "cart_id")
        - cart: Cart

        @ManyToOne
        @JoinColumn(name = "product_id")
        - product: Product
        
        - quantity: Integer
}

@Getter
@Setter
class Order extends BaseModel {
    attributes:
        @ManyToOne
        @JoinColumn(name = "customer_id")
        - customer: Customer

        @OneToMany(mappedBy = "order")
        - orderItems: List<OrderItem>
        
        @Enumerated(EnumType.STRING)
        - status: OrderStatus
        
        @ManyToOne
        @JoinColumn(name = "payment_id")
        - payment: Payment
        
        @OneToOne(mappedBy = "order")
        - invoice: invoice

        @ManyToOne
        @JoinColumn(name = "delivery_address_id")
        - deliveryAddress: Address
}

@Getter
@Setter
abstract class User extends BaseModel {
    attributes:
        - name: String
        - email: String
        - password: String

        @Embedded
        - phone: PhoneNumber
    
    behaviors:
        + abstract getUserType(): UserType
}


@Getter
@Setter
@Embeddable
class PhoneNumber {
    attributes:
        @Enumerated(EnumType.STRING)
        - country: Country

        - phoneNumber: String
}


@Getter
@Setter
class Customer extends User {
    attributes:
        @OneToMany(mappedBy = "user")
        - addresses: List<Address>

        @OneToMany(mappedBy = "customer")
        - orders: List<Order>
        
        @OneToMany(mappedBy = "customer")
        - invoices: List<Invoice>
        
        @OneToMany(mappedBy = "customer")
        - sessions: List<CustomerSession>
}

@Getter
@Setter
class Address extends BaseModel {
    attributes:
        @ManyToOne
        @JoinColumn(name = "user_id")
        - user: User

        - addressLine1: String
        - addressLine2: String
        - landmark: String
        - pinCode: String
        - city: String
        - state: String  // Note: State is an interface; Conversion handled in app logic
        
        @Enumerated(EnumType.STRING)
        - country: Country

        @OneToMany(mappedBy = "deliveryAddress")
        - orders: List<Order>
}


@Getter
@Setter
class OrderItem extends BaseModel {
    attributes:
        @ManyToOne
        @JoinColumn(name = "order_id")
        - order: Order

        @ManyToOne
        @JoinColumn(name = "product_id")
        - product: Product
        
        - quantity: Integer

        @ManyToOne
        @JoinColumn(name = "invoice_id")
        - invoice: Invoice
}

@Getter
@Setter
class Payment extends BaseModel {
    attributes:
        @OneToMany(mappedBy = "payment")
        - orders: List<Order>

        - amount: BigDecimal  // Not using Double for monetary value due to inherent precision issues with floating point arithmetic
        
        @Enumerated(EnumType.STRING)
        - status: PaymentStatus
        
        - transactionId: String

        @OneToMany(mappedBy = "payment")
        - invoices: List<Invoice>
}


@Getter
@Setter
class Invoice extends BaseModel {
    attributes:
        @OneToOne
        @JoinColumn(name = "order_id")
        - order: Order

        @OneToMany(mappedBy = "invoice")
        - orderItems: List<OrderItem>

        @ManyToOne
        @JoinColumn(name = "payment_id")
        - payment: Payment

        @ManyToOne
        @JoinColumn(name = "seller_id")
        - seller: Seller
        
        @ManyToOne
        @JoinColumn(name = "customer_id")
        - customer: Customer
}


@Getter
@Setter
class Seller extends User {
    attributes:
        - panNumber: String
        - gstRegNumber: String

        @OneToOne
        @JoinColumn(name = "address_id")
        - address: Address
        
        @OneToMany(mappedBy = "seller")
        - invoices: List<Invoice>

        @OneToMany(mappedBy = "seller")
        - products: List<Product>
}

@Getter
@Setter
class ProductInventory extends BaseModel {
    attributes:
        @OneToOne
        @JoinColumn(name = "product_id")
        - product: Product

        - quantity: Integer
}

```
