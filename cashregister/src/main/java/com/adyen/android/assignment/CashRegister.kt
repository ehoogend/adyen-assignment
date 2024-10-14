package com.adyen.android.assignment

import com.adyen.android.assignment.money.Change
import kotlin.math.min

/**
 * The CashRegister class holds the logic for performing transactions.
 *
 * @param change The change that the CashRegister is holding.
 */
class CashRegister(private val change: Change) {
    /**
     * Performs a transaction for a product/products with a certain price and a given amount.
     *
     * @param price The price of the product(s).
     * @param amountPaid The amount paid by the shopper.
     *
     * @return The change for the transaction.
     *
     * @throws TransactionException If the transaction cannot be performed.
     */
    fun performTransaction(price: Long, amountPaid: Change): Change {
        return when {
            price < 0 -> throw TransactionException("Price cannot be negative")
            price > amountPaid.total -> throw TransactionException("Insufficient amount paid")
            change.total < amountPaid.total - price -> throw TransactionException("Insufficient change available")
            price == amountPaid.total -> {
                amountPaid.getElements().forEach {
                    change.add(it, amountPaid.getCount(it))
                }
                Change.none()
            }

            else -> {
                val changeToReturn = getChange(price, amountPaid)
                amountPaid.getElements().forEach {
                    change.add(it, amountPaid.getCount(it))
                }
                changeToReturn.getElements().forEach {
                    change.remove(it, changeToReturn.getCount(it))
                }
                changeToReturn
            }
        }
    }

    /**
     * Calculates the change for a given price and amount paid.
     *
     * @param price The price of the product(s).
     * @param amountPaid The amount paid by the shopper.
     * @return The change for the transaction.
     */
    private fun getChange(price: Long, amountPaid: Change): Change {
        val availableChange = change + amountPaid
        var remainingChangeToReturn = amountPaid.total - price
        val changeToReturn = Change.none()

        availableChange.getElements()
            .sortedByDescending { it.minorValue }
            .forEach { element ->
                if (remainingChangeToReturn >= element.minorValue) {
                    val numElements = min(
                        availableChange.getCount(element).toLong(),
                        (remainingChangeToReturn / element.minorValue)
                    )
                    if (numElements > 0) {
                        changeToReturn.add(element, numElements.toInt())
                        availableChange.remove(element, numElements.toInt())
                        remainingChangeToReturn -= element.minorValue * numElements
                    }
                }
            }
        if (remainingChangeToReturn > 0) {
            throw TransactionException("Insufficient correct denominations of change available. Short by: $remainingChangeToReturn")
        }
        return changeToReturn
    }

    class TransactionException(message: String, cause: Throwable? = null) :
        Exception(message, cause)
}
