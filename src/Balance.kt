import java.math.BigDecimal
import java.math.RoundingMode

sealed class BalanceException(message: String, cause: Throwable? = null) :
    Exception(message, cause)

class NotEnoughMoneyException : BalanceException("Not enough money to withdraw")
class InvalidCurrencyCount : BalanceException("Invalid count of currency")

class Balance {
    private val balance = mutableMapOf(
        "RUB" to BigDecimal("10000").setScale(2, RoundingMode.DOWN),
        "USD" to BigDecimal("1000").setScale(2, RoundingMode.DOWN),
        "EUR" to BigDecimal("1000").setScale(2, RoundingMode.DOWN),
        "USDT" to BigDecimal("1000").setScale(2, RoundingMode.DOWN),
        "BTC" to BigDecimal("1.5").setScale(8, RoundingMode.DOWN)
    )

    fun doesUserHaveCurrency(key: String): Boolean {
        return balance.containsKey(key)
    }

    fun getAvailableCurrencies(): List<String> {
        return balance.keys.toList()
    }

    fun getCurrencyBalance(key: String): BigDecimal? {
        return balance[key]
    }

    private fun iWithdraw(currency: String, amount: BigDecimal) {
        if (currency == "BTC") {
            balance[currency] = (balance[currency]!! - amount).setScale(8, RoundingMode.DOWN)
        } else {
            balance[currency] = (balance[currency]!! - amount).setScale(2, RoundingMode.DOWN)
        }
    }

    fun withdraw(currency: String, amount: BigDecimal) {
        if (!balance.containsKey(currency)) {
            throw CurrencyNotExistsException()
        }
        if (balance[currency]!! < amount) {
            throw NotEnoughMoneyException()
        }
        iWithdraw(currency, amount)
    }

    private fun iDeposit(currency: String, amount: BigDecimal) {
        if (currency == "BTC") {
            balance[currency] = (balance[currency]!! + amount).setScale(8, RoundingMode.DOWN)
        } else {
            balance[currency] = (balance[currency]!! + amount).setScale(2, RoundingMode.DOWN)
        }
    }

    fun deposit(currency: String, amount: BigDecimal) {
        if (!balance.containsKey(currency)) {
            throw CurrencyNotExistsException()
        }
        iDeposit(currency, amount)
    }
}