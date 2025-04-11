package com.example.grocerymanagement.constants;


public class AppConstant {

//    Cloudinary key
    public static final String CLOUD_NAME = "dx9imbssh";
    public static final String CLOUD_API_KEY = "262813512613356";
    public static final String CLOUD_SECRET = "2uEQHVpA1Ct3ns42H2cXwopLX78";
    public static final String CLOUD_ENVIRONMENT_VARIABLE = "CLOUDINARY_URL=cloudinary://262813512613356:2uEQHVpA1Ct3ns42H2cXwopLX78@dx9imbssh";

//    host name
//    public static final String BASE_URL = "http://192.168.1.53/";
    public static final String BASE_URL = "http://192.168.1.41/";

//  Collection
    public static final String PRODUCT = "products";
    public static final String CATEGORIES = "Categories";
    public static final String SALES = "Sales";
    public static final String CLIENTS = "Clients";
    public static final String STAFFS = "Staffs";
    public static final String ORDERS = "Orders";
    public static final String USERS = "Users";

/*   -------------------------- Fields---------------------------- */

//    Product
    public static final String PROD_NAME = "name";
    public static final String PROD_PRICE = "price";
    public static final String PROD_DESCRIPTION = "description";
    public static final String PROD_QUANTITY = "quantity";
    public static final String PROD_CATEGORY = "category";
    public static final String PROD_BARCODE = "barCode";
    public static final String PROD_WEIGHT = "weight";
    public static final String PROD_IMAGE = "image";
    public static final String PROD_SALE = "sale";

//    Category
    public static final String CATE_NAME = "categoryName";
    public static final String CATE_IS_ENABLED = "isEnable";

//    Sale
    public static final String SALE_NAME = "saleName";
    public static final String SALE_STATUS = "status";
    public static final String SALE_VALUE = "saleValue";
    public static final String SALE_START_DATE = "saleStartDate";
    public static final String SALE_END_DATE = "saleEndDate";
    public static final String SALE_BAR_CODE = "barCodeSale";

//    Client
    public static final String CLIENT_NAME = "customerName";
    public static final String CLIENT_ADDRESS = "address";
    public static final String CLIENT_EMAIL = "email";
    public static final String CLIENT_ENABLED = "enabled";
    public static final String CLIENT_PHONE_NUMBER = "phoneNumber";

//    Staff
    public static final String STAFF_NAME = "staffName";
    public static final String STAFF_CODE_NUMBER= "staffCodeNumber";
    public static final String STAFF_ACCOUNT = "staffAccount";
    public static final String STAFF_ENABLE = "enable";
    public static final String STAFF_POSITION = "staffPosition";

//    Order
    public static final String ORDER_LIST_PRDD = "listProd";
    public static final String ORDER_CUSTOMER_NAME = "customerName";
    public static final String ORDER_STATUS = "enable";
    public static final String ORDER_CUSTOMER_NUMBER_PHONE = "phoneNumber";

//    User
    public static final String USER_ROLE = "role";
    public static final String USER_FULL_NAME = "fullName";
    public static final String USER_PHONE_NUMBER = "phoneNumber";
    public static final String USER_PASSWORD = "password";
    public static final String USER_EMAIL = "email";

//    TestImage
    public static final String TEST_IMAGE = "testImage";
    public static final String TEST_IMAGE_URL = "testImageUrl";
    public static final String TEST_IMAGE_NAME = "testImageName";
    public static final String TEST_IMAGE_DATE = "testImageDate";
    public static final String TEST_IMAGE_ID = "testImageId";


}
