import java.math.BigDecimal
import java.math.RoundingMode
import java.security.SecureRandom

sealed class ExchangerException(message: String, cause: Throwable? = null) :
    Exception(message, cause)

class NotEnoughCurrencyOnBalance(currency: String) :
    ExchangerException("User don't have enough $currency on balance")

class NoSuchExchange(from: String, to: String) :
    ExchangerException("No such exchange from $from to $to")

class Exchanger {
    private val sr = SecureRandom()
    private val currencies = mutableMapOf(
        Pair("RUB", "USD") to BigDecimal("90").setScale(2, RoundingMode.DOWN),
        Pair("RUB", "EUR") to BigDecimal("90").setScale(2, RoundingMode.DOWN),
        Pair("USD", "EUR") to BigDecimal("90").setScale(2, RoundingMode.DOWN),
        Pair("USD", "USDT") to BigDecimal("90").setScale(2, RoundingMode.DOWN),
        Pair("USD", "BTC") to BigDecimal("90").setScale(8, RoundingMode.DOWN),
    )

    fun getCurrencyCourse(key: Pair<String, String>): BigDecimal? {
        return currencies[key]
    }

    fun getExchangeableCurrencies(): List<Pair<String, String>> {
        return currencies.keys.toList()
    }

    private fun randomise() {
        for ((key) in currencies) {
            val rnd = BigDecimal(sr.nextInt(95, 105)).divide(BigDecimal(100))
            // There is no multithreading, so that's OK
            if (key.second == "BTC") {
                currencies[key] = currencies[key]!!.multiply(rnd).setScale(8, RoundingMode.DOWN)
            } else {
                currencies[key] = currencies[key]!!.multiply(rnd).setScale(2, RoundingMode.DOWN)
            }
        }
    }

    private fun iExchange(balance: Balance, what: String, to: String, many: BigDecimal) {
        balance.withdraw(what, many)

        val rate = currencies[Pair(what, to)]
        if(rate != null) {
            balance.deposit(to, (many / rate))
        } else {
            balance.deposit(to, many * currencies[Pair(to, what)]!!)
        }
    }

    fun exchange(balance: Balance, what: String, to: String, manyUnpres: BigDecimal) {
        if (!balance.doesUserHaveCurrency(what) || !balance.doesUserHaveCurrency(to)) {
            throw CurrencyNotExistsException()
        }
        if(!(currencies.containsKey(Pair(what, to)) || currencies.containsKey(Pair(to, what)))) {
            throw NoSuchExchange(what, to)
        }
        if (manyUnpres <= BigDecimal.ZERO) {
            throw InvalidCurrencyCount()
        }
        if (balance.getCurrencyBalance(what)!! < manyUnpres) {
            throw NotEnoughCurrencyOnBalance(what)
        }
        val many = if(what == "BTC") {
            if(manyUnpres.scale() > 8) {
                throw TooPreciseValue()
            }
            manyUnpres.setScale(8, RoundingMode.DOWN)
        } else {
            if(manyUnpres.scale() > 2) {
                throw TooPreciseValue()
            }
            manyUnpres.setScale(2, RoundingMode.DOWN)
        }

        iExchange(balance, what, to, many)
        randomise()
    }
}