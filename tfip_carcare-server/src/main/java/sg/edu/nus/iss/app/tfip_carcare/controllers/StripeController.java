package sg.edu.nus.iss.app.tfip_carcare.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.json.Json;
import sg.edu.nus.iss.app.tfip_carcare.models.Payment;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class StripeController {

    @Autowired
    @Qualifier("stripeAPIKey")
    String stripeApiKey;

    @PostMapping(path = "")
    public ResponseEntity<String> paymentAtCheckout(@RequestBody Payment payment) throws StripeException {

        // We initilize stripe object with the api key
        Stripe.apiKey = stripeApiKey;
        System.out.println("Stripe secret is "+ stripeApiKey);

        // We create a stripe session parameters
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)   //1.method
                .setMode(SessionCreateParams.Mode.PAYMENT)                          //2.mode
                .setSuccessUrl(payment.getSuccessUrl())                             //3.URL
                .setCancelUrl(payment.getCancelUrl())                               //4.URL
                
                .addLineItem(SessionCreateParams.LineItem.builder()             
                    .setQuantity(payment.getQuantity())                             //5.1.quantity    
                    .setPriceData(SessionCreateParams.LineItem.PriceData.builder()  
                           .setCurrency(payment.getCurrency())                      //5.2.1.currency
                           .setUnitAmount(payment.getAmount())                      //5.2.2. amount
                           .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(payment.getName()).build())                //5.2.3 Product data->name
                           .build())
                    .build())
                .build();
        // create a stripe session
        Session session = Session.create(params);
        // We get the sessionId you can get more info from the session object
        String jsonResult = Json.createObjectBuilder()
                                .add("id",session.getId()).build().toString();

        return ResponseEntity.ok().body(jsonResult);
    }

}
