package tasks

data class Item(val name: String, val price: Double, val quantity: Int)

class ShoppingCart {
    private val items = mutableListOf<Item>()
    private var appliedPromo: String? = null

    fun addItem(item: Item) {
        if (item.price <= 0) throw IllegalArgumentException("Price must be positive")
        items.add(item)
    }

    fun applyPromo(code: String) {
        if (appliedPromo != null) throw IllegalStateException("Promo already applied")
        appliedPromo = code
    }

    fun total(): Double {
        var sum = items.sumOf { it.price * it.quantity }
        when (appliedPromo) {
            "SAVE10" -> sum *= 0.9
            "HALFOFF" -> sum *= 0.5
        }
        return sum
    }
}