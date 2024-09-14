import java.math.BigDecimal

fun main() {
    val balance = Balance()
    val exchanger = Exchanger()

    println("Команды")
    println("exchange N ВАЛЮТА1 ВАЛЮТА2 - обменять N ВАЛЮТА1 на ВАЛЮТА2")
    println("info - получить курсы валют")
    println("balance - получить баланс")
    println("exit или Ctrl-D - закрыть")

    while (true) {
        val input = readlnOrNull() ?: return
        val spl = input.trim().split(" ")
        if (input == "" || spl.isEmpty()) {
            continue
        }

        val cmd = spl[0]
        when (cmd) {
            "info" -> {
                if (spl.size != 1) {
                    println("Неверно введена комманда")
                    continue
                }

                var out = "Курсы валют\n"
                for (key in exchanger.getExchangeableCurrencies()) {
                    // Kotlin will use StringBuilder here
                    out += key.first
                    out += " / "
                    out += key.second
                    out += ": "
                    out += exchanger.getCurrencyCourse(key)
                    out += "\n"
                }
                println(out)
            }

            "balance" -> {
                if (spl.size != 1) {
                    println("Неверно введена комманда")
                    continue
                }
                var out = "Ваш баланс\n"
                for (key in balance.getAvailableCurrencies()) {
                    // Kotlin will use StringBuilder here
                    out += balance.getCurrencyBalance(key)
                    out += " "
                    out += key
                    out += "\n"
                }
                println(out)
            }

            "exchange" -> {
                if (spl.size != 4) {
                    println("Неверно введена комманда")
                    continue
                }
                val what = spl[2]
                val to = spl[3]
                val many = try {
                    BigDecimal(spl[1])
                } catch (_: NumberFormatException) {
                    println("Неверно записано количество денег")
                    continue
                }
                try {
                    exchanger.exchange(balance, what, to, many)
                } catch (_: NotEnoughCurrencyOnBalance) {
                    println("Не хватает денег для обмена")
                    continue
                } catch (_: CurrencyNotExistsException) {
                    println("Такой валюты не существует!")
                    continue
                } catch (_: InvalidCurrencyCount) {
                    println("Неверно записано количество денег")
                    continue
                } catch (_: TooPreciseValue) {
                    println("Номинал меньше возможного")
                    continue
                } catch (_ : NoSuchExchange) {
                    println("Такого обмена не существует")
                    continue
                }
                println("Операция выполнена успешно")
            }

            "exit" -> break
            else -> println("Неверно введена комманда")
        }
    }
}