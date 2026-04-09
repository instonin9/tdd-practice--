package tasks

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ShoppingCartTest {

    private val cart = ShoppingCart()

    @Test
    fun `empty cart returns zero total`() {
        assertThat(cart.total()).isEqualTo(0.0)
    }

    @Test
    fun `adding single item calculates correct total`() {
        cart.addItem(Item("Book", 15.0, 2))
        assertThat(cart.total()).isEqualTo(30.0)
    }

    @Test
    fun `adding item actually appends to the end of collection`() {
        val item1 = Item("Apple", 1.0, 1)
        val item2 = Item("Banana", 2.0, 1)
        cart.addItem(item1)
        cart.addItem(item2)
        // Проверка через сумму, что оба учтены
        assertThat(cart.total()).isEqualTo(3.0)
    }

    @Test
    fun `multiple items with different quantities calculate correct total`() {
        cart.addItem(Item("Pen", 5.0, 3))
        cart.addItem(Item("Notebook", 20.0, 1))
        assertThat(cart.total()).isEqualTo(35.0)
    }

    @Test
    fun `promo code SAVE10 applies ten percent discount`() {
        cart.addItem(Item("Laptop", 100.0, 1))
        cart.applyPromo("SAVE10")
        assertThat(cart.total()).isEqualTo(90.0)
    }

    @Test
    fun `promo code HALFOFF applies fifty percent discount`() {
        cart.addItem(Item("Laptop", 100.0, 1))
        cart.applyPromo("HALFOFF")
        assertThat(cart.total()).isEqualTo(50.0)
    }

    @Test
    fun `applying second promo code throws exception`() {
        cart.addItem(Item("Item", 10.0, 1))
        cart.applyPromo("SAVE10")

        assertThrows<IllegalStateException> {
            cart.applyPromo("HALFOFF")
        }
    }

    @Test
    fun `adding item with negative price throws exception`() {
        assertThrows<IllegalArgumentException> {
            cart.addItem(Item("Broken", -5.0, 1))
        }
    }

    @Test
    fun `adding item with zero price throws exception`() {
        assertThrows<IllegalArgumentException> {
            cart.addItem(Item("Free", 0.0, 1))
        }
    }
}