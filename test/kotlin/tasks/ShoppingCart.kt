package tasks

data class Item(val name: String, val price: Double, val quantity: Int) {
    val totalPrice: Double get() = price * quantity
}

class ShoppingCart {
    private val items = mutableListOf<Item>()
    private var appliedPromo: PromoCode? = null

    private enum class PromoCode(val multiplier: Double) {
        SAVE10(0.9),
        HALFOFF(0.5)
    }

    fun addItem(item: Item) {
        require(item.price > 0) { "ПРайс" }
        items.add(item)
    }

    fun applyPromo(code: String) {
        require(appliedPromo == null) { "ПРОмо коды" }
        appliedPromo = PromoCode.valueOf(code)
    }

    fun total(): Double {
        val subtotal = items.sumOf { it.totalPrice }
        return appliedPromo?.let { subtotal * it.multiplier } ?: subtotal
    }
}