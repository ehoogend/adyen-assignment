package com.adyen.android.assignment

import com.adyen.android.assignment.money.Bill
import com.adyen.android.assignment.money.Change
import com.adyen.android.assignment.money.Coin
import org.junit.Assert.fail
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CashRegisterTest {

    @Test
    fun `performTransaction negative price`() {
        // Check if performTransaction throws TransactionException when price is negative
        val cashRegister = CashRegister(Change.none())
        val price = -100L
        assertFailsWith<CashRegister.TransactionException> {
            cashRegister.performTransaction(
                price,
                Change.none()
            )
        }
    }

    @Test
    fun `performTransaction negative amountPaid`() {
        // Check if performTransaction throws TransactionException when amountPaid is negative
        val cashRegister = CashRegister(Change.none())
        val price = 100L
        assertFailsWith<CashRegister.TransactionException> {
            cashRegister.performTransaction(
                price,
                Change.none()
            )
        }
    }

    @Test
    fun `performTransaction insufficient amountPaid`() {
        // Check if performTransaction throws TransactionException when amountPaid is less than price
        val cashRegister = CashRegister(Change.none())
        val price = 100_00L
        assertFailsWith<CashRegister.TransactionException> {
            cashRegister.performTransaction(
                price,
                Change().add(Bill.TEN_EURO, 1)
            )
        }
    }

    @Test
    fun `performTransaction insufficient change`() {
        // Check if performTransaction throws TransactionException when insufficient change is available.
        val cashRegister = CashRegister(Change.none())
        val price = 100_00L
        assertFailsWith<CashRegister.TransactionException> {
            cashRegister.performTransaction(
                price,
                Change().add(Bill.TWO_HUNDRED_EURO, 1)
            )
        }
    }

    @Test
    fun `performTransaction exact amountPaid`() {
        // Check if performTransaction returns Change.none() when amountPaid is equal to price
        val cashRegister = CashRegister(Change.none())
        val price = 100_00L
        assertEquals(
            Change.none(),
            cashRegister.performTransaction(
                price,
                Change().add(Bill.ONE_HUNDRED_EURO, 1)
            )
        )
    }

    @Test
    fun `performTransaction more than enough amountPaid`() {
        // Check if performTransaction returns the correct change when amountPaid is greater than price
        val cashRegister = CashRegister(Change().add(Bill.TEN_EURO, 2))
        val price = 10_00L
        val amountPaid = Change().add(Bill.TWENTY_EURO, 1)
        val expectedChange = Change().add(Bill.TEN_EURO, 1)
        assertEquals(
            expectedChange,
            cashRegister.performTransaction(price, amountPaid)
        )
    }

    @Test
    fun `performTransaction large price and amountPaid`() {
        // Check if performTransaction handles large price and amountPaid values correctly without overflow
        val cashRegister =
            CashRegister(Change().add(Bill.FIVE_HUNDRED_EURO, 10).add(Coin.ONE_EURO, 10))
        val price = 4999_00L
        val amountPaid = Change().add(Bill.FIVE_HUNDRED_EURO, 10)
        val expectedChange = Change().add(Coin.ONE_EURO, 1)
        assertEquals(
            expectedChange,
            cashRegister.performTransaction(price, amountPaid)
        )
    }

    @Test
    fun `performTransaction Insufficient small denominations`() {
        // Check if the performTransaction throws TransactionException if sufficient but incorrect (too large) denominations of change are available
        val cashRegister = CashRegister(Change().add(Bill.FIVE_HUNDRED_EURO, 10))
        val price = 4999_00L
        val amountPaid = Change().add(Bill.FIVE_HUNDRED_EURO, 10)
        assertFailsWith<CashRegister.TransactionException> {
            cashRegister.performTransaction(price, amountPaid)
        }
    }

    @Test
    fun `performTransaction various denominations`() {
        // Check if performTransaction returns the correct change with various denominations of MonetaryElement
        val cashRegister = CashRegister(
            Change()
                .add(Bill.FIFTY_EURO, 2)
                .add(Bill.TWENTY_EURO, 5)
                .add(Bill.TEN_EURO, 1)
                .add(Coin.TWO_EURO, 2)
                .add(Coin.FIFTY_CENT, 10)
        )
        val price = 65_50L
        val amountPaid = Change().add(Bill.ONE_HUNDRED_EURO, 1)
        val expectedChange = Change()
            .add(Bill.TWENTY_EURO, 1)
            .add(Bill.TEN_EURO, 1)
            .add(Coin.TWO_EURO, 2)
            .add(Coin.FIFTY_CENT, 1)
        assertEquals(
            expectedChange,
            cashRegister.performTransaction(price, amountPaid)
        )
    }

    @Test
    fun `performTransaction empty amountPaid`() {
        // Check if performTransaction handles the case when amountPaid is empty
        val cashRegister = CashRegister(Change.none())
        val price = 100L
        assertFailsWith<CashRegister.TransactionException> {
            cashRegister.performTransaction(
                price,
                Change.none()
            )
        }
    }

    @Test
    fun `performTransaction multiple transactions`() {
        // Check if performTransaction handles multiple consecutive transactions correctly
        val cashRegister = CashRegister(
            Change()
                .add(Bill.TEN_EURO, 10)
                .add(Bill.FIVE_EURO, 10)
                .add(Coin.TWENTY_CENT, 10)
                .add(Coin.FIVE_CENT, 50)
        )

        // First transaction
        val price1 = 5_00L
        val amountPaid1 = Change().add(Bill.TEN_EURO, 1)
        val expectedChange1 = Change().add(Bill.FIVE_EURO, 1)
        assertEquals(
            expectedChange1,
            cashRegister.performTransaction(price1, amountPaid1)
        )

        // Second transaction
        val price2 = 12_00L
        val amountPaid2 = Change().add(Bill.TWENTY_EURO, 1)
        val expectedChange2 = Change()
            .add(Bill.FIVE_EURO, 1)
            .add(Coin.TWENTY_CENT, 10)
            .add(Coin.FIVE_CENT, 20)
        assertEquals(
            expectedChange2,
            cashRegister.performTransaction(price2, amountPaid2)
        )
    }

    @Test
    fun `performTransaction change becomes depleted`() {
        // Check if performTransaction throws an exception when the cash register runs out of change
        val cashRegister = CashRegister(Change().add(Coin.ONE_EURO, 1))
        val price = 400L
        val amountPaid = Change().add(Bill.FIVE_EURO, 1)

        val expectedChange = Change().add(Coin.ONE_EURO, 1)
        // First transaction should succeed
        assertEquals(
            expectedChange,
            cashRegister.performTransaction(price, amountPaid)
        )

        // Second transaction with same amount should fail due to insufficient change
        assertFailsWith<CashRegister.TransactionException> {
            cashRegister.performTransaction(price, amountPaid)
        }
    }

    @Test
    fun `performTransaction returns the minimal amount of coins`() {
        // Check if performTransaction returns the minimal amount of coins / bills possible
        val cashRegister = CashRegister(
            Change().add(Bill.FIFTY_EURO, 100).add(Bill.ONE_HUNDRED_EURO, 1).add(Bill.TEN_EURO, 100)
        )
        val price = 400_00L
        val amountPaid = Change().add(Bill.ONE_HUNDRED_EURO, 5)

        val expectedChange = Change().add(Bill.ONE_HUNDRED_EURO, 1)

        assertEquals(
            expectedChange,
            cashRegister.performTransaction(price, amountPaid)
        )
    }
}
