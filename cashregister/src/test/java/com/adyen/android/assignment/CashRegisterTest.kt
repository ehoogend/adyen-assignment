package com.adyen.android.assignment

import com.adyen.android.assignment.money.Bill
import com.adyen.android.assignment.money.Change
import com.adyen.android.assignment.money.Coin
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

    @Test
    fun `performTransaction large amount of change`() {
        // Check if performTransaction handles large amounts of change well
        val cashRegister = CashRegister(
            Change().apply {
                Bill.entries.forEach { add(it, 99999999) }
                Coin.entries.forEach { add(it, 99999999) }
            }
        )
        val price = 400_000_00L
        val amountPaid = Change().apply {
            Bill.entries.forEach { add(it, 99999999) }
            Coin.entries.forEach { add(it, 99999999) }
        }

        val expectedChange = Change().apply {
            add(Bill.FIVE_HUNDRED_EURO, 49451)
            add(Bill.TWO_HUNDRED_EURO, 1)
            add(Bill.FIFTY_EURO, 1)
            add(Bill.FIVE_EURO, 1)
            add(Coin.ONE_EURO, 1)
            add(Coin.FIFTY_CENT, 1)
            add(Coin.TWENTY_CENT, 1)
            add(Coin.TEN_CENT, 1)
            add(Coin.FIVE_CENT, 1)
            add(Coin.TWO_CENT, 1)
            add(Coin.ONE_CENT, 1)
        }
        assertEquals(
            expectedChange,
            cashRegister.performTransaction(price, amountPaid)
        )
    }

    @Test
    fun `edge case 1 - using lower denominations only`() {
        val initialChange = Change()
        initialChange.add(Bill.FIVE_EURO, 1)
        initialChange.add(Coin.TWO_EURO, 3)
        val cashRegister = CashRegister(initialChange)

        val paidChange = Change().add(Bill.TEN_EURO, 1)

        // Paid 10, costs 4, return 6
        val returnedChange = cashRegister.performTransaction(4_00L, paidChange)

        assert(returnedChange.total == 6_00L)
    }

    @Test
    fun `edge case 2 - using higher and lower denominations`() {
        val initialChange = Change()
        initialChange.add(Bill.FIVE_EURO, 4)
        initialChange.add(Coin.TWO_EURO, 3)
        val cashRegister = CashRegister(initialChange)

        val paidChange = Change().add(Bill.TEN_EURO, 2)

        // Paid 20, costs 4, return 16
        val returnedChange = cashRegister.performTransaction(4_00L, paidChange)

        assert(returnedChange.total == 16_00L)
    }

    @Test
    fun `edge case 3 - using lower denominations only`() {
        val initialChange = Change()
        initialChange.add(Bill.TWO_HUNDRED_EURO, 3)
        val cashRegister = CashRegister(initialChange)

        val paidChange = Change().add(Bill.FIVE_HUNDRED_EURO, 4)

        // Paid 2000, costs 1400, return 600
        val returnedChange = cashRegister.performTransaction(1400_00L, paidChange)

        assertEquals(600_00L, returnedChange.total)
        assertEquals(setOf(Bill.TWO_HUNDRED_EURO), returnedChange.getElements())
        assertEquals(3, returnedChange.getCount(Bill.TWO_HUNDRED_EURO))
    }

    @Test
    fun `edge case 4 - returning change paid by customer`() {
        val registerChange = Change()

        val cashRegister = CashRegister(registerChange)

        val paidChange = Change().apply {
            add(Coin.TWO_EURO, 3)
        }

        println(cashRegister.performTransaction(4_00L, paidChange))
    }
}
