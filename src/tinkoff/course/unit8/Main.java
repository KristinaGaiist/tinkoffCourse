package unit8;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import unit8.repository.HibernateBrandRepository;
import unit8.repository.HibernateCarRepository;
import unit8.repository.HibernateCityRepository;
import unit8.repository.HibernateCustomerRepository;
import unit8.repository.HibernateModelRepository;
import unit8.service.BrandService;
import unit8.service.CarService;
import unit8.service.CityService;
import unit8.service.CustomerService;
import unit8.service.ModelService;

public class Main {

    private final static SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
    private final static BrandService BRAND_SERVICE = new BrandService(new HibernateBrandRepository(SESSION_FACTORY));
    private final static CityService CITY_SERVICE = new CityService(new HibernateCityRepository(SESSION_FACTORY));
    private final static ModelService MODEL_SERVICE = new ModelService(new HibernateModelRepository(SESSION_FACTORY));
    private static final HibernateCarRepository CAR_REPOSITORY = new HibernateCarRepository(SESSION_FACTORY);
    private final static CarService CAR_SERVICE = new CarService(CAR_REPOSITORY);
    private final static CustomerService CUSTOMER_SERVICE =
        new CustomerService(new HibernateCustomerRepository(SESSION_FACTORY), CAR_REPOSITORY);

    public static void main(String... args) {

        brand();
        city();
        model();
        car();
        customer();

        CUSTOMER_SERVICE.addCustomerCar(51, 52);
        System.out.println(CUSTOMER_SERVICE.getCustomersByCarBrand("BMW"));
        System.out.println(CUSTOMER_SERVICE.getCustomerById(51).getCars());
        CUSTOMER_SERVICE.deleteCustomerCar(51, CAR_SERVICE.getCar("n675jd"));
        System.out.println(CUSTOMER_SERVICE.getCustomerById(51).getCars());
    }

    private static void brand() {
        BRAND_SERVICE.createBrand("BMW");
        BRAND_SERVICE.createBrand("Mercedes");
        BRAND_SERVICE.createBrand("Audi");
        BRAND_SERVICE.createBrand("Porsche");
        BRAND_SERVICE.createBrand("Toyota");
        BRAND_SERVICE.createBrand("Opel");

        System.out.println(BRAND_SERVICE.getBrandById(BRAND_SERVICE.getBrandByName("BMW").getId()));
        System.out.println(BRAND_SERVICE.getBrandByName("BMW"));
        BRAND_SERVICE.renameBrand("Opel", "Reno");
        BRAND_SERVICE.deleteBrand("Reno");
        System.out.println(BRAND_SERVICE.getAllBrands());
    }

    public static void city() {
        CITY_SERVICE.createCity("Moscow");
        CITY_SERVICE.createCity("Novosibirsk");
        CITY_SERVICE.createCity("Sochi");
        CITY_SERVICE.createCity("Krasnoyarsk");
        CITY_SERVICE.createCity("Ekaterenburg");
        CITY_SERVICE.createCity("Omsk");

        System.out.println(CITY_SERVICE.getCityById(CITY_SERVICE.getCityByName("Moscow").getId()));
        System.out.println(CITY_SERVICE.getCityByName("Moscow"));
        CITY_SERVICE.renameCity("Omsk", "Tomsk");
        CITY_SERVICE.deleteCity("Tomsk");
        System.out.println(CITY_SERVICE.getAllCities());
    }

    public static void model() {
        MODEL_SERVICE.createModel("X5", BRAND_SERVICE.getBrandByName("BMW"));
        MODEL_SERVICE.createModel("X7", BRAND_SERVICE.getBrandByName("BMW"));
        MODEL_SERVICE.createModel("i8", BRAND_SERVICE.getBrandByName("BMW"));
        MODEL_SERVICE.createModel("GLA", BRAND_SERVICE.getBrandByName("Mercedes"));
        MODEL_SERVICE.createModel("TT", BRAND_SERVICE.getBrandByName("Audi"));
        MODEL_SERVICE.createModel("Panamera", BRAND_SERVICE.getBrandByName("Porsche"));
        MODEL_SERVICE.createModel("Camry", BRAND_SERVICE.getBrandByName("Toyota"));
        MODEL_SERVICE.createModel("Corolla", BRAND_SERVICE.getBrandByName("Toyota"));

        System.out.println(MODEL_SERVICE.getModelById(MODEL_SERVICE.getModel("X5").getId()));
        System.out.println(MODEL_SERVICE.getModel("X5"));
        MODEL_SERVICE.updateModel("Corolla", "Civic");
        MODEL_SERVICE.deleteModel("Civic");
        System.out.println(MODEL_SERVICE.getAllModels());
    }

    public static void car() {
        CAR_SERVICE.createCar("f657ju", MODEL_SERVICE.getModel("X5"));
        CAR_SERVICE.createCar("n675jd", MODEL_SERVICE.getModel("X7"));
        CAR_SERVICE.createCar("p093bv", MODEL_SERVICE.getModel("TT"));
        CAR_SERVICE.createCar("z327gb", MODEL_SERVICE.getModel("GLA"));
        CAR_SERVICE.createCar("s470jn", MODEL_SERVICE.getModel("Panamera"));
        CAR_SERVICE.createCar("a128fh", MODEL_SERVICE.getModel("Camry"));
        CAR_SERVICE.createCar("j686kv", MODEL_SERVICE.getModel("i8"));
        CAR_SERVICE.createCar("o735db", MODEL_SERVICE.getModel("Camry"));
        CAR_SERVICE.createCar("k564nb", MODEL_SERVICE.getModel("Camry"));

        System.out.println(CAR_SERVICE.getCarById(CAR_SERVICE.getCar("f657ju").getId()));
        System.out.println(CAR_SERVICE.getCar("s470jn"));
        CAR_SERVICE.updateStateNumber("k564nb", "k753nh");
        CAR_SERVICE.deleteByStateNumber("k753nh");
        System.out.println(CAR_SERVICE.getAllCars());
    }

    private static void customer() {
        CUSTOMER_SERVICE.createCustomer("Kristina", "Kirinyuk", "Viktorovna", CITY_SERVICE.getCityByName("Moscow"));
        CUSTOMER_SERVICE.createCustomer("Aleksey", "Kirinyuk", "Igorevich", CITY_SERVICE.getCityByName("Moscow"));
        CUSTOMER_SERVICE.createCustomer("Anna", "Frolova", "Dmitrievna", CITY_SERVICE.getCityByName("Novosibirsk"));
        CUSTOMER_SERVICE.createCustomer("Michail", "Korenev", "Alekseevich", CITY_SERVICE.getCityByName("Sochi"));
        CUSTOMER_SERVICE.createCustomer("Iliya", "Shagdonov", "Vladislavovich",
                                        CITY_SERVICE.getCityByName("Krasnoyarsk"));
        CUSTOMER_SERVICE.createCustomer("Dianya", "Petrosyan", "Samvelovna", CITY_SERVICE.getCityByName("Krasnoyarsk"));
        CUSTOMER_SERVICE.createCustomer("Vladislav", "Petrosyan", "Maksimovich", CITY_SERVICE.getCityByName("Moscow"));
        CUSTOMER_SERVICE.createCustomer("Olga", "Belskaya", "Vladimirovna", CITY_SERVICE.getCityByName("Ekaterenburg"));
        CUSTOMER_SERVICE.createCustomer("Egor", "Kogdin", "Timofeevich", CITY_SERVICE.getCityByName("Ekaterenburg"));
        CUSTOMER_SERVICE.createCustomer("Margarita", "Borisova", "Aleksandrovna",
                                        CITY_SERVICE.getCityByName("Krasnoyarsk"));
        CUSTOMER_SERVICE.createCustomer("Anastasia", "Parneva", "Evgenevna", CITY_SERVICE.getCityByName("Sochi"));
        CUSTOMER_SERVICE.createCustomer("Tatyana", "Popova", "Petrovna", CITY_SERVICE.getCityByName("Novosibirsk"));

        System.out.println(CUSTOMER_SERVICE.getAllCustomers());
        CUSTOMER_SERVICE.updateFirstName(62, "Tanya");
        System.out.println(CUSTOMER_SERVICE.getCustomerById(62));
        CUSTOMER_SERVICE.deleteById(62);
        System.out.println(CUSTOMER_SERVICE.getAllCustomers());
    }
}
