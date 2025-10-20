package edu.trincoll.patterns.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ShippingData Tests")
class ShippingDataTest {

    @Test
    @DisplayName("Should create shipping data with all fields")
    void shouldCreateShippingDataWithAllFields() {
        var data = new ShippingData("International", 5.0, 100.0, "Express");

        assertThat(data.destination()).isEqualTo("International");
        assertThat(data.weight()).isEqualTo(5.0);
        assertThat(data.distance()).isEqualTo(100.0);
        assertThat(data.serviceType()).isEqualTo("Express");
    }

    @Test
    @DisplayName("Should create domestic shipping data with convenience constructor")
    void shouldCreateDomesticShippingData() {
        var data = new ShippingData(5.0, 100.0, "Standard");

        assertThat(data.destination()).isEqualTo("Domestic");
        assertThat(data.weight()).isEqualTo(5.0);
        assertThat(data.distance()).isEqualTo(100.0);
        assertThat(data.serviceType()).isEqualTo("Standard");
    }

    @Test
    @DisplayName("Should reject negative weight")
    void shouldRejectNegativeWeight() {
        assertThatThrownBy(() -> new ShippingData(-1.0, 100.0, "Standard"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Weight must be positive");
    }

    @Test
    @DisplayName("Should reject zero weight")
    void shouldRejectZeroWeight() {
        assertThatThrownBy(() -> new ShippingData(0.0, 100.0, "Standard"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Weight must be positive");
    }

    @Test
    @DisplayName("Should reject negative distance")
    void shouldRejectNegativeDistance() {
        assertThatThrownBy(() -> new ShippingData(5.0, -10.0, "Standard"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Distance cannot be negative");
    }

    @Test
    @DisplayName("Should allow zero distance")
    void shouldAllowZeroDistance() {
        var data = new ShippingData(5.0, 0.0, "Local");

        assertThat(data.distance()).isEqualTo(0.0);
    }
}
