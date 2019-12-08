//package com.foodme.application.infrastructure;
//
//public class NetSqlCartRepositoryTest {
//    private static final String ConnectionString = "Data Source=127.0.0.1,1433;Initial Catalog=FoodMeDev;User ID=SA;Password=StrongPassword1;";
//    private User user;
//    private Product shampoo = new Product(ProductId.new(),"shampoo",12.32M);
//    private Product soap = new Product(ProductId.new(),"soap",3.42M);
//    private ICartRepository cartRepository;
//    private IOrderRepository orderRepository;
//    private IProductsReadModel productsReadModel;
//    private IOrderReadModel orderReadModel;
//    private DomainEventPubSub domainEventPubSub;
//
//    public void dataBaseFixtureOneTimeSetUp() throws Exception {
//        Migrator.migrateDb(ConnectionString);
//    }
//
//    public void setup() throws Exception {
//        deleteTables("carts", "cart_items");
//        user = new User(UserId.new ());
//        domainEventPubSub = new DomainEventPubSub();
//        cartRepository = new SqlCartRepository(ConnectionString, domainEventPubSub);
//        orderRepository = new SqlOrderRepository(ConnectionString, domainEventPubSub);
//        productsReadModel = new InMemoryProductsReadModel(domainEventPubSub);
//        orderReadModel = new InMemoryOrderReadModel();
//    }
//
//    public void tearDown() throws Exception {
//        domainEventPubSub.close();
//    }
//
//    protected void deleteTables(String... tables) throws Exception {
//        /* [UNSUPPORTED] 'var' as type is unsupported "var" */
//        sqlConnection = new SqlConnection(ConnectionString);
//        try {
//            {
//                sqlConnection.Open();
//                tables.AsList().ForEach(/* [UNSUPPORTED] to translate lambda expressions we need an explicit delegate type, try adding a cast "(table) => {
//                    return sqlConnection.Execute("DELETE from {table}");
//                }" */);
//            }
//        } finally {
//            if (sqlConnection != null)
//                Disposable.mkDisposable(sqlConnection).dispose();
//
//        }
//    }
//
//    private FoodMe.ReadModel.Product mostSeenProduct(Product product, int quantity) throws Exception {
//        return new FoodMe.ReadModel.Product(product.getId(), product.getName(), product.getPrice(), quantity);
//    }
//
//    private FoodMe.ReadModel.Order readModelOrderFrom(Order order) throws Exception {
//        throw new UnsupportedOperationException();
//    }
//
//    public async Task = new async();
//
//    StoreAndLoadCart() throws Exception {
//        /* [UNSUPPORTED] 'var' as type is unsupported "var" */
//        cart = Cart.CreateEmptyFor(user);
//        cart.AddProduct(shampoo, 2);
//        cart.AddProduct(soap, 1);
//        await;
//        cartRepository.SaveAsync(cart);
//        var;
//        loadedCart = await;
//        cartRepository.LoadAsync(cart.Id);
//        Assert.That(cart.Items, Is.EqualTo(loadedCart.Items));
//        Assert.That(cart.NextAggregateVersion, Is.EqualTo(loadedCart.NextAggregateVersion));
//        Assert.That(cart.Id, Is.EqualTo(loadedCart.Id));
//    }
//
//}
//
//
