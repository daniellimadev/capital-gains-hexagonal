# Code Challenge: Capital Gain in Hexagonal

<p align="center">
   <i>Code Challenge: Capital Gain</i> command line program (CLI) that calculates the tax to be paid on profits or losses from operations in the financial stock market.
</p>

# Architecture

For this project I used a hexagonal design pattern, so that in the future I can feed this application with
with extended API. As this standard it is Ports and Adapters, protecting the business side more and how the ports and adapters
I have more control of the application and it is more organized!!

# Description

The program receives lists, one per line, of stock financial market operations in format
json via standard input (stdin). Each operation in this list contains the following fields:
```
┏━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ Name       ┃ Meaning                                                          ┃
┡━━━━━━━━━━━━╇━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┩
┃ operation  ┃ Whether the operation is a buy or sell operation                 ┃
┃ unit-cost  ┃ Unit price of the share in a currency with two decimal places    ┃
┃ quantity   ┃ Number of shares traded                                          ┃
┡━━━━━━━━━━━━╇━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┩
```
This is an example of the input:
```JSON
[ {"operation":"buy", "unit-cost":10.00, "quantity": 10000},
   {"operation":"sell", "unit-cost":20.00, "quantity": 5000}]
[ {"operation":"buy", "unit-cost":20.00, "quantity": 10000},
   {"operation":"sell", "unit-cost":10.00, "quantity": 5000}]


```

The operations will be in the order in which they occurred, that is, the second operation in the list occurred
after the first and so on.
Each line is an independent simulation, your program should not maintain the state obtained in one line
for the others.
The last line of the input will be an empty line.

For each line of input, the program must return a list containing the tax paid for each
operation received. The elements of this list must be encoded in json format and the output must be
returned via standard output (stdout). The return consists of the following field:
```
┏━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
┃ Name       ┃ Meaning                                  ┃
┡━━━━━━━━━━━━╇━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┩
┃ tax        ┃ The amount of tax paid on a transaction  ┃
┡━━━━━━━━━━━━╇━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┩
```
This is an example of the output:
```JSON
[{"tax":0.00}, {"tax":10000.00}]
[{"tax":0.00}, {"tax":0.00}]
```

The list returned by the program must be the same size as the list of operations processed in the input.
For example, if three operations were processed (buy, buy, sell), the program's return should be a list
with three values that represent the tax paid on each operation.

# Rules

The program must deal with two types of operations (buy and sell) and it must follow the following rules:
- The percentage of tax paid is 20% of the profit obtained from the operation. In other words, the tax will be paid when there is a sales transaction whose price is higher than the weighted average purchase price.
- To determine whether the transaction resulted in profit or loss, you can calculate the weighted average price, so when you buy shares you must recalculate the weighted average price using this formula: new-weighted-average = ((amount-of-shares) current * current-weighted-average) + (quantity-of-shares * purchase-value)) / (current-quantity-of-shares + quantity-of-shares-purchased) . For example, if you bought 10 shares for R$20.00, sold 5, then bought another 5 for R$10.00, the weighted average is ((5 x 20.00) + (5 x 10.00)) / (5 + 5) = 15.00 .
- You must use past losses to deduct multiple future profits, until all losses are deducted.
- Losses happen when you sell shares at a value lower than the weighted average purchase price. In this case, no tax must be paid and you must subtract the loss from the following profits before calculating the tax.
- You do not pay any tax if the total value of the operation (unit cost of the action x quantity) is less than or equal to $20,000.00. Use the total value of the transaction and not the profit made to determine whether or not tax should be paid. And don't forget to deduct the loss from your subsequent profits.
- No taxes are paid on purchase transactions.

You can assume that no trade will sell more shares than you currently have.

# Example

When the application receives two lines, they must be handled as two independent simulations. O
program should not carry the state obtained from processing the first input to the other
executions.

input:
```JSON
[[{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
  {"operation":"sell", "unit-cost":2.00, "quantity": 5000},
  {"operation":"sell", "unit-cost":20.00, "quantity": 2000},
  {"operation":"sell", "unit-cost":20.00, "quantity": 2000},
  {"operation":"sell", "unit-cost":25.00, "quantity": 1000},
  {"operation":"buy", "unit-cost":20.00, "quantity": 10000},
  {"operation":"sell", "unit-cost":15.00, "quantity": 5000},
  {"operation":"sell", "unit-cost":30.00, "quantity": 4350},
  {"operation":"sell", "unit-cost":30.00, "quantity": 650}]
```
Output:
```JSON
[{"tax":0.00},{"tax":0.00},{"tax":0.00},{"tax":0.00},{"tax":3000.00},{"tax":0.00},{"tax":0.00},{"tax":3700.00},{"tax":0.00}]
```

# Libraries

- To load the information input I used java.util.Scanner.
- To convert Json to Object I used jackson.
- For Spock Framework Tests.



# Running

- Running in the IDE:
- Go to the cli folder and run main from the CapitalGainsCommand.java file


# Considerations

- I believe it could be possible to improve testing with Spock Framework, as I'm not using Junit to test other types
   the framework.
- I did not evaluate performance with a larger volume of data.
- I would like to deliver in a functional paradigm, but I don't have mastery of it yet.
- I am completely open to suggestions for improvements or replacement/use of patterns.
- Thank you for being able to participate in this process!

<br>

<h3>Author</h3>

<a href="https://www.linkedin.com/in/danielpereiralima/">
 <img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/96916005?v=4" width="100px;" alt=""/>

Made by Daniel Pereira Lima 👋🏽 Contact!

[![Linkedin Badge](https://img.shields.io/badge/-Daniel-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/danielpereiralima/)](https://www.linkedin.com/in/danielpereiralima/)
