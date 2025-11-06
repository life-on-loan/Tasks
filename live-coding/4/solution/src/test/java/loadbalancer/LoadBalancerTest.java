package loadbalancer;

import com.nurlan.loadbalancer.LoadBalancer;
import com.nurlan.loadbalancer.strategy.LoadBalancerStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

/*

 */
@ExtendWith(MockitoExtension.class)
public class LoadBalancerTest {

    @Mock
    LoadBalancerStrategy strategy;

    @Test
    void registerAddress_withNullValue_throwException() {
        //given
        LoadBalancer loadBalancer = new LoadBalancer();
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> loadBalancer.register(null));
    }

    @Test
    void registerAddresses_successful() {
        //given
        LoadBalancer loadBalancer = new LoadBalancer();
        //when
        for (int i = 0; i < 10; i++) {
            loadBalancer.register(URI.create("https://nurlan" + i));
        }
        //then
        assertEquals(10, loadBalancer.getSize());
    }

    @Test
    void registerAddresses_moreThanTenAddresses_throwException() {
        //given
        LoadBalancer loadBalancer = new LoadBalancer();

        //when
        for (int i = 0; i < 10; i++) {
            loadBalancer.register(URI.create("https://nurlan" + i));
        }

        //then
        assertThrows(IllegalStateException.class, () -> loadBalancer.register(URI.create("https://nurlan0")));
    }

    @Test
    void registerAddresses_addingDuplicatedAddress_skip() {
        //given
        LoadBalancer loadBalancer = new LoadBalancer();
        //when
        for (int i = 0; i < 2; i++) {
            loadBalancer.register(URI.create("https://nurlan" + i));
        }
        loadBalancer.register(URI.create("https://nurlan0"));

        //then
        assertEquals(2, loadBalancer.getSize());
    }

    @Test
    void gettingRandomAddress_successful() {
        //given
        LoadBalancer loadBalancer = new LoadBalancer(strategy);
        loadBalancer.register(URI.create("https://nurlan"));
        loadBalancer.register(URI.create("https://nurlanqwe"));

        //when
        Mockito.when(strategy.get(any())).thenReturn(URI.create("https://nurlan"));

        //then
        assertEquals(loadBalancer.get(), URI.create("https://nurlan"));
        Mockito.verify(strategy.get(any()));
    }

}
