package com.adyen.android.assignment.snapshots

import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import com.adyen.android.assignment.getMetadata
import com.adyen.android.assignment.utils.MainDispatcherRule
import com.airbnb.android.showkase.models.Showkase
import com.airbnb.android.showkase.models.ShowkaseBrowserComponent
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class FullscreenSnapshots {

    @get:Rule
    val paparazzi: Paparazzi = Paparazzi(
        maxPercentDifference = 0.1,
    )

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun take(
        @TestParameter(valuesProvider = ShowkaseComponentProvider::class)
        showkaseComponent: ShowkaseComponent,
    ) {
        paparazzi.snapshot {
            showkaseComponent.content()
        }
    }
}

/**
 * A helper class primarily to use the component key as the parameterized test name which uses `toString()`.
 */
class ShowkaseComponent(private val showkaseBrowserComponent: ShowkaseBrowserComponent) {
    val content: @Composable () -> Unit = showkaseBrowserComponent.component
    override fun toString(): String =
        "${showkaseBrowserComponent.componentName}_${showkaseBrowserComponent.styleName}"
}

object ShowkaseComponentProvider : TestParameter.TestParameterValuesProvider {
    override fun provideValues(): List<ShowkaseComponent> =
        Showkase.getMetadata().componentList.map(::ShowkaseComponent)
}

