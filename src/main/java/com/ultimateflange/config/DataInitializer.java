package com.ultimateflange.config;

import com.ultimateflange.model.Order;
import com.ultimateflange.model.OrderStatus;
import com.ultimateflange.model.Product;
import com.ultimateflange.model.User;
import com.ultimateflange.repository.OrderRepository;
import com.ultimateflange.repository.ProductRepository;
import com.ultimateflange.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create demo users if not exists
        if (userRepository.count() == 0) {
            // Partner user
            User partner = new User();
            partner.setFirstName("Demo");
            partner.setLastName("Partner");
            partner.setEmail("partner@test.com");
            partner.setPassword(passwordEncoder.encode("password"));
            partner.setCompany("Demo Company");
            partner.setIndustry("Oil & Gas");
            partner.setUserType("partner");
            userRepository.save(partner);

            // Supplier user
            User supplier = new User();
            supplier.setFirstName("Demo");
            supplier.setLastName("Supplier");
            supplier.setEmail("supplier@test.com");
            supplier.setPassword(passwordEncoder.encode("password"));
            supplier.setCompany("Precision Flange Co.");
            supplier.setIndustry("Manufacturing");
            supplier.setUserType("supplier");
            userRepository.save(supplier);

            // Second supplier
            User supplier2 = new User();
            supplier2.setFirstName("Industrial");
            supplier2.setLastName("Flange");
            supplier2.setEmail("industrial@test.com");
            supplier2.setPassword(passwordEncoder.encode("password"));
            supplier2.setCompany("Industrial Flange Works");
            supplier2.setIndustry("Manufacturing");
            supplier2.setUserType("supplier");
            userRepository.save(supplier2);

            // Create products for suppliers
            createProductsForSupplier(supplier);
            createProductsForSupplier2(supplier2);
        }
    }

    private void createProductsForSupplier(User supplier) {
        // Long Weld Neck Flange
        Product lwn = new Product();
        lwn.setProductKey("lwn");
        lwn.setName("Long Weld Neck Flange");
        lwn.setDescription("ASME B16.5 Class 900 Long Weld Neck Flange for high-pressure applications");
        lwn.setPrice(12500.0);
        lwn.setStock(50);
        lwn.setUnit("pieces");
        lwn.setCategory("flanges");
        lwn.setMaterial("Carbon Steel, Stainless Steel");
        lwn.setSpecs("ASME B16.5, Class 900, DN15");
        lwn.setStandard("ASME B16.5");
        lwn.setDiameter("DN15 (1/2\")");
        lwn.setPressure("Class 900");
        lwn.setFeatures("ASME B16.5 compliant,Precision machined,Full material traceability,High-quality finish");
        lwn.setSupplier(supplier);
        productRepository.save(lwn);

        // Weld Neck Flange
        Product wnf = new Product();
        wnf.setProductKey("wnf");
        wnf.setName("Weld Neck Flange");
        wnf.setDescription("Standard weld neck flange for general piping applications");
        wnf.setPrice(8500.0);
        wnf.setStock(100);
        wnf.setUnit("pieces");
        wnf.setCategory("flanges");
        wnf.setMaterial("Carbon Steel, Stainless Steel");
        wnf.setSpecs("ASME B16.5, Class 150");
        wnf.setStandard("ASME B16.5");
        wnf.setDiameter("DN50 (2\")");
        wnf.setPressure("Class 150");
        wnf.setFeatures("ASME B16.5 compliant,Precision machined,Full material traceability");
        wnf.setSupplier(supplier);
        productRepository.save(wnf);
    }

    private void createProductsForSupplier2(User supplier) {
        // Slip-On Flange
        Product swf = new Product();
        swf.setProductKey("swf");
        swf.setName("Slip-On Flange");
        swf.setDescription("Slip-on flange for easy installation");
        swf.setPrice(6500.0);
        swf.setStock(200);
        swf.setUnit("pieces");
        swf.setCategory("flanges");
        swf.setMaterial("Carbon Steel, Stainless Steel");
        swf.setSpecs("ASME B16.5, Class 150");
        swf.setStandard("ASME B16.5");
        swf.setDiameter("DN50 (2\")");
        swf.setPressure("Class 150");
        swf.setFeatures("ASME B16.5 compliant,Easy installation,Cost-effective");
        swf.setSupplier(supplier);
        productRepository.save(swf);

        // Blind Flange
        Product blf = new Product();
        blf.setProductKey("blf");
        blf.setName("Blind Flange");
        blf.setDescription("Blind flange for closing pipe ends");
        blf.setPrice(5500.0);
        blf.setStock(75);
        blf.setUnit("pieces");
        blf.setCategory("flanges");
        blf.setMaterial("Carbon Steel, Stainless Steel");
        blf.setSpecs("ASME B16.5, Class 150");
        blf.setStandard("ASME B16.5");
        blf.setDiameter("DN50 (2\")");
        blf.setPressure("Class 150");
        blf.setFeatures("ASME B16.5 compliant,Pressure containment,Full material traceability");
        blf.setSupplier(supplier);
        productRepository.save(blf);
    }
}