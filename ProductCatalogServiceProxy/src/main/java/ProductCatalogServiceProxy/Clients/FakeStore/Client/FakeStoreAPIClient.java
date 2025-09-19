package ProductCatalogServiceProxy.Clients.FakeStore.Client;

import ProductCatalogServiceProxy.Clients.FakeStore.DTO.FakeStoreProductDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

//All the internal http connections to fakestore api client has been added in this class.
//This will separate the logic from service layer so that service layer calls this APIClient
//to connect with the api and get the appropriate response
@Component
public class FakeStoreAPIClient {
    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreAPIClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public FakeStoreProductDTO [] getProducts()
    {
        RestTemplate restTemplate=restTemplateBuilder.build();
        FakeStoreProductDTO[] fakeStoreProductDTOS=restTemplate.getForEntity("https://fakestoreapi.com/products",FakeStoreProductDTO[].class).getBody();
        return fakeStoreProductDTOS;
    }
    public FakeStoreProductDTO getProduct(Long productId)
    {
        RestTemplate restTemplate=restTemplateBuilder.build();
        FakeStoreProductDTO fakeStoreProductDTO=restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDTO.class,productId).getBody();
        return fakeStoreProductDTO;
    }

    public FakeStoreProductDTO createProduct(FakeStoreProductDTO fakeStoreProductDTO)
    {
        RestTemplate restTemplate=restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDTO> fakeStoreProductDTOResponseEntity=restTemplate.postForEntity("https://fakestoreapi.com/products",fakeStoreProductDTO, FakeStoreProductDTO.class);
        return fakeStoreProductDTOResponseEntity.getBody();
    }

    /*
     For Update we do not have the PatchForEntity inbuilt method, So thats why we wrote a custom
     Method requestForEntity based on existing methods of restTemplateBuilder.We will be sending
     Http request(Patch),DTO & id.
     */
    public FakeStoreProductDTO updateProduct(Long id,FakeStoreProductDTO fakeStoreProductDTORequest)
    {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDTO> fakeStoreProductDTOResponseEntity = requestForEntity(HttpMethod.PATCH,"https://fakestoreapi.com/products/{id}",fakeStoreProductDTORequest, FakeStoreProductDTO.class,id);
        return fakeStoreProductDTOResponseEntity.getBody();
    }

    public <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod, String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate=restTemplateBuilder.requestFactory(HttpComponentsClientHttpRequestFactory.class).build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }
}
