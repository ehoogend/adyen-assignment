package com.adyen.android.assignment

import com.adyen.android.assignment.money.Change
import com.adyen.android.assignment.money.MonetaryElement
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

        while (remainingChangeToReturn > 0) {
            val elementToTake =
                availableChange.firstElementSmallerOrEqualTo(remainingChangeToReturn)
            val maxNumElementsToTake = remainingChangeToReturn / elementToTake.minorValue
            val numElementsToTake =
                min(maxNumElementsToTake.toInt(), availableChange.getCount(elementToTake))

            changeToReturn.add(elementToTake, numElementsToTake)
            availableChange.remove(elementToTake, numElementsToTake)
            remainingChangeToReturn -= elementToTake.minorValue * numElementsToTake
        }
        return changeToReturn
    }

    /**
     * Returns the first [MonetaryElement] from the [Change] whose minor value is less than or equal
     * to the given value.
     *
     * @param value The value to compare against.
     * @return The first [MonetaryElement] that meets the criteria.
     * @throws TransactionException If no such element is found.
     */
    private fun Change.firstElementSmallerOrEqualTo(value: Long): MonetaryElement =
        getElements()
            .sortedByDescending { it.minorValue }
            .firstOrNull { it.minorValue <= value }
            ?: throw TransactionException("Insufficient small denominations of change available")

    class TransactionException(message: String, cause: Throwable? = null) :
        Exception(message, cause)
}
