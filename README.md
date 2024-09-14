# Обменник

Проверки:
- Неверные комманды
- Отсутствие средств на счёте
- Номинал меньше допустимого

https://youtu.be/IRHZ3cKp1DQ
Логи видео
```
Команды
exchange N ВАЛЮТА1 ВАЛЮТА2 - обменять N ВАЛЮТА1 на ВАЛЮТА2
info - получить курсы валют
balance - получить баланс
exit или Ctrl-D - закрыть
info
Курсы валют
RUB / USD: 90.00
RUB / EUR: 90.00
USD / EUR: 90.00
USD / USDT: 90.00
USD / BTC: 90.00000000

balance
Ваш баланс
10000.00 RUB
1000.00 USD
1000.00 EUR
1000.00 USDT
1.50000000 BTC

exchange 1 USDT RUB
Такого обмена не существует
exchange 1 USD RUB
Операция выполнена успешно
info
Курсы валют
RUB / USD: 90.90
RUB / EUR: 86.40
USD / EUR: 89.10
USD / USDT: 87.30
USD / BTC: 87.30000000

exchange 1 EUR RUB
Операция выполнена успешно
info
Курсы валют
RUB / USD: 90.90
RUB / EUR: 85.53
USD / EUR: 86.42
USD / USDT: 90.79
USD / BTC: 90.79200000

balance
Ваш баланс
10176.40 RUB
999.00 USD
999.00 EUR
1000.00 USDT
1.50000000 BTC

exchange 1 USDT USD
Операция выполнена успешно
balance
Ваш баланс
10176.40 RUB
1089.79 USD
999.00 EUR
999.00 USDT
1.50000000 BTC

exchange 1 BTC USD
Операция выполнена успешно
balance
Ваш баланс
10176.40 RUB
1180.58 USD
999.00 EUR
999.00 USDT
0.50000000 BTC

exchange 1 EUR USD
Операция выполнена успешно
exchange 1 EEWQ USD
Такой валюты не существует!
echchange 1 EUR USDT
Неверно введена комманда
exchage 1 EUR USDT
Неверно введена комманда
exchange 1 EUR USDT
Такого обмена не существует
exchange 1 EUR BTC
Такого обмена не существует
exchange 0.0001 BTC USD
Операция выполнена успешно
balance
Ваш баланс
10176.40 RUB
1262.67 USD
998.00 EUR
999.00 USDT
0.49990000 BTC

exchange 0.00001
Неверно введена комманда
exchange 0.00001 USD BTC
Номинал меньше возможного
exchange 0.01 USD RUB
Операция выполнена успешно


^D

Process finished with exit code 0

```